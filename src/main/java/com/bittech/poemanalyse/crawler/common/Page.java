package com.bittech.poemanalyse.crawler.common;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import lombok.Data;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Page是对htmlpage的包装，比htmlpage内容更加丰富
 * 如果采集的页面是文档页面的话，采集时base,path,detail 有值
 * 解析时htmlpage有值，会找到子页面，会在子页面加subpage,detail是false,把子页面加回队列中，当detail为true时
 * 到清洗，数据写到数据库中
 */

@Data
public class Page {

    // 数据网站的根地址，比如：https://www.gushiwen.org/
    private final String base;


    //具体网页的路径，比如：/gushi/tangshi.aspx
    private final   String path;

    //public Page(String base, String path, boolean b) {

   // }

   // public Page() {

    //}

    public String getUrl(){
        return this.base+this.path;
    }

    //标识网页是否是详情页
    private final boolean detail;

    /**
     * 数据对象，最终产生的数据，HashMap的包装类
     */
    private DataSet dataSet = new DataSet();

    //网页DOM对象
    public HtmlPage htmlPage;


    //子页面集合
    public  Set<Page> subPage = new HashSet<Page>();




}









