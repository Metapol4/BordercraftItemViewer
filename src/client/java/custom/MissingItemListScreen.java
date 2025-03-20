package custom;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.ClientStatusC2SPacket;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.stat.StatHandler;
import net.minecraft.stat.StatType;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.*;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Stream;


public class MissingItemListScreen extends Screen {
    public MissingItemListScreen(Text title) {
        super(title);
    }

    private final static Logger LOGGER =
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private final Set<TagKey<Item>> searchResultTags = new HashSet();

    Set<Item> excludeList = Sets.newIdentityHashSet();
    List<ItemViewerWidget> itemViewerWidgets;
    List<Item> searchResultsItemStacks;
    private int ItemCount = 0;
    private TextFieldWidget searchField;
    private boolean searching;

    @Override
    protected void init() {
        ButtonWidget buttonWidget = ButtonWidget.builder(Text.of("Close"), (btn) -> {
            // When the button is clicked, we can display a toast to the screen.
            MinecraftClient.getInstance().setScreen(null);
        }).dimensions(width - 120, height - 20, 120, 20).build();
        // x, y, width, height
        // It's recommended to use the fixed height of 20 to prevent rendering issues with the button
        // textures.

        // Register the button widget.
        this.addDrawableChild(buttonWidget);
        this.client.getNetworkHandler().sendPacket(new ClientStatusC2SPacket(ClientStatusC2SPacket.Mode.REQUEST_STATS));

        FillExcludeList();

        List<Item> items = MakeItemsList();

        ItemCount = items.size();
        // RenderItems();
        CreateItemWidgets();

        Objects.requireNonNull(this.client.textRenderer);
        TextRenderer txtRender = this.client.textRenderer;
        String string = this.searchField != null ? this.searchField.getText() : "";
        Text text = Text.of("Search...");

        this.searchField = new TextFieldWidget(txtRender, width - 120, 20, 81, 14, Text.translatable("itemGroup.search"));
        this.searchField.setMaxLength(50);
        this.searchField.setVisible(true);
        this.searchField.setEditableColor(16777215);
        this.searchField.setText(string);
        this.searchField.setPlaceholder(text);
    }

    private void RenderItems(/*DrawContext context*/) {
        EmptyItemWidgets();
        FillExcludeList();
        List<Item> items = MakeItemsList();
        int size = 16;
        for (int i = 0; i < width / size; i++) {
            for (int j = 0; j < height / size; j++) {
                int index = i * (height / size) + j;
                if (index >= items.size() || index >= itemViewerWidgets.size())
                    break;
                Item item = items.get(index);
                if (excludeList.contains(item)) {
                    continue;
                }
                ItemViewerWidget itemViewerWidget = itemViewerWidgets.get(index);
                itemViewerWidget.SetItem(item);
                // itemViewerWidget
                //ItemViewerWidget itemViewerWidget = new ItemViewerWidget(i * size, j * size, size, size, Text.empty(), item);
            }
        }
    }

    private void EmptyItemWidgets() {
        for (ItemViewerWidget itemViewerWidget : itemViewerWidgets) {
            itemViewerWidget.SetItem(null);
        }
    }

    private void CreateItemWidgets(/*DrawContext context*/) {
        List<Item> items = MakeItemsList();
        itemViewerWidgets = Lists.newArrayListWithCapacity(items.size());
        int size = 16;
        for (int i = 0; i < width / size; i++) {
            for (int j = 0; j < height / size; j++) {
                int index = i * (height / size) + j;
                if (items.size() <= index)
                    break;
                Item item = items.get(index);
                if (excludeList.contains(item)) {
                    continue;
                }
                ItemViewerWidget itemViewer = new ItemViewerWidget(i * size, j * size, size, size, Text.empty(), null);
                itemViewerWidgets.add(itemViewer);
                this.addDrawableChild(itemViewer);
            }
        }
    }

