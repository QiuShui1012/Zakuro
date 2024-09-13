package zh.qiushui.mod.zakuro.html;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
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

    private static void genLanguageModulesList(String htmlLangCode, String mcLangCode) throws IOException {
        boolean htmlLangCodeIsNull = htmlLangCode == null;
        String htmlTrueLangCode = htmlLangCodeIsNull ? "en-US" : htmlLangCode;

        Document html = Document.createShell(PROJECT_PATH +
                "index" + (htmlLangCodeIsNull ? "" : "_" + htmlTrueLangCode) + ".html");
        html.charset(Charset.defaultCharset());
        html.title(HtmlI18n.translate(htmlTrueLangCode, "title"));

        Element htmlEl = htmlEl(html);
        htmlEl.attr("lang", htmlTrueLangCode);

        Element head = html.head();

        Element css = head.appendElement("link");
        css.attr("rel", "stylesheet");
        css.attr("href", "web/css/style.css");

        Element icon = head.appendElement("link");
        icon.attr("rel", "Shortcut Icon");
        icon.attr("type", "image/x-icon");
        icon.attr("href", "web/img/icon.ico");

        Element header = htmlEl.prependElement("header");

        Element titleHeaderDiv = header.appendElement("div");
        titleHeaderDiv.id("titleHeader");
            Element a = titleHeaderDiv.appendElement("a");
            a.id();
            a.attr("href", "https://github.com/QiuShui1012/Zakuro");
            a.appendChild(new TextNode("Zakuro"));

        Element languageDiv = header.appendElement("div");
        languageDiv.id("languageDiv");
        for (String language : HtmlI18n.getLanguages()) {
            Element aLan = languageDiv.appendElement("a");
            aLan.attr("href",
                    "index" + (language.equals("en-US") ? "" : "_" + language) + ".html");
                Element imgLan = aLan.appendElement("img");
                imgLan.attr("src",
                        "web/img/" + language + ".svg");
                imgLan.attr("alt",
                        HtmlI18n.translate(language));
        }

        Element body = html.body();

        Element div = body.appendElement("div");
        div.id("bodyDiv");

        Element pTitle = div.appendElement("h1");
        pTitle.id("title");
        pTitle.appendChild(new TextNode(HtmlI18n.translate(htmlTrueLangCode, "title")));

        Element modulesList = div.appendElement("ul");
        modulesList.id("modulesList");

        for (ModuleInfo info : Modules.MODULE_INFO_LIST) {
            String liClass = "modulesListLi";
            String liId = liClass + info.getModuleAbbreviate().toUpperCase(Locale.ROOT);
            Element li = modulesList.appendElement("li");
            li.addClass(liClass);
            li.id(liId);
            info.appendHtml(li, htmlTrueLangCode, mcLangCode);
        }

        FileOutputStream fos = new FileOutputStream(PROJECT_PATH +
                "index" + (htmlLangCodeIsNull ? "" : "_" + htmlTrueLangCode) + ".html", false);
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
