package seedu.address.ui.nodes.customer;

import seedu.address.model.customer.Customer;
import seedu.address.ui.Node;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class CustomerContactNumberNode extends Node<Customer> {

    public CustomerContactNumberNode(List<Customer> backingList) {
        super(backingList);
    }

    @Override
    public SortedSet<String> getValues() {
        SortedSet<String> values = new TreeSet<>();
        backingList.forEach(customer -> values.add(customer.getContactNumber().toString()));
        return values;
    }

}
