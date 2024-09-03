package zh.qiushui.mod.zakuro.modules;

import zh.qiushui.mod.zakuro.ZakuroUtil;
import zh.qiushui.mod.zakuro.modules.info.FixRodsCollisionBoxInfo;
import zh.qiushui.mod.zakuro.modules.info.FixZombieReinforcementInfo;
import zh.qiushui.mod.zakuro.api.module.ModuleInfo;
import zh.qiushui.mod.zakuro.modules.info.DeleteTooExpensiveInfo;

import java.util.List;
import java.util.Map;

public class Modules {
    public static final List<? extends ModuleInfo> MODULE_INFO_LIST = List.of(
            new DeleteTooExpensiveInfo(),
            new FixRodsCollisionBoxInfo(),
            new FixZombieReinforcementInfo()
    );

    public static final Map<String, Boolean> MODULE_ENABLED_MAP = Map.of(
            MODULE_INFO_LIST.get(0).getRawModuleId(), ZakuroUtil.config.modules.deleteTooExpensive,
            MODULE_INFO_LIST.get(1).getRawModuleId(), ZakuroUtil.config.modules.fixRodsCollisionBox,
            MODULE_INFO_LIST.get(2).getRawModuleId(), ZakuroUtil.config.modules.fixZombieReinforcement
    );
}
