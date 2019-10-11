package seedu.address.statistic;

import seedu.address.model.ReadOnlyDataBook;
import seedu.address.model.order.Order;
import seedu.address.model.phone.Phone;

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
     * calculate total Gross Profit
     */
    String calculateTotalProfit(ReadOnlyDataBook<Order> orderBook, ReadOnlyDataBook<Phone> phoneBook);

    /**
     * calculate total Gross Revenue
     */
    String calculateTotalRevenue(ReadOnlyDataBook<Order> orderBook);

    /**
     * calculate total Gross Cost
     */
    String calculateTotalCost(ReadOnlyDataBook<Order> orderBook, ReadOnlyDataBook<Phone> PhoneBook);


}
