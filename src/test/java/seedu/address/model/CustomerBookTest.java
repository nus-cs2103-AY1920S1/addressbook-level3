package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTACTNUMBER_BOB;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalCustomers.ALICE;
import static seedu.address.testutil.TypicalCustomers.getTypicalCustomerBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.customer.Customer;
import seedu.address.model.customer.exceptions.DuplicateCustomerException;
import seedu.address.testutil.CustomerBuilder;

public class CustomerBookTest {

    private final CustomerBook customerBook = new CustomerBook();

    @Test
    public void constructor_noArgument_equalsEmptyList() {
        assertEquals(Collections.emptyList(), customerBook.getList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> customerBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyDataBookCustomer_replacesData() {
        CustomerBook newData = getTypicalCustomerBook();
        customerBook.resetData(newData);
        assertEquals(newData, customerBook);
    }

    @Test
    public void resetData_withDuplicateCustomers_throwsDuplicateCustomerException() {
        // Two persons with the same identity fields
        Customer editedAlice = new CustomerBuilder(ALICE).withContactNumber(VALID_CONTACTNUMBER_BOB)
                .build();
        List<Customer> newCustomers = Arrays.asList(ALICE, editedAlice);
        CustomerBookStub newData = new CustomerBookStub(newCustomers);

        assertThrows(DuplicateCustomerException.class, () -> customerBook.resetData(newData));
    }

    @Test
    public void hasCustomer_nullCustomer_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> customerBook.hasCustomer(null));
    }

    @Test
    public void hasCustomer_customerNotInAddressBook_returnsFalse() {
        assertFalse(customerBook.hasCustomer(ALICE));
    }

    @Test
    public void hasCustomer_customerInCustomerBook_returnsTrue() {
        customerBook.addCustomer(ALICE);
        assertTrue(customerBook.hasCustomer(ALICE));
    }

    @Test
    public void hasCustomer_customerWithSameIdentityFieldsInCustomerBook_returnsTrue() {
        customerBook.addCustomer(ALICE);
        Customer editedAlice = new CustomerBuilder(ALICE).withContactNumber(VALID_CONTACTNUMBER_BOB)
                .build();
        assertTrue(customerBook.hasCustomer(editedAlice));
    }

    @Test
    public void getCustomerList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> customerBook.getList().remove(0));
    }

    /**
     * A stub ReadOnlyDataBook(Customer) whose customers list can violate interface constraints.
     */
    private static class CustomerBookStub implements ReadOnlyDataBook<Customer> {
        private final ObservableList<Customer> customers = FXCollections.observableArrayList();

        CustomerBookStub(Collection<Customer> customers) {
            this.customers.setAll(customers);
        }

        @Override
        public ObservableList<Customer> getList() {
            return customers;
        }
    }

}
