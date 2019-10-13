package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalOrders.VIPORDER;
import static seedu.address.testutil.TypicalOrders.getTypicalOrderBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.order.Order;
import seedu.address.model.order.exceptions.DuplicateOrderException;
import seedu.address.testutil.OrderBuilder;

public class OrderBookTest {

    private final OrderBook orderBook = new OrderBook();

    @Test
    public void constructor_noArgument_equalsEmptyList() {
        assertEquals(Collections.emptyList(), orderBook.getList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> orderBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyDataBookOrder_replacesData() {
        OrderBook newData = getTypicalOrderBook();
        orderBook.resetData(newData);
        assertEquals(newData, orderBook);
    }

    @Test
    public void resetData_withDuplicateOrders_throwsDuplicateOrderException() {
        // Two orders with the same identity fields
        Order editedVipOrder = new OrderBuilder(VIPORDER, true).build();
        List<Order> newOrders = Arrays.asList(VIPORDER, editedVipOrder);
        OrderBookStub newData = new OrderBookStub(newOrders);

        assertThrows(DuplicateOrderException.class, () -> orderBook.resetData(newData));
    }

    @Test
    public void hasOrder_nullOrder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> orderBook.hasOrder(null));
    }

    @Test
    public void hasOrder_orderNotInAddressBook_returnsFalse() {
        assertFalse(orderBook.hasOrder(VIPORDER));
    }

    @Test
    public void hasOrder_orderInOrderBook_returnsTrue() {
        orderBook.addOrder(VIPORDER);
        assertTrue(orderBook.hasOrder(VIPORDER));
    }

    @Test
    public void hasOrder_orderWithSameIdentityFieldsInOrderBook_returnsTrue() {
        orderBook.addOrder(VIPORDER);
        Order clonedVipOrder = new OrderBuilder(VIPORDER, true).build();
        assertTrue(orderBook.hasOrder(clonedVipOrder));
    }

    @Test
    public void getOrderList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> orderBook.getList().remove(0));
    }

    /**
     * A stub ReadOnlyDataBook(Order) whose orders list can violate interface constraints.
     */
    private static class OrderBookStub implements ReadOnlyDataBook<Order> {
        private final ObservableList<Order> orders = FXCollections.observableArrayList();

        OrderBookStub(Collection<Order> orders) {
            this.orders.setAll(orders);
        }

        @Override
        public ObservableList<Order> getList() {
            return orders;
        }
    }

}
