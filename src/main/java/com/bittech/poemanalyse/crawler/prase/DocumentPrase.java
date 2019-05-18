package com.bittech.poemanalyse.crawler.prase;

import com.bittech.poemanalyse.crawler.common.Page;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * 链接解析
 */

public class DocumentPrase implements Prase {
    @Override
    public void Prase(Page page) {
       if(page.isDetail()){
           return ;
       }
        HtmlPage htmlPage = page.getHtmlPage();
        htmlPage.getBody()
                .getElementsByAttribute("div","class","typecont")
                .forEach(div->{
                    DomNodeList<HtmlElement> aNodeList = div.getElementsByTagName("a");
                    aNodeList.forEach(aNode->{
                        String path = aNode.getAttribute("href");
                        Page subPage = new Page(page.getBase(),path,true);
                   page.getSubPage().add(subPage);
                    });
                });
    }
}
