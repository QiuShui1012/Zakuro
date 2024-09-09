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
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.naming.OperationNotSupportedException;

@Mixin(CandleBlock.class)
public abstract class MixinCandleBlock extends AbstractCandleBlock {
    @Shadow @Final public static IntProperty CANDLES;
    @Shadow @Final private static VoxelShape ONE_CANDLE_SHAPE;

    protected MixinCandleBlock(Settings settings) {
        super(settings);
    }

    @Inject(method = "getOutlineShape(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/ShapeContext;)Lnet/minecraft/util/shape/VoxelShape;", at = @At(value = "HEAD"), cancellable = true)
    public void getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context, CallbackInfoReturnable<VoxelShape> cir) {
        switch (state.get(CANDLES)) {
            case 1:
            default: cir.setReturnValue(ONE_CANDLE_SHAPE);
            case 2: cir.setReturnValue(VoxelShapes.union(
                    Block.createCuboidShape(9.0, 0.0, 6.0, 11.0, 6.0, 8.0),
                    Block.createCuboidShape(7.0, 0.0, 5.0, 9.0, 5.0, 7.0)
            ));
            case 3: cir.setReturnValue(VoxelShapes.union(
                    Block.createCuboidShape(8.0, 0.0, 6.0, 10.0, 6.0, 8.0),
                    Block.createCuboidShape(7.0, 0.0, 5.0, 9.0, 5.0, 7.0),
                    Block.createCuboidShape(9.0, 0.0, 7.0, 11.0, 3.0, 9.0)
            ));
            case 4: cir.setReturnValue(VoxelShapes.union(
                    Block.createCuboidShape(8.0, 0.0, 5.0, 10.0, 6.0, 7.0),
                    Block.createCuboidShape(5.0, 0.0, 5.0, 7.0, 5.0, 7.0),
                    Block.createCuboidShape(6.0, 0.0, 8.0, 8.0, 3.0, 10.0),
                    Block.createCuboidShape(9.0, 0.0, 8.0, 11.0, 5.0, 10.0)
            ));
        }
    }
}
