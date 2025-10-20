package com.timo.sigmamod.client.overlay;

import com.timo.sigmamod.SigmaMod;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public final class SigmaOverlay implements IGuiOverlay {
    public static final SigmaOverlay INSTANCE = new SigmaOverlay();

    private SigmaOverlay() {
    }

    @Override
    public void render(final ForgeGui gui, final GuiGraphics graphics, final float partialTick, final int screenWidth, final int screenHeight) {
        renderHud(graphics, screenWidth, screenHeight);
    }

    public static void renderHud(final GuiGraphics graphics, final int screenWidth, final int screenHeight) {
        final SigmaOverlayController controller = SigmaOverlayController.getInstance();
        if (!controller.isOverlayActive()) {
            return;
        }

        graphics.fill(0, 0, screenWidth, screenHeight, 0xA0000000);

        final OverlayTexture texture = controller.getCurrentTexture();
        if (texture == null) {
            SigmaMod.LOGGER.warn("Overlay active without texture; skipping draw.");
            return;
        }

        final int originalWidth = texture.width();
        final int originalHeight = texture.height();
        if (originalWidth <= 0 || originalHeight <= 0) {
            SigmaMod.LOGGER.warn("Overlay texture has invalid size {}x{}", originalWidth, originalHeight);
            return;
        }

        final double targetHeightPixels = Math.min(screenHeight * 0.30D, 220.0D);
        double scale = targetHeightPixels / originalHeight;
        final double maxWidth = screenWidth * 0.8D;
        if (originalWidth * scale > maxWidth) {
            scale = maxWidth / originalWidth;
        }

        final int renderWidth = (int) Math.round(originalWidth * scale);
        final int renderHeight = (int) Math.round(originalHeight * scale);

        final int x = (screenWidth - renderWidth) / 2;
        final int y = screenHeight - renderHeight - 20;

        graphics.blit(texture.location(), x, y, 0, 0, renderWidth, renderHeight, renderWidth, renderHeight);
    }
}
