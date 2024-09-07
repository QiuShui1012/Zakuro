package zh.qiushui.mod.zakuro.api.module;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import zh.qiushui.mod.zakuro.Zakuro;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public abstract class ModuleInfo {
    protected final String rawModuleId;
    protected final String rawModuleDescription;
    protected final String moduleAbbreviate;
    protected final Identifier moduleId;
    protected final Map<String, Boolean> configs = new HashMap<>();
    public final String moduleNameTranslationKey;
    public final String moduleDescriptionTranslationKey;

    public ModuleInfo(String moduleId, String moduleDesc) {
        this.rawModuleId = moduleId;
        this.rawModuleDescription = moduleDesc;
        this.moduleAbbreviate = moduleId.charAt(0) + Zakuro.separateStringUpperAndLower(moduleId).getLeft();
        this.moduleId = Zakuro.id(moduleId.toLowerCase(Locale.ROOT));
        this.moduleNameTranslationKey = Zakuro.buildTranslationKey("text.autoconfig.", ".option.modules." + moduleId);
        this.moduleDescriptionTranslationKey = Zakuro.buildTranslationKey("text.autoconfig.", ".option.modules." + moduleId + ".@Tooltip");
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
        initEnglishModuleExtra(builder);
    }

    public void initEnglishModuleName(FabricLanguageProvider.TranslationBuilder builder) {
        builder.add(this.moduleNameTranslationKey, getRawModuleId());
    }
    public void initEnglishModuleDescription(FabricLanguageProvider.TranslationBuilder builder) {
        builder.add(this.moduleDescriptionTranslationKey, getRawModuleDescription());
    }
    public void initEnglishModuleExtra(FabricLanguageProvider.TranslationBuilder builder) {
    }

    protected abstract void registerConfigs();

    public boolean shouldApplyMixin(String name) {
        return configs.get(name);
    }

    public String getRawModuleId() {
        return this.rawModuleId;
    }

    public String getRawModuleDescription() {
        return this.rawModuleDescription;
    }

    public String getModuleAbbreviate() {
        return this.moduleAbbreviate;
    }

    public void appendHtmlExtra(Element tBody, String htmlLangCode, String mcLangCode) {
        Element tRModule = tBody.appendElement("tr");
            Element tDName = tRModule.appendElement("td");
            tDName.addClass("moduleName");
            tDName.id(tBody.id() + "Name");
            tDName.appendChild(new TextNode(Zakuro.translate(mcLangCode, this.moduleNameTranslationKey, this.getRawModuleId())));
            Element tDDesc = tRModule.appendElement("td");
            tDDesc.addClass("moduleDesc");
            tDDesc.id(tBody.id() + "Desc");
            tDDesc.appendChild(new TextNode(Zakuro.translate(mcLangCode, this.moduleDescriptionTranslationKey, this.getRawModuleDescription())));
    }
}
