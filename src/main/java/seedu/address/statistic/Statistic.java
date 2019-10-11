package seedu.address.statistic;

import seedu.address.model.ReadOnlyDataBook;
import seedu.address.model.order.Order;

/**
 * API of statistics component
 * contains methods that CommandResult will call to calculate
 */
public interface Statistic {

    /**
     * calculate total Cost
     */
    void calculateTotalCost();

    /**
     * Insert model into current instance
     */
    void getOrderBook(ReadOnlyDataBook<Order> OrderBook);


    /**
     * calculate total Profit
     */
    String calculateTotalProfit();

    /**
     * calculate total Gross revenue
     */
    void calculateTotalRevenue();
}
