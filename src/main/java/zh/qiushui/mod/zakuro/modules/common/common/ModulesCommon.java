package zh.qiushui.mod.zakuro.modules.common.common;

import net.minecraft.block.AbstractBlock;
import net.minecraft.world.RaycastContext;
import zh.qiushui.mod.zakuro.Zakuro;

public class ModulesCommon {
    public static void init() {
        if (Zakuro.config.retainBlocksOriginalInteractableRange) {
            RaycastContext.ShapeType.OUTLINE.provider = AbstractBlock.AbstractBlockState::zakuro$getOutlineShapeWithOriginal;
        }
    }
}
