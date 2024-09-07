package zh.qiushui.mod.zakuro.modules.mixin.fixBlocksOutlineShape.sign;

import net.minecraft.block.AbstractSignBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.SignBlock;
import net.minecraft.block.WoodType;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(SignBlock.class)
public abstract class MixinSignBlock extends AbstractSignBlock {
    @Shadow @Final public static IntProperty ROTATION;
    @Unique
    private static final VoxelShape BAR_SHAPE = Block.createCuboidShape(7.0 + 1.0 / 3.0, 0.0, 7.0 + 1.0 / 3.0,
            9.0 - 1.0 / 3.0, 9.0 + 1.0 / 3.0, 9.0 - 1.0 / 3.0);
    @Unique
    private static final VoxelShape X_AXIS_SHAPE = buildSign(0.0, 7.0 + 1.0 / 3.0, 16.0, 1.0 + 1.0 / 3.0);
    @Unique
    private static final VoxelShape Z_AXIS_SHAPE = buildSign(7.0 + 1.0 / 3.0, 0.0, 1.0 + 1.0 / 3.0, 16.0);

    protected MixinSignBlock(Settings settings, WoodType type) {
        super(settings, type);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(ROTATION)) {
            default -> SHAPE;
            case 0, 8 -> X_AXIS_SHAPE;
            case 4, 12 -> Z_AXIS_SHAPE;
        };
    }

    @Unique
    private static VoxelShape buildSign(double minXBoard, double minZBoard, double widthX, double widthZ) {
        return VoxelShapes.union(
                BAR_SHAPE,
                buildPartOfSign(minXBoard, minZBoard, widthX, widthZ)
        );
    }

    @Unique
    private static VoxelShape buildPartOfSign(double minXBoard, double minZBoard, double widthX, double widthZ) {
        return Block.createCuboidShape(minXBoard, 9.0 + 1.0 / 3.0, minZBoard,
                minXBoard + widthX, 17.0 + 1.0 / 3.0, minZBoard + widthZ);
    }
}
