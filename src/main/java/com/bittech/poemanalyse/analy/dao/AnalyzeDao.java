package com.bittech.poemanalyse.analy.dao;

import com.bittech.poemanalyse.analy.entity.PoetryInfo;
import com.bittech.poemanalyse.analy.model.AuthorPoemCount;

import java.util.List;

public interface AnalyzeDao {
    /**
     * 分析唐诗中作者的创作数量
     * @return
     */
    List<AuthorPoemCount> authorPoemCount();

    /**
     * 查询所有的诗文，提供给业务层进行分析
     * @return
     */
     List<PoetryInfo> queryAllPoetry();

}
