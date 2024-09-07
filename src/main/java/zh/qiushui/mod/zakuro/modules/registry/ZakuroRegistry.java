package zh.qiushui.mod.zakuro.modules.registry;

import zh.qiushui.mod.core.registry.PackagedRegistry;
import zh.qiushui.mod.zakuro.Zakuro;

public class ZakuroRegistry {
    public static final PackagedRegistry REGISTER = new PackagedRegistry(Zakuro::id);

    public static void initialize() {
        Zakuro.LOGGER.info("Start registering registrable things...");
        ZakuroItems.initialize();
        Zakuro.LOGGER.info("Complete registering!");
    }
}
