package zh.qiushui.mod.zakuro.modules.info;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import zh.qiushui.mod.zakuro.Zakuro;
import zh.qiushui.mod.zakuro.api.module.ModuleInfo;
import zh.qiushui.mod.zakuro.html.HtmlI18n;

public class FixBlocksOutlineShapeInfo extends ModuleInfo {
    public FixBlocksOutlineShapeInfo() {
        super("fixBlocksOutlineShape",
                "This module fixes the blocks' outline shape and syncs it to the blocks' model.");
    }

    @Override
    public void initEnglishModuleExtra(FabricLanguageProvider.TranslationBuilder builder) {
        builder.add(this.moduleNameTranslationKey + ".aaaInit", "Init");
        builder.add(this.moduleNameTranslationKey + ".aaaInit.@Tooltip", "Enable this option to make other options in this module effective.");
        builder.add(this.moduleNameTranslationKey + ".blocks", "Blocks");
        builder.add(this.moduleNameTranslationKey + ".blocks.bell", "Bell");
        builder.add(this.moduleNameTranslationKey + ".blocks.candle", "Candle");
        builder.add(this.moduleNameTranslationKey + ".blocks.endRod", "End Rod");
        builder.add(this.moduleNameTranslationKey + ".blocks.fence", "Fence");
        builder.add(this.moduleNameTranslationKey + ".blocks.fenceGate", "Fence Gate");
        builder.add(this.moduleNameTranslationKey + ".blocks.lightningRod", "Lightning Rod");
        builder.add(this.moduleNameTranslationKey + ".blocks.repeater", "Repeater");
        builder.add(this.moduleNameTranslationKey + ".blocks.sign", "Sign");
        builder.add(this.moduleNameTranslationKey + ".blocks.signWall", "signWall");
        builder.add(this.moduleNameTranslationKey + ".blocks.torch", "Torch");
        builder.add(this.moduleNameTranslationKey + ".blocks.torchRedstone", "Redstone Torch");
        builder.add(this.moduleNameTranslationKey + ".blocks.tripwireHook", "Tripwire Hook");
    }

    @Override
    protected void registerMixinConfigs() {
        configs.put(this.rawModuleId + ".init", Zakuro.config.fixBlocksOutlineShape.aaaInit);
    }

    @Override
    public void appendHtmlExtra(Element li, String htmlLangCode, String mcLangCode) {
        Element tDDesc = li.getElementById(li.id() + "Desc");
        assert tDDesc != null;
        tDDesc.append("<br>");

        Element div = tDDesc.appendElement("div");
        div.id("blocksDiv");
        div.appendChild(new TextNode(HtmlI18n.translate(htmlLangCode, "fbos.blocks")));
            Element blockList = div.appendElement("ul");
                buildBlocksSubmodule(blockList, mcLangCode, "blocks.bell", "Bell");
                buildBlocksSubmodule(blockList, mcLangCode, "blocks.candle", "Candle");
                buildBlocksSubmodule(blockList, mcLangCode, "blocks.endRod", "End Rod");
                buildBlocksSubmodule(blockList, mcLangCode, "blocks.fence", "Fence");
                buildBlocksSubmodule(blockList, mcLangCode, "blocks.fenceGate", "Fence Gate");
                buildBlocksSubmodule(blockList, mcLangCode, "blocks.lightningRod", "Lightning Rod");
                buildBlocksSubmodule(blockList, mcLangCode, "blocks.repeater", "Repeater");
                buildBlocksSubmodule(blockList, mcLangCode, "blocks.sign", "Sign");
                buildBlocksSubmodule(blockList, mcLangCode, "blocks.signWall", "signWall");
                buildBlocksSubmodule(blockList, mcLangCode, "blocks.torch", "Torch");
                buildBlocksSubmodule(blockList, mcLangCode, "blocks.torchRedstone", "Redstone Torch");
                buildBlocksSubmodule(blockList, mcLangCode, "blocks.tripwireHook", "Tripwire Hook");

        tDDesc.appendChild(new TextNode(HtmlI18n.translate(htmlLangCode, "fbos.note")));
    }

    private void buildBlocksSubmodule(Element ul, String mcLangCode, String suffix, String defaultValue) {
        Element redstoneTorch = ul.appendElement("li");
        redstoneTorch.appendChild(new TextNode(Zakuro.translate(
                mcLangCode,
                this.moduleNameTranslationKey + "." + suffix, defaultValue
        )));
    }
}
