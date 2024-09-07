package zh.qiushui.mod.zakuro.modules;

import zh.qiushui.mod.zakuro.Zakuro;
import zh.qiushui.mod.zakuro.api.module.ModuleInfo;

public class ModuleUtil {
    public static String calculateModuleName(Class<?> basePackageClass, String mixinClassName) {
        String moduleName;
        moduleName = mixinClassName.replace(basePackageClass.getPackageName() + ".", "");
        int rightDot = mixinClassName.lastIndexOf(".") - 1;
        return moduleName.substring(0, rightDot);
    }

    public static boolean shouldApplyMixin(String name) {
        Zakuro.initConfig();

        String baseModuleName = name.substring(0, name.indexOf(".") - 1);
        for (ModuleInfo info : Modules.MODULE_INFO_LIST) {
            if (info.getRawModuleId().equals(baseModuleName)) {
                return info.shouldApplyMixin(name);
            }
        }

        return false;
    }
}
