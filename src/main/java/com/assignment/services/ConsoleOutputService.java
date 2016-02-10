package com.assignment.services;

import com.assignment.entities.menu.MainMenuOptions;
import com.assignment.entities.orders.OrderAction;
import com.assignment.entities.stocks.Stock;

import java.util.Date;
import java.util.Map;

/**
 * @author kbezos
 */
public interface ConsoleOutputService {
    public MainMenuOptions getMainMenuTradingOption();
    public Integer getSelectedStockFromMenu(Map<Integer, Stock> transactionalStockData, String selectedMenuOption);
    public void doPrintTradingConsoleStart();
    public void doPrintDividendYieldPerStock(Stock stock);
    public void doPrintPERatioPerStock(Stock stock);
    public void doPrintStockPricePerStock(Stock stock);
    public void doPrintGBCEIndexforAllStocks(Map<Integer, Stock> transactionalStockData);
    public void doPrintHistoryTradingActity(Map<Date, OrderAction> historyTradingLog, Date dateLogLimit);
    public void doPrintDoubleLineSeparator();
    public Double getTradePriceOfOrder();
    public Integer getTradeVolumeOfOrder();
    public void doPrintError(String warning);
}
