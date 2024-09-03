package zh.qiushui.mod.zakuro.api.module;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import zh.qiushui.mod.zakuro.ZakuroUtil;

import java.util.Locale;

public abstract class ModuleInfo {
    protected final String rawModuleId;
    protected final Identifier moduleId;
    protected final String moduleNameTranslationKey;
    protected final String moduleDescriptionTranslationKey;

    public ModuleInfo(String moduleId) {
        this.rawModuleId = moduleId;
        this.moduleId = ZakuroUtil.id(moduleId.toLowerCase(Locale.ROOT));
        this.moduleNameTranslationKey = "text.autoconfig." + ZakuroUtil.MOD_ID + ".option.modules." + moduleId;
        this.moduleDescriptionTranslationKey = "text.autoconfig." + ZakuroUtil.MOD_ID + ".option.modules." + moduleId + ".@Tooltip";
    }

    public Identifier getModuleId() {
        return moduleId;
    }

    public Text getModuleName() {
        return Text.translatable(moduleNameTranslationKey);
    }

    public Text getModuleDescription() {
        return Text.translatable(moduleDescriptionTranslationKey);
    }

    public void initEnglishTranslation(FabricLanguageProvider.TranslationBuilder builder) {
        initEnglishModuleName(builder);
        initEnglishModuleDescription(builder);
    }
    public void initEnglishModuleName(FabricLanguageProvider.TranslationBuilder builder) {
        builder.add(this.moduleNameTranslationKey, getRawModuleId());
    }

    public abstract void initEnglishModuleDescription(FabricLanguageProvider.TranslationBuilder builder);

    public String getRawModuleId() {
        return this.rawModuleId;
    }
}
