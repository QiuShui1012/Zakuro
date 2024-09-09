package zh.qiushui.mod.zakuro.html;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HtmlI18n {
    private static final Map<String, String> I18N_MAP = Map.of(
            // en-US
            "en-US.fbos.blocks", "The following are supported blocks:",
            "en-US.fbos.note", "Attention: We do not support the problem about the outline shape does not match the model due to changes in the custom resourcepacks! Please do not report it on the issues page.",
            "en-US.title", "Zakuro Modules List",
            "en-US.thead.name", "Module Name",
            "en-US.thead.desc", "Description",

            // zh-CN
            "zh-CN.fbos.blocks", "以下是支持的方块：",
            "zh-CN.fbos.note", "注意：我们不支持由于因自定义资源包更改模型而导致的轮廓形状与模型不匹配的问题！请不要将其报告至issues页面。",
            "zh-CN.title", "Zakuro模块列表",
            "zh-CN.thead.name", "模块名",
            "zh-CN.thead.desc", "描述"
    );

    public static String translate(String key) {
        return I18N_MAP.get(key);
    }

    public static String translate(String code, String suffixStartsWithoutDot) {
        return I18N_MAP.get(code + "." + suffixStartsWithoutDot);
    }

    public static List<String> getLanguages() {
        List<String> languages = new ArrayList<>();

        for (String key : I18N_MAP.keySet()) {
            int dotIndex = key.indexOf(".");
            String language = key.substring(0, dotIndex);
            if (!languages.contains(language)) {
                languages.add(language);
            }
        }

        return languages;
    }
}
