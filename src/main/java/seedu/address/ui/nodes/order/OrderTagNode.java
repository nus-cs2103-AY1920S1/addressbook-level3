package seedu.address.ui.nodes.order;

import seedu.address.model.order.Order;
import seedu.address.ui.Node;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class OrderTagNode extends Node<Order> {

    public OrderTagNode(List<Order> backingList) {
        super(backingList);
    }

    @Override
    public SortedSet<String> getValues() {
        SortedSet<String> values = new TreeSet<>();
        backingList.forEach(order -> order.getTags().forEach(tag -> values.add(tag.toString())));
        return values;
    }

}
