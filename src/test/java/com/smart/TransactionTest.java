package com.smart;

import com.smart.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.annotations.Test;


public class TransactionTest {

    static Logger logger = LogManager.getLogger(TransactionTest.class.getName());

    @Test
    public void testTransaction(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
        logger.info("begining...");
        UserService service = (UserService) ctx.getBean("userService");
        service.logon("zhangsan");
        logger.info("ending...");
    }

}
