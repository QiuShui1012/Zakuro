package zh.qiushui.mod.zakuro.modules.mixin.fixBlocksOutlineShape.init;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BellBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.enums.Attachment;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import zh.qiushui.mod.zakuro.Zakuro;

import static net.minecraft.block.BellBlock.ATTACHMENT;
import static net.minecraft.block.BellBlock.FACING;

@SuppressWarnings("deprecation")
@Mixin(BellBlock.class)
public abstract class MixinBellBlock extends AbstractBlock {
    @Shadow @Final private static VoxelShape BELL_SHAPE;
    @Shadow @Final private static VoxelShape EAST_WEST_SHAPE;
    @Shadow @Final private static VoxelShape NORTH_SOUTH_SHAPE;

    @Shadow public abstract VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context);

    @Unique private static final VoxelShape FINE_EAST_WEST_SHAPE = VoxelShapes.union(
            BELL_SHAPE,
            Block.createCuboidShape(7.0, 13.0, 2.0, 9.0, 15.0, 14.0),
            Block.createCuboidShape(6.0, 0.0, 0.0, 10.0, 16.0, 2.0),
            Block.createCuboidShape(6.0, 0.0, 14.0, 10.0, 16.0, 16.0)
    );
    @Unique private static final VoxelShape FINE_NORTH_SOUTH_SHAPE = VoxelShapes.union(
            BELL_SHAPE,
            Block.createCuboidShape(2.0, 13.0, 7.0, 14.0, 15.0, 9.0),
            Block.createCuboidShape(0.0, 0.0, 6.0, 2.0, 16.0, 10.0),
            Block.createCuboidShape(14.0, 0.0, 6.0, 16.0, 16.0, 10.0)
    );

    public MixinBellBlock(Settings settings) {
        super(settings);
    }

    @Redirect(method = "getShape(Lnet/minecraft/block/BlockState;)Lnet/minecraft/util/shape/VoxelShape;", at = @At(value = "FIELD", target = "Lnet/minecraft/block/BellBlock;EAST_WEST_SHAPE:Lnet/minecraft/util/shape/VoxelShape;"))
    private VoxelShape redirectEastWestShape() {
        return Zakuro.config.fixBlocksOutlineShape.blocks.bell ? FINE_EAST_WEST_SHAPE : EAST_WEST_SHAPE;
    }
    @Redirect(method = "getShape(Lnet/minecraft/block/BlockState;)Lnet/minecraft/util/shape/VoxelShape;", at = @At(value = "FIELD", target = "Lnet/minecraft/block/BellBlock;NORTH_SOUTH_SHAPE:Lnet/minecraft/util/shape/VoxelShape;"))
    private VoxelShape redirectNorthSouthShape() {
        return Zakuro.config.fixBlocksOutlineShape.blocks.bell ? FINE_NORTH_SOUTH_SHAPE : NORTH_SOUTH_SHAPE;
    }

    @Override
    public VoxelShape zakuro$getOutlineShapeWithOriginal(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.union(
                Zakuro.config.retainBlocksOriginalInteractableRange ? getOriginalShape(state) : VoxelShapes.empty(),
                state.getOutlineShape(world, pos, context)
        );
    }

    @Unique
    private VoxelShape getOriginalShape(BlockState state) {
        Direction direction = state.get(FACING);
        Attachment attachment = state.get(ATTACHMENT);
        if (attachment == Attachment.FLOOR) {
            return direction != Direction.NORTH && direction != Direction.SOUTH ? EAST_WEST_SHAPE : NORTH_SOUTH_SHAPE;
        } else {
            return VoxelShapes.empty();
        }
    }
}
