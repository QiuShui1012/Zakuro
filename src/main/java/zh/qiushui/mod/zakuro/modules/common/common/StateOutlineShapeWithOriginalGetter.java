package zh.qiushui.mod.zakuro.modules.common.common;

import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public interface StateOutlineShapeWithOriginalGetter {
    default VoxelShape zakuro$getOutlineShapeWithOriginal(BlockView world, BlockPos pos, ShapeContext context) {
        return null;
    }
}
