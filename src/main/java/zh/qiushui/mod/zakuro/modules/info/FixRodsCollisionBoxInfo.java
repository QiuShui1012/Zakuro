package zh.qiushui.mod.zakuro.modules.info;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import zh.qiushui.mod.zakuro.api.module.ModuleInfo;

public class FixRodsCollisionBoxInfo extends ModuleInfo {
    public FixRodsCollisionBoxInfo() {
        super("fixRodsCollisionBox",
                "This module fixes the rods' collision box and syncs its shape to the rods' model.");
    }
}
