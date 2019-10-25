package seedu.address.ui.graphs;

import seedu.address.model.customer.Customer;
import seedu.address.ui.Graph;

import java.util.List;

/**
 * Represents a graph used for autocomplete in adding a customer.
 */
public class AddCustomerGraph extends Graph<Customer> {

    public AddCustomerGraph(List<Customer> backingList) {
        super("add-c", AddCustomerStartNode.getInstance(backingList));
    }

}
