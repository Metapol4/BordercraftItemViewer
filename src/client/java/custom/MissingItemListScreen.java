package custom;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.StatsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.stat.StatHandler;
import net.minecraft.stat.StatType;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;

import java.util.List;
import java.util.Set;
import java.util.logging.Logger;


public class MissingItemListScreen extends Screen {
    public MissingItemListScreen(Text title) {
        super(title);
    }

    private final static Logger LOGGER =
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    @Override
    protected void init() {
        ButtonWidget buttonWidget = ButtonWidget.builder(Text.of("Close"), (btn) -> {
            // When the button is clicked, we can display a toast to the screen.
            MinecraftClient.getInstance().setScreen(null);
        }).dimensions(40, 40, 120, 20).build();
        // x, y, width, height
        // It's recommended to use the fixed height of 20 to prevent rendering issues with the button
        // textures.

        // Register the button widget.
        this.addDrawableChild(buttonWidget);

        Set<Item> excludeList = Sets.newIdentityHashSet();
        StatHandler statHandler;
        statHandler = MinecraftClient.getInstance().player.getStatHandler();
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

        List<Item> items = new java.util.ArrayList<>(Registries.ITEM.stream().toList());

        items.removeAll(excludeList);
        LOGGER.info("items: " + String.valueOf(items.size()));
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

                ItemViewerWidget itemViewerWidget = new ItemViewerWidget(i * size, j * size, size, size, Text.empty(), item);
                this.addDrawableChild(itemViewerWidget);
                LOGGER.info("drew: " + item.toString());
                LOGGER.info("id: " + index);
            }
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        // Minecraft doesn't have a "label" widget, so we'll have to draw our own text.
        // We'll subtract the font height from the Y position to make the text appear above the button.
        // Subtracting an extra 10 pixels will give the text some padding.
        // textRenderer, text, x, y, color, hasShadow
        context.drawText(this.textRenderer, "Special Button", 40, 40 - this.textRenderer.fontHeight - 10, 0xFFFFFFFF, true);
    }
}
