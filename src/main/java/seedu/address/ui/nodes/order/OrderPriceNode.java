package seedu.address.ui.nodes.order;

import seedu.address.model.order.Order;
import seedu.address.ui.Node;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

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
