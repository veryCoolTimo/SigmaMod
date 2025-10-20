package com.timo.sigmamod.client.overlay;

import com.timo.sigmamod.SigmaMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public final class SigmaOverlayScreen extends Screen {
    private static final Component TITLE = Component.translatable("screen." + SigmaMod.MOD_ID + ".overlay");

    public SigmaOverlayScreen() {
        super(TITLE);
    }

    @Override
    protected void init() {
        super.init();
        setFocused(null);
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public boolean isPauseScreen() {
        return true;
    }

    @Override
    public void onClose() {
        SigmaOverlayController.getInstance().stopOverlay(Minecraft.getInstance(), false);
        super.onClose();
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }

    @Override
    public void render(final GuiGraphics graphics, final int mouseX, final int mouseY, final float partialTick) {
        SigmaOverlay.renderHud(graphics, width, height);
    }
}
