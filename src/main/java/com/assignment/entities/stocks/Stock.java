package com.assignment.entities.stocks;


import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author kbezos
 */
public abstract class Stock implements StockCalculator, java.io.Serializable {
    private String stockSymbol;
    private Double lastDividend;
    private Integer parValue;
    private Integer volume;
    private Double stockPrice;
    private Double dividendYield;
    private Double peRatio;
    private Map<Date, Double> stockPriceHistory;

    private static final int MAX_STOCK_SYMBOL_LENGTH = 3;

    public Stock(String stockSymbol, Double lastDividend, Integer parValue) {
        this.stockPriceHistory = new LinkedHashMap<Date, Double>();
        this.stockSymbol = stockSymbol.substring(0, MAX_STOCK_SYMBOL_LENGTH).toUpperCase();
        this.lastDividend = lastDividend;
        this.parValue = parValue;
        this.dividendYield = 0.0;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(String stockSymbol) {
        if (stockSymbol != null && !stockSymbol.isEmpty()){
            this.stockSymbol = stockSymbol.substring(0, MAX_STOCK_SYMBOL_LENGTH).toUpperCase();
        } else  {
            System.out.println("Error the stock symbol is either null or empty...");
        }
    }

    public Double getLastDividend() {
        return lastDividend;
    }

    public void setLastDividend(Double lastDividend) {
        this.lastDividend = lastDividend;
    }

    public Integer getParValue() {
        return parValue;
    }

    public void setParValue(Integer parValue) {
        this.parValue = parValue;
    }

    public Integer getVolume() {
        if (volume == null) {
            return 0;
        }
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public Double getStockPrice() {
        if (stockPrice == null) {
            return 0.0;
        }
        return stockPrice;
    }

    public void setStockPrice(Double stockPrice) {
        this.stockPrice = stockPrice;
    }

    public Double getDividendYield() {
        return dividendYield;
    }

    public void setDividendYield(Double dividendYield) {
        this.dividendYield = dividendYield;
    }

    public Double getPeRatio() {
        return peRatio;
    }

    public void setPeRatio(Double peRatio) {
        this.peRatio = peRatio;
    }

    public Map<Date, Double> getStockPriceHistory() {
        return stockPriceHistory;
    }

    public void setStockPriceHistory(Map<Date, Double> stockPriceHistory) {
        this.stockPriceHistory = stockPriceHistory;
    }

    public void updateStockPriceHistory(Double stockPrice) {
        this.stockPriceHistory.put(new Date(), stockPrice);
    }

    public Double doCalculateStockPERatio() {
        if(this.getStockPrice() == null ||this.getStockPrice() == 0.0) {
            return 0.0;
        } else if (this.getLastDividend() == null || this.getLastDividend() == 0) {
            return 0.0;
        }
        return this.getStockPrice()/this.getLastDividend();
    }

    public Double doCalculateGBCEIndex() {
        Double factorial = 1.0;
        int count = 0;

        for(Map.Entry stockRecord : this.stockPriceHistory.entrySet()) {
            count++;
            Double stockPrice  = (Double) stockRecord.getValue();
            factorial = stockPrice * factorial;
        }
        if (count == 0){
            return 0.0;
        }
        double exp = (double) 1 / count;
        return Double.valueOf(Math.pow(factorial, exp));
    }
}
