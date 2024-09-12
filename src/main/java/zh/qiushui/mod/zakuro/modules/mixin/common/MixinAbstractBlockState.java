package zh.qiushui.mod.zakuro.modules.mixin.common;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.state.State;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import zh.qiushui.mod.zakuro.modules.common.common.StateOutlineShapeWithOriginalGetter;

@Mixin(AbstractBlock.AbstractBlockState.class)
public abstract class MixinAbstractBlockState extends State<Block, BlockState> implements StateOutlineShapeWithOriginalGetter {
    protected MixinAbstractBlockState(Block owner, ImmutableMap<Property<?>, Comparable<?>> entries, MapCodec<BlockState> codec) {
        super(owner, entries, codec);
    }

    @Shadow
    public abstract Block getBlock();

    @Shadow
    protected abstract BlockState asBlockState();

    @Override
    public VoxelShape zakuro$getOutlineShapeWithOriginal(BlockView world, BlockPos pos, ShapeContext context) {
        return this.getBlock().zakuro$getOutlineShapeWithOriginal(this.asBlockState(), world, pos, context);
    }
}
