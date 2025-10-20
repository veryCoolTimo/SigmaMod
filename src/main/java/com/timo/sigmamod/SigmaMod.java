package com.timo.sigmamod;

import com.mojang.logging.LogUtils;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(SigmaMod.MOD_ID)
public final class SigmaMod {
    public static final String MOD_ID = "sigmamod";
    public static final Logger LOGGER = LogUtils.getLogger();

    public SigmaMod() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::handleClientSetup);
        LOGGER.info("SigmaMod loaded. Client hooks will be ready for the overlay setup.");
    }

    private void handleClientSetup(final FMLClientSetupEvent event) {
        LOGGER.info("Client setup reached. Overlay registration can happen here.");
    }
}
