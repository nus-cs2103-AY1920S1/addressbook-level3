package seedu.address.logic.nodes.order;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import seedu.address.logic.Node;
import seedu.address.model.order.Order;

/**
 * Represents a {@code Node} tracking {@code Order} {@code Customer} for autocompletion.
 */
public class OrderCustomerIndexNode extends Node<Order> {

    public OrderCustomerIndexNode(List<Order> backingList) {
        super(backingList);
    }

    @Override
    public SortedSet<String> getValues() {
        SortedSet<String> values = new TreeSet<>();
        int minIndex = 1;
        int maxIndex = backingList.size();
        values.add(String.valueOf(minIndex));
        values.add(String.valueOf(maxIndex));
        return values;
    }

}
