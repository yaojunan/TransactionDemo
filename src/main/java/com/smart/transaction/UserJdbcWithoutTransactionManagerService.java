package com.smart.transaction;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserJdbcWithoutTransactionManagerService {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addScore(String userName , int toAdd){
        String sql = "UPDATE t_user u SET u.score = u.score + ? WHERE user_name = ? ";
        jdbcTemplate.update(sql , toAdd , userName);
    }

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("jdbcWithoutTx.xml");
        UserJdbcWithoutTransactionManagerService service = (UserJdbcWithoutTransactionManagerService) ctx.getBean("userService");
        JdbcTemplate jdbcTemplate = (JdbcTemplate) ctx.getBean("jdbcTemplate");
        BasicDataSource basicDataSource = (BasicDataSource) jdbcTemplate.getDataSource();
        System.out.println("autoCommit:" + basicDataSource.getDefaultAutoCommit());
        jdbcTemplate.execute("INSERT INTO t_user(user_name,password,score)" +
                "VALUES('tom','123456',0)");
        service.addScore("tom" , 20);
        /*int score = jdbcTemplate.queryForInt("SELECT score FROM t_user WHERE user_name = 'tom'");
        System.out.println("score:" + score);*/
        jdbcTemplate.execute("DELETE FROM t_user WHERE user_name = 'tom'");

    }

}
