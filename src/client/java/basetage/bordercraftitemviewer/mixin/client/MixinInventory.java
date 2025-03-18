package basetage.bordercraftitemviewer.mixin.client;

import basetage.bordercraftitemviewer.BordercraftItemViewer;
import custom.MissingItemListScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.StatsScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.screen.ingame.RecipeBookScreen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InventoryScreen.class)
public abstract class MixinInventory extends RecipeBookScreen<PlayerScreenHandler> {
    private static final Identifier EXAMPLE_LAYER = Identifier.of(BordercraftItemViewer.MOD_ID, "hud-example-layer");

    public MixinInventory(PlayerScreenHandler handler, RecipeBookWidget<?> recipeBook, PlayerInventory inventory, Text title) {
        super(handler, recipeBook, inventory, title);
    }


    @Inject(method = "init", at = @At("RETURN"))
    public void onInit(CallbackInfo ci)
    {
        ButtonWidget buttonWidget = ButtonWidget.builder(Text.of("Open Missing Items List"), (btn) -> {
            MinecraftClient.getInstance().setScreen(
                    new MissingItemListScreen(Text.empty())
            );
        }).dimensions(0, 0, 120, 20).build();
        // x, y, width, height
        // It's recommended to use the fixed height of 20 to prevent rendering issues with the button
        // textures.

        // Register the button widget.
        this.addDrawableChild(buttonWidget);
    }
    @Inject(method = "render", at = @At("RETURN"))
    public void onRender(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci)
    {

    }

}
