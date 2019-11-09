package seedu.address.logic.nodes.customer;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import seedu.address.logic.AutoCompleteNode;
import seedu.address.model.customer.Customer;

/**
 * Represents a {@code Node} tracking {@code Customer} {@code Name} for autocompletion.
 */
public class CustomerNameNode extends AutoCompleteNode<List<Customer>> {

    public CustomerNameNode(List<Customer> pointer) {
        super(pointer);
    }

    @Override
    public SortedSet<String> getValues() {
        SortedSet<String> values = new TreeSet<>();
        pointer.forEach(customer -> values.add(customer.getCustomerName().toString()));
        return values;
    }

}
