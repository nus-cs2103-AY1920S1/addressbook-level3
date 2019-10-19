package seedu.address.statistic;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.commons.util.StringUtil.convertCalendarDateToString;
import static seedu.address.statistic.DateUtil.extractMonth;
import static seedu.address.statistic.DateUtil.getListOfYearMonth;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.scene.chart.XYChart;
import org.apache.commons.math3.stat.StatUtils;

import javafx.collections.ObservableList;
import seedu.address.commons.util.MoneyUtil;
import seedu.address.commons.util.StatsPayload;
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
    public XYChart.Series<String, Number> calculateTotalRevenueOnCompletedGraph(ReadOnlyDataBook<Order> orderBook,
                                                                     StatsPayload statsPayload) {
        List<Order> listOfOrders = orderBook.getList();
        List<Calendar> listOfYearMonth = getListOfYearMonth(listOfOrders,statsPayload);
        System.out.println(listOfYearMonth);
        return null;
    }



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
        List<Double> completedOrderPriceList = getFilteredOrderListByDate(orderBook, statsPayload)
                .map(currentOrder -> MoneyUtil.convertToDouble(currentOrder.getPrice()))
                .collect(Collectors.toList());
        return completedOrderPriceList.stream().mapToDouble(d -> d).toArray();
    }

    private static double[] getDoublePhoneCostArray(ReadOnlyDataBook<Order> orderBook, StatsPayload statsPayload) {
        ObservableList<Order> orderList = orderBook.getList();
        List<Double> completedOrderPhoneList = getFilteredOrderListByDate(orderBook, statsPayload)
                        .map(currentOrder -> MoneyUtil.convertToDouble(currentOrder.getPhone().getCost()))
                        .collect(Collectors.toList());
        return completedOrderPhoneList.stream().mapToDouble(d -> d).toArray();
    }

    private static Stream<Order> getFilteredOrderListByDate(ReadOnlyDataBook<Order> orderBook, StatsPayload statsPayload) {
        ObservableList<Order> orderList = orderBook.getList();
        Stream<Order> filteredOrderListByDate =
                orderList.stream()
                        .filter(currentOrder -> currentOrder.getStatus() == Status.COMPLETED)
                        .filter(currentOrder -> currentOrder.getSchedule().isPresent())
                        .filter(currentOrder -> statsPayload.getStartingDate().compareTo(
                                currentOrder.getSchedule().get().getCalendar()) <= 0)
                        .filter(currentOrder -> statsPayload.getEndingDate().compareTo(
                                currentOrder.getSchedule().get().getCalendar()) > 0);
        return filteredOrderListByDate;
    }

    private static double caculateRevenueMonth(List<Order> orderList, int month) {
        double[] doubleRevenueList = orderList.stream()
                .filter(currentOrder -> currentOrder.getStatus() == Status.COMPLETED)
                .filter(currentOrder -> currentOrder.getSchedule().isPresent())
                .filter(currentOrder -> extractMonth(currentOrder) == month )
                .map(currentOrder -> MoneyUtil.convertToDouble(currentOrder.getPrice()))
                .collect(Collectors.toList())
                .stream()
                .mapToDouble(d -> d).toArray();
        return StatUtils.sum(doubleRevenueList);
    }
}
