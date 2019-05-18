package com.bittech.poemanalyse.web;

import com.bittech.poemanalyse.analy.model.AuthorPoemCount;
import com.bittech.poemanalyse.analy.model.WordCount;
import com.bittech.poemanalyse.analy.service.AnalyzeService;
import spark.ResponseTransformer;
import spark.Spark;
import com.google.gson.Gson;

import java.util.List;

public class WebController {
    private final AnalyzeService analyzeService;

    public WebController(AnalyzeService analyzeService) {
        this.analyzeService = analyzeService;
    }
    //http://127.0.0.1:4567/
    //analyze/author_count
    public List<AuthorPoemCount> authorPoemCount(){

        return analyzeService.authorPoemCount();
    }
    //http：//127.0.0.1:4567/
    //analyze/word_cloud
    public List<WordCount> analyzeWordCloud(){
        return analyzeService.analyzeWordCloud();
    }

    public void launch(){
        ResponseTransformer transformer = new JSONResponseTransformer();
        //src/main/resources/static
        Spark.staticFileLocation("/static");

        Spark.get("/analyze/author_count",(request,response)->authorPoemCount(),transformer);
        Spark.get("/analyze/word_cloud",(request,response)->analyzeWordCloud(),transformer);

    }


    public static class JSONResponseTransformer implements ResponseTransformer{
        //Object->String
        private Gson gson = new Gson();

        @Override
        public String render(Object o) throws Exception {
            return gson.toJson(o);
        }
    }


}
