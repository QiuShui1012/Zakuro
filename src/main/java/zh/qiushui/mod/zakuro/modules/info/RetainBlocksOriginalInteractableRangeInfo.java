package zh.qiushui.mod.zakuro.modules.info;

import zh.qiushui.mod.zakuro.api.module.ModuleInfo;

public class RetainBlocksOriginalInteractableRangeInfo extends ModuleInfo {
    public RetainBlocksOriginalInteractableRangeInfo() {
        super("retainBlocksOriginalInteractableRange",
                "This module retains the original interactive range of the blocks based on the fixBlocksOutlineShape module. ",
                        "If all sub functions of the fixBlocksOutlineShape module are not enabled, the module will be useless.");
    }

    @Override
    protected void registerConfigs() {
        // no configs
    }
}
