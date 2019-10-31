package seedu.address.logic.nodes.customer;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import seedu.address.logic.Node;
import seedu.address.model.customer.Customer;

/**
 * Represents a {@code Node} tracking {@code Customer} {@code Tag} for autocompletion.
 */
public class CustomerTagNode extends Node<Customer> {

    public CustomerTagNode(List<Customer> backingList) {
        super(backingList);
    }

    @Override
    public SortedSet<String> getValues() {
        SortedSet<String> values = new TreeSet<>();
        backingList.forEach(customer ->
                customer.getTags().forEach(tag ->
                        values.add(tag.toString().replaceAll("\\[|\\]", ""))));
        return values;
    }

}
