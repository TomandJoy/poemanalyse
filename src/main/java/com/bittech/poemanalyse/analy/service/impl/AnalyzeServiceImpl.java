package com.bittech.poemanalyse.analy.service.impl;

import com.bittech.poemanalyse.analy.dao.AnalyzeDao;
import com.bittech.poemanalyse.analy.entity.PoetryInfo;
import com.bittech.poemanalyse.analy.model.AuthorPoemCount;
import com.bittech.poemanalyse.analy.model.WordCount;
import com.bittech.poemanalyse.analy.service.AnalyzeService;
import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.NlpAnalysis;


import java.util.*;

public class AnalyzeServiceImpl implements AnalyzeService {
    private final AnalyzeDao analyzeDao;

    public AnalyzeServiceImpl(AnalyzeDao analyzeDao) {
        this.analyzeDao = analyzeDao;
    }

    @Override
    public List<AuthorPoemCount> authorPoemCount() {
        /**
         * 此处结果并未排序，排序方式：
         * 1.DAO层SQL排序
         * 2.Serice层进行数据排序
         */
        List<AuthorPoemCount> authorPoemCounts = analyzeDao.authorPoemCount();

        Collections.sort(authorPoemCounts, new Comparator<AuthorPoemCount>() {
            @Override
            public int compare(AuthorPoemCount o1, AuthorPoemCount o2) {
                //降序
                return (-1)*o1.getCount().compareTo(o2.getCount());
            }
        });
        return authorPoemCounts;
    }

    @Override
    public List<WordCount> analyzeWordCloud() {

        /**
         * 1.查询出所有数据
         * 2.取出title content
         * 3.分词 不要：null,阿拉伯数字，一个字的
         * 4.统计 k-v k是词  v是词频
         */
     Map<String,Integer> map = new HashMap<>();
     List<PoetryInfo> poetryInfos = analyzeDao.queryAllPoetry();
        for (PoetryInfo poetryInfo:poetryInfos
             ) {
            List<Term> terms = new ArrayList<>();
            String title = poetryInfo.getTitle();
            String content = poetryInfo.getContent();

            terms.addAll(NlpAnalysis.parse(title).getTerms());
            terms.addAll(NlpAnalysis.parse(content).getTerms());

            Iterator<Term> iterator = terms.iterator();
            while (iterator.hasNext()){
                Term term = iterator.next();
                if(term.getNatureStr()==null || term.getNatureStr().equals("w")){
                    iterator.remove();
                    continue;
                }
               if(term.getRealName().length()<2){
                    iterator.remove();
                    continue;
               }
               //统计  k:词的名称，v：词出现的频率
                String realName = term.getRealName();
                Integer count = 0;
                if(map.containsKey(realName)){
                   count = map.get(realName)+1;
                }else{
                    count = 1;
                }
                map.put(realName,count);
            }

        }
        List<WordCount> wordCounts = new ArrayList<>();
        for(Map.Entry<String,Integer> entry:map.entrySet()){
            WordCount wordCount = new WordCount();
            wordCount.setCount(entry.getValue());
            wordCount.setWord(entry.getKey());
            wordCounts.add(wordCount);
        }
        return wordCounts;
    }

}
