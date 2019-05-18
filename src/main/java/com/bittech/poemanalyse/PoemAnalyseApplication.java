package com.bittech.poemanalyse;

import com.alibaba.druid.pool.DruidDataSource;
import com.bittech.poemanalyse.analy.dao.AnalyzeDao;
import com.bittech.poemanalyse.analy.dao.impl.AnalyzeDaoImpl;
import com.bittech.poemanalyse.analy.entity.PoetryInfo;
import com.bittech.poemanalyse.analy.model.AuthorPoemCount;
import com.bittech.poemanalyse.analy.model.WordCount;
import com.bittech.poemanalyse.analy.service.AnalyzeService;
import com.bittech.poemanalyse.analy.service.impl.AnalyzeServiceImpl;
import com.bittech.poemanalyse.config.ConfigProperties;
import com.bittech.poemanalyse.config.ObjectFactory;
import com.bittech.poemanalyse.crawler.Crawler;
import com.bittech.poemanalyse.crawler.common.Page;
import com.bittech.poemanalyse.crawler.pipeline.DataBasePipeline;
import com.bittech.poemanalyse.crawler.prase.DataPagePrase;
import com.bittech.poemanalyse.crawler.prase.DocumentPrase;
import com.bittech.poemanalyse.web.WebController;

import java.io.IOException;
import java.util.List;

public class PoemAnalyseApplication {
    public static void main(String[] args) {
    //   Crawler crawler = ObjectFactory.getInstance().getObject(Crawler.class);
     //  crawler.start();
        WebController webController = ObjectFactory.getInstance()
                .getObject(WebController.class);
        webController.launch();
    }
}


