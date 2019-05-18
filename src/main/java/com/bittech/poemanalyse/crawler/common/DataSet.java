package com.bittech.poemanalyse.crawler.common;

import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

/**
 * 存储清洗的数据
 */
@ToString
public class DataSet {
    /**
     * data把DOM解析，清洗之后存储的数据
     * 比如：（K,V）
     * 标题：XXX
     * 作者:XXX
     * 正文：XXX
     */
    private Map<String,Object> data = new HashMap<>();

    //放数据
    public void putData(String key,Object value){
        this.data.put(key,value);
    }

    //取得key
    public Object getData(String key){
        return this.data.get(key);
    }

    //取得所有数据
    public Map<String,Object> getData(){
        return new HashMap<>(this.data);
    }
}
