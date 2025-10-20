package com.timo.sigmamod.client.overlay;

import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

public final class SigmaOverlayReloadListener extends SimplePreparableReloadListener<Void> {
    public static final SigmaOverlayReloadListener INSTANCE = new SigmaOverlayReloadListener();

    private SigmaOverlayReloadListener() {
    }

    @Override
    protected Void prepare(final ResourceManager resourceManager, final ProfilerFiller profiler) {
        return null;
    }

    @Override
    protected void apply(final Void object, final ResourceManager resourceManager, final ProfilerFiller profiler) {
        SigmaOverlayAssets.invalidateTextureCache();
    }
}
