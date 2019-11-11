package seedu.address.logic.autocomplete.graphs.add;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CUSTOMER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;

import seedu.address.logic.autocomplete.graphs.Edge;
import seedu.address.logic.autocomplete.graphs.GraphWithStartNode;
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
        super();
        initialise(model);
    }

    private void initialise(Model model) {
        List<Order> orderList = model.getOrderBook().getList();
        OrderCustomerIndexNode orderCustomerIndexNode = new OrderCustomerIndexNode(orderList);
        OrderPhoneIndexNode orderPhoneIndexNode = new OrderPhoneIndexNode(orderList);
        OrderPriceNode orderPriceNode = new OrderPriceNode(orderList);
        OrderTagNode orderTagNode = new OrderTagNode(orderList);
        addEdges(
                new Edge<>(PREFIX_PHONE, startingNode, orderCustomerIndexNode),
                new Edge<>(PREFIX_CUSTOMER, orderCustomerIndexNode, orderPhoneIndexNode),
                new Edge<>(PREFIX_PRICE, orderPhoneIndexNode, orderPriceNode),
                new Edge<>(PREFIX_TAG, orderPriceNode, orderTagNode),
                new Edge<>(PREFIX_TAG, orderTagNode, orderTagNode)
        );
    }

}
