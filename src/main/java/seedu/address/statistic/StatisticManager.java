package seedu.address.statistic;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.math3.stat.StatUtils;

import javafx.collections.ObservableList;
import seedu.address.commons.util.MoneyUtil;
import seedu.address.commons.util.StatsPayload;
import seedu.address.model.ReadOnlyDataBook;
import seedu.address.model.order.Order;
import seedu.address.model.order.Status;
import seedu.address.model.phone.Phone;

/**
 * Represents the in-memory statistics module of the current SML data.Ò
 * Utility module to calculate statistics from the model data.
 */
public class StatisticManager implements Statistic {


    public StatisticManager() {}

    /*--------------------Methods to calculate--------------------------*/
    @Override
    public String calculateTotalProfitOnCompleted(ReadOnlyDataBook<Order> orderBook,
                                                  ReadOnlyDataBook<Phone> phoneBook,
                                                  StatsPayload statsPayload) {

        double revenueString = Double.parseDouble(
                this.calculateTotalRevenueOnCompleted(orderBook, statsPayload));

        double costString = Double.parseDouble(
                this.calculateTotalCostOnCompleted(orderBook, phoneBook, statsPayload));

        double totalProfitResult = revenueString - costString;

        return String.valueOf(totalProfitResult);
    }

    @Override
    public String calculateTotalProfitOnCompleted(ReadOnlyDataBook<Order> orderBook,
                                                  ReadOnlyDataBook<Phone> phoneBook) {

        double revenueString = Double.parseDouble(this.calculateTotalRevenueOnCompleted(orderBook));

        double costString = Double.parseDouble(this.calculateTotalCostOnCompleted(orderBook, phoneBook));

        double totalProfitResult = revenueString - costString;

        return String.valueOf(totalProfitResult);
    }

    @Override
    public String calculateTotalRevenueOnCompleted(ReadOnlyDataBook<Order> orderBook) {
        requireNonNull(orderBook);
        double[] completedOrderPriceArray = getDoubleOrderPriceArray(orderBook);
        double totalRevenue = StatUtils.sum(completedOrderPriceArray);
        return String.valueOf(totalRevenue);
    }

    @Override
    public String calculateTotalRevenueOnCompleted(ReadOnlyDataBook<Order> orderBook, StatsPayload statsPayload) {
        requireAllNonNull(orderBook, statsPayload);
        double[] completedOrderPriceArray = getDoubleOrderPriceArray(orderBook, statsPayload);
        double totalRevenue = StatUtils.sum(completedOrderPriceArray);
        return String.valueOf(totalRevenue);
    }

    @Override
    public String calculateTotalCostOnCompleted(ReadOnlyDataBook<Order> orderBook,
                                                ReadOnlyDataBook<Phone> phoneBook) {
        requireAllNonNull(orderBook, phoneBook);
        double[] completedOrderPhoneCostList = getDoublePhoneCostArray(orderBook);
        double totalCost = StatUtils.sum(completedOrderPhoneCostList);
        return String.valueOf(totalCost);
    }

    @Override
    public String calculateTotalCostOnCompleted(ReadOnlyDataBook<Order> orderBook,
                                                ReadOnlyDataBook<Phone> phoneBook , StatsPayload statsPayload) {
        requireAllNonNull(orderBook, phoneBook);
        double[] completedOrderPhoneCostList = getDoublePhoneCostArray(orderBook, statsPayload);
        double totalCost = StatUtils.sum(completedOrderPhoneCostList);
        return String.valueOf(totalCost);
    }


    /*-------------- helper methods ------------------*/
    private static double[] getDoubleOrderPriceArray(ReadOnlyDataBook<Order> orderBook, StatsPayload statsPayload) {
        ObservableList<Order> orderList = orderBook.getList();
        List<Double> completedOrderPriceList =
               orderList.stream()
                       .filter(currentOrder -> currentOrder.getStatus() == Status.COMPLETED)
                       .filter(currentOrder -> currentOrder.getSchedule().isPresent())
                       .filter(currentOrder -> {
                           return statsPayload.getStartingDate().compareTo(
                                   currentOrder.getSchedule().get().getCalendar()) <= 0;
                       })
                       .filter(currentOrder -> {
                           return statsPayload.getEndingDate().compareTo(
                                   currentOrder.getSchedule().get().getCalendar()) > 0;
                       })
                       .map(currentOrder -> MoneyUtil.convertToDouble(currentOrder.getPrice()))
                       .collect(Collectors.toList());
        return completedOrderPriceList.stream().mapToDouble(d -> d).toArray();
    }

    private static double[] getDoubleOrderPriceArray(ReadOnlyDataBook<Order> orderBook) {
        ObservableList<Order> orderList = orderBook.getList();
        List<Double> completedOrderPriceList =
                orderList.stream()
                .filter(currentOrder -> currentOrder.getStatus() == Status.COMPLETED)
                .map(currentOrder -> MoneyUtil.convertToDouble(currentOrder.getPrice()))
                .collect(Collectors.toList());
        return completedOrderPriceList.stream().mapToDouble(d -> d).toArray();
    }

    private static double[] getDoublePhoneCostArray(ReadOnlyDataBook<Order> orderBook) {
        ObservableList<Order> orderList = orderBook.getList();
        List<Double> completedOrderPhoneList =
                orderList.stream()
                .filter(currentOrder -> currentOrder.getStatus() == Status.COMPLETED)
                .map(currentOrder -> MoneyUtil.convertToDouble(currentOrder.getPhone().getCost()))
                .collect(Collectors.toList());
        return completedOrderPhoneList.stream().mapToDouble(d -> d).toArray();
    }

    private static double[] getDoublePhoneCostArray(ReadOnlyDataBook<Order> orderBook, StatsPayload statsPayload) {
        ObservableList<Order> orderList = orderBook.getList();
        List<Double> completedOrderPhoneList =
                orderList.stream()
                        .filter(currentOrder -> currentOrder.getStatus() == Status.COMPLETED)
                        .filter(currentOrder -> currentOrder.getSchedule().isPresent())
                        .filter(currentOrder -> {
                            return statsPayload.getStartingDate().compareTo(
                                    currentOrder.getSchedule().get().getCalendar()) <= 0;
                        })
                        .filter(currentOrder -> {
                            return statsPayload.getEndingDate().compareTo(
                                    currentOrder.getSchedule().get().getCalendar()) > 0;
                        })
                        .map(currentOrder -> MoneyUtil.convertToDouble(currentOrder.getPhone().getCost()))
                        .collect(Collectors.toList());
        return completedOrderPhoneList.stream().mapToDouble(d -> d).toArray();
    }
}
