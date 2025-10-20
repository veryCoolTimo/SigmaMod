package com.timo.sigmamod.client.overlay;

import com.timo.sigmamod.SigmaMod;
import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.RandomSource;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

public final class SigmaOverlayAssets {
    private static final Predicate<ResourceLocation> PNG_SELECTOR = location -> location.getPath().endsWith(".png");
    private static List<OverlayTexture> textures = Collections.emptyList();
    private static boolean warnedMissingSounds = false;

    private SigmaOverlayAssets() {
    }

    public static OverlayTexture pickRandomTexture(final RandomSource random) {
        final List<OverlayTexture> available = loadTexturesIfNeeded();
        if (available.isEmpty()) {
            return null;
        }
        return available.get(random.nextInt(available.size()));
    }

    public static ResourceLocation pickRandomSound(final RandomSource random) {
        final List<ResourceLocation> available = listOverlaySounds();
        if (available.isEmpty()) {
            return null;
        }
        return available.get(random.nextInt(available.size()));
    }

    private static List<OverlayTexture> loadTexturesIfNeeded() {
        if (!textures.isEmpty()) {
            return textures;
        }

        final ResourceManager resourceManager = Minecraft.getInstance().getResourceManager();
        final Map<ResourceLocation, Resource> discovered = resourceManager.listResources("textures/gui/overlay", PNG_SELECTOR);
        if (discovered.isEmpty()) {
            textures = Collections.emptyList();
            SigmaMod.LOGGER.warn("SigmaMod overlay assets: no PNG textures found in assets/{}/textures/gui/overlay", SigmaMod.MOD_ID);
            return textures;
        }

        final List<OverlayTexture> loaded = new ArrayList<>();
        for (ResourceLocation path : discovered.keySet()) {
            final Optional<Resource> optionalResource = resourceManager.getResource(path);
            if (optionalResource.isEmpty()) {
                continue;
            }

            try (InputStream stream = optionalResource.get().open(); NativeImage image = NativeImage.read(stream)) {
                loaded.add(new OverlayTexture(new ResourceLocation(path.getNamespace(), path.getPath()), image.getWidth(), image.getHeight()));
            } catch (IOException ex) {
                SigmaMod.LOGGER.error("Failed to load overlay texture {}", path, ex);
            }
        }

        if (loaded.isEmpty()) {
            SigmaMod.LOGGER.warn("SigmaMod overlay assets: texture discovery found entries but none could be loaded.");
            textures = Collections.emptyList();
        } else {
            textures = List.copyOf(loaded);
        }

        return textures;
    }

    public static void invalidateTextureCache() {
        textures = Collections.emptyList();
    }

    private static List<ResourceLocation> listOverlaySounds() {
        final SoundManager soundManager = Minecraft.getInstance().getSoundManager();
        if (soundManager == null) {
            return Collections.emptyList();
        }

        final List<ResourceLocation> results = new ArrayList<>();
        for (ResourceLocation soundLocation : soundManager.getAvailableSounds()) {
            if (!SigmaMod.MOD_ID.equals(soundLocation.getNamespace())) {
                continue;
            }
            if (!soundLocation.getPath().startsWith("overlay/")) {
                continue;
            }
            results.add(soundLocation);
        }

        if (results.isEmpty()) {
            if (!warnedMissingSounds) {
                SigmaMod.LOGGER.warn("SigmaMod overlay assets: no registered sound events found with path overlay/*");
                warnedMissingSounds = true;
            }
        } else {
            warnedMissingSounds = false;
        }

        return results;
    }
}
