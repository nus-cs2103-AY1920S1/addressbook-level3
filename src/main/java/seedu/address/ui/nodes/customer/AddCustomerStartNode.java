package seedu.address.ui.nodes.customer;

import seedu.address.model.customer.Customer;
import seedu.address.ui.Node;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class AddCustomerStartNode extends Node<Customer> {

    public AddCustomerStartNode(List<Customer> backingList) {
        super(backingList);
    }

    @Override
    public SortedSet<String> getValues() {
        return new TreeSet<>();
    }

}
