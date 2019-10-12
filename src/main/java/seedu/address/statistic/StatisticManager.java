
package seedu.address.statistic;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.math3.stat.StatUtils;

import javafx.collections.ObservableList;
import seedu.address.model.ReadOnlyDataBook;
import seedu.address.model.order.Order;
import seedu.address.model.order.Status;
import seedu.address.model.phone.Phone;

/**
 * Represents the in-memory statistics module of the current SML data.
 * Utility module to calculate statistics from the model data.
 */
public class StatisticManager implements Statistic {


    public StatisticManager() {}

    @Override
    public String calculateTotalProfit(ReadOnlyDataBook<Order> orderBook, ReadOnlyDataBook<Phone> phoneBook) {
        double revenueString = Double.parseDouble(this.calculateTotalRevenue(orderBook));
        double costString = Double.parseDouble(this.calculateTotalCost(orderBook, phoneBook));
        double totalProfitResult = revenueString - costString;
        return String.valueOf(totalProfitResult);
    }

    @Override
    public String calculateTotalRevenue(ReadOnlyDataBook<Order> orderBook) {
        requireNonNull(orderBook);
        double[] completedOrderPriceArray = getDoubleOrderPriceArray(orderBook);
        double totalRevenue = StatUtils.sum(completedOrderPriceArray);
        return String.valueOf(totalRevenue);
    }

    @Override
    public String calculateTotalCost(ReadOnlyDataBook<Order> orderBook,
                                     ReadOnlyDataBook<Phone> phoneBook) {
        requireAllNonNull(orderBook, phoneBook);
        double[] completedOrderPhoneCostList = getDoublePhoneCostArray(orderBook);
        double totalCost = StatUtils.sum(completedOrderPhoneCostList);
        return String.valueOf(totalCost);
    }


    // helper methods
    private static double[] getDoubleOrderPriceArray(ReadOnlyDataBook<Order> orderBook) {
        ObservableList<Order> orderList = orderBook.getList();
        List<Double> completedOrderPriceList =
                orderList.stream()
                .filter(currentOrder -> currentOrder.getStatus() == Status.COMPLETED)
                .map(currentOrder -> Double.parseDouble(currentOrder.getPrice().value))
                .collect(Collectors.toList());
        return completedOrderPriceList.stream().mapToDouble(d -> d).toArray();
    }

    private static double[] getDoublePhoneCostArray(ReadOnlyDataBook<Order> orderBook) {
        ObservableList<Order> orderList = orderBook.getList();
        List<Double> completedOrderPhoneList =
                orderList.stream()
                .filter(currentOrder -> currentOrder.getStatus() == Status.COMPLETED)
                .map(currentOrder -> Double.parseDouble(currentOrder.getPhone().getCost().value))
                .collect(Collectors.toList());
        return completedOrderPhoneList.stream().mapToDouble(d -> d).toArray();
    }
}
