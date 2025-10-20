package com.timo.sigmamod.client;

import com.timo.sigmamod.SigmaMod;
import com.timo.sigmamod.client.input.SigmaKeyMappings;
import com.timo.sigmamod.client.overlay.SigmaOverlay;
import com.timo.sigmamod.client.overlay.SigmaOverlayReloadListener;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SigmaMod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class SigmaClientHooks {
    private SigmaClientHooks() {
    }

    @SubscribeEvent
    public static void registerOverlays(final RegisterGuiOverlaysEvent event) {
        event.registerAboveAll("sigmamod_overlay", SigmaOverlay.INSTANCE);
    }

    @SubscribeEvent
    public static void registerKeyMappings(final RegisterKeyMappingsEvent event) {
        event.register(SigmaKeyMappings.OPEN_OVERLAY);
    }

    @SubscribeEvent
    public static void registerReloadListeners(final RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(SigmaOverlayReloadListener.INSTANCE);
    }
}
