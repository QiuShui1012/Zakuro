package zh.qiushui.mod.zakuro.modules.mixin.fixBlocksOutlineShape.init;

import net.minecraft.block.BlockState;
import net.minecraft.block.RedstoneTorchBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.TorchBlock;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import zh.qiushui.mod.zakuro.Zakuro;

import static zh.qiushui.mod.zakuro.modules.common.fixBlocksOutlineShape.torches.FineBoundingShape.FINE_BOUNDING_SHAPE;

@Mixin(RedstoneTorchBlock.class)
public abstract class MixinRedstoneTorchBlock extends TorchBlock {
    public MixinRedstoneTorchBlock(Settings settings, ParticleEffect particle) {
        super(settings, particle);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return Zakuro.config.fixBlocksOutlineShape.blocks.torchRedstone ? FINE_BOUNDING_SHAPE : BOUNDING_SHAPE;
    }
}
