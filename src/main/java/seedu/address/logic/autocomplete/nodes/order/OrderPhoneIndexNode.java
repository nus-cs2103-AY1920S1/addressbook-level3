package seedu.address.logic.autocomplete.nodes.order;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import seedu.address.logic.autocomplete.nodes.AutoCompleteNode;
import seedu.address.model.order.Order;

/**
 * Represents a {@code Node} tracking {@code Order} {@code Phone} for autocompletion.
 */
public class OrderPhoneIndexNode extends AutoCompleteNode<List<Order>> {

    public OrderPhoneIndexNode(List<Order> pointer) {
        super(pointer);
    }

    @Override
    public SortedSet<String> getValues() {
        SortedSet<String> values = new TreeSet<>();
        int minIndex = 1;
        int maxIndex = pointer.size();
        if (maxIndex > 0) {
            values.add(String.valueOf(minIndex));
            values.add(String.valueOf(maxIndex));
        }
        return values;
    }

}
