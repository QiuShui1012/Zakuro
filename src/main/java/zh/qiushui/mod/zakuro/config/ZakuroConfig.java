package zh.qiushui.mod.zakuro.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import zh.qiushui.mod.zakuro.Zakuro;

@Config(name = Zakuro.MOD_ID)
public class ZakuroConfig implements ConfigData {

    @ConfigEntry.Gui.CollapsibleObject(startExpanded = true)
    public ModulesConfig modules = new ModulesConfig();

    public static class ModulesConfig {
        @ConfigEntry.Gui.RequiresRestart
        @ConfigEntry.Gui.Tooltip
        public boolean deleteTooExpensive = false;

        @ConfigEntry.Gui.CollapsibleObject
        @ConfigEntry.Gui.RequiresRestart
        @ConfigEntry.Gui.Tooltip
        public FixBlocksOutlineShapeConfig fixBlocksOutlineShape = new FixBlocksOutlineShapeConfig();

        @ConfigEntry.Gui.RequiresRestart
        @ConfigEntry.Gui.Tooltip
        public boolean fixZombieReinforcement = false;
    }

    public static class FixBlocksOutlineShapeConfig {
        public boolean endRod = false;
        public boolean fence = false;
        public boolean lightningRod = false;
        public boolean sign = false;
        public boolean signWall = false;
    }
}
