package seedu.address.statistic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.statistic.TypicalStatsPayload.DEFAULT_STATS_PAYLOAD_COST_1;
import static seedu.address.statistic.TypicalStatsPayload.DEFAULT_STATS_PAYLOAD_PROFIT_1;
import static seedu.address.statistic.TypicalStatsPayload.DEFAULT_STATS_PAYLOAD_REVENUE_1;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

import seedu.address.commons.util.MoneyUtil;
import seedu.address.model.ReadOnlyDataBook;
import seedu.address.model.order.Order;
import seedu.address.model.phone.Phone;
import seedu.address.testutil.TypicalOrders;
import seedu.address.testutil.TypicalPhones;


public class StatisticsManagerTest {

    private Statistic statisticModule = new StatisticManager();


    @Test
    public void execute_calculateTotalRevenueOnCompleted() {
        OrderBookStub orderBookStub = new OrderBookStub();
        String actualResult = String.valueOf(
                orderBookStub
                        .getList()
                        .stream()
                        .map(x -> MoneyUtil.convertToDouble(x.getPrice()))
                        .reduce((x, y) -> x + y).get());
        String testResult = this.statisticModule.calculateTotalRevenueOnCompleted(
                orderBookStub, DEFAULT_STATS_PAYLOAD_REVENUE_1);
        assertEquals(actualResult, testResult);
    }

    @Test
    public void execute_calculateTotalProfitOnCompleted() {
        OrderBookStub orderBookStub = new OrderBookStub();
        String actualResult = String.valueOf(
                 orderBookStub
                         .getList()
                         .stream()
                         .map(x -> MoneyUtil.convertToDouble(x.getPrice())
                                 - MoneyUtil.convertToDouble(x.getPhone().getCost()))
                         .reduce((x, y) -> x + y).get());
        String testResult = this.statisticModule.calculateTotalProfitOnCompleted(orderBookStub,
                DEFAULT_STATS_PAYLOAD_PROFIT_1);
        assertEquals(actualResult, testResult);
    }

    @Test
    public void execute_calculateTotalCostOnCompleted() {
        OrderBookStub orderBookStub = new OrderBookStub();
        String actualResult = String.valueOf(
                orderBookStub
                        .getList()
                        .stream()
                        .map(x -> MoneyUtil.convertToDouble(x.getPhone().getCost()))
                        .reduce((x, y) -> x + y).get());
        String testResult = this.statisticModule.calculateTotalCostOnCompleted(
                orderBookStub, DEFAULT_STATS_PAYLOAD_COST_1);
        assertEquals(actualResult, testResult);
    }

    @Test
    public void execute_calculateTotalProfitOnCompletedGraph() {
        Statistic freshStatisticModule = new StatisticManager();
        OrderBookStub orderBookStub = new OrderBookStub();
        ObservableList<XYChart.Data<String, Number>> expectedResult = new XyChartExpectedProfitStub().getList();
        //orderBookStub.getList().forEach(x -> System.out.println(x));
        XYChart.Series<String, Number> testResult = freshStatisticModule.calculateTotalProfitOnCompletedGraph(
                orderBookStub, TypicalStatsPayload.DEFAULT_STATS_PAYLOAD_GRAPH);

        ObservableList<XYChart.Data<String, Number>> testResultData = testResult.getData();
        for (int i = 0; i < testResultData.size(); i++) {
            String currentTestString = testResultData.get(i).getXValue();
            Number currentTestProfit = testResultData.get(i).getYValue().doubleValue();
            String currentExpTestString = expectedResult.get(i).getXValue();
            Number currentExpTestProfit = expectedResult.get(i).getYValue().doubleValue();
            //System.out.println(currentTestProfit);
            assertTrue(currentTestString.equals(currentExpTestString));
            assertTrue(currentTestProfit.equals(currentExpTestProfit));
        }

        //ObservableList<XYChart.Data<String, Number>> expectedResultData = expectedResult.getData();
        //testResultData.forEach(x -> System.out.println(x));
    }


