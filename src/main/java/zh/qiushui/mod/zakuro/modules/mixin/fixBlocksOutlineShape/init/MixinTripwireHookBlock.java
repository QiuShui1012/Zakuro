package zh.qiushui.mod.zakuro.modules.mixin.fixBlocksOutlineShape.init;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.TripwireHookBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import zh.qiushui.mod.zakuro.Zakuro;

import static net.minecraft.block.TripwireHookBlock.FACING;

@Mixin(TripwireHookBlock.class)
public abstract class MixinTripwireHookBlock extends AbstractBlock {
    @Shadow @Final protected static VoxelShape WEST_SHAPE;
    @Shadow @Final protected static VoxelShape EAST_SHAPE;
    @Shadow @Final protected static VoxelShape NORTH_SHAPE;
    @Shadow @Final protected static VoxelShape SOUTH_SHAPE;

    @SuppressWarnings("deprecation")
    @Shadow public abstract VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context);

    @Unique private static final VoxelShape FINE_SOUTH_SHAPE = Block.createCuboidShape(6.0, 1.0, 14.0, 10.0, 9.0, 16.0);
    @Unique private static final VoxelShape FINE_NORTH_SHAPE = Block.createCuboidShape(6.0, 1.0, 0.0, 10.0, 9.0, 2.0);
    @Unique private static final VoxelShape FINE_EAST_SHAPE = Block.createCuboidShape(14.0, 1.0, 6.0, 16.0, 9.0, 10.0);
    @Unique private static final VoxelShape FINE_WEST_SHAPE = Block.createCuboidShape(0.0, 1.0, 6.0, 2.0, 9.0, 10.0);

    public MixinTripwireHookBlock(Settings settings) {
        super(settings);
    }

    @Inject(method = "getOutlineShape(" +
            "Lnet/minecraft/block/BlockState;" +
            "Lnet/minecraft/world/BlockView;" +
            "Lnet/minecraft/util/math/BlockPos;" +
            "Lnet/minecraft/block/ShapeContext;)" +
            "Lnet/minecraft/util/shape/VoxelShape;", at = @At(value = "RETURN"), cancellable = true)
    private void getFineOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context, CallbackInfoReturnable<VoxelShape> cir) {
        cir.setReturnValue(
                switch (state.get(FACING)) {
                    default -> Zakuro.config.fixBlocksOutlineShape.blocks.tripwireHook ? FINE_WEST_SHAPE : WEST_SHAPE;
                    case WEST -> Zakuro.config.fixBlocksOutlineShape.blocks.tripwireHook ? FINE_EAST_SHAPE : EAST_SHAPE;
                    case SOUTH -> Zakuro.config.fixBlocksOutlineShape.blocks.tripwireHook ? FINE_NORTH_SHAPE : NORTH_SHAPE;
                    case NORTH -> Zakuro.config.fixBlocksOutlineShape.blocks.tripwireHook ? FINE_SOUTH_SHAPE : SOUTH_SHAPE;
        });
    }

    @Override
    public VoxelShape zakuro$getOutlineShapeWithOriginal(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return Zakuro.config.retainBlocksOriginalInteractableRange ? getOriginalOutlineShape(state) : getOutlineShape(state, world, pos, context);
    }

    @Unique
    private VoxelShape getOriginalOutlineShape(BlockState state) {
        return switch (state.get(FACING)) {
            default -> WEST_SHAPE;
            case WEST -> EAST_SHAPE;
            case SOUTH -> NORTH_SHAPE;
            case NORTH -> SOUTH_SHAPE;
        };
    }
}
