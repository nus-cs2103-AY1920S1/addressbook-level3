package seedu.deliverymans.model.database;

import javafx.collections.ObservableList;
import seedu.deliverymans.model.customer.Customer;

/**
 * Unmodifiable view of an address book
 */

public interface ReadOnlyCustomerDatabase {

    /**
     * Returns an unmodifiable view of the customers list.
     * This list will not contain any duplicate customers.
     */
    ObservableList<Customer> getCustomerList();
}
