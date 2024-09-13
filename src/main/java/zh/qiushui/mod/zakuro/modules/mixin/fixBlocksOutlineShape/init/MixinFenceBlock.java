package zh.qiushui.mod.zakuro.modules.mixin.fixBlocksOutlineShape.init;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.HorizontalConnectingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import zh.qiushui.mod.zakuro.Zakuro;

@Mixin(FenceBlock.class)
public abstract class MixinFenceBlock extends HorizontalConnectingBlock {
    @Unique
    private final VoxelShape[] outlineFenceShapes = this.createFenceShapes(16.0, 15.0);
    @Unique
    private final VoxelShape[] collisionFenceShapes = this.createFenceShapes(24.0, 24.0);

    public MixinFenceBlock(float radius1, float radius2, float boundingHeight1, float boundingHeight2, float collisionHeight, Settings settings) {
        super(radius1, radius2, boundingHeight1, boundingHeight2, collisionHeight, settings);
    }

    @Override
    public VoxelShape zakuro$getOutlineShapeWithOriginal(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.union(
                Zakuro.config.retainBlocksOriginalInteractableRange ? super.getOutlineShape(state, world, pos, context) : VoxelShapes.empty(),
                state.getOutlineShape(world, pos, context)
        );
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return Zakuro.config.fixBlocksOutlineShape.blocks.fence ? this.outlineFenceShapes[this.getShapeIndex(state)] : super.getOutlineShape(state, world, pos, context);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return Zakuro.config.fixBlocksOutlineShape.blocks.fence ? this.collisionFenceShapes[this.getShapeIndex(state)] : super.getCollisionShape(state, world, pos, context);
    }

    @Unique
    protected VoxelShape[] createFenceShapes(double maxYMain, double maxYSideUpper) {
        VoxelShape voxelShapeMain = Block.createCuboidShape(6.0, 0.0, 6.0, 10.0, maxYMain, 10.0);

        VoxelShape voxelShape0100 = VoxelShapes.union(
                Block.createCuboidShape(7.0, 6.0, 0.0, 9.0, 9.0, 9.0),
                Block.createCuboidShape(7.0, 12.0, 0.0, 9.0, maxYSideUpper, 9.0)
        );
        VoxelShape voxelShape0001 = VoxelShapes.union(
                Block.createCuboidShape(7.0, 6.0, 7.0, 9.0, 9.0, 16.0),
                Block.createCuboidShape(7.0, 12.0, 7.0, 9.0, maxYSideUpper, 16.0)
        );
        VoxelShape voxelShape0010 = VoxelShapes.union(
                Block.createCuboidShape(0.0, 6.0, 7.0, 9.0, 9.0, 9.0),
                Block.createCuboidShape(0.0, 12.0, 7.0, 9.0, maxYSideUpper, 9.0)
        );
        VoxelShape voxelShape1000 = VoxelShapes.union(
                Block.createCuboidShape(7.0, 6.0, 7.0, 16.0, 9.0, 9.0),
                Block.createCuboidShape(7.0, 12.0, 7.0, 16.0, maxYSideUpper, 9.0)
        );

        VoxelShape[] voxelShapes = new VoxelShape[]{
                VoxelShapes.empty(),
                voxelShape0001,
                voxelShape0010,
                VoxelShapes.union(voxelShape0001, voxelShape0010),
                voxelShape0100,
                VoxelShapes.union(voxelShape0001, voxelShape0100),
                VoxelShapes.union(voxelShape0010, voxelShape0100),
                VoxelShapes.union(voxelShape0001, voxelShape0010, voxelShape0100),
                voxelShape1000,
                VoxelShapes.union(voxelShape0001, voxelShape1000),
                VoxelShapes.union(voxelShape0010, voxelShape1000),
                VoxelShapes.union(voxelShape0001, voxelShape0010, voxelShape1000),
                VoxelShapes.union(voxelShape0100, voxelShape1000),
                VoxelShapes.union(voxelShape0001, voxelShape0100, voxelShape1000),
                VoxelShapes.union(voxelShape0010, voxelShape0100, voxelShape1000),
                VoxelShapes.union(voxelShape0001, voxelShape0010, voxelShape0100, voxelShape1000)
        };

        for (int i = 0; i < 16; i++) {
            voxelShapes[i] = VoxelShapes.union(voxelShapeMain, voxelShapes[i]);
        }

        return voxelShapes;
    }
}
