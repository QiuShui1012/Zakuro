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
        builder.add(this.moduleNameTranslationKey + ".candle", "Candle");
        builder.add(this.moduleNameTranslationKey + ".endRod", "End Rod");
        builder.add(this.moduleNameTranslationKey + ".fence", "Fence");
        builder.add(this.moduleNameTranslationKey + ".fenceGate", "Fence Gate");
        builder.add(this.moduleNameTranslationKey + ".lightningRod", "Lightning Rod");
        builder.add(this.moduleNameTranslationKey + ".sign", "Sign");
        builder.add(this.moduleNameTranslationKey + ".signWall", "Sign On The Wall");
    }

    @Override
    protected void registerConfigs() {
        configs.put(this.rawModuleId + ".candle", Zakuro.config.modules.fixBlocksOutlineShape.candle);
        configs.put(this.rawModuleId + ".endRod", Zakuro.config.modules.fixBlocksOutlineShape.endRod);
        configs.put(this.rawModuleId + ".fence", Zakuro.config.modules.fixBlocksOutlineShape.fence);
        configs.put(this.rawModuleId + ".fenceGate", Zakuro.config.modules.fixBlocksOutlineShape.fenceGate);
        configs.put(this.rawModuleId + ".lightningRod", Zakuro.config.modules.fixBlocksOutlineShape.lightningRod);
        configs.put(this.rawModuleId + ".sign", Zakuro.config.modules.fixBlocksOutlineShape.sign);
        configs.put(this.rawModuleId + ".signWall", Zakuro.config.modules.fixBlocksOutlineShape.signWall);
    }

    @Override
    public void appendHtmlExtra(Element tBody, String htmlLangCode, String mcLangCode) {
        super.appendHtmlExtra(tBody, htmlLangCode, mcLangCode);
        Element tDDesc = tBody.getElementById(tBody.id() + "Desc");
        assert tDDesc != null;
        tDDesc.append("<br>");

        Element div = tDDesc.appendElement("div");
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
