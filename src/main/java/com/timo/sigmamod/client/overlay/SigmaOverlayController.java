package com.timo.sigmamod.client.overlay;

import com.timo.sigmamod.SigmaMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;

public final class SigmaOverlayController {
    private static final int OVERLAY_DURATION_TICKS = 20 * 4; // 4 seconds
    private static final int RANDOM_INTERVAL_MIN_TICKS = 20 * 90; // 1.5 minutes
    private static final int RANDOM_INTERVAL_MAX_TICKS = 20 * 180; // 3 minutes

    private static final SigmaOverlayController INSTANCE = new SigmaOverlayController();

    public static SigmaOverlayController getInstance() {
        return INSTANCE;
    }

    private final RandomSource random = RandomSource.create();

    private int ticksUntilNextRandom = -1;
    private int activeTicks = 0;
    private boolean overlayActive = false;
    private OverlayTexture currentTexture = null;
    private ResourceSoundHandle currentSound = null;

    private SigmaOverlayController() {
    }

    public void clientTick(final Minecraft minecraft) {
        if (minecraft == null) {
            return;
        }

        if (minecraft.player == null) {
            if (overlayActive) {
                stopOverlay(minecraft, minecraft.screen instanceof SigmaOverlayScreen);
            }
            return;
        }

        if (overlayActive) {
            activeTicks++;
            if (activeTicks >= OVERLAY_DURATION_TICKS) {
                stopOverlay(minecraft, true);
            }
            return;
        }

        if (minecraft.screen != null) {
            return;
        }

        if (ticksUntilNextRandom < 0) {
            scheduleNextRandom();
            return;
        }

        if (ticksUntilNextRandom > 0) {
            ticksUntilNextRandom--;
            return;
        }

        triggerRandom(minecraft);
    }

    public void triggerManual(final Minecraft minecraft) {
        if (minecraft.player == null) {
            return;
        }
        if (overlayActive) {
            return;
        }

        startOverlay(minecraft, Activation.MANUAL);
    }

    private void triggerRandom(final Minecraft minecraft) {
        if (minecraft.player == null) {
            return;
        }

        startOverlay(minecraft, Activation.RANDOM);
    }

    private void startOverlay(final Minecraft minecraft, final Activation activation) {
        final OverlayTexture texture = SigmaOverlayAssets.pickRandomTexture(random);
        if (texture == null) {
            SigmaMod.LOGGER.warn("SigmaMod overlay activation skipped (no textures available).");
            scheduleNextRandom();
            return;
        }

        final ResourceSoundHandle sound = pickSound();

        overlayActive = true;
        activeTicks = 0;
        currentTexture = texture;
        currentSound = sound;

        if (sound != null) {
            sound.play(minecraft);
        }

        if (minecraft.screen == null) {
            minecraft.setScreen(new SigmaOverlayScreen());
        }

        SigmaMod.LOGGER.debug("Overlay started via {}", activation);
    }

    public boolean isOverlayActive() {
        return overlayActive;
    }

    public OverlayTexture getCurrentTexture() {
        return currentTexture;
    }

    public void stopOverlay(final Minecraft minecraft, final boolean closeScreen) {
        if (!overlayActive) {
            return;
        }

        overlayActive = false;
        activeTicks = 0;

        if (currentSound != null) {
            currentSound.stop(minecraft);
            currentSound = null;
        }

        currentTexture = null;

        if (closeScreen && minecraft.screen instanceof SigmaOverlayScreen) {
            minecraft.setScreen(null);
        }

        ticksUntilNextRandom = -1;
    }

    private void scheduleNextRandom() {
        ticksUntilNextRandom = Mth.nextInt(random, RANDOM_INTERVAL_MIN_TICKS, RANDOM_INTERVAL_MAX_TICKS);
    }

    private ResourceSoundHandle pickSound() {
        final var soundLocation = SigmaOverlayAssets.pickRandomSound(random);
        if (soundLocation == null) {
            return null;
        }

        final SoundEvent soundEvent = SoundEvent.createVariableRangeEvent(soundLocation);
        return new ResourceSoundHandle(soundEvent);
    }

    private enum Activation {
        RANDOM,
        MANUAL
    }

    private static final class ResourceSoundHandle {
        private final SoundEvent event;
        private SimpleSoundInstance activeInstance;

        private ResourceSoundHandle(final SoundEvent event) {
            this.event = event;
        }

        private void play(final Minecraft minecraft) {
            final SimpleSoundInstance sound = SimpleSoundInstance.forUI(event, 1.0F, 1.0F);
            minecraft.getSoundManager().play(sound);
            this.activeInstance = sound;
        }

        private void stop(final Minecraft minecraft) {
            if (activeInstance != null) {
                minecraft.getSoundManager().stop(activeInstance);
                activeInstance = null;
            }
        }
    }
}
