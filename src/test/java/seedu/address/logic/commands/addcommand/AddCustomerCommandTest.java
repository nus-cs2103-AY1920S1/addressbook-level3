package seedu.address.logic.commands.addcommand;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.DataBook;
import seedu.address.model.ReadOnlyDataBook;
import seedu.address.model.customer.Customer;
import seedu.address.testutil.CustomerBuilder;
import seedu.address.testutil.ModelStub;

public class AddCustomerCommandTest {


    @Test
    public void constructor_nullCustomer_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCustomerCommand(null));
    }

    @Test
    public void execute_customerAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingCustomerAdded modelStub = new ModelStubAcceptingCustomerAdded();
        Customer validCustomer = new CustomerBuilder().build();

        CommandResult commandResult = new AddCustomerCommand(validCustomer)
                .executeUndoableCommand(modelStub, new CommandHistory(), new UndoRedoStack());

        assertEquals(String.format(AddCustomerCommand.MESSAGE_SUCCESS, validCustomer),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validCustomer), modelStub.customersAdded);
    }

    @Test
    public void execute_duplicateCustomer_throwsCommandException() {
        Customer validCustomer = new CustomerBuilder().build();
        AddCustomerCommand addCustomerCommand = new AddCustomerCommand(validCustomer);
        ModelStub modelStub = new ModelStubWithCustomer(validCustomer);

        assertThrows(CommandException.class, AddCustomerCommand.MESSAGE_DUPLICATE_CUSTOMER, ()
            -> addCustomerCommand.executeUndoableCommand(modelStub, new CommandHistory(), new UndoRedoStack()));
    }

    @Test
    public void equals() {

        Customer alice = new CustomerBuilder().withName("Alice").build();
        Customer bob = new CustomerBuilder().withName("Bob").build();
        AddCustomerCommand addAliceCommand = new AddCustomerCommand(alice);
        AddCustomerCommand addBobCommand = new AddCustomerCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCustomerCommand addAliceCommandCopy = new AddCustomerCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * A Model stub that contains a single customer.
     */
    private class ModelStubWithCustomer extends ModelStub {
        private final Customer customer;

        ModelStubWithCustomer(Customer customer) {
            requireNonNull(customer);
            this.customer = customer;
        }

        @Override
        public boolean hasCustomer(Customer customer) {
            requireNonNull(customer);
            return this.customer.isSameAs(customer);
        }
    }

    /**
     * A Model stub that always accept the customer being added.
     */
    private class ModelStubAcceptingCustomerAdded extends ModelStub {
        final ArrayList<Customer> customersAdded = new ArrayList<>();

        @Override
        public boolean hasCustomer(Customer customer) {
            requireNonNull(customer);
            return customersAdded.stream().anyMatch(customer::isSameAs);
        }

        @Override
        public void addCustomer(Customer customer) {
            requireNonNull(customer);
            customersAdded.add(customer);
        }

        @Override
        public ReadOnlyDataBook<Customer> getCustomerBook() {
            return new DataBook<Customer>();
        }
    }

}
