package zh.qiushui.mod.zakuro.modules.registry;

import net.minecraft.item.Item;
import zh.qiushui.mod.core.registry.PackagedRegistry;
import zh.qiushui.mod.zakuro.Zakuro;
import zh.qiushui.mod.zakuro.ZakuroUtil;

public class ZakuroRegistry {
    public static final PackagedRegistry REGISTER = new PackagedRegistry(ZakuroUtil::id);

    public static void initialize() {
        ZakuroUtil.LOGGER.info("Start registering registrable things...");
        ZakuroItems.initialize();
        ZakuroUtil.LOGGER.info("Complete registering!");
    }
}
