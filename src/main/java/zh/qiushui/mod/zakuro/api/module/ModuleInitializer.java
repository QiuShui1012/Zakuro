package zh.qiushui.mod.zakuro.api.module;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public abstract class ModuleInitializer {
    public void initialize() {
        this.onInitialize();
    }

    public abstract void onInitialize();
}
