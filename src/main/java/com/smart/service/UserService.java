package com.smart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.SQLException;

@Service("userService")
public class UserService extends BaseService{

    private ScoreService scoreService;

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setScoreService(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void updateLastLogonTime(String userName){
        String sql = "UPDATE t_user u SET u.last_logon_time = ? WHERE user_name = ?";
        jdbcTemplate.update(sql , System.currentTimeMillis() , userName);
    }

    //该方法嵌套调用了本类的其他方法及其他服务类的方法
    public void logon(String userName){
        System.out.println("before UserService.updateLastLogonTime()");
        updateLastLogonTime(userName);//本服务类的其他方法
        System.out.println("after UserService.updateLastLogonTime()");

        System.out.println("before ScoreService.addScore()");
        scoreService.addScore(userName , 20);//其他服务类的方法
        System.out.println("after ScoreService.addScore()");
    }



}
