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

        Element div = li.appendElement("div");
        div.id("blocksDiv");
        div.appendChild(new TextNode(HtmlI18n.translate(htmlLangCode, "fbos.blocks")));
            Element blockList = div.appendElement("ul");
                Element candle = blockList.appendElement("li");
                candle.appendChild(new TextNode(Zakuro.translate(
                        mcLangCode,
                        this.moduleNameTranslationKey + ".candle", "Candle"
                )));

                Element endRod = blockList.appendElement("li");
                endRod.appendChild(new TextNode(Zakuro.translate(
                        mcLangCode,
                        this.moduleNameTranslationKey + ".endRod", "End Rod"
                )));

                Element fence = blockList.appendElement("li");
                fence.appendChild(new TextNode(Zakuro.translate(
                        mcLangCode,
                        this.moduleNameTranslationKey + ".fence", "Fence"
                )));

                Element fenceGate = blockList.appendElement("li");
                fenceGate.appendChild(new TextNode(Zakuro.translate(
                        mcLangCode,
                        this.moduleNameTranslationKey + ".fenceGate", "Fence Gate"
                )));

                Element lightningRod = blockList.appendElement("li");
                lightningRod.appendChild(new TextNode(Zakuro.translate(
                        mcLangCode,
                        this.moduleNameTranslationKey + ".lightningRod", "Lightning Rod"
                )));

                Element sign = blockList.appendElement("li");
                sign.appendChild(new TextNode(Zakuro.translate(
                        mcLangCode,
                        this.moduleNameTranslationKey + ".sign", "Sign"
                )));

                Element signWall = blockList.appendElement("li");
                signWall.appendChild(new TextNode(Zakuro.translate(
                        mcLangCode,
                        this.moduleNameTranslationKey + ".signWall", "Sign On The Wall"
                )));

        tDDesc.appendChild(new TextNode(HtmlI18n.translate(htmlLangCode, "fbos.note")));
    }
}
