package seedu.address.statistic;

import static java.util.Objects.requireNonNull;

//import org.apache.commons.math3.stat.StatUtils;

import seedu.address.model.Model;
import seedu.address.model.ReadOnlyDataBook;
import seedu.address.model.customer.Customer;
import seedu.address.model.order.Order;
import seedu.address.model.phone.Phone;
import seedu.address.model.schedule.Schedule;

/**
 * Represents the in-memory statistics module of the current SML data.
 */
public class StatisticManager implements Statistic {

    /*
    private final Model currentModel;

    // private data fields from the model instance
    private final ReadOnlyDataBook<Customer> customerBook;
    private final ReadOnlyDataBook<Phone> phoneBook;
    private final ReadOnlyDataBook<Order> orderBook;
    private final ReadOnlyDataBook<Schedule> scheduleBook;
*/
    // data to store the statistics that has been calculated
    private StatisticBook currentBook;


    public StatisticManager() {

        /*currentModel = model;
        customerBook = currentModel.getCustomerBook();
        phoneBook = currentModel.getPhoneBook();
        orderBook = currentModel.getOrderBook();
        scheduleBook = currentModel.getScheduleBook();*/
        currentBook = new StatisticBook();
    }

    @Override
    public void getOrderBook(ReadOnlyDataBook<Order> orderBook) {

    }

    @Override
    public void calculateTotalCost() {

    }

    @Override
    public String calculateTotalProfit() {
        return "test stats";
    }

    @Override
    public void calculateTotalRevenue() {

    }
}