    private void FillExcludeList() {
        if(excludeList != null)
            excludeList.clear();
        excludeList.add(Items.AIR);
        excludeList.add(Items.BEDROCK);
        excludeList.add(Items.BUDDING_AMETHYST);
        excludeList.add(Items.CHORUS_PLANT);
        excludeList.add(Items.END_PORTAL_FRAME);
        excludeList.add(Items.FARMLAND);
        excludeList.add(Items.FROGSPAWN);
        excludeList.add(Items.INFESTED_STONE);
        excludeList.add(Items.INFESTED_COBBLESTONE);
        excludeList.add(Items.INFESTED_DEEPSLATE);
        excludeList.add(Items.INFESTED_CHISELED_STONE_BRICKS);
        excludeList.add(Items.INFESTED_STONE_BRICKS);
        excludeList.add(Items.INFESTED_MOSSY_STONE_BRICKS);
        excludeList.add(Items.INFESTED_CRACKED_STONE_BRICKS);
        excludeList.add(Items.SPAWNER);
        excludeList.add(Items.TRIAL_SPAWNER);
        excludeList.add(Items.BARRIER);
        excludeList.add(Items.COMMAND_BLOCK);
        excludeList.add(Items.JIGSAW);
        excludeList.add(Items.LIGHT);
        excludeList.add(Items.COMMAND_BLOCK_MINECART);
        excludeList.add(Items.PETRIFIED_OAK_SLAB);
        excludeList.add(Items.PLAYER_HEAD);
        excludeList.add(Items.STRUCTURE_BLOCK);
        excludeList.add(Items.STRUCTURE_VOID);
        excludeList.add(Items.DEBUG_STICK);
        excludeList.add(Items.VAULT);
        excludeList.add(Items.REINFORCED_DEEPSLATE);
        excludeList.add(Items.KNOWLEDGE_BOOK);


        excludeList.add(Items.ARMADILLO_SPAWN_EGG);
        excludeList.add(Items.ALLAY_SPAWN_EGG);
        excludeList.add(Items.AXOLOTL_SPAWN_EGG);
        excludeList.add(Items.BAT_SPAWN_EGG);
        excludeList.add(Items.BEE_SPAWN_EGG);
        excludeList.add(Items.BLAZE_SPAWN_EGG);
        excludeList.add(Items.BOGGED_SPAWN_EGG);
        excludeList.add(Items.BREEZE_SPAWN_EGG);
        excludeList.add(Items.CAT_SPAWN_EGG);
        excludeList.add(Items.CAMEL_SPAWN_EGG);
        excludeList.add(Items.CAVE_SPIDER_SPAWN_EGG);
        excludeList.add(Items.CHICKEN_SPAWN_EGG);
        excludeList.add(Items.COD_SPAWN_EGG);
        excludeList.add(Items.COW_SPAWN_EGG);
        excludeList.add(Items.CREEPER_SPAWN_EGG);
        excludeList.add(Items.DOLPHIN_SPAWN_EGG);
        excludeList.add(Items.DONKEY_SPAWN_EGG);
        excludeList.add(Items.DROWNED_SPAWN_EGG);
        excludeList.add(Items.ELDER_GUARDIAN_SPAWN_EGG);
        excludeList.add(Items.ENDER_DRAGON_SPAWN_EGG);
        excludeList.add(Items.ENDERMAN_SPAWN_EGG);
        excludeList.add(Items.ENDERMITE_SPAWN_EGG);
        excludeList.add(Items.EVOKER_SPAWN_EGG);
        excludeList.add(Items.FOX_SPAWN_EGG);
        excludeList.add(Items.FROG_SPAWN_EGG);
        excludeList.add(Items.GHAST_SPAWN_EGG);
        excludeList.add(Items.GLOW_SQUID_SPAWN_EGG);
        excludeList.add(Items.GOAT_SPAWN_EGG);
        excludeList.add(Items.GUARDIAN_SPAWN_EGG);
        excludeList.add(Items.HOGLIN_SPAWN_EGG);
        excludeList.add(Items.HORSE_SPAWN_EGG);
        excludeList.add(Items.HUSK_SPAWN_EGG);
        excludeList.add(Items.IRON_GOLEM_SPAWN_EGG);
        excludeList.add(Items.MAGMA_CUBE_SPAWN_EGG);
        excludeList.add(Items.LLAMA_SPAWN_EGG);
        excludeList.add(Items.MOOSHROOM_SPAWN_EGG);
        excludeList.add(Items.MULE_SPAWN_EGG);
        excludeList.add(Items.OCELOT_SPAWN_EGG);
        excludeList.add(Items.PANDA_SPAWN_EGG);
        excludeList.add(Items.PARROT_SPAWN_EGG);
        excludeList.add(Items.PHANTOM_SPAWN_EGG);
        excludeList.add(Items.PIG_SPAWN_EGG);
        excludeList.add(Items.PIGLIN_SPAWN_EGG);
        excludeList.add(Items.PIGLIN_BRUTE_SPAWN_EGG);
        excludeList.add(Items.PILLAGER_SPAWN_EGG);
        excludeList.add(Items.POLAR_BEAR_SPAWN_EGG);
        excludeList.add(Items.PUFFERFISH_SPAWN_EGG);
        excludeList.add(Items.RABBIT_SPAWN_EGG);
        excludeList.add(Items.RAVAGER_SPAWN_EGG);
        excludeList.add(Items.SALMON_SPAWN_EGG);
        excludeList.add(Items.SHEEP_SPAWN_EGG);
        excludeList.add(Items.SHULKER_SPAWN_EGG);
        excludeList.add(Items.SILVERFISH_SPAWN_EGG);
        excludeList.add(Items.SKELETON_SPAWN_EGG);
        excludeList.add(Items.SKELETON_HORSE_SPAWN_EGG);
        excludeList.add(Items.SLIME_SPAWN_EGG);
        excludeList.add(Items.SNIFFER_SPAWN_EGG);
        excludeList.add(Items.SNOW_GOLEM_SPAWN_EGG);
        excludeList.add(Items.SPIDER_SPAWN_EGG);
        excludeList.add(Items.SQUID_SPAWN_EGG);
        excludeList.add(Items.STRAY_SPAWN_EGG);
        excludeList.add(Items.STRIDER_SPAWN_EGG);
        excludeList.add(Items.TADPOLE_SPAWN_EGG);
        excludeList.add(Items.TRADER_LLAMA_SPAWN_EGG);
        excludeList.add(Items.TROPICAL_FISH_SPAWN_EGG);
        excludeList.add(Items.TURTLE_SPAWN_EGG);
        excludeList.add(Items.VEX_SPAWN_EGG);
        excludeList.add(Items.VILLAGER_SPAWN_EGG);
        excludeList.add(Items.VINDICATOR_SPAWN_EGG);
        excludeList.add(Items.WANDERING_TRADER_SPAWN_EGG);
        excludeList.add(Items.WARDEN_SPAWN_EGG);
        excludeList.add(Items.WITCH_SPAWN_EGG);
        excludeList.add(Items.WITHER_SPAWN_EGG);
        excludeList.add(Items.WITHER_SKELETON_SPAWN_EGG);
        excludeList.add(Items.WOLF_SPAWN_EGG);
        excludeList.add(Items.ZOGLIN_SPAWN_EGG);
        excludeList.add(Items.CREAKING_SPAWN_EGG);
        excludeList.add(Items.ZOMBIE_SPAWN_EGG);
        excludeList.add(Items.ZOMBIE_HORSE_SPAWN_EGG);
        excludeList.add(Items.ZOMBIE_VILLAGER_SPAWN_EGG);
        excludeList.add(Items.ZOMBIFIED_PIGLIN_SPAWN_EGG);

        StatHandler statHandler;
        statHandler = this.client.player.getStatHandler(); //MinecraftClient.getInstance().player.getStatHandler();
        List<StatType<Item>> itemStatTypes;
        itemStatTypes = Lists.newArrayList(new StatType[]{Stats.BROKEN, Stats.CRAFTED, Stats.USED, Stats.PICKED_UP, Stats.DROPPED});
        for (Item item : Registries.ITEM) {
            boolean bl = false;
            for (StatType<Item> statType : itemStatTypes) {
                if (statType.hasStat(item) && statHandler.getStat(statType.getOrCreateStat(item)) > 0) {
                    bl = true;
                }
            }

            if (bl) {
                excludeList.add(item);
            }
        }
    }

