package com.assignment;

import com.assignment.services.SuperSimpleStocksService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author kbezos
 */
public class SuperSimpleStocks {
    private static final Logger logger = LoggerFactory.getLogger(SuperSimpleStocks.class);
    private static ConfigurableApplicationContext applicationContext;

    public static void main(String []  args) {
        applicationContext = new ClassPathXmlApplicationContext("/spring/application-context.xml");
        SuperSimpleStocksService stockService = (SuperSimpleStocksService) applicationContext.getBean("stockService");

        logger.info("SuperSimpleStocks application is running ..");
        stockService.doExecuteTrading();
    }
}
