package com.bittech.poemanalyse.crawler;

import com.bittech.poemanalyse.crawler.common.Page;
import com.bittech.poemanalyse.crawler.pipeline.ConsolePipeline;
import com.bittech.poemanalyse.crawler.pipeline.pipeline;
import com.bittech.poemanalyse.crawler.prase.DataPagePrase;
import com.bittech.poemanalyse.crawler.prase.DocumentPrase;
import com.bittech.poemanalyse.crawler.prase.Prase;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
/**
爬重调度框架
 */
public class Crawler {

    /**
     * 放置文档页面（超链接）
     * 未被采集和解析的页面
     * Page htmlPage dataSet
     */
    private final Queue<Page> docQueue = new LinkedBlockingDeque<>();

    /**
     * 放置详情页面(处理完成，数据在dataSet)
     */
    private final Queue<Page> detailQueue = new LinkedBlockingDeque<>();

    /**
     * 采集器用htmlunit工具
     */
    private final WebClient webClient;

    /**
     * 所有解析器
     */
    private final List<Prase> praseList = new LinkedList<>();

    /**
     * 所有的清洗器（管道）
     */
    private final List<pipeline> pipelineList = new LinkedList<>();

    /**
     * 线程调度器
     */
    private final ExecutorService executorService;


    public Crawler() {
        this.webClient = new WebClient(BrowserVersion.CHROME);
        this.webClient.getOptions().setJavaScriptEnabled(false);
        this.executorService = Executors.newFixedThreadPool(
                8, new ThreadFactory() {
                    private final AtomicInteger id = new AtomicInteger(0);

                    @Override
                    public Thread newThread(Runnable r) {
                        Thread thread = new Thread(r);
                        thread.setName("Crawler-Thread-" + id.getAndIncrement());
                        return thread;
                    }
                }
        );
    }

    public void start() {
        this.executorService.submit(this::prase);

        this.executorService.submit(this::pipeline);


    }

    private void prase() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            final Page page = this.docQueue.poll();
            if (page == null) {
                continue;
            }
            //把单线程变为多线程
            this.executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        HtmlPage htmlPage = Crawler.this.webClient.getPage(page.getUrl());
                        page.setHtmlPage(htmlPage);

                        for (Prase prase : praseList) {
                            prase.Prase(page);
                        }
                        if (page.isDetail()) {
                            Crawler.this.detailQueue.add(page);
                        } else {
                            Iterator<Page> iterator = page.getSubPage().iterator();
                            while (iterator.hasNext()) {
                                Page subPage = iterator.next();
                                Crawler.this.docQueue.add(subPage);
                                iterator.remove();
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    }

    public void pipeline() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            final Page page = this.detailQueue.poll();
            if (page == null) {
                continue;
            }
            this.executorService.submit(new Runnable() {
                @Override
                public void run() {
                    for (pipeline pipeline : Crawler.this.pipelineList) {
                        pipeline.pipeline(page);
                    }
                }
            });

        }
    }

    public void addPage(Page page) {
        this.docQueue.add(page);
    }

    public void addPrase(Prase prase) {
        this.praseList.add(prase);
    }

    public void addPipeline(pipeline pipeline) {
        this.pipelineList.add(pipeline);
    }

    /**
     * 停止爬虫
     */
    public void stop() {
        if (this.executorService != null && this.executorService.isShutdown()) {
            this.executorService.shutdown();
        }

    }

}