package seedu.address.ui.nodes.customer;

import seedu.address.model.customer.Customer;
import seedu.address.ui.Node;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class CustomerTagNode extends Node<Customer> {

    public CustomerTagNode(List<Customer> backingList) {
        super(backingList);
    }

    @Override
    public SortedSet<String> getValues() {
        SortedSet<String> values = new TreeSet<>();
        backingList.forEach(customer -> customer.getTags().forEach(tag -> values.add(tag.toString())));
        return values;
    }

}
