package seedu.address.statistic;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.math3.stat.StatUtils;

import javafx.collections.ObservableList;
import seedu.address.commons.util.MoneyUtil;
import seedu.address.model.ReadOnlyDataBook;
import seedu.address.model.order.Order;
import seedu.address.model.order.Status;

/**
 * Represents the in-memory statistics module of the current SML data.Ò
 * Utility module to calculate statistics from the model data.
 */
public class StatisticManager implements Statistic {

    public StatisticManager() {}

    /*--------------------Methods to calculate--------------------------*/
    @Override
    public String calculateTotalProfitOnCompleted(ReadOnlyDataBook<Order> orderBook,
                                                  StatsPayload statsPayload) {
        double revenue = this.getTotalRevenue(orderBook, statsPayload);
        double cost = this.getTotalCost(orderBook, statsPayload);
        double totalProfit = revenue - cost;
        return String.valueOf(totalProfit);
    }

    @Override
    public String calculateTotalRevenueOnCompleted(ReadOnlyDataBook<Order> orderBook, StatsPayload statsPayload) {
        requireAllNonNull(orderBook, statsPayload);
        double totalRevenue = getTotalRevenue(orderBook, statsPayload);
        return String.valueOf(totalRevenue);
    }

    @Override
    public String calculateTotalCostOnCompleted(ReadOnlyDataBook<Order> orderBook, StatsPayload statsPayload) {
        requireAllNonNull(orderBook);
        double totalCost = getTotalCost(orderBook, statsPayload);
        return String.valueOf(totalCost);
    }

    /*-------------- helper methods ------------------*/
    private double getTotalRevenue(ReadOnlyDataBook<Order> orderBook, StatsPayload statsPayload) {
        double[] completedOrderPriceArray = getDoubleOrderPriceArray(orderBook, statsPayload);
        return StatUtils.sum(completedOrderPriceArray);
    }

    private double getTotalCost(ReadOnlyDataBook<Order> orderBook, StatsPayload statsPayload) {
        double[] completedOrderPhoneCostList = getDoublePhoneCostArray(orderBook, statsPayload);
        return StatUtils.sum(completedOrderPhoneCostList);
    }
    private static double[] getDoubleOrderPriceArray(ReadOnlyDataBook<Order> orderBook, StatsPayload statsPayload) {
        ObservableList<Order> orderList = orderBook.getList();
        List<Double> completedOrderPriceList =
               orderList.stream()
                       .filter(currentOrder -> currentOrder.getStatus() == Status.COMPLETED)
                       .filter(currentOrder -> currentOrder.getSchedule().isPresent())
                       .filter(currentOrder -> statsPayload.getStartingDate().compareTo(
                               currentOrder.getSchedule().get().getCalendar()) <= 0)
                       .filter(currentOrder -> statsPayload.getEndingDate().compareTo(
                               currentOrder.getSchedule().get().getCalendar()) > 0)
                       .map(currentOrder -> MoneyUtil.convertToDouble(currentOrder.getPrice()))
                       .collect(Collectors.toList());
        return completedOrderPriceList.stream().mapToDouble(d -> d).toArray();
    }

    private static double[] getDoublePhoneCostArray(ReadOnlyDataBook<Order> orderBook, StatsPayload statsPayload) {
        ObservableList<Order> orderList = orderBook.getList();
        List<Double> completedOrderPhoneList =
                orderList.stream()
                        .filter(currentOrder -> currentOrder.getStatus() == Status.COMPLETED)
                        .filter(currentOrder -> currentOrder.getSchedule().isPresent())
                        .filter(currentOrder -> statsPayload.getStartingDate().compareTo(
                                currentOrder.getSchedule().get().getCalendar()) <= 0)
                        .filter(currentOrder -> statsPayload.getEndingDate().compareTo(
                                currentOrder.getSchedule().get().getCalendar()) > 0)
                        .map(currentOrder -> MoneyUtil.convertToDouble(currentOrder.getPhone().getCost()))
                        .collect(Collectors.toList());
        return completedOrderPhoneList.stream().mapToDouble(d -> d).toArray();
    }
}
