package de.lootwig.lootclient;

import net.minecraft.world.entity.EquipmentSlot;

/**
 * Small performance helpers for Lootclient.
 *
 * This first version avoids recreating the armor-slot array and FPS label
 * on every HUD render. It is not a replacement for Sodium's chunk renderer.
 */
public final class LootclientPerformance {

    private LootclientPerformance() {
    }

    private static final EquipmentSlot[] ARMOR_SLOTS = {
            EquipmentSlot.HEAD,
            EquipmentSlot.CHEST,
            EquipmentSlot.LEGS,
            EquipmentSlot.FEET
    };

    private static int lastFps = Integer.MIN_VALUE;
    private static String cachedFpsText = "FPS: 0";

    public static EquipmentSlot[] getArmorSlots() {
        return ARMOR_SLOTS;
    }

    public static String getFpsText(int fps) {
        if (fps != lastFps) {
            lastFps = fps;
            cachedFpsText = "FPS: " + fps;
        }

        return cachedFpsText;
    }
}
