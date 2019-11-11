package seedu.address.logic.autocomplete.graphs.add;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CUSTOMER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.List;

import seedu.address.logic.autocomplete.edges.AutoCompleteEdge;
import seedu.address.logic.autocomplete.graphs.GraphWithStartNode;
import seedu.address.logic.autocomplete.nodes.AutoCompleteNode;
import seedu.address.logic.autocomplete.nodes.EmptyAutoCompleteNode;
import seedu.address.logic.autocomplete.nodes.order.OrderCustomerIndexNode;
import seedu.address.logic.autocomplete.nodes.order.OrderPhoneIndexNode;
import seedu.address.logic.autocomplete.nodes.order.OrderPriceNode;
import seedu.address.logic.autocomplete.nodes.order.OrderTagNode;
import seedu.address.model.Model;
import seedu.address.model.order.Order;

/**
 * Represents a {@code Graph} used to support autocomplete for {@code AddOrderCommand}.
 */
public class AddOrderGraph extends GraphWithStartNode {

    public AddOrderGraph(Model model) {
        super(model);
    }

    @Override
    protected void build(Model model) {
        List<Order> orderList = model.getOrderBook().getList();
        AutoCompleteNode<?> addOrderStartNode = EmptyAutoCompleteNode.getInstance();
        setStartingNode(addOrderStartNode);
        OrderCustomerIndexNode orderCustomerIndexNode = new OrderCustomerIndexNode(orderList);
        OrderPhoneIndexNode orderPhoneIndexNode = new OrderPhoneIndexNode(orderList);
        OrderPriceNode orderPriceNode = new OrderPriceNode(orderList);
        OrderTagNode orderTagNode = new OrderTagNode(orderList);
        edgeList.addAll(Arrays.asList(
                new AutoCompleteEdge<>(PREFIX_PHONE, addOrderStartNode, orderCustomerIndexNode),
                new AutoCompleteEdge<>(PREFIX_CUSTOMER, orderCustomerIndexNode, orderPhoneIndexNode),
                new AutoCompleteEdge<>(PREFIX_PRICE, orderPhoneIndexNode, orderPriceNode),
                new AutoCompleteEdge<>(PREFIX_TAG, orderPriceNode, orderTagNode),
                new AutoCompleteEdge<>(PREFIX_TAG, orderTagNode, orderTagNode)
        ));
    }

}
