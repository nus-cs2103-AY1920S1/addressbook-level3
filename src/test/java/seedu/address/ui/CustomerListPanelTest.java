package seedu.address.ui;

import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.address.testutil.TypicalCustomers.getTypicalCustomers;
import static seedu.address.ui.GuiTestAssert.assertCardDisplaysCustomer;

import java.util.Collections;

import org.junit.jupiter.api.Test;

import guitests.guihandles.cards.CustomerCardHandle;
import guitests.guihandles.panels.CustomerListPanelHandle;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.customer.ContactNumber;
import seedu.address.model.customer.Customer;
import seedu.address.model.customer.CustomerName;
import seedu.address.model.customer.Email;
import seedu.address.ui.panels.CustomerListPanel;


public class CustomerListPanelTest extends GuiUnitTest {
    private static final ObservableList<Customer> TYPICAL_CUSTOMERS =
            FXCollections.observableList(getTypicalCustomers());

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 6000;

    private final SimpleObjectProperty<Customer> selectedCustomer = new SimpleObjectProperty<>();
    private CustomerListPanelHandle customerListPanelHandle;

    @Test
    public void display() {
        initUi(TYPICAL_CUSTOMERS);

        for (int i = 0; i < TYPICAL_CUSTOMERS.size(); i++) {
            customerListPanelHandle.navigateToCard(TYPICAL_CUSTOMERS.get(i));
            Customer expectedCustomer = TYPICAL_CUSTOMERS.get(i);
            CustomerCardHandle actualCard = customerListPanelHandle.getCustomerCardHandle(i);

            assertCardDisplaysCustomer(expectedCustomer, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }


    /**
     * Verifies that creating and deleting large number of customers in {@code CustomerListPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */

    @Test
    public void performanceTest() {
        ObservableList<Customer> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of person cards exceeded time limit");
    }

    /**
     * Returns a list of customers containing {@code customerCount} persons that is used to populate the
     * {@code CustomerListPanel}.
     */
    private ObservableList<Customer> createBackingList(int customerCount) {
        ObservableList<Customer> backingList = FXCollections.observableArrayList();
        for (int i = 0; i < customerCount; i++) {
            CustomerName name = new CustomerName(i + "a");
            ContactNumber contactNumber = new ContactNumber("12345678");
            Email email = new Email("a@aa");
            Customer customer = new Customer(name, contactNumber, email, Collections.emptySet());
            backingList.add(customer);
        }
        return backingList;
    }

    /**
     * Initializes {@code customerListPanelHandle} with a {@code CustomerListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code CustomerListPanel}.
     */

    private void initUi(ObservableList<Customer> backingList) {
        CustomerListPanel customerListPanel =
                new CustomerListPanel(backingList);
        uiPartExtension.setUiPart(customerListPanel);

        customerListPanelHandle = new CustomerListPanelHandle(getChildNode(customerListPanel.getRoot(),
                CustomerListPanelHandle.CUSTOMER_LIST_VIEW_ID));
    }
}
