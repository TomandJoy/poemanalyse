package com.bittech.poemanalyse.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.bittech.poemanalyse.analy.dao.AnalyzeDao;
import com.bittech.poemanalyse.analy.dao.impl.AnalyzeDaoImpl;
import com.bittech.poemanalyse.analy.service.AnalyzeService;
import com.bittech.poemanalyse.analy.service.impl.AnalyzeServiceImpl;
import com.bittech.poemanalyse.crawler.Crawler;
import com.bittech.poemanalyse.crawler.common.Page;
import com.bittech.poemanalyse.crawler.pipeline.ConsolePipeline;
import com.bittech.poemanalyse.crawler.pipeline.DataBasePipeline;
import com.bittech.poemanalyse.crawler.prase.DataPagePrase;
import com.bittech.poemanalyse.crawler.prase.DocumentPrase;
import com.bittech.poemanalyse.web.WebController;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public final class ObjectFactory {

    private static final ObjectFactory instance = new ObjectFactory();

    //存放所有对象
    private  final Map<Class,Object> objectHashMap = new HashMap<>();

    private  ObjectFactory(){
        //1.初始化配置对象
        initConfigProperties();
        //创建数据源对象
        initDataSource();

        //创建爬重对象
        initCrawler();

        //web对象
        initWebController();

        //对象清单打印输出
        printObjectList();
    }

    private void initWebController() {
        DataSource dataSource = getObject(DataSource.class);
        AnalyzeDao analyzeDao = new AnalyzeDaoImpl(dataSource);
        AnalyzeService analyzeService = new AnalyzeServiceImpl(analyzeDao);
        WebController webController = new WebController(analyzeService);
        objectHashMap.put(WebController.class,webController);
    }

    private void initCrawler() {
        ConfigProperties configProperties = getObject(ConfigProperties.class);
        DataSource dataSource = getObject(DataSource.class);
        final Page page = new Page(configProperties.getCrawlerBase(),
                configProperties.getCrawlerPath(),
                configProperties.isCrawlerDetail());

        Crawler crawler = new Crawler();
        crawler.addPrase(new DocumentPrase());
        crawler.addPrase(new DataPagePrase());
        if(configProperties.isEnableConsole()){
            crawler.addPipeline(new ConsolePipeline());
        }
        crawler.addPipeline(new DataBasePipeline(dataSource));
        crawler.addPage(page);

        objectHashMap.put(Crawler.class,crawler);
    }

    private void initDataSource() {
        ConfigProperties configProperties = getObject(ConfigProperties.class);
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUsername(configProperties.getDbUsername());
        dataSource.setPassword(configProperties.getDbPassword());
        dataSource.setDriverClassName(configProperties.getDbDriverClass());
        dataSource.setUrl(configProperties.getDbUrl());

       objectHashMap.put(DataSource.class,dataSource);
    }

    private void initConfigProperties() {
        ConfigProperties configProperties = new ConfigProperties();
        objectHashMap.put(ConfigProperties.class,configProperties);

    }

    public  <T> T getObject(Class classz){
        if(!objectHashMap.containsKey(classz)){
            throw new IllegalArgumentException("Class"+classz.getName()+"not found Object");
        }
        return (T) objectHashMap.get(classz);
    }
    public static ObjectFactory getInstance(){
        return instance;
    }

    private void printObjectList(){
        System.out.println("---------ObjectFactory List---------------");
        for(Map.Entry<Class,Object> entry : objectHashMap.entrySet()){
            System.out.println(String.format("[%-5s]-->[%s]",entry.getKey().getCanonicalName(),
                    entry.getValue().getClass().getCanonicalName()));
        }
        System.out.println("------------------------------------------");

    }

}
