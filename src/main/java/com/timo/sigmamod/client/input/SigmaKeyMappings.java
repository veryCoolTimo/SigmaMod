package com.timo.sigmamod.client.input;

import com.timo.sigmamod.SigmaMod;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public final class SigmaKeyMappings {
    private SigmaKeyMappings() {
    }

    private static final String CATEGORY = "key.categories." + SigmaMod.MOD_ID;

    public static final KeyMapping OPEN_OVERLAY = new KeyMapping(
            "key." + SigmaMod.MOD_ID + ".overlay",
            GLFW.GLFW_KEY_Z,
            CATEGORY
    );
}
