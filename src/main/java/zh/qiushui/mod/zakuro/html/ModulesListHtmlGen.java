package zh.qiushui.mod.zakuro.html;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import zh.qiushui.mod.zakuro.ZakuroUtil;
import zh.qiushui.mod.zakuro.api.module.ModuleInfo;
import zh.qiushui.mod.zakuro.modules.Modules;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

public class ModulesListHtmlGen {
    private static final String PROJECT_PATH;

    static {
        String projectPath;
        try {
            projectPath = new File("").getCanonicalPath() + "/";
        } catch (IOException e) {
            projectPath = "";
        }
        PROJECT_PATH = projectPath;
    }

    public static void main(String[] args) {
        genModulesList();
    }

    public static void genModulesList() {
        try {
            genLanguageModulesList(null, "en_us");
            genLanguageModulesList("zh-CN", "zh_cn");
        } catch (IOException ignored) {}
    }

    private static void genLanguageModulesList(String htmlLanguageCode, String minecraftLanguageCode) throws IOException {
        boolean htmlLanguageCodeIsNull = htmlLanguageCode == null;
        String htmlTrueLanguageCode = htmlLanguageCodeIsNull ? "en-US" : htmlLanguageCode;

        Document html = Document.createShell(PROJECT_PATH +
                "index" + (htmlLanguageCodeIsNull ? "" : "_" + htmlTrueLanguageCode) + ".html");
        html.charset(Charset.defaultCharset());
        html.title(HtmlI18n.translate(htmlTrueLanguageCode, "title"));

        Element htmlEl = htmlEl(html);
        htmlEl.attr("lang", htmlTrueLanguageCode);

        Element head = html.head();

        Element css = head.appendElement("link");
        css.attr("rel", "stylesheet");
        css.attr("href", "css/style.css");

        Element icon = head.appendElement("link");
        icon.attr("rel", "icon");
        icon.attr("type", "image/png");
        icon.attr("href", "src/main/resources/assets/zakuro/icon.png");

        Element header = htmlEl.prependElement("header");

        Element p = header.appendElement("p");
        p.id("titleHeader");
        p.appendChild(new TextNode("Zakuro"));

        Element languageDiv = header.appendElement("div");
        languageDiv.id("languageDiv");

        for (String language : HtmlI18n.getLanguages()) {
            Element aLan = languageDiv.appendElement("a");
            aLan.attr("href",
                    "index" + (language.equals("en-US") ? "" : "_" + language) + ".html");
            aLan.appendChild(new TextNode(language));
        }

        Element body = html.body();

        Element div = body.appendElement("div");
        div.id("bodyDiv");

        Element pTitle = div.appendElement("p");
        pTitle.id("title");
        pTitle.appendChild(new TextNode(HtmlI18n.translate(htmlTrueLanguageCode, "title")));

        Element table = div.appendElement("table");
        table.id("modulesList");
            Element tHead = table.appendElement("thead");
            tHead.id("modulesListHead");
                Element tR = tHead.appendElement("tr");
                    Element tHName = tR.appendElement("th");
                    tHName.id("modulesListHeadName");
                    tHName.appendChild(new TextNode(HtmlI18n.translate(htmlTrueLanguageCode, "thead.name")));
                    Element tHDesc = tR.appendElement("th");
                    tHDesc.id("modulesListHeadDesc");
                    tHDesc.appendChild(new TextNode(HtmlI18n.translate(htmlTrueLanguageCode, "thead.desc")));

        for (ModuleInfo info : Modules.MODULE_INFO_LIST) {
            String tBodyId = "modulesListBody" + info.getModuleAbbreviate().toUpperCase(Locale.ROOT);
            Element tBody = table.appendElement("tbody");
            tBody.id(tBodyId);
                Element tRModule = tBody.appendElement("tr");
                    Element tDName = tRModule.appendElement("td");
                    tDName.addClass("moduleName");
                    tDName.id(tBodyId + "Name");
                    tDName.appendChild(new TextNode(ZakuroUtil.translate(minecraftLanguageCode, info.moduleNameTranslationKey, info.getRawModuleId())));
                    Element tDDesc = tRModule.appendElement("td");
                    tDDesc.addClass("moduleDesc");
                    tDDesc.id(tBodyId + "Desc");
                    tDDesc.appendChild(new TextNode(ZakuroUtil.translate(minecraftLanguageCode, info.moduleDescriptionTranslationKey, info.getRawModuleDescription())));
        }

        FileOutputStream fos = new FileOutputStream(PROJECT_PATH +
                "index" + (htmlLanguageCodeIsNull ? "" : "_" + htmlTrueLanguageCode) + ".html", false);
        OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
        osw.write(html.html());
        osw.close();
    }

    private static Element htmlEl(Document html) {
        Element el = html.firstElementChild();
        while (el != null) {
            if (el.nameIs("html"))
                return el;
            el = el.nextElementSibling();
        }
        return html.appendElement("html");
    }
}
