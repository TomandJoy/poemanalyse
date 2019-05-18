package com.bittech.poemanalyse.analy.dao.impl;

import com.bittech.poemanalyse.analy.dao.AnalyzeDao;
import com.bittech.poemanalyse.analy.entity.PoetryInfo;
import com.bittech.poemanalyse.analy.model.AuthorPoemCount;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AnalyzeDaoImpl implements AnalyzeDao {

    private final DataSource dataSource;

    public AnalyzeDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<AuthorPoemCount> authorPoemCount() {
        List<AuthorPoemCount> datas = new ArrayList<>();
        //try()自动关闭
        String sql = "select count(*) as count,author  from poetry_info group by author;";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

        ){
            while (resultSet.next()){
                AuthorPoemCount authorPoemCount = new AuthorPoemCount();
                authorPoemCount.setAuthor(resultSet.getString("author"));
                authorPoemCount.setCount(resultSet.getInt("count"));
                datas.add(authorPoemCount);
            }

        }catch (SQLException e){
            e.printStackTrace();

        }
        return datas;
    }

    @Override
    public List<PoetryInfo> queryAllPoetry() {
        List<PoetryInfo> datas = new ArrayList<>();
        String sql = "select title,dynasty,author,content from poetry_info limit 2;";
        try(Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        ) {
            while (resultSet.next()){
                PoetryInfo poetryInfo = new PoetryInfo();
                poetryInfo.setTitle(resultSet.getString("title"));
                poetryInfo.setDynasty(resultSet.getString("dynasty"));
                poetryInfo.setAuthor(resultSet.getString("author"));
                poetryInfo.setContent(resultSet.getString("content"));
                datas.add(poetryInfo);
            }

        }catch (SQLException e){
            e.printStackTrace();

        }
        return datas;
    }
}
