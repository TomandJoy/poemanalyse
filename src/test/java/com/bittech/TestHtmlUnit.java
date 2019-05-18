package com.bittech;


import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;

import java.io.IOException;

/**
 * 模拟浏览器采集网页的内容
 */
public class TestHtmlUnit {
    public static void main(String[] args) {
        try(WebClient webClient = new WebClient(BrowserVersion.CHROME)){
            try {
                //不执行js文件
                webClient.getOptions().setJavaScriptEnabled(false);
                HtmlPage htmlPage = webClient.getPage("https://so.gushiwen.org/shiwenv_45c396367f59.aspx");
                HtmlElement bodyElement = htmlPage.getBody();
                //asText取出文本
                //String text = bodyElement.asText();

                //asXml取出结构
                //String text = bodyElement.asText();
                //System.out.println(text);

                /**
                 * 解析：如何在文档中找自己想要的，用DOM
                 *
                 */
               // HtmlDivision domElement = (HtmlDivision) htmlPage.getElementById(
                 //       "contson105d08a95e7d");
                //String divContent = domElement.asText();
               // System.out.println(divContent);


                //标题
                String titlePath = "//div[@class='cont']/h1/text()";
                DomText titleDom = (DomText) bodyElement.getByXPath(titlePath).get(0);
                String title = titleDom.asText();
                System.out.println(title);

                //作者
                String authorPath = "//div[@class='cont']/p[@class='source']/a[2]";
                HtmlAnchor authorAnchor = (HtmlAnchor) bodyElement.getByXPath(authorPath).get(0);
                String author = authorAnchor.asText();
                System.out.println(author);

                //朝代
                String dynastyPath = "//div[@class='cont']/p[@class='source']/a[1]";
                HtmlAnchor dynastyAnchor = (HtmlAnchor) bodyElement.getByXPath(dynastyPath).get(0);
                String dynasty = dynastyAnchor.asText();
                System.out.println(dynasty);

                //正文
                String textPath = "//div[@class='cont']/div[@class='contson']";
                HtmlDivision textDivision = (HtmlDivision) bodyElement.getByXPath(textPath).get(0);
                String text = textDivision.asText();
                System.out.println(text);











            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
