package zh.qiushui.mod.zakuro.modules.mixin;

import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import zh.qiushui.mod.zakuro.Zakuro;
import zh.qiushui.mod.zakuro.modules.ModuleUtil;

import java.util.List;
import java.util.Set;

public class MixinModuleConfigPlugin implements IMixinConfigPlugin {
    static {
        Zakuro.initConfig();
    }

    @Override
    public void onLoad(String mixinPackage) {
        // do nothing
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (mixinClassName.startsWith("zh.qiushui.mod.zakuro.modules.mixin.common")) {
            return true;
        }

        String basePackageName = ModuleUtil.calculateModuleName(this.getClass(), mixinClassName);
        return ModuleUtil.shouldApplyMixin(basePackageName);
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
        // do nothing
    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
        // do nothing
    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
        // do nothing
    }
}
