package zh.qiushui.mod.zakuro.modules.mixin.fixBlocksOutlineShape.init;

import net.minecraft.block.AbstractRedstoneGateBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RepeaterBlock;
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
import zh.qiushui.mod.zakuro.Zakuro;

@SuppressWarnings("deprecation")
@Mixin(RepeaterBlock.class)
public abstract class MixinRepeaterBlock extends AbstractRedstoneGateBlock {
    @Shadow @Final public static IntProperty DELAY;
    @Unique private static final double minYTorch = 2.0;
    @Unique private static final double maxYTorch = 7.0;

    @Unique private static final VoxelShape[][] outlineShapes = createOutlineShapes();

    protected MixinRepeaterBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return Zakuro.config.fixBlocksOutlineShape.blocks.repeater ? SHAPE : outlineShapes[state.get(FACING).getHorizontal()][state.get(DELAY) - 1];
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Unique
    private static VoxelShape[][] createOutlineShapes() {
        VoxelShape[][] bases = createOutlineShapesBases();
        VoxelShape[][] delays = createOutlineShapesDelays();

        VoxelShape[][] result = new VoxelShape[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result[i][j] = VoxelShapes.union(
                        bases[i][j],
                        delays[j][i]
                );
            }
        }

        return result;
    }

    @Unique
    private static VoxelShape[][] createOutlineShapesBases() {
        double minXTorchNorth = 7.0;
        double minZTorchNorth = 12.0;

        double minXTorchEast  = 16.0 - (minZTorchNorth + 2.0);
        double minZTorchEast  = minXTorchNorth;

        double minXTorchSouth = 16.0 - (minXTorchNorth + 2.0);
        double minZTorchSouth = 16.0 - (minZTorchNorth + 2.0);

        double minXTorchWest  = minZTorchNorth;
        double minZTorchWest  = 16.0 - (minXTorchNorth + 2.0);

        return new VoxelShape[][]{
                createDirectionOutlineShapesBases(minXTorchSouth, minZTorchSouth),
                createDirectionOutlineShapesBases(minXTorchWest, minZTorchWest),
                createDirectionOutlineShapesBases(minXTorchNorth, minZTorchNorth),
                createDirectionOutlineShapesBases(minXTorchEast, minZTorchEast),
        };
    }

    @Unique
    private static VoxelShape[] createDirectionOutlineShapesBases(double minXTorch, double minZTorch) {
        return new VoxelShape[]{
                VoxelShapes.union(
                        SHAPE,
                        Block.createCuboidShape(minXTorch, minYTorch, minZTorch, minXTorch + 2, maxYTorch, minZTorch + 2)
                ),
                VoxelShapes.union(
                        SHAPE,
                        Block.createCuboidShape(minXTorch, minYTorch, minZTorch, minXTorch + 2, maxYTorch, minZTorch + 2)
                ),
                VoxelShapes.union(
                        SHAPE,
                        Block.createCuboidShape(minXTorch, minYTorch, minZTorch, minXTorch + 2, maxYTorch, minZTorch + 2)
                ),
                VoxelShapes.union(
                        SHAPE,
                        Block.createCuboidShape(minXTorch, minYTorch, minZTorch, minXTorch + 2, maxYTorch, minZTorch + 2)
                ),
        };
    }

    @Unique
    private static VoxelShape[][] createOutlineShapesDelays() {
        return new VoxelShape[][]{
                createDirectionOutlineShapesTorches(0),
                createDirectionOutlineShapesTorches(1),
                createDirectionOutlineShapesTorches(2),
                createDirectionOutlineShapesTorches(3),
        };
    }

    @Unique
    private static VoxelShape[] createDirectionOutlineShapesTorches(int delay) {
        double minXTorchNorth = 7.0;
        double minZTorchNorth = 8.0 - (delay * 2.0);

        double minXTorchEast  = 16.0 - (minZTorchNorth + 2.0);
        double minZTorchEast  = minXTorchNorth;

        double minXTorchSouth = 16.0 - (minXTorchNorth + 2.0);
        double minZTorchSouth = 16.0 - (minZTorchNorth + 2.0);

        double minXTorchWest  = minZTorchNorth;
        double minZTorchWest  = 16.0 - (minXTorchNorth + 2.0);

        return new VoxelShape[]{
                Block.createCuboidShape(minXTorchSouth, minYTorch, minZTorchSouth, minXTorchSouth + 2, maxYTorch, minZTorchSouth + 2),
                Block.createCuboidShape(minXTorchWest, minYTorch, minZTorchWest, minXTorchWest + 2, maxYTorch, minZTorchWest + 2),
                Block.createCuboidShape(minXTorchNorth, minYTorch, minZTorchNorth, minXTorchNorth + 2, maxYTorch, minZTorchNorth + 2),
                Block.createCuboidShape(minXTorchEast, minYTorch, minZTorchEast, minXTorchEast + 2, maxYTorch, minZTorchEast + 2),
        };
    }
}
