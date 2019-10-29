package seedu.address.logic.nodes.order;

import seedu.address.model.order.Order;
import seedu.address.logic.Node;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class AddOrderStartNode extends Node<Order> {

    public AddOrderStartNode(List<Order> backingList) {
        super(backingList);
    }

    @Override
    public SortedSet<String> getValues() {
        return new TreeSet<>();
    }

}
