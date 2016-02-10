package com.assignment.services;

import com.assignment.entities.menu.MainMenuOptions;
import com.assignment.entities.orders.OrderAction;
import com.assignment.entities.stocks.Stock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Scanner;

/**
 * @author kbezos
 */
public class ConsoleOutputServiceImpl implements ConsoleOutputService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    public static final String DOUBLE_LINE_SEPARATOR = "=======================================================";
    public static final String SINGLE_LINE_SEPARATOR = "-------------------------------------------------------";
    public static final String ENTER_CHOICE = "Enter your choice :" ;
    public static final NumberFormat FORMARTER = new DecimalFormat("#0.000");
    public static final SimpleDateFormat dateFormater = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");

    private Scanner scanIn;

    @Value("${minutes.history.ago:15}")
    public String minHistoryAgo;

    @Override
    public MainMenuOptions getMainMenuTradingOption() {
        int option = 0;
        int maxOption = 0;
        boolean isValidAnswer = false;
        scanIn = new Scanner(System.in);
        do {
            System.out.println("\nPlease select one of the following trading activities :");
            System.out.println(SINGLE_LINE_SEPARATOR);
            for (MainMenuOptions menuOption : MainMenuOptions.class.getEnumConstants()) {
                System.out.println(menuOption.getOption()+") " + menuOption.getMessage());
                maxOption = menuOption.getOption();
            }
            System.out.print(ENTER_CHOICE);
            option  =  scanIn.nextInt();
            scanIn.nextLine();
            isValidAnswer = (option > 0 && option < maxOption +1 ) ? true : false;
        } while(!isValidAnswer);
        return MainMenuOptions.getMainMenuSelectionByOption(option);
    }

    @Override
    public Integer getSelectedStockFromMenu(Map<Integer, Stock> transactionalStockData, String selectedMenuOption) {
        Integer selectedStockId = 0;
        boolean isValidAnswer = false;
        scanIn = new Scanner(System.in);

        do {
            System.out.println("\n <> " + selectedMenuOption +" select one of the following stocks :");
            System.out.println(SINGLE_LINE_SEPARATOR);
            for (Integer stockId : transactionalStockData.keySet()) {
                System.out.println(stockId +") " + transactionalStockData.get(stockId).getStockSymbol());
            }
            System.out.println(transactionalStockData.size()+1 +") * Return To Main Menu");
            System.out.print(ENTER_CHOICE);
            selectedStockId  =  scanIn.nextInt();
            scanIn.nextLine();
            isValidAnswer = (selectedStockId > 0 && selectedStockId < transactionalStockData.size()+2) ? true : false;
        } while (!isValidAnswer);
        return selectedStockId;
    }

    @Override
    public void doPrintTradingConsoleStart() {
        System.out.println("========== SuperSimpleStocks Trading Console ==========");
    }

    @Override
    public void doPrintDividendYieldPerStock(Stock stock) {
        doPrintDoubleLineSeparator();
        System.out.println("---> The Dividend Yield of " + stock.getStockSymbol() +  " is : " + FORMARTER.format(stock.getDividendYield()));
    }

    @Override
    public void doPrintPERatioPerStock(Stock stock) {
        System.out.println("---> The PE / Ration of " + stock.getStockSymbol() +  " is : " + FORMARTER.format(stock.getPeRatio()));
    }

    @Override
    public void doPrintStockPricePerStock(Stock stock) {
        System.out.println("---> The Stock Price of " + stock.getStockSymbol() +  " is : " + FORMARTER.format(stock.getStockPrice()));
    }

    @Override
    public void doPrintGBCEIndexforAllStocks(Map<Integer, Stock> transactionalStockData) {
        System.out.println("---> The GBCE Index Value Per Stock is :");
        System.out.println(SINGLE_LINE_SEPARATOR);
        for (Map.Entry<Integer, Stock> transStock : transactionalStockData.entrySet()) {
            Stock stock = transStock.getValue();
            System.out.println(" > " + stock.getStockSymbol() +  " : " + FORMARTER.format(stock.doCalculateGBCEIndex()));
        }
        doPrintDoubleLineSeparator();
    }

    @Override
    public void doPrintHistoryTradingActity(Map<Date, OrderAction> historyTradingLog, Date dateLogLimit) {
        System.out.println("\n=========================== Last " + minHistoryAgo + " mins ===========================\n");
        System.out.println("\t\tTimestamp       Action   Stock    TradeQuantity    TradePrice");
        System.out.println("---------------------------------------------------------------------");
        for (Map.Entry<Date, OrderAction> historyRecord : historyTradingLog.entrySet()) {
            Date dateLog = historyRecord.getKey();
            if(dateLog.before(dateLogLimit)) {
                break;
            }
            System.out.println(dateFormater.format(dateLog) + historyRecord.getValue().getHistoryActionRecord());
        }
        System.out.println("=====================================================================");
    }

    @Override
    public void doPrintDoubleLineSeparator() {
        System.out.println(DOUBLE_LINE_SEPARATOR);
    }

    @Override
    public Double getTradePriceOfOrder() {
        doPrintDoubleLineSeparator();
        scanIn = new Scanner(System.in);
        System.out.print("---> Enter trade price:");
        Double tradePrice = scanIn.nextDouble();
        return tradePrice;

    }

    @Override
    public Integer getTradeVolumeOfOrder() {
        scanIn = new Scanner(System.in);
        System.out.print("---> Enter trade quantity :");
        Integer tradeQuantity =  scanIn.nextInt();
        doPrintDoubleLineSeparator();
        return tradeQuantity;
    }

    @Override
    public void doPrintError(String warning) {
        System.out.println(warning);
        logger.error("");
    }
}
