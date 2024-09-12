package zh.qiushui.mod.zakuro.init.initializer;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import zh.qiushui.mod.zakuro.init.ZakuroKeyBindings;

@Environment(EnvType.CLIENT)
public class ZakuroClientInitializer implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ZakuroKeyBindings.registerAll();
    }
}
