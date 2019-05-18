package com.bittech.poemanalyse.analy.service;

import com.bittech.poemanalyse.analy.model.AuthorPoemCount;
import com.bittech.poemanalyse.analy.model.WordCount;

import java.util.List;

public interface AnalyzeService {
    /**
     * 分析唐诗中作者的创作数量
     * @return
     */
    List<AuthorPoemCount> authorPoemCount();

    /**
     * 词云分析
     * @return
     */
    List<WordCount> analyzeWordCloud();
}
