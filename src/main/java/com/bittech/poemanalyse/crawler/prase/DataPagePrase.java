package com.bittech.poemanalyse.crawler.prase;

import com.bittech.poemanalyse.crawler.common.Page;

import com.gargoylesoftware.htmlunit.html.*;

/**
 * 详情页面解析
 */

public class DataPagePrase implements Prase{

    @Override
    public void Prase(Page page) {
        if(!page.isDetail()){
            return;
        }


        HtmlPage htmlPage = page.getHtmlPage();
        HtmlElement bodyElement = htmlPage.getBody();

        //标题
        String titlePath = "//div[@class='cont']/h1/text()";
        DomText titleDom = (DomText) bodyElement.getByXPath(titlePath).get(0);
        String title = titleDom.asText();
        //System.out.println(title);

        //作者
        String authorPath = "//div[@class='cont']/p[@class='source']/a[2]";
        HtmlAnchor authorAnchor = (HtmlAnchor) bodyElement.getByXPath(authorPath).get(0);
        String author = authorAnchor.asText();
       // System.out.println(author);

        //朝代
        String dynastyPath = "//div[@class='cont']/p[@class='source']/a[1]";
        HtmlAnchor dynastyAnchor = (HtmlAnchor) bodyElement.getByXPath(dynastyPath).get(0);
        String dynasty = dynastyAnchor.asText();
        //System.out.println(dynasty);

        //正文
        String textPath = "//div[@class='cont']/div[@class='contson']";
        HtmlDivision textDivision = (HtmlDivision) bodyElement.getByXPath(textPath).get(0);
        String text = textDivision.asText();
        //System.out.println(text);


        //把解析的数据都放到这里了，好处是可以加更多的数据
        page.getDataSet().putData("title",title);
        page.getDataSet().putData("dynasty",dynasty);
        page.getDataSet().putData("author",author);
        page.getDataSet().putData("text",text);

        //可以加更多数据
        page.getDataSet().putData("url",page.getUrl());


    }
}
