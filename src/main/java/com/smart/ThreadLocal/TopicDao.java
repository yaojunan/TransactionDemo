package com.smart.ThreadLocal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TopicDao {

    public static ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal<Connection>();

    public static Connection getConnection() throws SQLException {
        //如果connectionThreadLocal没有本线程对应的Connection，则创建一个新的Connection，
        //并将其保存到线程本地变量中。
        if(connectionThreadLocal.get() == null){
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test");
            connectionThreadLocal.set(conn);
            return conn;
        } else {
            //直接返回线程本地变量
            return connectionThreadLocal.get();
        }
    }

    public void addTopic() throws SQLException {
        //从ThreadLocal中获取线程对应的Connection

        Statement statement = getConnection().createStatement();
    }

}
