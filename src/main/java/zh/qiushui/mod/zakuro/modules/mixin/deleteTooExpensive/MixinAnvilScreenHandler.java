package zh.qiushui.mod.zakuro.modules.mixin.deleteTooExpensive;

import net.minecraft.screen.AnvilScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(AnvilScreenHandler.class)
public class MixinAnvilScreenHandler {

    @ModifyConstant(method = "updateResult", constant = @Constant(intValue = 40, ordinal = 2))
    private int updateInt(int input) {
        return Integer.MAX_VALUE;
    }

}
