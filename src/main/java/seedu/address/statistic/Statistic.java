package seedu.address.statistic;

import javafx.scene.chart.XYChart;
import seedu.address.model.ReadOnlyDataBook;
import seedu.address.model.order.Order;

/**
 * API of statistics component
 * contains methods that CommandResult will call to calculate
 */
public interface Statistic {

    /**
     * Method to calculate total profit on completed Orders
     * @param orderBook order book of model
     * @param statsPayload payload object containing stats query
     * @return total calculated profit
     */
    String calculateTotalProfitOnCompleted(ReadOnlyDataBook<Order> orderBook,
                                           StatsPayload statsPayload);

    XYChart.Series<String, Number> calculateTotalProfitOnCompletedGraph(ReadOnlyDataBook<Order> orderBook,
                                                                       StatsPayload statsPayload);

    /**
     * Method to calculate total revenue on completed Orders
     * @param orderBook order book of model
     * @param statsPayload payload object containing stats query
     * @return total calculated  revenue
     */
    String calculateTotalRevenueOnCompleted(ReadOnlyDataBook<Order> orderBook,
                                            StatsPayload statsPayload);

    XYChart.Series<String, Number> calculateTotalRevenueOnCompletedGraph(ReadOnlyDataBook<Order> orderBook,
                                             StatsPayload statsPayload);

    /**
     * Method to calculate total Cost from {@Code phone} on completed orders
     * @param orderBook order book of model
     * @param statsPayload payload object containing stats query
     * @return total calculated cost
     */
    String calculateTotalCostOnCompleted(ReadOnlyDataBook<Order> orderBook,
                                         StatsPayload statsPayload);

    XYChart.Series<String, Number> calculateTotalCostOnCompletedGraph(ReadOnlyDataBook<Order> orderBook,
                                                                        StatsPayload statsPayload);

}
