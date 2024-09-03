package zh.qiushui.mod.zakuro;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zh.qiushui.mod.zakuro.config.ZakuroConfig;

public class ZakuroUtil {
    public static final String MOD_ID = "zakuro";
    public static final String MOD_NAME = "Zakuro";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);
    public static ZakuroConfig config = null;

    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }

    public static Identifier idVanilla(String path) {
        return Identifier.of("minecraft", path);
    }

    public static void initConfig() {
        try {
            AutoConfig.register(ZakuroConfig.class, Toml4jConfigSerializer::new);
            ZakuroUtil.config = AutoConfig.getConfigHolder(ZakuroConfig.class).getConfig();
        } catch (RuntimeException e) {
            ZakuroUtil.LOGGER.warn("Zakuro Config is already registered, pass.");
        }
    }
}
