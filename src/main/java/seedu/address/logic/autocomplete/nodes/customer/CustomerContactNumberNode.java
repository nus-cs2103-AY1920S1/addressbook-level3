package seedu.address.logic.autocomplete.nodes.customer;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import seedu.address.logic.autocomplete.nodes.AutoCompleteNode;
import seedu.address.model.customer.Customer;

/**
 * Represents a {@code Node} tracking {@code Customer} {@code ContactNumber} for autocompletion.
 */
public class CustomerContactNumberNode extends AutoCompleteNode<List<Customer>> {

    public CustomerContactNumberNode(List<Customer> pointer) {
        super(pointer);
        requireNonNull(pointer);
    }

    @Override
    public SortedSet<String> getValues() {
        SortedSet<String> values = new TreeSet<>();
        pointer.forEach(customer -> values.add(customer.getContactNumber().toString()));
        return values;
    }

}
