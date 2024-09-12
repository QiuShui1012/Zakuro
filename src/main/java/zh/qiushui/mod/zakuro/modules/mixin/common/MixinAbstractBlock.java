package zh.qiushui.mod.zakuro.modules.mixin.common;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import zh.qiushui.mod.zakuro.modules.common.common.OutlineShapeWithOriginalGetter;

@Mixin(AbstractBlock.class)
public abstract class MixinAbstractBlock implements OutlineShapeWithOriginalGetter {
    @SuppressWarnings("dep-ann")
    @Shadow @Deprecated public abstract VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context);

    @Override
    public VoxelShape zakuro$getOutlineShapeWithOriginal(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return this.getOutlineShape(state, world, pos, context);
    }
}
