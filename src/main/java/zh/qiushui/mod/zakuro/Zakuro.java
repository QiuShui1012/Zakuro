package zh.qiushui.mod.zakuro;

import net.fabricmc.api.ModInitializer;
import zh.qiushui.mod.zakuro.modules.registry.ZakuroRegistry;

public class Zakuro implements ModInitializer {
	@Override
	public void onInitialize() {
		ZakuroUtil.LOGGER.info("Initializing...");
		ZakuroUtil.initConfig();
		ZakuroRegistry.initialize();
		ZakuroUtil.LOGGER.info("Complete!");
	}
}