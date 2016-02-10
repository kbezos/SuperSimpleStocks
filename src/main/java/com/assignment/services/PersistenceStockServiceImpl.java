package com.assignment.services;

import com.assignment.entities.orders.OrderAction;
import com.assignment.entities.stocks.CommonStock;
import com.assignment.entities.stocks.PreferredStock;
import com.assignment.entities.stocks.Stock;
import org.apache.commons.lang.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author kbezos
 */
public class PersistenceStockServiceImpl implements PersistenceStockService{
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private Map<Date, OrderAction> historyTradingLog;
    private Map<Integer, Stock> transactionalStockData;

    @Override
    public Map<Integer, Stock> getReferenceStockDataFromExchange() {
        Map<Integer, Stock>  referenceStockData = new LinkedHashMap<Integer, Stock>();
        CommonStock cs1 = new CommonStock("TEA", 0.0, 100);
        CommonStock cs2 = new CommonStock("POP", 8.0, 100);
        CommonStock cs3 = new CommonStock("ALE", 23.0, 60);
        PreferredStock ps4 = new PreferredStock("GIN", 8.0,  0.02, 100);
        CommonStock cs5 = new CommonStock("JOE", 13.0, 250);

        referenceStockData.put(1, cs1);
        referenceStockData.put(2, cs2);
        referenceStockData.put(3, cs3);
        referenceStockData.put(4, ps4);
        referenceStockData.put(5, cs5);
        return  referenceStockData;
    }

    @Override
    public void initTransactionalData() {
        historyTradingLog = new LinkedHashMap<Date, OrderAction>();
        transactionalStockData =  new LinkedHashMap<Integer, Stock>();
        this.setTransactionalStockData(this.getReferenceStockDataFromExchange());
    }


    @Override
    public boolean commitTransaction(OrderAction orderAction) {
        logger.debug("Trying to commit the transaction of " + orderAction.getStock().getStockSymbol());
        Stock stock = orderAction.getStock();
        Stock backupStock = (Stock) SerializationUtils.clone(stock);
        boolean result = false;
        try {
            stock.setStockPrice(orderAction.doCalculateStockPrice());
            stock.setDividendYield(stock.doCalculateDividendYield());
            stock.setVolume(orderAction.doCalculateVolumeOfShares());
            stock.setPeRatio(stock.doCalculateStockPERatio());
            stock.setLastDividend(stock.doCalculateDividend());
            //Try to persist ..
            stock.updateStockPriceHistory(stock.getStockPrice());
            logger.debug("The transaction successfully committed.. ");
            result = true;
        } catch (Exception ex) {
            logger.error("An exception thrown during the commit phase ...");
            logger.debug("This transaction would be rollback..");
            //This Exception should be more specific.. i.e Network / DB / Calculations etc
            //In a real application this transaction should be handled proper (queue / db )
            //If something happens - rollback ..
            orderAction.setStock(backupStock);
        }  finally {
            //close any db connections ..

        }
        return result;
    }

    @Override
    public Map<Date, OrderAction> getHistoryTradingLog() {
        return this.historyTradingLog;
    }

    @Override
    public void updateHistoryTradingLog(OrderAction orderAction) {
        this.historyTradingLog.put(new Date(), orderAction);
    }

    @Override
    public Map<Integer, Stock> getTransactionalStockData() {
        return this.transactionalStockData;
    }

    public void setTransactionalStockData(Map<Integer, Stock> transactionalStockData) {
        this.transactionalStockData = transactionalStockData;
    }
}