    @Test
    public void execute_calculateTotalCostOnCompletedGraph() {
        Statistic freshStatisticModule = new StatisticManager();
        OrderBookStub orderBookStub = new OrderBookStub();
        ObservableList<XYChart.Data<String, Number>> expectedResult = new XyChartExpectedCostStub().getList();
        //orderBookStub.getList().forEach(x -> System.out.println(x));
        XYChart.Series<String, Number> testResult = freshStatisticModule.calculateTotalCostOnCompletedGraph(
                orderBookStub, TypicalStatsPayload.DEFAULT_STATS_PAYLOAD_GRAPH);


        ObservableList<XYChart.Data<String, Number>> testResultData = testResult.getData();
        for (int i = 0; i < testResultData.size(); i++) {
            String currentTestString = testResultData.get(i).getXValue();
            Number currentTestCost = testResultData.get(i).getYValue().doubleValue();
            String currentExpTestString = expectedResult.get(i).getXValue();
            Number currentExpTestCost = expectedResult.get(i).getYValue().doubleValue();
            assertTrue(currentTestString.equals(currentExpTestString));
            assertTrue(currentTestCost.equals(currentExpTestCost));
        }

        //ObservableList<XYChart.Data<String, Number>> expectedResultData = expectedResult.getData();
        //testResultData.forEach(x -> System.out.println(x));
    }

    @Test
    public void execute_calculateTotalRevenueOnCompletedGraph() {
        Statistic freshStatisticModule = new StatisticManager();
        OrderBookStub orderBookStub = new OrderBookStub();
        ObservableList<XYChart.Data<String, Number>> expectedResultRevenue = new XyChartExpectedRevenueStub().getList();
        XYChart.Series<String, Number> testRevenue = freshStatisticModule.calculateTotalRevenueOnCompletedGraph(
                orderBookStub, TypicalStatsPayload.DEFAULT_STATS_PAYLOAD_GRAPH);


        ObservableList<XYChart.Data<String, Number>> testResultData = testRevenue.getData();

        for (int i = 0; i < testResultData.size(); i++) {
            String currentTestStringRev = testResultData.get(i).getXValue();
            Number currentTestCostRev = testResultData.get(i).getYValue().doubleValue();
            String currentExpTestStringRev = expectedResultRevenue.get(i).getXValue();
            Number currentExpTestCostRev = expectedResultRevenue.get(i).getYValue().doubleValue();
            assertTrue(currentTestStringRev.equals(currentExpTestStringRev));
            assertTrue(currentTestCostRev.equals(currentExpTestCostRev));
        }

        //ObservableList<XYChart.Data<String, Number>> expectedResultData = expectedResult.getData();
        //testResultData.forEach(x -> System.out.println(x));
    }


    /**
     * Stub method to build a dummy order book for use here
     */
    private class OrderBookStub implements ReadOnlyDataBook<Order> {
        private final ObservableList<Order> orders = FXCollections.observableArrayList();

        OrderBookStub() {
            ObservableList<Order> listOfTypicalOrders = FXCollections.observableArrayList();
            listOfTypicalOrders.add(TypicalOrders.ORDERONESTATS);
            listOfTypicalOrders.add(TypicalOrders.ORDERTWOSTATS);
            listOfTypicalOrders.add(TypicalOrders.ORDERTHREESTATS);
            listOfTypicalOrders.add(TypicalOrders.ORDERFOURSTATS);
            listOfTypicalOrders.add(TypicalOrders.ORDERFIVESTATS);
            listOfTypicalOrders.add(TypicalOrders.ORDERSIXSTATS);
            listOfTypicalOrders.add(TypicalOrders.ORDERSEVENSTATS);
            this.orders.setAll(listOfTypicalOrders);
        }

        @Override
        public ObservableList<Order> getList() {
            return orders;
        }
    }

    /**
     * Stub method to build a dummy order book for use here
     */
    private class PhoneBookStub implements ReadOnlyDataBook<Phone> {
        private final ObservableList<Phone> phones = FXCollections.observableArrayList();

        PhoneBookStub() {
            ObservableList<Phone> listOfTypicalPhones = FXCollections.observableArrayList();
            listOfTypicalPhones.add(TypicalPhones.IPHONEONE);
            listOfTypicalPhones.add(TypicalPhones.IPHONETWO);
            listOfTypicalPhones.add(TypicalPhones.ANDROIDONE);
            listOfTypicalPhones.add(TypicalPhones.IPHONETWO);

            this.phones.setAll(listOfTypicalPhones);
        }

        @Override
        public ObservableList<Phone> getList() {
            return phones;
        }
    }

