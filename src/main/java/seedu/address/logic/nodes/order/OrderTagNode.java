package seedu.address.logic.nodes.order;

import seedu.address.model.order.Order;
import seedu.address.logic.Node;

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
        backingList.forEach(order ->
                order.getTags().forEach(tag ->
                        values.add(tag.toString().replaceAll("\\[|\\]", ""))));
        return values;
    }

}
