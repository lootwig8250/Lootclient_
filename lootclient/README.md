# Lootclient 1.0.1

Client-side Fabric mod project for Minecraft Java 1.21.11.

## Features in this source build
- Right Shift opens the Lootclient module menu.
- Toggleable FPS display, armor-item HUD, and sprint-state indicator.
- HUD preferences are saved in `config/lootclient.properties`.
- Lootclient branding is drawn on the title screen and in the bottom-right of the inventory. The title-screen text is currently an overlay; the vanilla logo is not yet fully replaced.
- No texture pack or visual world effects are added.

## Build with GitHub Actions (no local PC build required)

The repository includes `.github/workflows/build.yml`. After uploading this project to a GitHub repository, open **Actions → Build Lootclient → Run workflow**. When the workflow succeeds, open its run and download the `lootclient-1.0.0` artifact. It contains the compiled mod JAR.

The workflow runs on GitHub-hosted Ubuntu and downloads the required build dependencies there. No GitHub token or secret is needed.

## Build requirements
- JDK 21
- Gradle 9.2 or newer (Fabric Loom 1.14 requires Gradle 9.2+)
- Internet access on the first build so Gradle can download Minecraft, Fabric, and plugin dependencies

From this project directory, run:

```bash
gradle build
```

The remapped mod JAR should be created in `build/libs/lootclient-1.0.1.jar`. This project has not yet been compiled or tested in Minecraft in the current environment.

## Install
Put the generated JAR in the Minecraft instance's `mods` folder and install Fabric Loader plus the matching Fabric API for Minecraft 1.21.11.

## Performance note
This version adds lightweight HUD utilities only. It does not yet implement a renderer optimization or guarantee an FPS increase. It also does not bundle Sodium or other third-party mods.
