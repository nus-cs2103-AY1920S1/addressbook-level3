package seedu.address.logic.autocomplete.nodes.order;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import seedu.address.logic.autocomplete.nodes.AutoCompleteNode;
import seedu.address.model.order.Order;

/**
 * Represents a {@code Node} tracking {@code Order} {@code Price} for autocompletion.
 */
public class OrderPriceNode extends AutoCompleteNode<List<Order>> {

    public OrderPriceNode(List<Order> pointer) {
        super(pointer);
    }

    @Override
    public SortedSet<String> getValues() {
        SortedSet<String> values = new TreeSet<>();
        pointer.forEach(order -> values.add(order.getPrice().toString()));
        return values;
    }

}
