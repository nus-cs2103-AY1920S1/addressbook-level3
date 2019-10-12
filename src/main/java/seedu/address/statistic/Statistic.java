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
     * Method to calculate total profit on completed Orders
     * @param orderBook order book of model
     * @param phoneBook phone book of model
     * @return total calculated profit
     */
    String calculateTotalProfitOnCompleted(ReadOnlyDataBook<Order> orderBook, ReadOnlyDataBook<Phone> phoneBook);

    /**
     * Method to calculate total revenue on completed Orders
     * @param orderBook order book of model
     * @return total calculated  revenue
     */
    String calculateTotalRevenueOnCompleted(ReadOnlyDataBook<Order> orderBook);

    /**
     * Method to calculate total Cost from {@Code phone} on completed orders
     * @param orderBook order book of model
     * @param phoneBook phone book of model
     * @return total calculated cost
     */
    String calculateTotalCostOnCompleted(ReadOnlyDataBook<Order> orderBook, ReadOnlyDataBook<Phone> phoneBook);


}
