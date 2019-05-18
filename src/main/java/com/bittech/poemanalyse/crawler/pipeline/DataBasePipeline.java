package com.bittech.poemanalyse.crawler.pipeline;

import com.bittech.poemanalyse.crawler.common.Page;


import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 实现一个清洗（管道）将Page中的数据写入到数据库
 * 技术：JDBC
 * 数据库：MySQL
 * 数据库的准备工作：创建数据库，设计表，创建表
 * JDBC编程：驱动，DataSource
 */
public class DataBasePipeline implements pipeline {
    private final DataSource dataSource;

    public DataBasePipeline(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void pipeline(Page page) {

        String dynasty = (String) page.getDataSet().getData("dynasty");
        String author = (String) page.getDataSet().getData("author");
        String title = (String) page.getDataSet().getData("title");
        String content = (String) page.getDataSet().getData("content");


        String sql = "insert into poetry_info(title, dynasty, author, content) values (?,?,?,?)";
        /**
         * 1.建立连接，准备SQL命令
         */
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setString(1,title);
            statement.setString(2,dynasty);
            statement.setString(3,author);
            statement.setString(4,content);
            statement.executeUpdate();
        }catch (SQLException e){

        }
    }
}
