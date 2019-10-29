package seedu.address.logic.nodes.customer;

import seedu.address.model.customer.Customer;
import seedu.address.logic.Node;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class CustomerEmailNode extends Node<Customer> {

    public CustomerEmailNode(List<Customer> backingList) {
        super(backingList);
    }

    @Override
    public SortedSet<String> getValues() {
        SortedSet<String> values = new TreeSet<>();
        backingList.forEach(customer -> values.add(customer.getEmail().toString()));
        return values;
    }

}
