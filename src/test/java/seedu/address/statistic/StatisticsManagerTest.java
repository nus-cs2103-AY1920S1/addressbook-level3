package seedu.address.statistic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.commons.util.TypicalStatsPayload.DEFAULT_STATS_PAYLOAD_COST_1;
import static seedu.address.commons.util.TypicalStatsPayload.DEFAULT_STATS_PAYLOAD_PROFIT_1;
import static seedu.address.commons.util.TypicalStatsPayload.DEFAULT_STATS_PAYLOAD_REVENUE_1;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.address.model.ReadOnlyDataBook;
import seedu.address.model.order.Order;
import seedu.address.model.phone.Phone;
import seedu.address.testutil.TypicalOrders;
import seedu.address.testutil.TypicalPhones;


public class StatisticsManagerTest {

    private Statistic statisticModule = new StatisticManager();
    private ObservableList<Order> listOfTypicalOrders;


    @Test
    public void execute_calculateTotalRevenueOnCompleted() {
        OrderBookStub orderBookStub = new OrderBookStub();
        String actualResult = "0.0";
        String testResult = this.statisticModule.calculateTotalRevenueOnCompleted(orderBookStub,
                DEFAULT_STATS_PAYLOAD_REVENUE_1);
        assertEquals(actualResult, testResult);
    }

    @Test
    public void execute_calculateTotalProfitOnCompleted() {
        OrderBookStub orderBookStub = new OrderBookStub();
        String actualResult = "0.0";
        String testResult = this.statisticModule.calculateTotalProfitOnCompleted(orderBookStub,
                DEFAULT_STATS_PAYLOAD_PROFIT_1);
        assertEquals(actualResult, testResult);
    }

    @Test
    public void execute_calculateTotalCostOnCompleted() {
        OrderBookStub orderBookStub = new OrderBookStub();
        PhoneBookStub phoneBookStub = new PhoneBookStub();
        String actualResult = "0.0";
        String testResult = this.statisticModule.calculateTotalCostOnCompleted(orderBookStub,
                DEFAULT_STATS_PAYLOAD_COST_1);
        assertEquals(actualResult, testResult);
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

    /**
     * Stub method to build a dummy order book for use here
     */
    private static class PhoneBookStub implements ReadOnlyDataBook<Phone> {
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

}


