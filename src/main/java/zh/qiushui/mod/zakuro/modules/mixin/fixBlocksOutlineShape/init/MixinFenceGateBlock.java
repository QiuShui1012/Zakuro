package zh.qiushui.mod.zakuro.modules.mixin.fixBlocksOutlineShape.init;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.function.BooleanBiFunction;
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

    @Shadow @Final protected static VoxelShape X_AXIS_SHAPE;
    @Shadow @Final protected static VoxelShape Z_AXIS_SHAPE;
    @Shadow @Final protected static VoxelShape IN_WALL_X_AXIS_SHAPE;
    @Shadow @Final protected static VoxelShape IN_WALL_Z_AXIS_SHAPE;
    @Shadow @Final protected static VoxelShape X_AXIS_COLLISION_SHAPE;
    @Shadow @Final protected static VoxelShape Z_AXIS_COLLISION_SHAPE;
    @Unique private final VoxelShape[] gateShapes = this.createFenceGateShapes(false, false, false);
    @Unique private final VoxelShape[] openGateShapes = this.createFenceGateShapes(true, false, false);
    @Unique private final VoxelShape[] collisionGateShapes = this.createFenceGateShapes(false, false, true);

    @Unique private final VoxelShape[] inWallGateShapes = this.createFenceGateShapes(false, true, false);
    @Unique private final VoxelShape[] inWallOpenGateShapes = this.createFenceGateShapes(true, true, false);
    @Unique private final VoxelShape[] inWallCollisionGateShapes = this.createFenceGateShapes(false, true, true);

    protected MixinFenceGateBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape zakuro$getOutlineShapeWithOriginal(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.union(
                Zakuro.config.retainBlocksOriginalInteractableRange ? this.getOriginalOutlineShape(state) : VoxelShapes.empty(),
                state.getOutlineShape(world, pos, context)
        );
    }

    @Unique
    private int getFacingIndex(Direction direction) {
        return switch (direction) {
            default -> 0;
            case SOUTH -> 1;
            case NORTH -> 2;
            case EAST -> 3;
            case WEST -> 4;
        };
    }

    @Inject(method = "getOutlineShape(" +
            "Lnet/minecraft/block/BlockState;" +
            "Lnet/minecraft/world/BlockView;" +
            "Lnet/minecraft/util/math/BlockPos;" +
            "Lnet/minecraft/block/ShapeContext;)" +
            "Lnet/minecraft/util/shape/VoxelShape;", at = @At("HEAD"), cancellable = true)
    public void getOutlineGateShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context, CallbackInfoReturnable<VoxelShape> cir) {
        int facing = getFacingIndex(state.get(FACING));
        if (Zakuro.config.fixBlocksOutlineShape.blocks.fenceGate) {
            if (state.get(IN_WALL)) {
                cir.setReturnValue(state.get(OPEN) ? inWallOpenGateShapes[facing] : inWallGateShapes[facing]);
            } else {
                cir.setReturnValue(state.get(OPEN) ? openGateShapes[facing] : gateShapes[facing]);
            }
        } else {
            cir.setReturnValue(getOriginalOutlineShape(state));
        }
    }

    @Inject(method = "getCollisionShape(" +
            "Lnet/minecraft/block/BlockState;" +
            "Lnet/minecraft/world/BlockView;" +
            "Lnet/minecraft/util/math/BlockPos;" +
            "Lnet/minecraft/block/ShapeContext;)" +
            "Lnet/minecraft/util/shape/VoxelShape;", at = @At("HEAD"), cancellable = true)
    public void getCollisionGateShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context, CallbackInfoReturnable<VoxelShape> cir) {
        int facing = getFacingIndex(state.get(FACING));
        if (Zakuro.config.fixBlocksOutlineShape.blocks.fenceGate) {
            if (state.get(IN_WALL)) {
                cir.setReturnValue(state.get(OPEN) ? VoxelShapes.empty() : inWallCollisionGateShapes[facing]);
            } else {
                cir.setReturnValue(state.get(OPEN) ? VoxelShapes.empty() : collisionGateShapes[facing]);
            }
        } else {
            cir.setReturnValue(getOriginalCollisionShape(state));
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
                VoxelShapes.union(postLeftZ, postRightZ)
        };
    }

    @Unique
    protected VoxelShape[] createFenceGateMiddleNotOpening(double inWallDecrease, boolean isCollision) {
        double minYHand = 6.0 - inWallDecrease;
        double minYHandReduce = minYHand + 3.0;
        double maxYHandReduce = 12.0 - inWallDecrease;
        double maxYHand = Zakuro.calculateOneAndAHalfHeightBlockMaxY(isCollision, maxYHandReduce + 3.0);

        VoxelShape handMainX = Block.createCuboidShape(
                2.0, minYHand, 7.0,
                14.0, maxYHand, 9.0
        );
        VoxelShape handReduceLX = Block.createCuboidShape(
                2.0, minYHandReduce, 7.0,
                6.0, maxYHandReduce, 9.0
        );
        VoxelShape handReduceRX = Block.createCuboidShape(
                10.0, minYHandReduce, 7.0,
                14.0, maxYHandReduce, 9.0
        );
        VoxelShape handMainZ = Block.createCuboidShape(
                7.0, minYHand, 2.0,
                9.0, maxYHand, 14.0
        );
        VoxelShape handReduceLZ = Block.createCuboidShape(
                7.0, minYHandReduce, 2.0,
                9.0, maxYHandReduce, 6.0
        );
        VoxelShape handReduceRZ = Block.createCuboidShape(
                7.0, minYHandReduce, 10.0,
                9.0, maxYHandReduce, 14.0
        );

        return new VoxelShape[] {
                VoxelShapes.combine(
                        handMainX,
                        VoxelShapes.union(handReduceLX, handReduceRX),
                        BooleanBiFunction.ONLY_FIRST
                ),
                VoxelShapes.combine(
                        handMainZ,
                        VoxelShapes.union(handReduceLZ, handReduceRZ),
                        BooleanBiFunction.ONLY_FIRST
                )
        };
    }

    @Unique
    protected VoxelShape[] createFenceGateMiddleOpenings(double inWallDecrease, boolean isCollision) {
        return new VoxelShape[] {
                createFenceGateMiddleOpeningX(9.0 ,9.0, inWallDecrease, isCollision),
                createFenceGateMiddleOpeningX(1.0 ,3.0, inWallDecrease, isCollision),
                createFenceGateMiddleOpeningZ(9.0 ,9.0, inWallDecrease, isCollision),
                createFenceGateMiddleOpeningZ(1.0 ,3.0, inWallDecrease, isCollision),
        };
    }

    @Unique
    protected VoxelShape createFenceGateMiddleOpeningX(double minZHand, double minZHandReduce, double inWallDecrease, boolean isCollision) {
        double minYHand = 6.0 - inWallDecrease;
        double minYHandReduce = minYHand + 3.0;
        double maxYHandReduce = 12.0 - inWallDecrease;
        double maxYHand = Zakuro.calculateOneAndAHalfHeightBlockMaxY(isCollision, maxYHandReduce + 3.0);

        double maxZHand = minZHand + 6.0;
        double maxZHandReduce = minZHandReduce + 4.0;

        VoxelShape handMainL = Block.createCuboidShape(
                0.0, minYHand, minZHand,
                2.0, maxYHand, maxZHand
        );
        VoxelShape handReduceL = Block.createCuboidShape(
                0.0, minYHandReduce, minZHandReduce,
                2.0, maxYHandReduce, maxZHandReduce
        );
        VoxelShape handMainR = Block.createCuboidShape(
                14.0, minYHand, minZHand,
                16.0, maxYHand, maxZHand
        );
        VoxelShape handReduceR = Block.createCuboidShape(
                14.0, minYHandReduce, minZHandReduce,
                16.0, maxYHandReduce, maxZHandReduce
        );

        return VoxelShapes.union(
                VoxelShapes.combine(handMainL, handReduceL, BooleanBiFunction.ONLY_FIRST),
                VoxelShapes.combine(handMainR, handReduceR, BooleanBiFunction.ONLY_FIRST)
        );
    }

    @Unique
    protected VoxelShape createFenceGateMiddleOpeningZ(double minXHand, double minXHandReduce, double inWallDecrease, boolean isCollision) {
        double minYHand = 6.0 - inWallDecrease;
        double minYHandReduce = minYHand + 3.0;
        double maxYHandReduce = 12.0 - inWallDecrease;
        double maxYHand = Zakuro.calculateOneAndAHalfHeightBlockMaxY(isCollision, maxYHandReduce + 3.0);

        double maxXHand = minXHand + 6.0;
        double maxXHandReduce = minXHandReduce + 4.0;

        VoxelShape handMainL = Block.createCuboidShape(
                minXHand, minYHand, 0.0,
                maxXHand, maxYHand, 2.0
        );
        VoxelShape handReduceL = Block.createCuboidShape(
                minXHandReduce, minYHandReduce, 0.0,
                maxXHandReduce, maxYHandReduce, 2.0
        );
        VoxelShape handMainR = Block.createCuboidShape(
                minXHand, minYHand, 14.0,
                maxXHand, maxYHand, 16.0
        );
        VoxelShape handReduceR = Block.createCuboidShape(
                minXHandReduce, minYHandReduce, 14.0,
                maxXHandReduce, maxYHandReduce, 16.0
        );

        return VoxelShapes.union(
                VoxelShapes.combine(handMainL, handReduceL, BooleanBiFunction.ONLY_FIRST),
                VoxelShapes.combine(handMainR, handReduceR, BooleanBiFunction.ONLY_FIRST)
        );
    }

    @Unique
    private VoxelShape getOriginalOutlineShape(BlockState state) {
        if (state.get(IN_WALL)) {
            return state.get(FACING).getAxis() == Direction.Axis.X ? IN_WALL_X_AXIS_SHAPE : IN_WALL_Z_AXIS_SHAPE;
        } else {
            return state.get(FACING).getAxis() == Direction.Axis.X ? X_AXIS_SHAPE : Z_AXIS_SHAPE;
        }
    }

    @Unique
    private VoxelShape getOriginalCollisionShape(BlockState state) {
        if (state.get(IN_WALL)) {
            return VoxelShapes.empty();
        } else {
            return state.get(FACING).getAxis() == Direction.Axis.X ? X_AXIS_COLLISION_SHAPE : Z_AXIS_COLLISION_SHAPE;
        }
    }
}
