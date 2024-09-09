package zh.qiushui.mod.zakuro.modules.mixin.fixBlocksOutlineShape.fenceGate;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.state.property.BooleanProperty;
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
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import zh.qiushui.mod.zakuro.Zakuro;

@Mixin(FenceGateBlock.class)
public abstract class MixinFenceGateBlock extends HorizontalFacingBlock {
    @Shadow @Final public static BooleanProperty IN_WALL;
    @Shadow @Final public static BooleanProperty OPEN;

    @Unique private final VoxelShape[] gateShapes = this.createFenceGateShapes(false, false, false);
    @Unique private final VoxelShape[] openGateShapes = this.createFenceGateShapes(true, false, false);
    @Unique private final VoxelShape[] collisionGateShapes = this.createFenceGateShapes(false, false, true);

    @Unique private final VoxelShape[] inWallGateShapes = this.createFenceGateShapes(false, true, false);
    @Unique private final VoxelShape[] inWallOpenGateShapes = this.createFenceGateShapes(true, true, false);
    @Unique private final VoxelShape[] inWallCollisionGateShapes = this.createFenceGateShapes(false, true, true);

    protected MixinFenceGateBlock(Settings settings) {
        super(settings);
    }

    @Inject(method = "getOutlineShape(" +
            "Lnet/minecraft/block/BlockState;" +
            "Lnet/minecraft/world/BlockView;" +
            "Lnet/minecraft/util/math/BlockPos;" +
            "Lnet/minecraft/block/ShapeContext;)" +
            "Lnet/minecraft/util/shape/VoxelShape;", at = @At("HEAD"), cancellable = true)
    public void getOutlineGateShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context, CallbackInfoReturnable<VoxelShape> cir) {
        int facing = state.get(FACING).getHorizontal() + 1;
        if (state.get(IN_WALL)) {
            cir.setReturnValue(state.get(OPEN) ? inWallOpenGateShapes[facing] : inWallGateShapes[facing]);
        } else {
            cir.setReturnValue(state.get(OPEN) ? openGateShapes[facing] : gateShapes[facing]);
        }
    }

    @Inject(method = "getCollisionShape(" +
            "Lnet/minecraft/block/BlockState;" +
            "Lnet/minecraft/world/BlockView;" +
            "Lnet/minecraft/util/math/BlockPos;" +
            "Lnet/minecraft/block/ShapeContext;)" +
            "Lnet/minecraft/util/shape/VoxelShape;", at = @At("HEAD"), cancellable = true)
    public void getCollisionGateShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context, CallbackInfoReturnable<VoxelShape> cir) {
        int facing = state.get(FACING).getHorizontal() + 1;
        if (state.get(IN_WALL)) {
            cir.setReturnValue(state.get(OPEN) ? VoxelShapes.empty() : inWallCollisionGateShapes[facing]);
        } else {
            cir.setReturnValue(state.get(OPEN) ? VoxelShapes.empty() : collisionGateShapes[facing]);
        }
    }

    @Unique
    protected VoxelShape[] createFenceGateShapes(boolean isOpen, boolean isInWall, boolean isCollision) {
        double inWallDecrease = isInWall ? 3.0 : 0.0;

        VoxelShape[] posts = this.createFenceGatePostShapes(inWallDecrease, isCollision);

        VoxelShape[] middleOrOpen = isOpen ?
                this.createFenceGateMiddleOpenings(inWallDecrease, isCollision) :
                this.createFenceGateMiddleNotOpening(inWallDecrease, isCollision);

        VoxelShape[] shapes = new VoxelShape[5];
        shapes[0] = VoxelShapes.empty();

        for (int i = 0; i < 4; i++) {
            shapes[i+1] = VoxelShapes.union(
                    posts[(int) Math.floor(i/2.0)],
                    middleOrOpen[isOpen ? i : (int) Math.floor(i/2.0)]);
        }

        return shapes;
    }

    @Unique
    protected VoxelShape[] createFenceGatePostShapes(double inWallDecrease, boolean isCollision) {
        double minYPost = 5.0 - inWallDecrease;
        double maxYPost = Zakuro.calculateOneAndAHalfHeightBlockMaxY(isCollision, 16.0 - inWallDecrease);

        VoxelShape postLeftX = Block.createCuboidShape(0.0, minYPost, 7.0, 2.0, maxYPost, 9.0);
        VoxelShape postRightX = Block.createCuboidShape(14.0, minYPost, 7.0, 16.0, maxYPost, 9.0);
        VoxelShape postLeftZ = Block.createCuboidShape(7.0, minYPost, 0.0, 9.0, maxYPost, 2.0);
        VoxelShape postRightZ = Block.createCuboidShape(7.0, minYPost, 14.0, 9.0, maxYPost, 16.0);

        return new VoxelShape[] {
                VoxelShapes.union(postLeftX, postRightX),
                VoxelShapes.union(postLeftZ, postRightZ),
        };
    }

    @Unique
    protected VoxelShape[] createFenceGateMiddleNotOpening(double inWallDecrease, boolean isCollision) {
        double minYMiddleBarLower = 6.0 - inWallDecrease;
        double maxYMiddleBarLower = Zakuro.calculateOneAndAHalfHeightBlockMaxY(isCollision, minYMiddleBarLower + 3.0);
        double minYMiddleBarUpper = 12.0 - inWallDecrease;
        double maxYMiddleBarUpper = Zakuro.calculateOneAndAHalfHeightBlockMaxY(isCollision, minYMiddleBarUpper + 3.0);

        VoxelShape middleBarLowerX = Block.createCuboidShape(
                2.0, minYMiddleBarLower, 7.0,
                14.0, maxYMiddleBarLower, 9.0
        );
        VoxelShape middleBarUpperX = Block.createCuboidShape(
                2.0, minYMiddleBarUpper, 7.0,
                14.0, maxYMiddleBarUpper, 9.0
        );
        VoxelShape middleBarLowerZ = Block.createCuboidShape(
                7.0, minYMiddleBarLower, 2.0,
                9.0, maxYMiddleBarLower, 14.0
        );
        VoxelShape middleBarUpperZ = Block.createCuboidShape(
                7.0, minYMiddleBarUpper, 2.0,
                9.0, maxYMiddleBarUpper, 14.0
        );

        double minYMiddlePost = 9.0 - inWallDecrease;
        double maxYMiddlePost = Zakuro.calculateOneAndAHalfHeightBlockMaxY(isCollision, minYMiddlePost + 3.0);

        VoxelShape middlePostX = Block.createCuboidShape(
                6.0, minYMiddlePost, 7.0,
                10.0, maxYMiddlePost, 9.0
        );
        VoxelShape middlePostZ = Block.createCuboidShape(
                7.0, minYMiddlePost, 6.0,
                9.0, maxYMiddlePost, 10.0
        );

        return new VoxelShape[] {
                VoxelShapes.union(middleBarLowerX, middleBarUpperX, middlePostX),
                VoxelShapes.union(middleBarLowerZ, middleBarUpperZ, middlePostZ)
        };
    }

    @Unique
    protected VoxelShape[] createFenceGateMiddleOpenings(double inWallDecrease, boolean isCollision) {
        return new VoxelShape[] {
                createFenceGateMiddleOpeningZ(1.0 ,1.0, inWallDecrease, isCollision),
                createFenceGateMiddleOpeningX(1.0 ,1.0, inWallDecrease, isCollision),
                createFenceGateMiddleOpeningZ(9.0 ,13.0, inWallDecrease, isCollision),
                createFenceGateMiddleOpeningX(9.0 ,13.0, inWallDecrease, isCollision)
        };
    }

    @Unique
    protected VoxelShape createFenceGateMiddleOpeningX(double minZBar, double minZPost, double inWallDecrease, boolean isCollision) {
        double minYMiddleBarLower = 6.0 - inWallDecrease;
        double maxYMiddleBarLower = Zakuro.calculateOneAndAHalfHeightBlockMaxY(isCollision, minYMiddleBarLower + 3.0);
        double minYMiddleBarUpper = 12.0 - inWallDecrease;
        double maxYMiddleBarUpper = Zakuro.calculateOneAndAHalfHeightBlockMaxY(isCollision, minYMiddleBarUpper + 3.0);

        double minYMiddlePost = 9.0 - inWallDecrease;
        double maxYMiddlePost = Zakuro.calculateOneAndAHalfHeightBlockMaxY(isCollision, minYMiddlePost + 3.0);

        double maxZBar = minZBar + 6.0;
        double maxZPost = minZPost + 2.0;

        VoxelShape middleBarLowerL = Block.createCuboidShape(
                0.0, minYMiddleBarLower, minZBar,
                2.0, maxYMiddleBarLower, maxZBar
        );
        VoxelShape middleBarUpperL = Block.createCuboidShape(
                0.0, minYMiddleBarUpper, minZBar,
                2.0, maxYMiddleBarUpper, maxZBar
        );
        VoxelShape middleBarLowerR = Block.createCuboidShape(
                14.0, minYMiddleBarLower, minZBar,
                16.0, maxYMiddleBarLower, maxZBar
        );
        VoxelShape middleBarUpperR = Block.createCuboidShape(
                14.0, minYMiddleBarUpper, minZBar,
                16.0, maxYMiddleBarUpper, maxZBar
        );

        VoxelShape middlePostL = Block.createCuboidShape(
                0.0, minYMiddlePost, minZPost,
                2.0, maxYMiddlePost, maxZPost
        );
        VoxelShape middlePostR = Block.createCuboidShape(
                14.0, minYMiddlePost, minZPost,
                16.0, maxYMiddlePost, maxZPost
        );

        return VoxelShapes.union(
                middleBarLowerL, middleBarUpperL, middlePostL,
                middleBarLowerR, middleBarUpperR, middlePostR
        );
    }

    @Unique
    protected VoxelShape createFenceGateMiddleOpeningZ(double minXBar, double minXPost, double inWallDecrease, boolean isCollision) {
        double minYMiddleBarLower = 6.0 - inWallDecrease;
        double maxYMiddleBarLower = Zakuro.calculateOneAndAHalfHeightBlockMaxY(isCollision, minYMiddleBarLower + 3.0);
        double minYMiddleBarUpper = 12.0 - inWallDecrease;
        double maxYMiddleBarUpper = Zakuro.calculateOneAndAHalfHeightBlockMaxY(isCollision, minYMiddleBarUpper + 3.0);

        double minYMiddlePost = 9.0 - inWallDecrease;
        double maxYMiddlePost = Zakuro.calculateOneAndAHalfHeightBlockMaxY(isCollision, minYMiddlePost + 3.0);

        double maxXBar = minXBar + 6.0;
        double maxXPost = minXPost + 2.0;

        VoxelShape middleBarLowerL = Block.createCuboidShape(
                minXBar, minYMiddleBarLower, 0.0,
                maxXBar, maxYMiddleBarLower, 2.0
        );
        VoxelShape middleBarUpperL = Block.createCuboidShape(
                minXBar, minYMiddleBarUpper, 0.0,
                maxXBar, maxYMiddleBarUpper, 2.0
        );
        VoxelShape middleBarLowerR = Block.createCuboidShape(
                minXBar, minYMiddleBarLower, 14.0,
                maxXBar, maxYMiddleBarLower, 16.0
        );
        VoxelShape middleBarUpperR = Block.createCuboidShape(
                minXBar, minYMiddleBarUpper, 14.0,
                maxXBar, maxYMiddleBarUpper, 16.0
        );

        VoxelShape middlePostL = Block.createCuboidShape(
                minXPost, minYMiddlePost, 0.0,
                maxXPost, maxYMiddlePost, 2.0
        );
        VoxelShape middlePostR = Block.createCuboidShape(
                minXPost, minYMiddlePost, 14.0,
                maxXPost, maxYMiddlePost, 16.0
        );

        return VoxelShapes.union(
                middleBarLowerL, middleBarUpperL, middlePostL,
                middleBarLowerR, middleBarUpperR, middlePostR
        );
    }
}
