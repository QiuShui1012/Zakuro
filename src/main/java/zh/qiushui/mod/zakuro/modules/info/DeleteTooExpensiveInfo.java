package zh.qiushui.mod.zakuro.modules.info;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import zh.qiushui.mod.zakuro.api.module.ModuleInfo;

public class DeleteTooExpensiveInfo extends ModuleInfo {
    public DeleteTooExpensiveInfo() {
        super("deleteTooExpensive");
    }

    @Override
    public void initEnglishModuleDescription(FabricLanguageProvider.TranslationBuilder builder) {
        builder.add(this.moduleDescriptionTranslationKey, "This module removes the \"Too Expensive!\" tip on the anvil and keeps the penalty level on the item.");
    }
}
