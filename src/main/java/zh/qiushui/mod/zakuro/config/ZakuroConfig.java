package zh.qiushui.mod.zakuro.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import zh.qiushui.mod.zakuro.Zakuro;

@Config(name = Zakuro.MOD_ID)
public class ZakuroConfig implements ConfigData {

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

    @ConfigEntry.Gui.Tooltip(count = 2)
    public boolean retainBlocksOriginalInteractableRange = false;

    public static class FixBlocksOutlineShapeConfig {
        public boolean candle = false;
        public boolean endRod = false;
        public boolean fence = false;
        public boolean fenceGate = false;
        public boolean lightningRod = false;
        public boolean sign = false;
        public boolean signWall = false;
    }
}
