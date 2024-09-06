package zh.qiushui.mod.zakuro;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import zh.qiushui.mod.zakuro.api.module.ModuleInfo;
import zh.qiushui.mod.zakuro.html.ModulesListHtmlGen;
import zh.qiushui.mod.zakuro.modules.Modules;

public class ZakuroDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(EnglishProvider::new);
	}

	public static class EnglishProvider extends FabricLanguageProvider {
		protected EnglishProvider(FabricDataOutput dataOutput) {
			super(dataOutput);
		}

		@Override
		public void generateTranslations(TranslationBuilder builder) {
			initModulesTranslation(builder);
			builder.add(ZakuroUtil.buildTranslationKey("option.", ".category"), ZakuroUtil.MOD_NAME);
			builder.add(ZakuroUtil.buildTranslationKey("text.autoconfig.", ".title"), "Zakuro - Config");
			builder.add(ZakuroUtil.buildTranslationKey("text.autoconfig.", ".option.modules"), "Modules");
		}

		private void initModulesTranslation(TranslationBuilder builder) {
			for (ModuleInfo module : Modules.MODULE_INFO_LIST) {
				module.initEnglishTranslation(builder);
			}
		}
	}
}
