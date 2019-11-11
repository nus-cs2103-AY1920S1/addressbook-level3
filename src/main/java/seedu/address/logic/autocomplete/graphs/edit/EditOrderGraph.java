package seedu.address.logic.autocomplete.graphs.edit;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CUSTOMER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;

import seedu.address.logic.autocomplete.graphs.Edge;
import seedu.address.logic.autocomplete.graphs.GraphWithStartNodeAndPreamble;
import seedu.address.logic.autocomplete.nodes.order.OrderCustomerIndexNode;
import seedu.address.logic.autocomplete.nodes.order.OrderPhoneIndexNode;
import seedu.address.logic.autocomplete.nodes.order.OrderPriceNode;
import seedu.address.logic.autocomplete.nodes.order.OrderTagNode;
import seedu.address.model.Model;
import seedu.address.model.order.Order;

/**
 * Represents a {@code Graph} used to support autocomplete for {@code EditOrderCommand}.
 */
public class EditOrderGraph extends GraphWithStartNodeAndPreamble {

    public EditOrderGraph(Model model) {
        super(model.getFilteredOrderList());
        initialise(model);
    }

    /**
     * Initialises this graph's {@code Node}s.
     */
    private void initialise(Model model) {
        List<Order> orderList = model.getFilteredOrderList();
        OrderCustomerIndexNode orderCustomerIndexNode = new OrderCustomerIndexNode(orderList);
        OrderPhoneIndexNode orderPhoneIndexNode = new OrderPhoneIndexNode(orderList);
        OrderPriceNode orderPriceNode = new OrderPriceNode(orderList);
        OrderTagNode orderTagNode = new OrderTagNode(orderList);
        addEdges(
                new Edge<>(PREFIX_PHONE, startingNode, orderPhoneIndexNode),
                new Edge<>(PREFIX_CUSTOMER, startingNode, orderCustomerIndexNode),
                new Edge<>(PREFIX_PRICE, startingNode, orderPriceNode),
                new Edge<>(PREFIX_TAG, startingNode, orderTagNode),
                new Edge<>(PREFIX_PHONE, orderPhoneIndexNode, orderPhoneIndexNode),
                new Edge<>(PREFIX_CUSTOMER, orderPhoneIndexNode, orderCustomerIndexNode),
                new Edge<>(PREFIX_PRICE, orderPhoneIndexNode, orderPriceNode),
                new Edge<>(PREFIX_TAG, orderPhoneIndexNode, orderTagNode),
                new Edge<>(PREFIX_PHONE, orderCustomerIndexNode, orderPhoneIndexNode),
                new Edge<>(PREFIX_CUSTOMER, orderCustomerIndexNode, orderCustomerIndexNode),
                new Edge<>(PREFIX_PRICE, orderCustomerIndexNode, orderPriceNode),
                new Edge<>(PREFIX_TAG, orderCustomerIndexNode, orderTagNode),
                new Edge<>(PREFIX_PHONE, orderPriceNode, orderPhoneIndexNode),
                new Edge<>(PREFIX_CUSTOMER, orderPriceNode, orderCustomerIndexNode),
                new Edge<>(PREFIX_PRICE, orderPriceNode, orderPriceNode),
                new Edge<>(PREFIX_TAG, orderPriceNode, orderTagNode),
                new Edge<>(PREFIX_PHONE, orderTagNode, orderPhoneIndexNode),
                new Edge<>(PREFIX_CUSTOMER, orderTagNode, orderCustomerIndexNode),
                new Edge<>(PREFIX_PRICE, orderTagNode, orderPriceNode),
                new Edge<>(PREFIX_TAG, orderTagNode, orderTagNode)
        );
    }

}
