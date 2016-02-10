package com.assignment.entities.orders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author kbezos
 */
public class SellOrderAction extends OrderAction {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public Double doCalculateStockPrice() {
        Double previousTotalPrice = this.getStock().getVolume() * this.getStock().getStockPrice();
        Double transactionTotalPrice = this.getTradePrice() * this.getQuantityOfShares();
        Integer totalAmountOfShares = this.getStock().getVolume() -  this.getQuantityOfShares();

        return (previousTotalPrice -  transactionTotalPrice) / totalAmountOfShares;
    }

    @Override
    public Integer doCalculateVolumeOfShares() {
        return this.getStock().getVolume() - this.getQuantityOfShares();
    }

    @Override
    public String getHistoryActionRecord() {
        return "\t SELL  \t " +this.getStock().getStockSymbol()+ " \t\t " + this.getQuantityOfShares() + " \t\t\t " +this.getTradePrice();
    }

    @Override
    public boolean isValidTradePrice(Double tradePrice) {
        return tradePrice  > this.getStock().getVolume() ;
    }

    @Override
    public boolean isValidTradeQuantity(Integer tradeQuantity) {
        if (tradeQuantity > this.getStock().getVolume()) {
            logger.error("Invalid amount of quantity of shares tried to be trade .. ");
        }
        return tradeQuantity > this.getStock().getVolume();
    }
}
