package seedu.address.ui.graphs;

import seedu.address.ui.Graph;

/**
 * Represents a graph used for autocomplete in adding a customer.
 */
public class AddCustomerGraph extends Graph {

    public AddCustomerGraph() {
        super("add-c", AddCustomerStartNode.getInstance());
    }

}
