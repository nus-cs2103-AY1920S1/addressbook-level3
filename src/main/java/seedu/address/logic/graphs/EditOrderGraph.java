package seedu.address.logic.graphs;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CUSTOMER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.List;

import seedu.address.logic.AutoCompleteEdge;
import seedu.address.logic.AutoCompleteNode;
import seedu.address.logic.GraphWithStartNodeAndPreamble;
import seedu.address.logic.nodes.EmptyAutoCompleteNode;
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
        AutoCompleteNode<?> editOrderStartNode = EmptyAutoCompleteNode.getInstance();
        setStartingNode(editOrderStartNode);
        OrderCustomerIndexNode orderCustomerIndexNode = new OrderCustomerIndexNode(orderList);
        OrderPhoneIndexNode orderPhoneIndexNode = new OrderPhoneIndexNode(orderList);
        OrderPriceNode orderPriceNode = new OrderPriceNode(orderList);
        OrderTagNode orderTagNode = new OrderTagNode(orderList);
        edgeList.addAll(Arrays.asList(
                new AutoCompleteEdge<>(PREFIX_PHONE, editOrderStartNode, orderPhoneIndexNode),
                new AutoCompleteEdge<>(PREFIX_CUSTOMER, editOrderStartNode, orderCustomerIndexNode),
                new AutoCompleteEdge<>(PREFIX_PRICE, editOrderStartNode, orderPriceNode),
                new AutoCompleteEdge<>(PREFIX_TAG, editOrderStartNode, orderTagNode),
                new AutoCompleteEdge<>(PREFIX_PHONE, orderPhoneIndexNode, orderPhoneIndexNode),
                new AutoCompleteEdge<>(PREFIX_CUSTOMER, orderPhoneIndexNode, orderCustomerIndexNode),
                new AutoCompleteEdge<>(PREFIX_PRICE, orderPhoneIndexNode, orderPriceNode),
                new AutoCompleteEdge<>(PREFIX_TAG, orderPhoneIndexNode, orderTagNode),
                new AutoCompleteEdge<>(PREFIX_PHONE, orderCustomerIndexNode, orderPhoneIndexNode),
                new AutoCompleteEdge<>(PREFIX_CUSTOMER, orderCustomerIndexNode, orderCustomerIndexNode),
                new AutoCompleteEdge<>(PREFIX_PRICE, orderCustomerIndexNode, orderPriceNode),
                new AutoCompleteEdge<>(PREFIX_TAG, orderCustomerIndexNode, orderTagNode),
                new AutoCompleteEdge<>(PREFIX_PHONE, orderPriceNode, orderPhoneIndexNode),
                new AutoCompleteEdge<>(PREFIX_CUSTOMER, orderPriceNode, orderCustomerIndexNode),
                new AutoCompleteEdge<>(PREFIX_PRICE, orderPriceNode, orderPriceNode),
                new AutoCompleteEdge<>(PREFIX_TAG, orderPriceNode, orderTagNode),
                new AutoCompleteEdge<>(PREFIX_PHONE, orderTagNode, orderPhoneIndexNode),
                new AutoCompleteEdge<>(PREFIX_CUSTOMER, orderTagNode, orderCustomerIndexNode),
                new AutoCompleteEdge<>(PREFIX_PRICE, orderTagNode, orderPriceNode),
                new AutoCompleteEdge<>(PREFIX_TAG, orderTagNode, orderTagNode)
        ));
    }

}
