package zh.qiushui.mod.zakuro.api.module;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import zh.qiushui.mod.zakuro.ZakuroUtil;

import java.util.Locale;

public abstract class ModuleInfo {
    protected final String rawModuleId;
    protected final String rawModuleDescription;
    protected final String abbreviate;
    protected final Identifier moduleId;
    public final String moduleNameTranslationKey;
    public final String moduleDescriptionTranslationKey;

    public ModuleInfo(String moduleId, String moduleDesc) {
        this.rawModuleId = moduleId;
        this.rawModuleDescription = moduleDesc;
        this.abbreviate = moduleId.charAt(0) + ZakuroUtil.separateStringUpperAndLower(moduleId).getLeft();
        this.moduleId = ZakuroUtil.id(moduleId.toLowerCase(Locale.ROOT));
        this.moduleNameTranslationKey = ZakuroUtil.buildTranslationKey("text.autoconfig.", ".option.modules." + moduleId);
        this.moduleDescriptionTranslationKey = ZakuroUtil.buildTranslationKey("text.autoconfig.", ".option.modules." + moduleId + ".@Tooltip");
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

    public void initEnglishModuleDescription(FabricLanguageProvider.TranslationBuilder builder) {
        builder.add(this.moduleDescriptionTranslationKey, getRawModuleDescription());
    }

    public String getRawModuleId() {
        return this.rawModuleId;
    }

    public String getRawModuleDescription() {
        return this.rawModuleDescription;
    }

    public String getModuleAbbreviate() {
        return this.abbreviate;
    }
}
