package de.lootwig.lootclient.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InventoryScreen.class)
public abstract class InventoryScreenMixin {
    @Inject(method = "render", at = @At("TAIL"))
    private void lootclient$drawBrand(GuiGraphics graphics, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        Minecraft mc = Minecraft.getInstance();
        int x = mc.getWindow().getGuiScaledWidth() - mc.font.width("Lootclient") - 8;
        int y = mc.getWindow().getGuiScaledHeight() - 12;
        graphics.drawString(mc.font, "Lootclient", x, y, 0xFFFFD34E, true);
    }
}
