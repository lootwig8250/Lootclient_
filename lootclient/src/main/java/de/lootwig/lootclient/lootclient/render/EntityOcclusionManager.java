
package de.lootwig.lootclient.render;

import net.minecraft.world.entity.Entity;

import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Entity visibility cache for Lootclient.
 *
 * Safe fallback: entities remain visible unless a future
 * GPU occlusion backend explicitly confirms that they are hidden.
 *
 * This class does not modify block entities or chest rendering.
 */
public final class EntityOcclusionManager {

    private static final int MAX_ENTRIES = 4096;

    private static final Map<Entity, VisibilityEntry> VISIBILITY =
            new IdentityHashMap<>();

    private static long currentFrame;
    private static boolean enabled = false;

    private EntityOcclusionManager() {}

    public static void beginFrame() {
        currentFrame++;

        if (VISIBILITY.size() > MAX_ENTRIES) {
            cleanup();
        }
    }

    public static boolean isEnabled() {
        return enabled;
    }

    public static void setEnabled(boolean value) {
        enabled = value;

        if (!enabled) {
            VISIBILITY.clear();
        }
    }

    /**
     * Conservative default:
     * Unknown entities are always rendered.
     */
    public static boolean shouldRender(Entity entity) {
        if (!enabled || entity == null) {
            return true;
        }

        VisibilityEntry entry = VISIBILITY.get(entity);

        if (entry == null) {
            return true;
        }

        if (currentFrame - entry.lastUpdated > 2) {
            return true;
        }

        return !entry.occluded;
    }

    /**
     * Called by a future GPU occlusion backend.
     */
    public static void updateVisibility(
            Entity entity,
            boolean occluded
    ) {
        if (!enabled || entity == null) {
            return;
        }

        VISIBILITY.put(
                entity,
                new VisibilityEntry(occluded, currentFrame)
        );
    }

    public static void invalidate(Entity entity) {
        if (entity != null) {
            VISIBILITY.remove(entity);
        }
    }

    public static void clear() {
        VISIBILITY.clear();
        currentFrame = 0;
    }

    private static void cleanup() {
        Iterator<Map.Entry<Entity, VisibilityEntry>> iterator =
                VISIBILITY.entrySet().iterator();

        while (iterator.hasNext()) {
            VisibilityEntry entry = iterator.next().getValue();

            if (currentFrame - entry.lastUpdated > 2) {
                iterator.remove();
            }
        }
    }

    private static final class VisibilityEntry {
        private final boolean occluded;
        private final long lastUpdated;

        private VisibilityEntry(
                boolean occluded,
                long lastUpdated
        ) {
            this.occluded = occluded;
            this.lastUpdated = lastUpdated;
        }
    }
}
