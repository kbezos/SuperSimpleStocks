package com.assignment.services;

import com.assignment.entities.orders.OrderAction;
import com.assignment.entities.stocks.Stock;

import java.util.Date;
import java.util.Map;

/**
 * @author kbezos
 */
public interface PersistenceStockService {
    public Map<Integer, Stock> getReferenceStockDataFromExchange();
    public void initTransactionalData();
    public boolean commitTransaction(OrderAction orderAction);
    public Map<Date, OrderAction> getHistoryTradingLog();
    public void updateHistoryTradingLog(OrderAction orderAction);
    public Map<Integer, Stock> getTransactionalStockData();
    public void setTransactionalStockData(Map<Integer, Stock> transactionalStockData);

}
