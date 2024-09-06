package zh.qiushui.mod.zakuro.html;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HtmlI18n {
    private static final Map<String, String> I18N_MAP = Map.of(
            // en-US
            "en-US.title", "Zakuro Modules List",
            "en-US.thead.name", "Module Name",
            "en-US.thead.desc", "Description",

            // zh-CN
            "zh-CN.title", "Zakuro模块列表",
            "zh-CN.thead.name", "模块名",
            "zh-CN.thead.desc", "描述"
    );

    public static String translate(String key) {
        return I18N_MAP.get(key);
    }

    public static String translate(String code, String suffixNotStartsWithDot) {
        return I18N_MAP.get(code + "." + suffixNotStartsWithDot);
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
