package zh.qiushui.mod.zakuro.modules.mixin.fixBlocksOutlineShape.endRod;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.EndRodBlock;
import net.minecraft.block.RodBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import zh.qiushui.mod.zakuro.Zakuro;

@Mixin(EndRodBlock.class)
public class MixinEndRodBlock extends RodBlock {
    protected MixinEndRodBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape zakuro$getOutlineShapeWithOriginal(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.union(
                this.getOutlineShape(state, world, pos, context),
                Zakuro.config.retainBlocksOriginalInteractableRange ? super.getOutlineShape(state, world, pos, context) : VoxelShapes.empty()
        );
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(FACING).getAxis()) {
            default -> switch (state.get(FACING).getDirection()) {
                case POSITIVE -> VoxelShapes.union(
                        Block.createCuboidShape(0.0D, 6.0D, 6.0D, 1.0D, 10.0D, 10.0D),
                        Block.createCuboidShape(1.0D, 7.0D, 7.0D, 16.0D, 9.0D, 9.0D));
                case NEGATIVE -> VoxelShapes.union(
                        Block.createCuboidShape(15.0D, 6.0D, 6.0D, 16.0D, 10.0D, 10.0D),
                        Block.createCuboidShape(0.0D, 7.0D, 7.0D, 15.0D, 9.0D, 9.0D));
            };
            case Y -> switch (state.get(FACING).getDirection()) {
                case POSITIVE -> VoxelShapes.union(
                        Block.createCuboidShape(6.0D, 0.0D, 6.0D, 10.0D, 1.0D, 10.0D),
                        Block.createCuboidShape(7.0D, 1.0D, 7.0D, 9.0D, 16.0D, 9.0D));
                case NEGATIVE -> VoxelShapes.union(
                        Block.createCuboidShape(6.0D, 15.0D, 6.0D, 10.0D, 16.0D, 10.0D),
                        Block.createCuboidShape(7.0D, 0.0D, 7.0D, 9.0D, 15.0D, 9.0D));
            };
            case Z -> switch (state.get(FACING).getDirection()) {
                case POSITIVE -> VoxelShapes.union(
                        Block.createCuboidShape(6.0D, 6.0D, 0.0D, 10.0D, 10.0D, 1.0D),
                        Block.createCuboidShape(7.0D, 7.0D, 1.0D, 9.0D, 9.0D, 16.0D));
                case NEGATIVE -> VoxelShapes.union(
                        Block.createCuboidShape(6.0D, 6.0D, 15.0D, 10.0D, 10.0D, 16.0D),
                        Block.createCuboidShape(7.0D, 7.0D, 0.0D, 9.0D, 9.0D, 15.0D));
            };
        };
    }
}
