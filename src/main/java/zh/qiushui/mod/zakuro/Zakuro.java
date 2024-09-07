package zh.qiushui.mod.zakuro;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zh.qiushui.mod.core.QSCoreUtil;
import zh.qiushui.mod.zakuro.config.ZakuroConfig;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;

public class Zakuro {
    public static final String MOD_ID = "zakuro";
    public static final String MOD_NAME = "Zakuro";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);
    public static ZakuroConfig config = null;

    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }

    public static String buildTranslationKey(String prefix, String suffix) {
        return QSCoreUtil.buildCustomTranslationKey(MOD_ID, prefix, suffix);
    }

    public static void initConfig() {
        try {
            AutoConfig.register(ZakuroConfig.class, Toml4jConfigSerializer::new);
            Zakuro.config = AutoConfig.getConfigHolder(ZakuroConfig.class).getConfig();
        } catch (RuntimeException e) {
            Zakuro.LOGGER.warn("Zakuro Config is already registered, pass.");
        }
    }

    public static String translate(String code, String translationKey, @Nullable String defaultValue) {
        File languageFile = new File("src/main/" + (code.equals("en_us") ? "generated" : "resources") + "/assets/zakuro/lang/" + code + ".json");

        Gson gson = new Gson();

        try (InputStream is = Files.newInputStream(languageFile.toPath())) {
            JsonObject jsonObject = gson.fromJson(new InputStreamReader(is), JsonObject.class);

            return jsonObject.get(translationKey).getAsString();
        } catch (IOException e) {
            if (defaultValue != null) {
                return defaultValue;
            } else {
                throw new RuntimeException(e);
            }
        }
    }

    public static Pair<String, String> separateStringUpperAndLower(String value) {
        StringBuilder upper = new StringBuilder();
        StringBuilder lower = new StringBuilder();

        for (char c : value.toCharArray()) {
            if (Character.isUpperCase(c)) {
                upper.append(c);
            } else {
                lower.append(c);
            }
        }

        return new Pair<>(upper.toString(), lower.toString());
    }
}
