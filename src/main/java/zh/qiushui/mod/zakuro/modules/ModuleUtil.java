package zh.qiushui.mod.zakuro.modules;

import zh.qiushui.mod.zakuro.Zakuro;
import zh.qiushui.mod.zakuro.api.module.ModuleInfo;

public class ModuleUtil {
    public static String calculateModuleName(Class<?> basePackageClass, String mixinClassName) {
        String moduleName;
        int basePackage = basePackageClass.getPackageName().length() + 1;
        moduleName = mixinClassName.substring(basePackage);
        int rightDot = moduleName.lastIndexOf(".");
        return moduleName.substring(0, rightDot);
    }

    public static boolean shouldApplyMixin(String name) {
        String baseModuleName;

        if (name.contains(".")) {
            baseModuleName = name.substring(0, name.lastIndexOf("."));
        } else {
            baseModuleName = name;
        }

        Zakuro.LOGGER.debug(baseModuleName);
        for (ModuleInfo info : Modules.MODULE_INFO_LIST) {
            if (info.getRawModuleId().equals(baseModuleName)) {
                return info.shouldApplyMixin(name);
            }
        }

        return false;
    }
}
