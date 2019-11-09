package seedu.address.logic.graphs;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CUSTOMER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;

import seedu.address.logic.Edge;
import seedu.address.logic.GraphWithStartNodeAndPreamble;
import seedu.address.logic.Node;
import seedu.address.logic.nodes.order.OrderCustomerIndexNode;
import seedu.address.logic.nodes.order.OrderPhoneIndexNode;
import seedu.address.logic.nodes.order.OrderPriceNode;
import seedu.address.logic.nodes.order.OrderTagNode;
import seedu.address.model.Model;
import seedu.address.model.order.Order;

/**
 * Represents a {@code Graph} used to support autocomplete for {@code EditOrderCommand}.
 */
public class EditOrderGraph extends GraphWithStartNodeAndPreamble {

    public EditOrderGraph(Model model) {
        super(model);
    }

    @Override
    protected void build(Model model) {
        List<Order> orderList = model.getFilteredOrderList();
        setDataList(orderList);
        Node<?> editOrderStartNode = Node.emptyNode();
        setStartingNode(editOrderStartNode);
        Node<Order> orderCustomerIndexNode = new OrderCustomerIndexNode(orderList);
        Node<Order> orderPhoneIndexNode = new OrderPhoneIndexNode(orderList);
        Node<Order> orderPriceNode = new OrderPriceNode(orderList);
        Node<Order> orderTagNode = new OrderTagNode(orderList);
        edges.addAll(
                new Edge(PREFIX_PHONE, editOrderStartNode, orderPhoneIndexNode),
                new Edge(PREFIX_CUSTOMER, editOrderStartNode, orderCustomerIndexNode),
                new Edge(PREFIX_PRICE, editOrderStartNode, orderPriceNode),
                new Edge(PREFIX_TAG, editOrderStartNode, orderTagNode),
                new Edge(PREFIX_PHONE, orderPhoneIndexNode, orderPhoneIndexNode),
                new Edge(PREFIX_CUSTOMER, orderPhoneIndexNode, orderCustomerIndexNode),
                new Edge(PREFIX_PRICE, orderPhoneIndexNode, orderPriceNode),
                new Edge(PREFIX_TAG, orderPhoneIndexNode, orderTagNode),
                new Edge(PREFIX_PHONE, orderCustomerIndexNode, orderPhoneIndexNode),
                new Edge(PREFIX_CUSTOMER, orderCustomerIndexNode, orderCustomerIndexNode),
                new Edge(PREFIX_PRICE, orderCustomerIndexNode, orderPriceNode),
                new Edge(PREFIX_TAG, orderCustomerIndexNode, orderTagNode),
                new Edge(PREFIX_PHONE, orderPriceNode, orderPhoneIndexNode),
                new Edge(PREFIX_CUSTOMER, orderPriceNode, orderCustomerIndexNode),
                new Edge(PREFIX_PRICE, orderPriceNode, orderPriceNode),
                new Edge(PREFIX_TAG, orderPriceNode, orderTagNode),
                new Edge(PREFIX_PHONE, orderTagNode, orderPhoneIndexNode),
                new Edge(PREFIX_CUSTOMER, orderTagNode, orderCustomerIndexNode),
                new Edge(PREFIX_PRICE, orderTagNode, orderPriceNode),
                new Edge(PREFIX_TAG, orderTagNode, orderTagNode)
        );
    }

}
