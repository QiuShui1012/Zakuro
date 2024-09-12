package zh.qiushui.mod.zakuro.init.initializer;

import net.fabricmc.api.ModInitializer;
import zh.qiushui.mod.zakuro.Zakuro;
import zh.qiushui.mod.zakuro.init.ZakuroRegistry;
import zh.qiushui.mod.zakuro.modules.common.common.ModulesCommon;

public class ZakuroCommonInitializer implements ModInitializer {
    @Override
    public void onInitialize() {
        Zakuro.LOGGER.info("Initializing...");
        Zakuro.initConfig();
        ZakuroRegistry.init();
        ModulesCommon.init();
        Zakuro.LOGGER.info("Complete!");
    }
}
