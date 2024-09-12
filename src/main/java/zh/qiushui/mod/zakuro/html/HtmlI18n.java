package zh.qiushui.mod.zakuro.html;

import com.google.common.collect.ImmutableMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HtmlI18n {
    private static final Map<String, String> LANGUAGES_MAP = ImmutableMap.copyOf(new HashMap<>() {{
        // Languages
        put("en-US", "\uD83C\uDDFA\uD83C\uDDF8");
        put("zh-CN", "\uD83C\uDDE8\uD83C\uDDF3");
    }});

    private static final Map<String, String> I18N_MAP = ImmutableMap.copyOf(new HashMap<>() {{
        // en-US
        put("en-US.fbos.blocks", "The following are supported blocks:");
        put("en-US.fbos.note", "Attention: We do not support the problem about the outline shape does not match the model due to changes in the custom resourcepacks! Please do not report it on the issues page.");
        put("en-US.title", "Zakuro Modules List");
        put("en-US.thead.name", "Module Name");
        put("en-US.thead.desc", "Description");

        // zh-CN
        put("zh-CN.fbos.blocks", "以下是支持的方块：");
        put("zh-CN.fbos.note", "注意：我们不支持由于因自定义资源包更改模型而导致的轮廓形状与模型不匹配的问题！请不要将其报告至issues页面。");
        put("zh-CN.title", "Zakuro模块列表");
        put("zh-CN.thead.name", "模块名");
        put("zh-CN.thead.desc", "描述");
    }});

    public static String translate(String key) {
        String translated = I18N_MAP.get(key);
        return translated == null ? LANGUAGES_MAP.get(key) : translated;
    }

    public static String translate(String code, String suffixStartsWithoutDot) {
        return I18N_MAP.get(code + "." + suffixStartsWithoutDot);
    }

    public static List<String> getLanguages() {
        return LANGUAGES_MAP.keySet().stream().toList();
    }
}
