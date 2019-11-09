package seedu.address.logic.graphs;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import seedu.address.logic.AutoCompleteResult;
import seedu.address.logic.GraphBuiltFromModel;
import seedu.address.model.Model;
import seedu.address.model.order.Order;

/**
 * Represents a {@code Graph} used to support autocomplete for {@code FindOrderCommand}.
 */
public class FindOrderGraph extends GraphBuiltFromModel {

    private List<Order> orderList;

    public FindOrderGraph(Model model) {
        super(model);
    }

    @Override
    protected void build(Model model) {
        orderList = model.getOrderBook().getList();
    }

    @Override
    public AutoCompleteResult process(String input) {
        SortedSet<String> values = new TreeSet<>();
        List<String> idList = orderList.stream()
                .map(order -> order.getId().toString())
                .collect(Collectors.toList());
        List<String> priceList = orderList.stream()
                .map(order -> order.getPrice().toString())
                .collect(Collectors.toList());
        List<String> statusList = orderList.stream()
                .map(order -> order.getStatus().toString())
                .collect(Collectors.toList());
        values.addAll(idList);
        values.addAll(priceList);
        values.addAll(statusList);
        String stringToCompare;
        if (input.endsWith(" ")) {
            stringToCompare = "";
        } else {
            stringToCompare = input.substring(input.lastIndexOf(" ") + 1);
        }
        return new AutoCompleteResult(values, stringToCompare);
    }

}