    /**
     * hardcoded XYChart.series
     */
    private class XyChartExpectedCostStub {
        private final XYChart.Series<String, Number> testSeries = new XYChart.Series<>();

        XyChartExpectedCostStub() {
            testSeries.getData().add(new XYChart.Data<String, Number>("January 2019", 0.0));
            testSeries.getData().add(new XYChart.Data<String, Number>("February 2019", 300.0));
            testSeries.getData().add(new XYChart.Data<String, Number>("March 2019", 300.0));
            testSeries.getData().add(new XYChart.Data<String, Number>("April 2019", 0.0));
            testSeries.getData().add(new XYChart.Data<String, Number>("May 2019", 300.0));
            testSeries.getData().add(new XYChart.Data<String, Number>("June 2019", 900.0));
            testSeries.getData().add(new XYChart.Data<String, Number>("July 2019", 0.0));
            testSeries.getData().add(new XYChart.Data<String, Number>("August 2019", 0.0));
            testSeries.getData().add(new XYChart.Data<String, Number>("September 2019", 0.0));
            testSeries.getData().add(new XYChart.Data<String, Number>("October 2019", 0.0));
            testSeries.getData().add(new XYChart.Data<String, Number>("November 2019", 300.0));
            testSeries.getData().add(new XYChart.Data<String, Number>("December 2019", 0.0));
        }

        public ObservableList<XYChart.Data<String, Number>> getList() {
            return this.testSeries.getData();
        }
    }

    /**
     * hardcoded XYChart.series
     */
    private class XyChartExpectedRevenueStub {
        private final XYChart.Series<String, Number> testSeries = new XYChart.Series<>();

        XyChartExpectedRevenueStub() {
            testSeries.getData().add(new XYChart.Data<String, Number>("January 2019", 0.0));
            testSeries.getData().add(new XYChart.Data<String, Number>("February 2019", 1231.12));
            testSeries.getData().add(new XYChart.Data<String, Number>("March 2019", 909.0));
            testSeries.getData().add(new XYChart.Data<String, Number>("April 2019", 0.0));
            testSeries.getData().add(new XYChart.Data<String, Number>("May 2019", 500.0));
            testSeries.getData().add(new XYChart.Data<String, Number>("June 2019", 1500.0));
            testSeries.getData().add(new XYChart.Data<String, Number>("July 2019", 0.0));
            testSeries.getData().add(new XYChart.Data<String, Number>("August 2019", 0.0));
            testSeries.getData().add(new XYChart.Data<String, Number>("September 2019", 0.0));
            testSeries.getData().add(new XYChart.Data<String, Number>("October 2019", 0.0));
            testSeries.getData().add(new XYChart.Data<String, Number>("November 2019", 500.0));
            testSeries.getData().add(new XYChart.Data<String, Number>("December 2019", 0.0));
        }

        public ObservableList<XYChart.Data<String, Number>> getList() {
            return this.testSeries.getData();
        }
    }

    /**
     * hardcoded XYChart.series
     */
    private class XyChartExpectedProfitStub {
        private final XYChart.Series<String, Number> testSeries = new XYChart.Series<>();

        XyChartExpectedProfitStub() {
            testSeries.getData().add(new XYChart.Data<String, Number>("January 2019", 0.0));
            testSeries.getData().add(new XYChart.Data<String, Number>("February 2019", 931.12));
            testSeries.getData().add(new XYChart.Data<String, Number>("March 2019", 609.0));
            testSeries.getData().add(new XYChart.Data<String, Number>("April 2019", 0.0));
            testSeries.getData().add(new XYChart.Data<String, Number>("May 2019", 200.0));
            testSeries.getData().add(new XYChart.Data<String, Number>("June 2019", 600.0));
            testSeries.getData().add(new XYChart.Data<String, Number>("July 2019", 0.0));
            testSeries.getData().add(new XYChart.Data<String, Number>("August 2019", 0.0));
            testSeries.getData().add(new XYChart.Data<String, Number>("September 2019", 0.0));
            testSeries.getData().add(new XYChart.Data<String, Number>("October 2019", 0.0));
            testSeries.getData().add(new XYChart.Data<String, Number>("November 2019", 200.0));
            testSeries.getData().add(new XYChart.Data<String, Number>("December 2019", 0.0));
        }

        public ObservableList<XYChart.Data<String, Number>> getList() {
            return this.testSeries.getData();
        }
    }

}




