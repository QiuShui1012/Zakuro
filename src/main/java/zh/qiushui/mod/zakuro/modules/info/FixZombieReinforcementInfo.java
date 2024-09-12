package zh.qiushui.mod.zakuro.modules.info;

import zh.qiushui.mod.zakuro.Zakuro;
import zh.qiushui.mod.zakuro.api.module.ModuleInfo;

public class FixZombieReinforcementInfo extends ModuleInfo {
    public FixZombieReinforcementInfo() {
        super("fixZombieReinforcement",
                "This module fixes the zombie reinforcement and allows zombies and their variants to spawn clones of themselves, just like Minecraft 1.21.2 version does.");
    }

    @Override
    protected void registerConfigs() {
        configs.put(this.rawModuleId, Zakuro.config.fixZombieReinforcement);
    }
}
