package seedu.address.statistic;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.math3.stat.StatUtils;

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import seedu.address.commons.util.MoneyUtil;
import seedu.address.commons.util.StringUtil;
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
    public XYChart.Series<String, Number> calculateTotalProfitOnCompletedGraph(ReadOnlyDataBook<Order> orderBook,
                                                                                StatsPayload statsPayload) {
        requireAllNonNull(orderBook, statsPayload);
        //filter the list of orders to be only the orders within the starting and ending date.
        List<Order> listOfFilteredOrders = getFilteredOrderListByDate(orderBook,
                statsPayload)
                .collect(Collectors.toList());

        // returns a list of Months between starting and ending date.
        List<Calendar> listOfMonth = DateUtil.getListOfYearMonth(statsPayload);
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        // loops through the list of months, for each month, calculate the revenue for all orders in that month
        // returns this in the format of XYChat.Data().
        List<XYChart.Data<String, Number>> listOfMonthlyProfit =
                listOfMonth.stream()
                        .map(month -> processProfitByMonth(listOfFilteredOrders, month))
                        .collect(Collectors.toList());

        listOfMonthlyProfit.stream().forEach(x -> series.getData().add(x));
        return series;
    }

    @Override
    public XYChart.Series<String, Number> calculateTotalRevenueOnCompletedGraph(ReadOnlyDataBook<Order> orderBook,
                                                                     StatsPayload statsPayload) {
        requireAllNonNull(orderBook, statsPayload);
        //filter the list of orders to be only the orders within the starting and ending date.
        List<Order> listOfFilteredOrders = getFilteredOrderListByDate(orderBook,
                statsPayload)
                .collect(Collectors.toList());

        // returns a list of Months between starting and ending date.
        List<Calendar> listOfMonth = DateUtil.getListOfYearMonth(statsPayload);

        XYChart.Series<String, Number> series = new XYChart.Series<>();

        // loops through the list of months, for each month, calculate the revenue for all orders in that month
        // returns this in the format of XYChat.DaTa().
        List<XYChart.Data<String, Number>> listOfMonthlyRevenue =
                listOfMonth.stream()
                .map(month -> processRevenueByMonth(listOfFilteredOrders, month))
                .collect(Collectors.toList());

        listOfMonthlyRevenue.stream().forEach(x -> series.getData().add(x));
        return series;
    }

    @Override
    public XYChart.Series<String, Number> calculateTotalCostOnCompletedGraph(ReadOnlyDataBook<Order> orderBook,
                                                                               StatsPayload statsPayload) {
        requireAllNonNull(orderBook, statsPayload);
        //filter the list of orders to be only the orders within the starting and ending date.
        List<Order> listOfFilteredOrders = getFilteredOrderListByDate(orderBook,
                statsPayload)
                .collect(Collectors.toList());

        // returns a list of Months between starting and ending date.
        List<Calendar> listOfMonth = DateUtil.getListOfYearMonth(statsPayload);

        XYChart.Series<String, Number> series = new XYChart.Series<>();

        // loops through the list of months, for each month, calculate the cost for all orders in that month
        // returns this in the format of XYChat.DaTa().
        List<XYChart.Data<String, Number>> listOfMonthlyCost =
                listOfMonth.stream()
                        .map(month -> processCostByMonth(listOfFilteredOrders, month))
                        .collect(Collectors.toList());

        listOfMonthlyCost.stream().forEach(x -> series.getData().add(x));
        return series;
    }

    @Override
    public String calculateTotalProfitOnCompleted(ReadOnlyDataBook<Order> orderBook,
                                                  StatsPayload statsPayload) {
        requireAllNonNull(orderBook, statsPayload);
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

    // utility function to help with extract the relevant data to an XYChar.Data<A,B> object
    private XYChart.Data<String, Number> processProfitByMonth(List<Order> listOfOrders, Calendar month) {
        return new XYChart.Data<String, Number>(
                StringUtil.convertCalendarDateToGraphDisplay(month),
                StatisticManager.calculateProfitByMonth(listOfOrders, month));
    }

    // utility function to help with extract the relevant data to an XYChar.Data<A,B> object
    private XYChart.Data<String, Number> processRevenueByMonth(List<Order> listOfOrders, Calendar month) {
        return new XYChart.Data<String, Number>(
                StringUtil.convertCalendarDateToGraphDisplay(month),
                StatisticManager.calculateRevenueByMonth(listOfOrders, month));
    }

    // utility function to help with extract the relevant data to an XYChar.Data<A,B> object
    private XYChart.Data<String, Number> processCostByMonth(List<Order> listOfOrders, Calendar month) {
        return new XYChart.Data<String, Number>(
                StringUtil.convertCalendarDateToGraphDisplay(month),
                StatisticManager.calculateCostByMonth(listOfOrders, month));
    }

    /**
     * Takes in an orderlist, calculates the profit of all orders in this month
     */
    private static double calculateProfitByMonth(List<Order> orderList, Calendar month) {
        DecimalFormat profitFormatter = new DecimalFormat("#.####");
        profitFormatter.setRoundingMode(RoundingMode.CEILING);

        double[] doubleProfitList =
                StatisticManager.streamOfPresentOrders(orderList.stream())
                        .filter(currentOrder ->
                               DateUtil.extractMonth(currentOrder) == month.get(Calendar.MONTH)
                                        && DateUtil.extractYear(currentOrder) == month.get(Calendar.YEAR))
                        .map(currentOrder ->
                                profitFormatter.format(MoneyUtil.convertToDouble(currentOrder.getPrice())
                                -
                                MoneyUtil.convertToDouble(currentOrder.getPhone().getCost())))
                        .collect(Collectors.toList())
                        .stream()
                        .mapToDouble(Double::parseDouble).toArray();
        return StatUtils.sum(doubleProfitList);
    }

    /**
     * Takes in an orderlist, calculates the revenue of all orders in this month
     */
    private static double calculateRevenueByMonth(List<Order> orderList, Calendar month) {
        double[] doubleRevenueList =
                StatisticManager.streamOfPresentOrders(orderList.stream())
                .filter(currentOrder ->
                        DateUtil.extractMonth(currentOrder) == month.get(2)
                        && DateUtil.extractYear(currentOrder) == month.get(1))
                .map(currentOrder -> MoneyUtil.convertToDouble(currentOrder.getPrice()))
                .collect(Collectors.toList())
                .stream()
                .mapToDouble(d -> d).toArray();
        return StatUtils.sum(doubleRevenueList);
    }

    /**
     * Takes in an orderlist, calculates the cost of all orders in this month
     */
    private static double calculateCostByMonth(List<Order> orderList, Calendar month) {
        double[] doubleCostList =
                StatisticManager.streamOfPresentOrders(orderList.stream())
                        .filter(currentOrder ->
                                DateUtil.extractMonth(currentOrder) == month.get(2)
                                        && DateUtil.extractYear(currentOrder) == month.get(1))
                        .map(currentOrder -> MoneyUtil.convertToDouble(currentOrder.getPhone().getCost()))
                        .collect(Collectors.toList())
                        .stream()
                        .mapToDouble(d -> d).toArray();
        return StatUtils.sum(doubleCostList);
    }

    /*-------------- helper methods ------------------*/

    private double getTotalRevenue(ReadOnlyDataBook<Order> orderBook, StatsPayload statsPayload) {
        double[] completedOrderPriceArray = getOrderPriceArrayInDouble(orderBook, statsPayload);
        return StatUtils.sum(completedOrderPriceArray);
    }

    private double getTotalCost(ReadOnlyDataBook<Order> orderBook, StatsPayload statsPayload) {
        double[] completedOrderPhoneCostList = getPhoneCostArrayInDouble(orderBook, statsPayload);
        return StatUtils.sum(completedOrderPhoneCostList);
    }

    /*-----Methods that deal with returning double[]----*/
    private static double[] getOrderPriceArrayInDouble(ReadOnlyDataBook<Order> orderBook, StatsPayload statsPayload) {
        List<Double> completedOrderPriceList = getFilteredOrderListByDate(orderBook, statsPayload)
                .map(currentOrder -> MoneyUtil.convertToDouble(currentOrder.getPrice()))
                .collect(Collectors.toList());
        return completedOrderPriceList.stream().mapToDouble(d -> d).toArray();
    }

    private static double[] getPhoneCostArrayInDouble(ReadOnlyDataBook<Order> orderBook, StatsPayload statsPayload) {
        ObservableList<Order> orderList = orderBook.getList();
        List<Double> completedOrderPhoneList = getFilteredOrderListByDate(orderBook, statsPayload)
                .map(currentOrder -> MoneyUtil.convertToDouble(currentOrder.getPhone().getCost()))
                .collect(Collectors.toList());
        return completedOrderPhoneList.stream().mapToDouble(d -> d).toArray();
    }

    /*-----helper methods with regards to generating Stream----*/
    private static Stream<Order> streamOfPresentOrders(Stream<Order> streamOrder) {
        return streamOrder.filter(currentOrder -> currentOrder.getStatus() == Status.COMPLETED)
                .filter(currentOrder -> currentOrder.getSchedule().isPresent());
    }

    private static Stream<Order> getFilteredOrderListByDate(ReadOnlyDataBook<Order> orderBook,
                                                            StatsPayload statsPayload) {
        ObservableList<Order> orderList = orderBook.getList();
        return StatisticManager.streamOfPresentOrders(orderList.stream())
                .filter(currentOrder -> statsPayload.getStartingDate().compareTo(
                        DateUtil.extractMonthYear(currentOrder)) <= 0)
                .filter(currentOrder -> statsPayload.getEndingDate().compareTo(
                        DateUtil.extractMonthYear(currentOrder)) >= 0);
    }
}


