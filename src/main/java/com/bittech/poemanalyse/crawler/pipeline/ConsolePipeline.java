package com.bittech.poemanalyse.crawler.pipeline;

import com.bittech.poemanalyse.crawler.common.Page;

import java.util.Map;

public class ConsolePipeline implements pipeline{

    @Override
    public void pipeline(Page page) {
        Map<String,Object> data = page.getDataSet().getData();
        //存储
        System.out.println(data);
    }
}
