package com.smart.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {

    private static Logger logger = LogManager.getLogger(Test.class);

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
        logger.debug("begining...");
        UserService service = (UserService) ctx.getBean("userService");
        service.logon("zhangsan");
        logger.debug("ending...");
    }

}
