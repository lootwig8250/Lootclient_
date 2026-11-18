package de.lootwig.lootclient.mixin;

import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.SectionPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

/** Avoids repeatedly dirtying the same render section for bulk block updates. */
@Mixin(LevelRenderer.class)
public abstract class ChunkRenderingMixin {

    @Shadow
    public abstract void setSectionRangeDirty(
            int minSectionX, int minSectionY, int minSectionZ,
            int maxSectionX, int maxSectionY, int maxSectionZ
    );

    /**
     * @author Lootwig
     * @reason Convert the expanded block range to section coordinates first.
     */
    @Overwrite
    public void scheduleBlockRenders(
            int minX, int minY, int minZ,
            int maxX, int maxY, int maxZ
    ) {
        // Preserve vanilla's one-block expansion around the changed area.
        int minSectionX = SectionPos.blockToSectionCoord(minX - 1);
        int minSectionY = SectionPos.blockToSectionCoord(minY - 1);
        int minSectionZ = SectionPos.blockToSectionCoord(minZ - 1);
        int maxSectionX = SectionPos.blockToSectionCoord(maxX + 1);
        int maxSectionY = SectionPos.blockToSectionCoord(maxY + 1);
        int maxSectionZ = SectionPos.blockToSectionCoord(maxZ + 1);

        setSectionRangeDirty(
                minSectionX, minSectionY, minSectionZ,
                maxSectionX, maxSectionY, maxSectionZ
        );
    }
}
