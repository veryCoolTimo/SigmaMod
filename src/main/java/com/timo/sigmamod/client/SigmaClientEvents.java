package com.timo.sigmamod.client;

import com.timo.sigmamod.SigmaMod;
import com.timo.sigmamod.client.input.SigmaKeyMappings;
import com.timo.sigmamod.client.overlay.SigmaOverlayController;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SigmaMod.MOD_ID, value = Dist.CLIENT)
public final class SigmaClientEvents {
    private SigmaClientEvents() {
    }

    @SubscribeEvent
    public static void handleClientTick(final TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        final Minecraft minecraft = Minecraft.getInstance();
        SigmaOverlayController.getInstance().clientTick(minecraft);

        if (minecraft.player == null) {
            return;
        }

        if (SigmaKeyMappings.OPEN_OVERLAY.consumeClick()) {
            SigmaOverlayController.getInstance().triggerManual(minecraft);
        }
    }
}
