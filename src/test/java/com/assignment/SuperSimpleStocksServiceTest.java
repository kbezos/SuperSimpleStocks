package com.assignment;

import com.assignment.entities.orders.BuyOrderAction;
import com.assignment.entities.orders.OrderAction;
import com.assignment.entities.orders.SellOrderAction;
import com.assignment.entities.stocks.Stock;
import com.assignment.services.PersistenceStockService;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

/**
 * @author kbezos
 */
@SuppressWarnings({"SpringJavaAutowiringInspection", "ResultOfMethodCallIgnored"})

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/application-context.xml"} )
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SuperSimpleStocksServiceTest {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PersistenceStockService persistenceService;

    @Test
    public void testPersistenceInit() throws Exception{
        persistenceService.initTransactionalData();
        Map<Integer, Stock> testReferenceStockData = persistenceService.getReferenceStockDataFromExchange();
        Assert.assertNotNull("The reference stock data supposed to exist, but the getReferenceStockDataFromExchange returns NULL", testReferenceStockData);
    }

    @Test
    public void test01_BuyOrder() throws Exception{
        persistenceService.initTransactionalData();
        OrderAction order = new BuyOrderAction();
        Stock stock = persistenceService.getTransactionalStockData().get(1);
        order.setStock(stock);
        order.setTradePrice(100.0);
        order.setQuantityOfShares(120);
        boolean successfullyCommit = persistenceService.commitTransaction(order);
        Assert.assertNotEquals(successfullyCommit, false);
    }

    @Test
    public void test02_BuyOrderAndUpdateTransactionHistory() throws Exception{
        persistenceService.initTransactionalData();
        OrderAction order = new BuyOrderAction();
        Stock stock = persistenceService.getTransactionalStockData().get(1);
        order.setStock(stock);
        order.setTradePrice(100.0);
        order.setQuantityOfShares(120);
        persistenceService.commitTransaction(order);
        persistenceService.getTransactionalStockData().put(1, stock);
        persistenceService.updateHistoryTradingLog(order);
        Assert.assertNotNull("The reference stock data supposed to exist, but getTransactionalStockData returns NULL", persistenceService.getTransactionalStockData().containsKey(1));
    }

    @Test
    public void test03_SellOrder() throws Exception{
        persistenceService.initTransactionalData();
        OrderAction order = new SellOrderAction();
        Stock stock = persistenceService.getTransactionalStockData().get(1);
        order.setStock(stock);
        order.setTradePrice(80.0);
        order.setQuantityOfShares(20);
        boolean successfullyCommit = persistenceService.commitTransaction(order);
        Assert.assertNotEquals(successfullyCommit, false);
    }

    @Test
    public void test04_SellOrderAndUpdateTransactionHistory() throws Exception{
        persistenceService.initTransactionalData();
        OrderAction order = new SellOrderAction();
        Stock stock = persistenceService.getTransactionalStockData().get(1);
        order.setStock(stock);
        order.setTradePrice(80.0);
        order.setQuantityOfShares(20);
        persistenceService.commitTransaction(order);
        persistenceService.getTransactionalStockData().put(1, stock);
        persistenceService.updateHistoryTradingLog(order);
        Assert.assertNotNull("The reference stock data supposed to exist, but getTransactionalStockData returns NULL", persistenceService.getTransactionalStockData().containsKey(1));
    }

    @Test
    public void test05_DividendYieldCalculations() throws Exception{
        persistenceService.initTransactionalData();
        Stock stock = persistenceService.getTransactionalStockData().get(1);
        Assert.assertNotNull("The dividend yield should not be NULL", stock.doCalculateDividendYield());
    }

    @Test
    public void test06_DividendCalculations() throws Exception{
        persistenceService.initTransactionalData();
        Stock stock = persistenceService.getTransactionalStockData().get(1);
        Assert.assertNotNull("The dividend should not be NULL", stock.doCalculateDividend());
    }

    @Test
    public void test07_PERatioCalculations() throws Exception{
        persistenceService.initTransactionalData();
        Stock stock = persistenceService.getTransactionalStockData().get(1);
        Assert.assertNotNull("The P/E Ratio should not be NULL", stock.doCalculateStockPERatio());
    }

    @Test
    public void test08_GBCEIndexCalculations() throws Exception{
        persistenceService.initTransactionalData();
        Stock stock = persistenceService.getTransactionalStockData().get(1);
        Assert.assertNotNull("The GBCE Index should not be NULL", stock.doCalculateGBCEIndex());
    }

    @Test
    public void test09_PrintHistoryTradingActity() throws Exception{
        persistenceService.initTransactionalData();
        Assert.assertNotNull("The History Trading Log should not be NULL", persistenceService.getHistoryTradingLog());
    }
}
