package seedu.address.statistic;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import org.apache.commons.math3.stat.StatUtils;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.ReadOnlyDataBook;
import seedu.address.model.order.Order;
import seedu.address.model.order.Status;
import seedu.address.model.phone.Phone;

/**
 * Represents the in-memory statistics module of the current SML data.
 */
public class StatisticManager implements Statistic {


    public StatisticManager() {}

    @Override
    public void getOrderBook(ReadOnlyDataBook<Order> orderBook) {

    }

    @Override
    public void calculateTotalCost() {

    }

    @Override
    public String calculateTotalProfit(ReadOnlyDataBook<Order> orderBook, ReadOnlyDataBook<Phone> phoneBook) {
        double revenueString = Double.parseDouble(this.calculateTotalRevenue(orderBook));
        double costString = Double.parseDouble(this.calculateTotalCost(orderBook,phoneBook));
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
        List<Double> completedOrderPriceList = new ArrayList();
        for (Order currentOrder : orderList) {
            if (currentOrder.getStatus() == Status.COMPLETED) {
                Double currentPrice = Double.parseDouble(currentOrder.getPrice().value);
                completedOrderPriceList.add(currentPrice);
            }
        }
        return completedOrderPriceList.stream().mapToDouble(d -> d).toArray();
    }

    private static double[] getDoublePhoneCostArray(ReadOnlyDataBook<Order> orderBook) {
        ObservableList<Order> orderList = orderBook.getList();
        List<Double> completedOrderPhoneList = new ArrayList();
        for (Order currentOrder : orderList) {
            if (currentOrder.getStatus() == Status.COMPLETED) {
                Double currentPhonePrice = Double.parseDouble(
                        currentOrder.getPhone().getCost().value);
                completedOrderPhoneList.add(currentPhonePrice);
            }
        }
        return completedOrderPhoneList.stream().mapToDouble(d -> d).toArray();
    }
}
