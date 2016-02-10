package com.assignment.entities.orders;

/**
 * @author kbezos
 */
public class BuyOrderAction extends OrderAction {

    @Override
    public Double doCalculateStockPrice() {
        Double previousTotalPrice = this.getStock().getVolume() * this.getStock().getStockPrice();
        Double transactionTotalPrice = this.getTradePrice() * this.getQuantityOfShares();
        Integer totalAmountOfShares = this.getStock().getVolume() +  this.getQuantityOfShares();

        return (previousTotalPrice +  transactionTotalPrice) / totalAmountOfShares;
    }

    @Override
    public Integer doCalculateVolumeOfShares() {
        return this.getStock().getVolume() + this.getQuantityOfShares();
    }

    @Override
    public String getHistoryActionRecord() {
        return "\t BUY  \t " +this.getStock().getStockSymbol()+ " \t\t " + this.getQuantityOfShares() + " \t\t\t " +this.getTradePrice();
    }

    @Override
    public boolean isValidTradePrice(Double tradePrice) {
        return tradePrice  > 0;
    }

    @Override
    public boolean isValidTradeQuantity(Integer tradeQuantity) {
        return tradeQuantity  > 0 ;
    }
}
