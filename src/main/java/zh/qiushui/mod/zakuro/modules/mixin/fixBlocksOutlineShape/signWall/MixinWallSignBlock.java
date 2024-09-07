package zh.qiushui.mod.zakuro.modules.mixin.fixBlocksOutlineShape.signWall;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.block.AbstractSignBlock;
import net.minecraft.block.Block;
import net.minecraft.block.WallSignBlock;
import net.minecraft.block.WoodType;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Map;

@Mixin(WallSignBlock.class)
public abstract class MixinWallSignBlock extends AbstractSignBlock {
    @Unique
    private static final Map<Direction, VoxelShape> FIXED_FACING_TO_SHAPE = Maps.newEnumMap(
            ImmutableMap.of(
                    Direction.NORTH,
                    Block.createCuboidShape( 0.0, 4.0 + 1.0 / 3.0, 14.0 + 1.0 / 3.0, 16.0, 12.0 + 1.0 / 3.0, 16.0 - 1.0 / 3.0),
                    Direction.SOUTH,
                    Block.createCuboidShape(0.0, 4.0 + 1.0 / 3.0, 1.0 / 3.0, 16.0, 12.0 + 1.0 / 3.0, 2.0 - 1.0 / 3.0),
                    Direction.EAST,
                    Block.createCuboidShape( 1.0 / 3.0, 4.0 + 1.0 / 3.0, 0.0, 2.0 - 1.0 / 3.0, 12.0 + 1.0 / 3.0, 16.0),
                    Direction.WEST,
                    Block.createCuboidShape(14.0 + 1.0 / 3.0, 4.0 + 1.0 / 3.0, 0.0, 16.0 - 1.0 / 3.0, 12.0 + 1.0 / 3.0, 16.0)
            )
    );

    protected MixinWallSignBlock(Settings settings, WoodType type) {
        super(settings, type);
    }

    @Redirect(method = "getOutlineShape(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/ShapeContext;)Lnet/minecraft/util/shape/VoxelShape;", at = @At(value = "FIELD", target = "Lnet/minecraft/block/WallSignBlock;FACING_TO_SHAPE:Ljava/util/Map;", opcode = Opcodes.GETSTATIC))
    public Map<Direction, VoxelShape> getFacingToShape() {
        return FIXED_FACING_TO_SHAPE;
    }
}
