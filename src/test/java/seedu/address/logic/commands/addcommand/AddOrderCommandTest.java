/*package seedu.address.logic.commands.addcommand;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalOrders.getTypicalOrderBook;
import static seedu.address.testutil.TypicalCustomers.getTypicalCustomerBook;
import static seedu.address.testutil.TypicalPhones.getTypicalPhoneBook;
import static seedu.address.testutil.TypicalSchedules.getTypicalScheduleBook;

import java.util.ArrayList;
import java.util.Arrays;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.OrderBook;
import seedu.address.model.ReadOnlyDataBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.customer.Customer;
import seedu.address.model.order.Order;
import seedu.address.model.order.Price;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.CustomerBookBuilder;
import seedu.address.testutil.OrderBuilder;
import seedu.address.testutil.ModelStub;
import seedu.address.testutil.TypicalCustomers;
import seedu.address.testutil.TypicalIndexes;

public class AddOrderCommandTest {

    private Model model = new ModelManager(getTypicalCustomerBook(), getTypicalPhoneBook(),
            getTypicalOrderBook(), getTypicalScheduleBook(), new UserPrefs());

    @Test
    public void constructor_nullOrder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, ()
                -> new AddOrderCommand(null, null, null, null));
    }

    @Test
    public void execute_orderAcceptedByModel_addSuccessful() throws Exception {
        Order editedOrder = new OrderBuilder().build();

        Index customerIndex = TypicalIndexes.INDEX_FIRST_CUSTOMER;
        Index phoneIndex = TypicalIndexes.INDEX_FIRST_PHONE;
        Price price = new Price("$1212");
        Set<Tag> tagSet = new HashSet<Tag>();

        AddOrderCommand addOrderCommand = new AddOrderCommand(customerIndex, phoneIndex, price, tagSet);

        String expectedMessage = String.format(AddOrderCommand.MESSAGE_SUCCESS, );

        Model expectedModel = new ModelManager(new CustomerBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);



        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel)
    }

    @Test
    public void equals() {

        Phone iphone11 = new PhoneBuilder().withName("iPhone 11").build();
        Phone iphone11pro = new PhoneBuilder().withName("iPhone 11 Pro").build();
        AddPhoneCommand addiPhone11Command = new AddPhoneCommand(iphone11);
        AddPhoneCommand addiPhone11ProCommand = new AddPhoneCommand(iphone11pro);

        // same object -> returns true
        assertTrue(addiPhone11Command.equals(addiPhone11Command));

        // same values -> returns true
        AddPhoneCommand addiPhone11CommandCopy = new AddPhoneCommand(iphone11);
        assertTrue(addiPhone11Command.equals(addiPhone11CommandCopy));

        // different types -> returns false
        assertFalse(addiPhone11Command.equals(1));

        // null -> returns false
        assertFalse(addiPhone11Command.equals(null));

        // different person -> returns false
        assertFalse(addiPhone11Command.equals(addiPhone11ProCommand));
    }

    /**
     * A Model stub that contains a single order.

    private class ModelStubWithOrder extends ModelStub {
        private final Order order;

        ModelStubWithOrder(Order order) {
            requireNonNull(order);
            this.order = order;
        }

        @Override
        public boolean hasOrder(Order order) {
            requireNonNull(order);
            return this.order.isSameOrder(order);
        }

    }

    /**
     * A Model stub that always accept the order being added.

    private class ModelStubAcceptingOrderAdded extends ModelStub {
        final ArrayList<Order> ordersAdded = new ArrayList<>();

        @Override
        public boolean hasOrder(Order order) {
            requireNonNull(order);
            return ordersAdded.stream().anyMatch(order::isSameOrder);
        }

        @Override
        public void addOrder(Order order) {
            requireNonNull(order);
            ordersAdded.add(order);
        }

        @Override
        public ReadOnlyDataBook<Order> getOrderBook() {
            return new OrderBook();
        }

        @Override
        public ReadOnlyDataBook<Customer> getCustomerBook() {
            return new CustomerBookBuilder().withCustomer(TypicalCustomers.ALICE).build();
        }
    }

}*/
