package seedu.address.logic.nodes.order;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import seedu.address.logic.Node;
import seedu.address.model.order.Order;

/**
 * Represents a {@code Node} tracking {@code Order} {@code Price} for autocompletion.
 */
public class OrderPriceNode extends Node<Order> {

    public OrderPriceNode(List<Order> backingList) {
        super(backingList);
    }

    @Override
    public SortedSet<String> getValues() {
        SortedSet<String> values = new TreeSet<>();
        backingList.forEach(order -> values.add(order.getPrice().toString()));
        return values;
    }

}
