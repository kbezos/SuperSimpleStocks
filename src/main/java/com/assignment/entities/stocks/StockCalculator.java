package com.assignment.entities.stocks;

/**
 * @author kbezos
 */
public interface StockCalculator {
    public Double doCalculateDividendYield();
    public Double doCalculateDividend();
    public Double doCalculateStockPERatio();
    public Double doCalculateGBCEIndex();
}
