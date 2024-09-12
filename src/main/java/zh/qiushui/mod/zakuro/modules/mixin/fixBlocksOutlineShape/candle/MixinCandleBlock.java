package zh.qiushui.mod.zakuro.modules.mixin.fixBlocksOutlineShape.candle;

import net.minecraft.block.AbstractCandleBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CandleBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import zh.qiushui.mod.zakuro.Zakuro;

@Mixin(CandleBlock.class)
public abstract class MixinCandleBlock extends AbstractCandleBlock {
    @Shadow @Final public static IntProperty CANDLES;
    @Shadow @Final private static VoxelShape ONE_CANDLE_SHAPE;

    @Shadow @Final private static VoxelShape TWO_CANDLES_SHAPE;
    @Shadow @Final private static VoxelShape THREE_CANDLES_SHAPE;
    @Shadow @Final private static VoxelShape FOUR_CANDLES_SHAPE;
    @Unique
    private static final VoxelShape TWO_CANDLES_SHAPES = VoxelShapes.union(
            Block.createCuboidShape(9.0, 0.0, 6.0, 11.0, 6.0, 8.0),
            Block.createCuboidShape(5.0, 0.0, 7.0, 7.0, 5.0, 9.0)
    );
    @Unique
    private static final VoxelShape THREE_CANDLES_SHAPES = VoxelShapes.union(
            Block.createCuboidShape(8.0, 0.0, 6.0, 10.0, 6.0, 8.0),
            Block.createCuboidShape(5.0, 0.0, 7.0, 7.0, 5.0, 9.0),
            Block.createCuboidShape(7.0, 0.0, 9.0, 9.0, 3.0, 11.0)
    );
    @Unique
    private static final VoxelShape FOUR_CANDLES_SHAPES = VoxelShapes.union(
            Block.createCuboidShape(8.0, 0.0, 5.0, 10.0, 6.0, 7.0),
            Block.createCuboidShape(5.0, 0.0, 5.0, 7.0, 5.0, 7.0),
            Block.createCuboidShape(6.0, 0.0, 8.0, 8.0, 3.0, 10.0),
            Block.createCuboidShape(9.0, 0.0, 8.0, 11.0, 5.0, 10.0)
    );

    protected MixinCandleBlock(Settings settings) {
        super(settings);
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape zakuro$getOutlineShapeWithOriginal(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.union(
                this.getOutlineShape(state, world, pos, context),
                Zakuro.config.retainBlocksOriginalInteractableRange ? this.getOriginalOutlineShape(state) : VoxelShapes.empty()
        );
    }

    @Inject(method = "getOutlineShape(" +
            "Lnet/minecraft/block/BlockState;" +
            "Lnet/minecraft/world/BlockView;" +
            "Lnet/minecraft/util/math/BlockPos;" +
            "Lnet/minecraft/block/ShapeContext;)" +
            "Lnet/minecraft/util/shape/VoxelShape;", at = @At(value = "HEAD"), cancellable = true)
    public void getOutlineCandleShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context, CallbackInfoReturnable<VoxelShape> cir) {
        switch (state.get(CANDLES)) {
            case 1:
            default:
                cir.setReturnValue(ONE_CANDLE_SHAPE);
                break;
            case 2:
                cir.setReturnValue(TWO_CANDLES_SHAPES);
                break;
            case 3:
                cir.setReturnValue(THREE_CANDLES_SHAPES);
                break;
            case 4:
                cir.setReturnValue(FOUR_CANDLES_SHAPES);
        }
    }

    @Unique
    private VoxelShape getOriginalOutlineShape(BlockState state) {
        return switch (state.get(CANDLES)) {
            default -> ONE_CANDLE_SHAPE;
            case 2 -> TWO_CANDLES_SHAPE;
            case 3 -> THREE_CANDLES_SHAPE;
            case 4 -> FOUR_CANDLES_SHAPE;
        };
    }
}
