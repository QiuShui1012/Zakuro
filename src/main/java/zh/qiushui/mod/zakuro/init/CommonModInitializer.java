package zh.qiushui.mod.zakuro.init;

import net.fabricmc.api.ModInitializer;
import net.minecraft.block.AbstractBlock;
import net.minecraft.world.RaycastContext;
import zh.qiushui.mod.zakuro.Zakuro;
import zh.qiushui.mod.zakuro.modules.common.common.ModulesCommon;
import zh.qiushui.mod.zakuro.modules.registry.ZakuroRegistry;

public class CommonModInitializer implements ModInitializer {
    @Override
    public void onInitialize() {
        Zakuro.LOGGER.info("Initializing...");
        Zakuro.initConfig();
        ZakuroRegistry.initialize();
        ModulesCommon.init();
        Zakuro.LOGGER.info("Complete!");
    }
}
