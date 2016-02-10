package com.assignment.services;

import com.assignment.entities.menu.MainMenuOptions;
import com.assignment.entities.orders.BuyOrderAction;
import com.assignment.entities.orders.OrderAction;
import com.assignment.entities.orders.SellOrderAction;
import com.assignment.entities.stocks.Stock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;

/**
 * @author kbezos
 */
@Service
public class SuperSimpleStocksServiceImpl implements SuperSimpleStocksService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    public static final String BUY_MENU_OPTION = "[Buy Stocks]";
    public static final String SELL_MENU_OPTION = "[Sell Stocks]";
    public static final String CALCULATE_MENU_OPTION = "[Calculate Stock Attributes]";
    public static final String INVALID_ORDER_INPUT = "\nError : The order cannot be executed because either the trade price or the trade quantify are not valid ..";
    public static final String TRANSACTION_FAILED = "\nError : The transaction failed and could not be committed..";

    @Value("${minutes.history.ago:15}")
    public String minHistoryAgo;

    @Autowired
    private PersistenceStockService persistenceService;
    @Autowired
    private ConsoleOutputService consoleService;

    @Override
    public void doExecuteTrading() {
        persistenceService.initTransactionalData();
        consoleService.doPrintTradingConsoleStart();
        while (true) {
            MainMenuOptions mainMenuSelection = consoleService.getMainMenuTradingOption();
            switch (mainMenuSelection) {
                case BUY_STOCKS :           doExecuteBuyOrder();                                   break;
                case SELL_STOCKS:           doExecuteSellOrder();                                  break;
                case STOCK_CALCULATIONS:    doPrintStockMetrics();                                 break;
                case PRINT_TRADING_LOG:     doPrintTradingHistory(Integer.valueOf(minHistoryAgo)); break;
                case EXIT :                 System.exit(0);                                        break;
                default: logger.error("An invalid main trading option was given to the menu ..");
            }
        }
    }

    private void doExecuteBuyOrder() {
        OrderAction order = new BuyOrderAction();
        doExecuteOrder(order, BUY_MENU_OPTION);
    }

    private void doExecuteSellOrder() {
        OrderAction order = new SellOrderAction();
        doExecuteOrder(order, SELL_MENU_OPTION);
    }

    @Transactional
    private void doExecuteOrder(OrderAction order, String selectedMenuOption) {
        //In a real application / system this method should be transactional in order to provide atomicity - see @Transactional
        Double tradePrice = 0.0;
        Integer tradeQuantity = 0;
        Integer stockId = consoleService.getSelectedStockFromMenu(persistenceService.getTransactionalStockData(), selectedMenuOption);
        if (stockId > persistenceService.getTransactionalStockData().size()){
            return;
        }
        Stock stock = persistenceService.getTransactionalStockData().get(stockId);
        tradePrice = consoleService.getTradePriceOfOrder();
        tradeQuantity =  consoleService.getTradeVolumeOfOrder();
        order.setStock(stock);
        if (!order.isValidTradePrice(tradePrice) || !order.isValidTradeQuantity(tradeQuantity)) {
            consoleService.doPrintError(INVALID_ORDER_INPUT);
            return;
        }
        order.setTradePrice(tradePrice);
        order.setQuantityOfShares(tradeQuantity);
        boolean successfullyCommit = persistenceService.commitTransaction(order);
        //Pseudo Handling or transaction failure ...
        if (!successfullyCommit) {
            consoleService.doPrintError(TRANSACTION_FAILED);
            return;
        }
        //Depending on the audit requirements this action (update transaction and history) should be handled together with the commit action
        persistenceService.getTransactionalStockData().put(stockId, stock);
        persistenceService.updateHistoryTradingLog(order);
    }

    private void doPrintStockMetrics() {
        Integer stockId = consoleService.getSelectedStockFromMenu(persistenceService.getTransactionalStockData(), CALCULATE_MENU_OPTION);
        if (stockId > persistenceService.getTransactionalStockData().size()){
            return;
        }
        Stock stock = persistenceService.getTransactionalStockData().get(stockId);
        consoleService.doPrintDividendYieldPerStock(stock);
        consoleService.doPrintPERatioPerStock(stock);
        consoleService.doPrintStockPricePerStock(stock);
        consoleService.doPrintGBCEIndexforAllStocks(persistenceService.getTransactionalStockData());
    }


    @Override
    public void doPrintTradingHistory(Integer minsAgo) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MINUTE, -minsAgo);
        Date dateLogLimit = cal.getTime();
        consoleService.doPrintHistoryTradingActity(persistenceService.getHistoryTradingLog(), dateLogLimit);
    }
}
