# Overlay Asset Guide

Place your overlay textures (PNG with transparent background) in:

```
src/main/resources/assets/sigmamod/textures/gui/overlay/
```

Place your music clips (OGG) in:

```
src/main/resources/assets/sigmamod/sounds/overlay/
```

Each audio file must be declared in `src/main/resources/assets/sigmamod/sounds.json`. Use the `overlay/<name>` pattern. Example:

```
{
  "overlay.sample": {
    "sounds": [
      { "name": "sigmamod:overlay/sample", "stream": true }
    ]
  }
}
```

Add one block per clip and keep the file names in sync (e.g. `sample.ogg`).

The mod automatically discovers all PNGs in the `overlay` folder and every sound event whose path starts with `overlay/`, so no Java changes are needed when you add or remove clips.
