package de.lootwig.lootclient;

import net.minecraft.world.entity.EquipmentSlot;

/** Small allocation- and lookup-saving helpers for the Lootclient HUD. */
public final class LootclientPerformance {
    private LootclientPerformance() {}

    private static final EquipmentSlot[] ARMOR_SLOTS = {
            EquipmentSlot.HEAD,
            EquipmentSlot.CHEST,
            EquipmentSlot.LEGS,
            EquipmentSlot.FEET
    };

    private static int lastFps = Integer.MIN_VALUE;
    private static String cachedFpsText = "FPS: 0";

    /** Reuses the fixed armor-slot list instead of creating it during HUD rendering. */
    public static EquipmentSlot[] getArmorSlots() {
        return ARMOR_SLOTS;
    }

    /** Rebuilds the FPS label only when the FPS value changes. */
    public static String getFpsText(int fps) {
        if (fps != lastFps) {
            lastFps = fps;
            cachedFpsText = "FPS: " + fps;
        }
        return cachedFpsText;
    }
}
