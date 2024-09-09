package zh.qiushui.mod.zakuro.modules;

import zh.qiushui.mod.zakuro.modules.info.FixBlocksOutlineShapeInfo;
import zh.qiushui.mod.zakuro.modules.info.FixZombieReinforcementInfo;
import zh.qiushui.mod.zakuro.api.module.ModuleInfo;
import zh.qiushui.mod.zakuro.modules.info.DeleteTooExpensiveInfo;
import zh.qiushui.mod.zakuro.modules.info.SyncFenceGatesInWallPropertyInfo;

import java.util.List;

public class Modules {
    public static final List<? extends ModuleInfo> MODULE_INFO_LIST = List.of(
            new DeleteTooExpensiveInfo(),
            new FixBlocksOutlineShapeInfo(),
            new FixZombieReinforcementInfo(),
            new SyncFenceGatesInWallPropertyInfo()
    );
}
