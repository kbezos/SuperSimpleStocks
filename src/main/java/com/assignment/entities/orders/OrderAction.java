package com.assignment.entities.orders;

import com.assignment.entities.stocks.Stock;

/**
 * @author kbezos
 */
public abstract class OrderAction {

    private Stock stock;
    private Integer quantityOfShares;
    private Double tradePrice;

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public Integer getQuantityOfShares() {
        return quantityOfShares;
    }

    public void setQuantityOfShares(Integer quantityOfShares) {
        this.quantityOfShares = quantityOfShares;
    }

    public Double getTradePrice() {
        return tradePrice;
    }

    public void setTradePrice(Double tradePrice) {
        this.tradePrice = tradePrice;
    }

    public abstract Double doCalculateStockPrice();

    public abstract Integer doCalculateVolumeOfShares();

    public abstract String getHistoryActionRecord();

    public abstract boolean isValidTradePrice(Double tradePrice);

    public abstract boolean isValidTradeQuantity(Integer tradeQuantity);


}
