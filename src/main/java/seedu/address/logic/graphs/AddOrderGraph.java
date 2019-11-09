package seedu.address.logic.graphs;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CUSTOMER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.List;

import seedu.address.logic.Edge;
import seedu.address.logic.GraphWithStartNode;
import seedu.address.logic.Node;
import seedu.address.logic.nodes.order.OrderCustomerIndexNode;
import seedu.address.logic.nodes.order.OrderPhoneIndexNode;
import seedu.address.logic.nodes.order.OrderPriceNode;
import seedu.address.logic.nodes.order.OrderTagNode;
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
        Node<?> addOrderStartNode = Node.emptyNode();
        setStartingNode(addOrderStartNode);
        Node<Order> orderCustomerIndexNode = new OrderCustomerIndexNode(orderList);
        Node<Order> orderPhoneIndexNode = new OrderPhoneIndexNode(orderList);
        Node<Order> orderPriceNode = new OrderPriceNode(orderList);
        Node<Order> orderTagNode = new OrderTagNode(orderList);
        edges.addAll(
                new Edge(PREFIX_PHONE, addOrderStartNode, orderCustomerIndexNode),
                new Edge(PREFIX_CUSTOMER, orderCustomerIndexNode, orderPhoneIndexNode),
                new Edge(PREFIX_PRICE, orderPhoneIndexNode, orderPriceNode),
                new Edge(PREFIX_TAG, orderPriceNode, orderTagNode),
                new Edge(PREFIX_TAG, orderTagNode, orderTagNode)
        );
    }

}
