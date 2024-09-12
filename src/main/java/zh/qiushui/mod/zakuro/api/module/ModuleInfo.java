package zh.qiushui.mod.zakuro.api.module;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import zh.qiushui.mod.zakuro.Zakuro;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public abstract class ModuleInfo {
    protected final String rawModuleId;
    protected final String[] rawModuleDescription;
    protected final String moduleAbbreviate;
    protected final Identifier moduleId;
    protected final Map<String, Boolean> configs = new HashMap<>();
    public final String moduleNameTranslationKey;
    public final String[] moduleDescriptionTranslationKey;

    public ModuleInfo(String moduleId, String... moduleDesc) {
        this.rawModuleId = moduleId;
        this.rawModuleDescription = moduleDesc;
        this.moduleAbbreviate = moduleId.charAt(0) + Zakuro.separateStringUpperAndLower(moduleId).getLeft();
        this.moduleId = Zakuro.id(moduleId.toLowerCase(Locale.ROOT));
        this.moduleNameTranslationKey = Zakuro.buildTranslationKey("text.autoconfig.", ".option." + moduleId);

        int length = moduleDesc.length;
        this.moduleDescriptionTranslationKey = new String[length];
        for (int i = 0; i < length; i++) {
            this.moduleDescriptionTranslationKey[i] = Zakuro.buildTranslationKey("text.autoconfig.", ".option." + moduleId + ".@Tooltip" + (length == 1 ? "" : "[" + i + "]"));
        }

        if (Zakuro.config != null) {
            this.registerConfigs();
        }
    }

    public Identifier getModuleId() {
        return moduleId;
    }

    public Text getModuleName() {
        return Text.translatable(moduleNameTranslationKey);
    }

    public Text getModuleDescription(int index) {
        return Text.translatable(moduleDescriptionTranslationKey[index]);
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
        for (int i = 0; i < this.rawModuleDescription.length; i++) {
            builder.add(this.moduleDescriptionTranslationKey[i], getRawModuleDescription(i));
        }
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
        return StringUtils.join(this.rawModuleDescription);
    }

    public String getRawModuleDescription(int index) {
        return this.rawModuleDescription[index];
    }

    public String[] getRawModuleDescriptions() {
        return this.rawModuleDescription;
    }

    public String getModuleAbbreviate() {
        return this.moduleAbbreviate;
    }

    public void appendHtml(Element li, String htmlLangCode, String mcLangCode) {
        Element title = li.appendChild(new TextNode(Zakuro.translate(mcLangCode, this.moduleNameTranslationKey, this.getRawModuleId())));
        li.append("<br>");
        Element desc = li.appendElement("div");
        desc.id(li.id() + "Desc");
        for (int i = 0; i < this.moduleDescriptionTranslationKey.length; i++) {
            desc.appendChild(new TextNode(Zakuro.translate(mcLangCode, this.moduleDescriptionTranslationKey[i], this.getRawModuleDescription(i))));
            if (i != this.moduleDescriptionTranslationKey.length - 1) {
                desc.append("<br>");
            }
        }

        this.appendHtmlExtra(li, htmlLangCode, mcLangCode);
    }

    public void appendHtmlExtra(Element tBody, String htmlLangCode, String mcLangCode) {}
}
