package zh.qiushui.mod.zakuro.modules.mixin.fixBlocksOutlineShape.init;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.TorchBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import zh.qiushui.mod.zakuro.Zakuro;

import static zh.qiushui.mod.zakuro.modules.common.fixBlocksOutlineShape.torches.FineBoundingShape.FINE_BOUNDING_SHAPE;

@Mixin(TorchBlock.class)
public abstract class MixinTorchBlock extends AbstractBlock {
    @Shadow @Final protected static VoxelShape BOUNDING_SHAPE;

    public MixinTorchBlock(Settings settings) {
        super(settings);
    }

    @Redirect(method = "getOutlineShape(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/ShapeContext;)Lnet/minecraft/util/shape/VoxelShape;", at = @At(value = "FIELD", target = "Lnet/minecraft/block/TorchBlock;BOUNDING_SHAPE:Lnet/minecraft/util/shape/VoxelShape;"))
    public VoxelShape getBoundingShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return Zakuro.config.fixBlocksOutlineShape.blocks.torch ? FINE_BOUNDING_SHAPE : BOUNDING_SHAPE;
    }

    @Override
    public VoxelShape zakuro$getOutlineShapeWithOriginal(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return Zakuro.config.retainBlocksOriginalInteractableRange ? BOUNDING_SHAPE : FINE_BOUNDING_SHAPE;
    }
}
