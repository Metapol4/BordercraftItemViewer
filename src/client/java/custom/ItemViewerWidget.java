package custom;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.StatsScreen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.Item;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ItemViewerWidget extends ClickableWidget {
    public ItemViewerWidget(int x, int y, int width, int height, Text message, Item item) {
        super(x, y, width, height, message);
        this.item = item;

    }

    private Item item;

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        /*Identifier texture = Identifier.of("minecraft", "container/slot");
        context.drawGuiTexture(RenderLayer::getGuiTextured, texture, getX(), getY(), 18, 18);
        context.drawItemWithoutEntity(this.item.getDefaultStack(), getX() + 1, getY() + 1);
        if (isHovered()) {
            TextRenderer textRenderer;
            textRenderer = MinecraftClient.getInstance().textRenderer;
            context.drawTooltip(textRenderer, item.getName(), mouseX, mouseY, (Identifier) item.getComponents().get(DataComponentTypes.TOOLTIP_STYLE));
        }*/
    DrawItem(context, mouseX, mouseY, delta);
    }

    public void DrawItem(DrawContext context, int mouseX, int mouseY, float delta) {
        if(this.item == null)
            return;

        Identifier texture = Identifier.of("minecraft", "container/slot");
        context.drawGuiTexture(RenderLayer::getGuiTextured, texture, getX(), getY(), 18, 18);
        context.drawItemWithoutEntity(this.item.getDefaultStack(), getX() + 1, getY() + 1);
        if (isHovered()) {
            TextRenderer textRenderer;
            textRenderer = MinecraftClient.getInstance().textRenderer;
            context.drawTooltip(textRenderer, item.getName(), mouseX, mouseY, (Identifier) item.getComponents().get(DataComponentTypes.TOOLTIP_STYLE));
        }
    }

    public void SetItem(Item item){
        this.item = item;
    }


    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {
        // For brevity, we'll just skip this for now - if you want to add narration to your widget, you can do so here.
        return;
    }

}