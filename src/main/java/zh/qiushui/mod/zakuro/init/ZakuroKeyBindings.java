package zh.qiushui.mod.zakuro.init;

import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import org.lwjgl.glfw.GLFW;
import zh.qiushui.mod.zakuro.config.ZakuroConfig;

public class ZakuroKeyBindings {
    public static final String ZAKURO_CONFIG_CATEGORY_TRANSLATION_KEY = "option.zakuro.category";
    public static final KeyBinding OPEN_CONFIG = KeyBindingHelper.registerKeyBinding(new KeyBinding("option.zakuro.open_config", GLFW.GLFW_KEY_O , ZAKURO_CONFIG_CATEGORY_TRANSLATION_KEY));

    public static void registerAll() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (OPEN_CONFIG.wasPressed()) {
                client.setScreen(AutoConfig.getConfigScreen(ZakuroConfig.class, client.currentScreen).get());
            }
        });
    }
}
