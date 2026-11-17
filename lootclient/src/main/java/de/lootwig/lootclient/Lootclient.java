package de.lootwig.lootclient;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

public final class Lootclient implements ClientModInitializer {
    public static final String MOD_ID = "lootclient";
    private static KeyMapping openMenu;

    @Override
    public void onInitializeClient() {
        LootclientConfig.load();

        KeyMapping.Category category = KeyMapping.Category.register(
                Identifier.fromNamespaceAndPath(MOD_ID, "main")
        );
        openMenu = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.lootclient.open_menu",
                InputConstants.Type.KEYSYM,
                InputConstants.KEY_RIGHT_SHIFT,
                category
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openMenu.consumeClick()) {
                if (client.screen == null) client.setScreen(new LootclientScreen());
            }
        });

        HudElementRegistry.addLast(
                Identifier.fromNamespaceAndPath(MOD_ID, "pvp_hud"),
                Lootclient::renderHud
        );
    }

    private static void renderHud(GuiGraphics graphics, net.minecraft.client.DeltaTracker tickCounter) {
        Minecraft client = Minecraft.getInstance();
        if (client.player == null || client.options.hideGui) return;

        int y = 8;
        if (LootclientScreen.showFps) {
            drawHudText(graphics, client, "FPS: " + client.getFps(), 0xFFFFFFFF, y);
            y += 12;
        }
        if (LootclientScreen.showSprint) {
            boolean sprinting = client.player.isSprinting();
            drawHudText(graphics, client, sprinting ? "SPRINT: ON" : "SPRINT: OFF",
                    sprinting ? 0xFF55FF55 : 0xFFFF7777, y);
            y += 12;
        }
        if (LootclientScreen.showArmor) {
            int x = 8;
            for (ItemStack stack : client.player.getArmorSlots()) {
                if (!stack.isEmpty()) graphics.renderItem(stack, x, y);
                x += 18;
            }
        }
    }

    private static void drawHudText(GuiGraphics graphics, Minecraft client, String text, int color, int y) {
        graphics.drawString(client.font, text, 8, y, color, true);
    }
}
