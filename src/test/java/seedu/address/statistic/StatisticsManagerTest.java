package seedu.address.statistic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.address.model.OrderBook;
import seedu.address.model.ReadOnlyDataBook;
import seedu.address.model.order.Order;
import seedu.address.testutil.TypicalOrders;


public class StatisticsManagerTest {

    private Statistic statisticModule = new StatisticManager();
    private ObservableList<Order> listOfTypicalOrders;


    @Test
    public void execute_calculateTotalRevenueOnCompleted() {
        OrderBookStub orderBookStub = new OrderBookStub();
        String actualResult = "2640.12";
        String testResult = this.statisticModule.calculateTotalRevenueOnCompleted(orderBookStub);
        assertEquals(actualResult,testResult);
    }

    /**
     * Stub method to build a dummy order book for use here
     */
    private static class OrderBookStub implements ReadOnlyDataBook<Order> {
        private final ObservableList<Order> orders = FXCollections.observableArrayList();

        OrderBookStub() {
            ObservableList<Order> listOfTypicalOrders = FXCollections.observableArrayList();
            listOfTypicalOrders.add(TypicalOrders.ORDERONE);
            listOfTypicalOrders.add(TypicalOrders.ORDERTWO);
            listOfTypicalOrders.add(TypicalOrders.ORDERTHREE);
            this.orders.setAll(listOfTypicalOrders);
        }

        @Override
        public ObservableList<Order> getList() {
            return orders;
        }
    }


}


