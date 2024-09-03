package zh.qiushui.mod.zakuro;

import net.fabricmc.api.ModInitializer;

public class Zakuro implements ModInitializer {
	@Override
	public void onInitialize() {
		ZakuroUtil.LOGGER.info("Initializing...");
		ZakuroUtil.initConfig();
		ZakuroUtil.LOGGER.info("Complete!");
	}
}