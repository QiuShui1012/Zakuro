package zh.qiushui.mod.zakuro.modules.info;

import zh.qiushui.mod.zakuro.Zakuro;
import zh.qiushui.mod.zakuro.api.module.ModuleInfo;

public class SyncFenceGatesInWallPropertyInfo extends ModuleInfo {
    public SyncFenceGatesInWallPropertyInfo() {
        super("syncFenceGatesInWallProperty",
                "This module syncs the IN_WALL property of the same row of fence gates and enables them to unify the heights affected by the IN_WALL property.");
    }

    @Override
    protected void registerConfigs() {
        configs.put(this.rawModuleId, Zakuro.config.modules.syncFenceGatesInWallProperty);
    }
}
