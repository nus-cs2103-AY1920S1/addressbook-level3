package seedu.address.ui;

import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.address.testutil.TypicalOrders.getTypicalOrders;
import static seedu.address.ui.GuiTestAssert.assertCardDisplaysOrder;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import guitests.guihandles.cards.OrderCardHandle;
import guitests.guihandles.panels.OrderListPanelHandle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.address.model.customer.ContactNumber;
import seedu.address.model.customer.Customer;
import seedu.address.model.customer.CustomerName;
import seedu.address.model.customer.Email;
import seedu.address.model.order.Order;
import seedu.address.model.order.Price;
import seedu.address.model.order.Status;
import seedu.address.model.phone.Phone;
import seedu.address.model.schedule.Schedule;
import seedu.address.testutil.TypicalPhones;
import seedu.address.testutil.TypicalSchedules;
import seedu.address.ui.panels.OrderListPanel;



public class OrderListPanelTest extends GuiUnitTest {
    private static final ObservableList<Order> TYPICAL_ORDERS =
            FXCollections.observableList(getTypicalOrders());

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 5000;

    private final SimpleObjectProperty<Order> selectedOrder = new SimpleObjectProperty<>();
    private OrderListPanelHandle orderListPanelHandle;


    @Test
    public void display() {
        initUi(TYPICAL_ORDERS);

        for (int i = 0; i < TYPICAL_ORDERS.size(); i++) {
            orderListPanelHandle.navigateToCard(TYPICAL_ORDERS.get(i));
            Order expectedOrder = TYPICAL_ORDERS.get(i);
            OrderCardHandle actualCard = orderListPanelHandle.getOrderCardHandle(i);

            assertCardDisplaysOrder(expectedOrder, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }



    /**
     * Verifies that creating and deleting large number of order in {@code OrderListPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */

    @Test
    public void performanceTest() {
        ObservableList<Order> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of person cards exceeded time limit");
    }



    /**
     * Returns a list of orders containing {@code orderCount} persons that is used to populate the
     * {@code OrderListPanel}.
     */
    private ObservableList<Order> createBackingList(int orderCount) {
        ObservableList<Order> backingList = FXCollections.observableArrayList();
        for (int i = 0; i < orderCount; i++) {
            CustomerName name = new CustomerName(i + "a");
            ContactNumber contactNumber = new ContactNumber("12345678");
            Email email = new Email("a@aa");
            Customer customer = new Customer(name, contactNumber, email, Collections.emptySet());
            Phone orderPhone = TypicalPhones.IPHONEONE;
            Optional<Schedule> orderSchedule = Optional.of(TypicalSchedules.MONDAY_SCHEDULE);
            Price orderPrice = new Price("$1.00");
            Status orderStatus = Status.UNSCHEDULED;
            UUID orderUuId = UUID.randomUUID();
            Order order = new Order(orderUuId, customer, orderPhone, orderPrice,
                    orderStatus, orderSchedule, Collections.emptySet());
            backingList.add(order);
        }
        return backingList;
    }


    /**
     * Initializes {@code orderListPanelHandle} with a {@code OrderListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code OrderListPanel}.
     */

    private void initUi(ObservableList<Order> backingList) {
        OrderListPanel orderListPanel =
                new OrderListPanel(backingList);
        uiPartExtension.setUiPart(orderListPanel);
        orderListPanelHandle = new OrderListPanelHandle(getChildNode(orderListPanel.getRoot(),
                OrderListPanelHandle.ORDER_LIST_VIEW_ID));
    }

}
