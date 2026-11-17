package de.lootwig.lootclient;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public final class LootclientScreen extends Screen {
    public static boolean showFps = true;
    public static boolean showArmor = true;
    public static boolean showSprint = true;

    public LootclientScreen() {
        super(Component.literal("Lootclient"));
    }

    @Override
    protected void init() {
        int left = this.width / 2 - 100;
        int top = this.height / 2 - 42;
        addRenderableWidget(Button.builder(label("FPS-Anzeige", showFps), button -> {
            showFps = !showFps;
            button.setMessage(label("FPS-Anzeige", showFps));
            LootclientConfig.save();
        }).bounds(left, top, 200, 20).build());
        addRenderableWidget(Button.builder(label("Armor-HUD", showArmor), button -> {
            showArmor = !showArmor;
            button.setMessage(label("Armor-HUD", showArmor));
            LootclientConfig.save();
        }).bounds(left, top + 25, 200, 20).build());
        addRenderableWidget(Button.builder(label("Sprint-Indikator", showSprint), button -> {
            showSprint = !showSprint;
            button.setMessage(label("Sprint-Indikator", showSprint));
            LootclientConfig.save();
        }).bounds(left, top + 50, 200, 20).build());
        addRenderableWidget(Button.builder(Component.literal("Schließen"), button -> onClose())
                .bounds(left, top + 82, 200, 20).build());
    }

    private static Component label(String name, boolean enabled) {
        return Component.literal(name + ": " + (enabled ? "AN" : "AUS"));
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        renderBackground(graphics, mouseX, mouseY, delta);
        int panelLeft = this.width / 2 - 118;
        int panelTop = this.height / 2 - 91;
        graphics.fill(panelLeft, panelTop, panelLeft + 236, panelTop + 151, 0xD9101018);
        graphics.fill(panelLeft, panelTop, panelLeft + 236, panelTop + 2, 0xFFFFD34E);
        graphics.drawCenteredString(this.font, "LOOTCLIENT", this.width / 2, panelTop + 12, 0xFFFFD34E);
        graphics.drawCenteredString(this.font, "PvP MODULES  •  RIGHT SHIFT", this.width / 2, panelTop + 29, 0xFFBBBBBB);
        super.render(graphics, mouseX, mouseY, delta);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
