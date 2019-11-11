package seedu.address.logic.commands.addcommand;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalCustomers.ALICE;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CUSTOMER;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PHONE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CUSTOMER;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PHONE;
import static seedu.address.testutil.TypicalPhones.IPHONEXR;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.DataBook;
import seedu.address.model.ReadOnlyDataBook;
import seedu.address.model.customer.Customer;
import seedu.address.model.order.Order;
import seedu.address.model.order.Price;
import seedu.address.model.phone.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.ModelStub;
import seedu.address.testutil.OrderBuilder;

public class AddOrderCommandTest {

    private static final Index VALID_CUSTOMER_INDEX = INDEX_FIRST_CUSTOMER;
    private static final Index VALID_PHONE_INDEX = INDEX_FIRST_PHONE;
    private static final Price VALID_PRICE = new Price("$1000");
    private static final Set<Tag> VALID_TAG_SET = new HashSet<>();

    @Test
    public void constructor_nullCustomerIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, ()
            -> new AddOrderCommand(null, VALID_PHONE_INDEX, VALID_PRICE, VALID_TAG_SET));
    }

    @Test
    public void constructor_nullPhoneIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, ()
            -> new AddOrderCommand(VALID_CUSTOMER_INDEX, null, VALID_PRICE, VALID_TAG_SET));
    }

    @Test
    public void constructor_nullPrice_throwsNullPointerException() {
        assertThrows(NullPointerException.class, ()
            -> new AddOrderCommand(VALID_CUSTOMER_INDEX, VALID_PHONE_INDEX, null, VALID_TAG_SET));
    }

    @Test
    public void constructor_nullTags_throwsNullPointerException() {
        assertThrows(NullPointerException.class, ()
            -> new AddOrderCommand(VALID_CUSTOMER_INDEX, VALID_PHONE_INDEX, VALID_PRICE, null));
    }

    @Test
    public void execute_orderAcceptedByModel_addSuccessful() throws Exception {

        ModelStubAcceptingOrderAdded modelStub = new ModelStubAcceptingOrderAdded();

        CommandResult commandResult =
                new AddOrderCommand(VALID_CUSTOMER_INDEX, VALID_PHONE_INDEX, VALID_PRICE, VALID_TAG_SET)
                        .executeUndoableCommand(modelStub, new CommandHistory(), new UndoRedoStack());
        Order order = modelStub.ordersAdded.get(0);

        Order copyOrder = new OrderBuilder(order).build();

        assertEquals(String.format(AddOrderCommand.MESSAGE_SUCCESS, copyOrder),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(copyOrder), modelStub.ordersAdded);
    }

    @Test
    public void execute_duplicateOrder_throwsCommandException() {
        AddOrderCommand addOrderCommand =
                new AddOrderCommand(VALID_CUSTOMER_INDEX, VALID_PHONE_INDEX, VALID_PRICE, VALID_TAG_SET);
        ModelStub modelStub = new ModelStubWithOrder(new OrderBuilder().withCustomer(ALICE).withPhone(IPHONEXR)
                .withPrice(VALID_PRICE.toString()).withTags().build());

        assertThrows(CommandException.class, AddOrderCommand.MESSAGE_DUPLICATE_ORDER, ()
            -> addOrderCommand.executeUndoableCommand(modelStub, new CommandHistory(), new UndoRedoStack()));
    }

    @Test
    public void equals() {

        Price priceOne = new Price("$900");
        Price priceTwo = new Price("$1234");

        AddOrderCommand addOrderCommandOne = new AddOrderCommand(INDEX_FIRST_CUSTOMER, INDEX_FIRST_PHONE, priceOne,
                VALID_TAG_SET);

        // same object -> returns true
        assertTrue(addOrderCommandOne.equals(addOrderCommandOne));

        // same attributes -> returns true
        AddOrderCommand addOrderCommandOneCopy = new AddOrderCommand(INDEX_FIRST_CUSTOMER, INDEX_FIRST_PHONE, priceOne,
                VALID_TAG_SET);
        assertTrue(addOrderCommandOne.equals(addOrderCommandOneCopy));

        // different types -> returns false
        assertFalse(addOrderCommandOne.equals(1));

        // null -> returns false
        assertFalse(addOrderCommandOne.equals(null));

        // different customer index -> returns false
        AddOrderCommand addOrderCommandTwo = new AddOrderCommand(INDEX_SECOND_CUSTOMER, INDEX_FIRST_PHONE, priceOne,
                VALID_TAG_SET);
        assertFalse(addOrderCommandOne.equals(addOrderCommandTwo));

        // different phone index -> returns false
        addOrderCommandTwo = new AddOrderCommand(INDEX_FIRST_CUSTOMER, INDEX_SECOND_PHONE, priceOne,
                VALID_TAG_SET);
        assertFalse(addOrderCommandOne.equals(addOrderCommandTwo));

        // different price -> returns false
        addOrderCommandTwo = new AddOrderCommand(INDEX_FIRST_CUSTOMER, INDEX_FIRST_PHONE, priceTwo,
                VALID_TAG_SET);
        assertFalse(addOrderCommandOne.equals(addOrderCommandTwo));
    }

    /**
     * A Model stub that contains a single order.
     */
    private class ModelStubWithOrder extends ModelStub {
        private final Order order;
        private final ObservableList<Customer> filteredCustomerList = FXCollections.observableArrayList();
        private final ObservableList<Phone> filteredPhoneList = FXCollections.observableArrayList();

        ModelStubWithOrder(Order order) {
            requireNonNull(order);
            this.order = order;
            filteredCustomerList.add(ALICE);
            filteredPhoneList.add(IPHONEXR);
        }

        @Override
        public void addOrder(Order order) {
        }

        @Override
        public boolean hasOrder(Order order) {
            requireNonNull(order);
            return this.order.isSameAs(order);
        }

        @Override
        public ObservableList<Phone> getFilteredPhoneList() {
            return filteredPhoneList;
        }


        @Override
        public ObservableList<Customer> getFilteredCustomerList() {
            return filteredCustomerList;
        }

    }

    /**
     * A Model stub that always accept the order being added.
    */
    private class ModelStubAcceptingOrderAdded extends ModelStub {
        final ArrayList<Order> ordersAdded = new ArrayList<>();
        final ObservableList<Customer> filteredCustomerList = FXCollections.observableArrayList();
        final ObservableList<Phone> filteredPhoneList = FXCollections.observableArrayList();

        public ModelStubAcceptingOrderAdded() {
            filteredCustomerList.add(ALICE);
            filteredPhoneList.add(IPHONEXR);
        }

        @Override
        public boolean hasOrder(Order order) {
            requireNonNull(order);
            return ordersAdded.stream().anyMatch(order::isSameAs);
        }

        @Override
        public void addOrder(Order order) {
            requireNonNull(order);
            ordersAdded.add(order);
        }

        @Override
        public ReadOnlyDataBook<Order> getOrderBook() {
            return new DataBook<Order>();
        }

        @Override
        public ObservableList<Phone> getFilteredPhoneList() {
            return filteredPhoneList;
        }


        @Override
        public ObservableList<Customer> getFilteredCustomerList() {
            return filteredCustomerList;
        }
    }

}
