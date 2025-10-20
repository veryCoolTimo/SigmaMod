# Sigma Mod

A Forge 1.20.1 brainrot-inspired prank mod: the game freezes, the screen darkens, and a transparent trollface slides in at the bottom while a 4-second phonk sting plays. Want extra chaos? Smash `Z` to trigger it manually.

## Features
- Random overlay every 90–180 seconds, plus a manual trigger on `Z`.
- Trollface PNGs are scaled to a consistent display height regardless of their original resolution.
- Plug-and-play audio pack: drop `.ogg` clips into `assets/sigmamod/sounds/overlay/` and register them in `sounds.json`.

## Installation
1. Build the mod with `./gradlew build` or grab a prebuilt release.
2. Copy `build/libs/sigmamod-0.1.0.jar` into your Forge 1.20.1 `mods` folder (Java 17 required).
3. Launch Minecraft and enjoy the sigma jump-scare moments.

## Development
- `./gradlew runClient` starts a dev client with hot reloading.
- Asset layout:
  - PNG overlays: `src/main/resources/assets/sigmamod/textures/gui/overlay/`
  - OGG clips: `src/main/resources/assets/sigmamod/sounds/overlay/`
  - Sound registry: `src/main/resources/assets/sigmamod/sounds.json`
- Additional notes and examples live in `docs/overlay-assets.md`.
