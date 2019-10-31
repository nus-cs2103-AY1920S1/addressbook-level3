package seedu.address.logic.nodes.customer;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import seedu.address.logic.Node;
import seedu.address.model.customer.Customer;

/**
 * Represents a {@code Node} tracking {@code Customer} {@code ContactNumber} for autocompletion.
 */
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
