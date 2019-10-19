package seedu.address.statistic;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.statistic.DateUtil.extractMonth;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.scene.chart.XYChart;
import org.apache.commons.math3.stat.StatUtils;

import javafx.collections.ObservableList;
import seedu.address.commons.util.MoneyUtil;
import seedu.address.commons.util.StatsPayload;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.ReadOnlyDataBook;
import seedu.address.model.order.Order;
import seedu.address.model.order.Status;
import seedu.address.model.util.SampleDataUtil;

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
        //filter the list of orders to be only the orders within the starting and ending date.
        List<Order> listOfFilteredOrders = getFilteredOrderListByDate(SampleDataUtil.getSampleOrderBook()
                , statsPayload)
                .collect(Collectors.toList());

        // returns a list of Months between starting and ending date.
        List<Calendar> listOfMonth = DateUtil.getListOfYearMonth(statsPayload);
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        // loops through the list of months, for each month, calculate the revenue for all orders in that month
        // returns this in the format of XYChat.DaTa().
        List<XYChart.Data<String,Number>> listOfMonthlyRevenue =
                listOfMonth.stream()
                .map(month -> processMonth(listOfFilteredOrders, month))
                .collect(Collectors.toList());

        listOfMonthlyRevenue.stream().forEach( x -> series.getData().add(x));

        series.getData().forEach(x -> System.out.println(x));
        return series;
    }

    // utility function to help with extract the relevant data to an XYChar.Data<A,B> object
    private XYChart.Data<String, Number> processMonth(List<Order> listOfOrders, Calendar month) {
        return new XYChart.Data<String, Number>(
                StringUtil.convertCalendarMonthToString(month),
                StatisticManager.calculateRevenueByMonth(listOfOrders, month));
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



    /*-------Methods to help with calculating monthly detail---------*/
    /**
     * Takes in an orderlist, calculates the revenue of all orders in this month
     * @param orderList list of Orders
     * @param month the month
     */
    private static double calculateRevenueByMonth(List<Order> orderList, Calendar month) {
        double[] doubleRevenueList =
                StatisticManager.checkIfOrderIsPresent(orderList.stream())
                .filter(currentOrder -> extractMonth(currentOrder) == month.get(2) )
                .map(currentOrder -> MoneyUtil.convertToDouble(currentOrder.getPrice()))
                .collect(Collectors.toList())
                .stream()
                .mapToDouble(d -> d).toArray();
        return StatUtils.sum(doubleRevenueList);
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

    /*-----Methods that deal with returning double[]----*/
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



    /*-----helper methods with regards to Stream----*/
    private static Stream<Order> checkIfOrderIsPresent(Stream<Order> streamOrder) {
        return streamOrder.filter(currentOrder -> currentOrder.getStatus() == Status.COMPLETED)
                .filter(currentOrder -> currentOrder.getSchedule().isPresent());
    }

    private static Stream<Order> getFilteredOrderListByDate(ReadOnlyDataBook<Order> orderBook, StatsPayload statsPayload) {
        ObservableList<Order> orderList = orderBook.getList();
        Stream<Order> filteredOrderListByDate =
                StatisticManager.checkIfOrderIsPresent(orderList.stream())
                        .filter(currentOrder -> statsPayload.getStartingDate().compareTo(
                                currentOrder.getSchedule().get().getCalendar()) <= 0)
                        .filter(currentOrder -> statsPayload.getEndingDate().compareTo(
                                currentOrder.getSchedule().get().getCalendar()) > 0);
        return filteredOrderListByDate;
    }
}


