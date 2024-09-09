package zh.qiushui.mod.zakuro.modules.mixin.syncFenceGatesInWallProperty;

import net.minecraft.block.BlockState;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(FenceGateBlock.class)
public abstract class MixinFenceGateBlock extends HorizontalFacingBlock {
    @Shadow public abstract boolean isWall(BlockState state);

    @Shadow public abstract boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type);

    @Shadow @Final public static BooleanProperty IN_WALL;

    protected MixinFenceGateBlock(Settings settings) {
        super(settings);
    }

    @ModifyVariable(method = "getStateForNeighborUpdate(" +
            "Lnet/minecraft/block/BlockState;" +
            "Lnet/minecraft/util/math/Direction;" +
            "Lnet/minecraft/block/BlockState;" +
            "Lnet/minecraft/world/WorldAccess;" +
            "Lnet/minecraft/util/math/BlockPos;" +
            "Lnet/minecraft/util/math/BlockPos;)" +
            "Lnet/minecraft/block/BlockState;", at = @At("STORE"), name = "bl")
    private boolean isInWall(boolean bl, BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return neighborIsWall(neighborState, world, pos, direction) ||
                neighborIsInWall(neighborState, world, pos, direction);
    }

    @Unique
    private boolean neighborIsWall(BlockState neighborState, WorldAccess world, BlockPos pos, Direction direction) {
        return this.isWall(neighborState) || this.isWall(world.getBlockState(pos.offset(direction.getOpposite())));
    }

    @Unique
    private boolean isGate(BlockState neighborState) {
        return neighborState.isIn(BlockTags.FENCE_GATES);
    }

    @Unique
    private boolean neighborIsInWall(BlockState neighborState, WorldAccess world, BlockPos pos, Direction direction) {
        BlockState mayNeighborState = world.getBlockState(pos.offset(direction.getOpposite()));
        if (this.isGate(neighborState) && neighborState.get(FACING).rotateYClockwise().getAxis() == direction.getAxis()) {
            return neighborState.get(IN_WALL);
        } else if (this.isGate(mayNeighborState) && mayNeighborState.get(FACING).rotateYClockwise().getAxis() == direction.getAxis()) {
            return mayNeighborState.get(IN_WALL);
        } else {
            return false;
        }

    }
}