    private List<Item> MakeItemsList() {
        List<Item> items = new java.util.ArrayList<>(Registries.ITEM.stream().toList());
        items.removeAll(excludeList);
        if (searchResultsItemStacks != null)
            items.removeIf((resultCollection) -> !searchResultsItemStacks.contains(resultCollection));
        return items;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        String text = "Items Remaining: " + ItemCount;
        context.drawText(this.textRenderer, text, width - 120, 0, 0xFFFFFFFF, true);
        searchField.render(context, mouseX, mouseY, delta);
        RenderItems();
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 27)
            return super.keyPressed(keyCode, scanCode, modifiers);
        this.searching = false;
        if (this.searchField.keyPressed(keyCode, scanCode, modifiers)) {
            this.refreshSearchResults();
            return true;
        } /*else if (this.searchField.isFocused() && this.searchField.isVisible()) {
            return true;
        }*/ else if (this.client.options.chatKey.matchesKey(keyCode, scanCode) && !this.searchField.isFocused()) {
            this.searching = true;
            this.searchField.setFocused(true);
            return true;
        } else {
            return super.keyPressed(keyCode, scanCode, modifiers);
        }
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        this.searching = false;
        return super.keyReleased(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
        if (this.searching) {
            return false;
        }
        if (this.searchField.charTyped(chr, modifiers)) {
            this.refreshSearchResults();
            return true;
        } else {
            return super.charTyped(chr, modifiers);
        }
    }

    private void refreshSearchResults() {
        if (searchResultsItemStacks == null) {
            searchResultsItemStacks = new ArrayList<>();
        }

        searchResultsItemStacks.clear();
        String string = this.searchField.getText().toLowerCase(Locale.ROOT);

        List<Item> allItems = new java.util.ArrayList<>(Registries.ITEM.stream().toList());
        for (Item item : allItems) {
            if (item.toString().contains(string))
                searchResultsItemStacks.add(item);
        }
        LOGGER.info("Found item count: " + String.valueOf(searchResultsItemStacks.size()));
        LOGGER.info("with search: " + string);
    }


    private void searchForTags(String id) {
        int i = id.indexOf(58);
        Predicate<Identifier> predicate;
        if (i == -1) {
            predicate = (idx) -> idx.getPath().contains(id);
        } else {
            String string = id.substring(0, i).trim();
            String string2 = id.substring(i + 1).trim();
            predicate = (idx) -> idx.getNamespace().contains(string) && idx.getPath().contains(string2);
        }

        Stream var10000 = Registries.ITEM.streamTags().map(RegistryEntryList.Named::getTag).filter((tag) -> predicate.test(tag.id()));
        Set var10001 = this.searchResultTags;
        Objects.requireNonNull(var10001);
        var10000.forEach(var10001::add);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.searchField != null) {
            if (this.searchField.mouseClicked(mouseX, mouseY, button)) {
                this.searchField.setFocused(true);
                return true;
            }

            this.searchField.setFocused(false);
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }
}
