package zh.qiushui.mod.zakuro.api.module;

public abstract class ModuleInitializer {
    public void initialize() {
        this.onInitialize();
    }

    public abstract void onInitialize();
}
