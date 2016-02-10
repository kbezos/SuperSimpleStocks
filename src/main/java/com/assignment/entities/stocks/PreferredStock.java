package com.assignment.entities.stocks;

/**
 * @author kbezos
 */
public class PreferredStock extends Stock {
    private Double fixedDividend;

    public PreferredStock(String stockSymbol, Double lastDividend, Double fixedDividend, Integer parValue) {
        super(stockSymbol,lastDividend, parValue);
        this.fixedDividend = fixedDividend;
    }

    public Double getFixedDividend() {
        return fixedDividend;
    }

    public void setFixedDividend(Double fixedDividend) {
        this.fixedDividend = fixedDividend;
    }

    @Override
    public Double doCalculateDividendYield() {
        if (this.getFixedDividend() == null || this.getFixedDividend() == 0.0) {
            return 0.0;
        } else if (this.getParValue() == null || this.getParValue() == 0) {
            return 0.0;
        } else if (this.getStockPrice() == null || this.getStockPrice() == 0.0) {
            return 0.0;
        }
        return (this.getFixedDividend() * this.getParValue())/this.getStockPrice();
    }

    @Override
    public Double doCalculateDividend() {
        // Preferred stock dividend calculation :
        //Preferred Stock Dividend = Fixed Dividend * Par Value * Total Number of Shares
        if (this.getFixedDividend() == null || this.getFixedDividend() == 0.0) {
            return 0.0;
        } else if (this.getParValue() == null || this.getParValue() == 0) {
            return 0.0;
        } else if (this.getVolume() == null || this.getVolume() == 0) {
            return 0.0;
        }
        return this.getFixedDividend()* this.getParValue() * this.getVolume();
    }
}
