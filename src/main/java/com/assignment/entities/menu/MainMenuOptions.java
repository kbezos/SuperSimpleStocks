package com.assignment.entities.menu;

/**
 * @author kbezos
 */
public enum MainMenuOptions {
    BUY_STOCKS (1, "Buy Stocks"),
    SELL_STOCKS (2, "Sell Stocks"),
    STOCK_CALCULATIONS(3, "Stock Calculations"),
    PRINT_TRADING_LOG (4, "Print Trading log"),
    EXIT (5, "Exit");

    private int option;
    private String message;

    private MainMenuOptions(int option, String message) {
        this.option = option;
        this.message = message;
    }

    public static MainMenuOptions getMainMenuSelectionByOption(int option){
        MainMenuOptions selectedOption = null;
        for (MainMenuOptions menuOption : MainMenuOptions.class.getEnumConstants()) {
            if (menuOption.getOption() == option) {
                selectedOption = menuOption;
                break;
            }
        }
        return selectedOption;
    }

    public int getOption() {
        return option;
    }

    public String getMessage() {
        return message;
    }

    public String toString() {
        return message;
    }


}
