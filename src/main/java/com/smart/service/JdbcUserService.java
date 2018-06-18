package com.smart.service;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.SQLException;

@Service
public class JdbcUserService {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public void logon(String userName){
        try{
//            Connection conn = jdbcTemplate.getDataSource().getConnection();
            Connection conn = DataSourceUtils.getConnection(jdbcTemplate.getDataSource());
            String sql = "UPDATE t_user SET last_logon_time = ? WHERE user_name = ?";

            jdbcTemplate.update(sql , System.currentTimeMillis() , userName);

            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class UserServiceRunner extends Thread{
        private JdbcUserService userService;
        private String userName;

        public UserServiceRunner(JdbcUserService userService , String userName){
            this.userService = userService;
            this.userName = userName;
        }

        public void run(){
            userService.logon(userName);
        }
    }

    public static void asynchrLogon(JdbcUserService userService , String userName){
        UserServiceRunner runner = new UserServiceRunner(userService , userName);
        runner.start();
    }

    public static void sleep(long time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void reportConn(BasicDataSource basicDataSource){
        System.out.println(basicDataSource.getNumActive() + ":" + basicDataSource.getNumIdle());
    }

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext2.xml");
        JdbcUserService jdbcUserService = (JdbcUserService) ctx.getBean("jdbcUserService");

        BasicDataSource dataSource = (BasicDataSource) ctx.getBean("dataSource");

        JdbcUserService.reportConn(dataSource);

        JdbcUserService.asynchrLogon(jdbcUserService , "tom");
        JdbcUserService.sleep(5000);

        JdbcUserService.reportConn(dataSource);

        JdbcUserService.sleep(2000);

        JdbcUserService.reportConn(dataSource);
    }
}
