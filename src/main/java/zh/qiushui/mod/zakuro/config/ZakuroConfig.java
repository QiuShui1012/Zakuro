package zh.qiushui.mod.zakuro.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import zh.qiushui.mod.zakuro.ZakuroUtil;

@Config(name = ZakuroUtil.MOD_ID)
public class ZakuroConfig implements ConfigData {

    @ConfigEntry.Gui.CollapsibleObject(startExpanded = true)
    public ModulesConfig modules = new ModulesConfig();

    public static class ModulesConfig {
        @ConfigEntry.Gui.RequiresRestart
        @ConfigEntry.Gui.Tooltip
        public boolean deleteTooExpensive = false;

        @ConfigEntry.Gui.RequiresRestart
        @ConfigEntry.Gui.Tooltip
        public boolean fixRodsCollisionBox = false;

        @ConfigEntry.Gui.RequiresRestart
        @ConfigEntry.Gui.Tooltip
        public boolean fixZombieReinforcement = false;
    }
}
