package zh.qiushui.mod.zakuro.modules;

import zh.qiushui.mod.zakuro.ZakuroUtil;

public class ModuleUtil {
    public static String calculateBasePackageName(Class<?> packageRootClass, String mixinClassName) {
        String basePackageName;
        int left = packageRootClass.getPackageName().length() + 1;
        basePackageName = mixinClassName.substring(left);
        int right = basePackageName.indexOf(".");
        basePackageName = basePackageName.substring(0, right);
        return basePackageName;
    }

    public static boolean shouldApplyMixin(String basePackageName) {
        ZakuroUtil.initConfig();
        return Modules.MODULE_ENABLED_MAP.get(basePackageName);
    }
}
