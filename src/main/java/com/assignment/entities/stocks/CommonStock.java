package com.assignment.entities.stocks;

/**
 * @author kbezos
 */
public class CommonStock extends Stock {

    public CommonStock(String stockSymbol, Double lastDividend, Integer parValue) {
        super(stockSymbol,lastDividend, parValue);
    }

    @Override
    public Double doCalculateDividendYield() {
        if (this.getLastDividend() == null || this.getLastDividend() == 0.0) {
            return 0.0;
        } else if (this.getStockPrice() == null || this.getStockPrice() == 0.0) {
            return 0.0;
        }

        return this.getLastDividend()/this.getStockPrice();
    }

    @Override
    public Double doCalculateDividend() {
        // Common stock dividend calculation :
        // Common Stock Dividend = Dividend Yield * Par Value * Total Number of Shares
        if (this.getDividendYield() == null || this.getDividendYield() == 0.0) {
            return 0.0;
        } else if (this.getParValue() == null || this.getParValue() == 0) {
            return 0.0;
        } else if (this.getVolume() == null || this.getVolume() == 0) {
            return 0.0;
        }
        return this.getDividendYield()* this.getParValue() * this.getVolume();
    }
}
