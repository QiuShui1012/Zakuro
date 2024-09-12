package zh.qiushui.mod.zakuro.modules.info;

import zh.qiushui.mod.zakuro.Zakuro;
import zh.qiushui.mod.zakuro.api.module.ModuleInfo;

public class DeleteTooExpensiveInfo extends ModuleInfo {
    public DeleteTooExpensiveInfo() {
        super("deleteTooExpensive",
                "This module removes the \"Too Expensive!\" tip on the anvil and keeps the penalty level on the item.");
    }

    @Override
    protected void registerConfigs() {
        configs.put(this.rawModuleId, Zakuro.config.deleteTooExpensive);
    }
}
