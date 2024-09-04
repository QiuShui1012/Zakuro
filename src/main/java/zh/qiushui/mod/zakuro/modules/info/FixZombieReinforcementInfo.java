package zh.qiushui.mod.zakuro.modules.info;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import zh.qiushui.mod.zakuro.api.module.ModuleInfo;

public class FixZombieReinforcementInfo extends ModuleInfo {
    public FixZombieReinforcementInfo() {
        super("fixZombieReinforcement");
    }

    @Override
    public void initEnglishModuleDescription(FabricLanguageProvider.TranslationBuilder builder) {
        builder.add(this.moduleDescriptionTranslationKey, "This module fixes the zombie reinforcement and allows zombies and their variants to spawn clones of themselves, just like Minecraft 1.21.2 version does.");
    }
}
