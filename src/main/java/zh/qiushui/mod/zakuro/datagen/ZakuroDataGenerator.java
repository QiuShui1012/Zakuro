package zh.qiushui.mod.zakuro.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import zh.qiushui.mod.zakuro.Zakuro;
import zh.qiushui.mod.zakuro.api.module.ModuleInfo;
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
			builder.add(Zakuro.buildTranslationKey("option.", ".category"), Zakuro.MOD_NAME);
			builder.add(Zakuro.buildTranslationKey("option.", ".open_config"), "Open Config Screen");
			builder.add(Zakuro.buildTranslationKey("text.autoconfig.", ".title"), "Zakuro - Config");
		}

		private void initModulesTranslation(TranslationBuilder builder) {
			for (ModuleInfo module : Modules.MODULE_INFO_LIST) {
				module.initEnglishTranslation(builder);
			}
		}
	}
}
