package seedu.deliverymans.model;

import javafx.collections.ObservableList;
import seedu.deliverymans.model.customer.Customer;

/**
 * Wraps all data at the customer database level
 * Duplicates are not allowed (by .isSameCustomer comparison)
 */
public class CustomerBook implements ReadOnlyCustomerBook {

    @Override
    public ObservableList<Customer> getCustomerList() {
        return null;
    }
}
