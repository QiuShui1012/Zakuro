package zh.qiushui.mod.zakuro.keybinding;

import net.minecraft.client.option.KeyBinding;
import org.lwjgl.glfw.GLFW;

public class KeyBindings {
    public static final String ZAKURO_CONFIG_CATEGORY_TRANSLATION_KEY = "option.zakuro.category";
    public static final KeyBinding OPEN_CONFIG = new KeyBinding("option.zakuro.open_config", GLFW.GLFW_KEY_O , ZAKURO_CONFIG_CATEGORY_TRANSLATION_KEY);
}
