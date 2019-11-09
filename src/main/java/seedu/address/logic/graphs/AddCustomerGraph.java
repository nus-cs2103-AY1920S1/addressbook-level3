package seedu.address.logic.graphs;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.List;

import seedu.address.logic.AutoCompleteEdge;
import seedu.address.logic.AutoCompleteNode;
import seedu.address.logic.GraphWithStartNode;
import seedu.address.logic.nodes.EmptyAutoCompleteNode;
import seedu.address.logic.nodes.customer.CustomerContactNumberNode;
import seedu.address.logic.nodes.customer.CustomerEmailNode;
import seedu.address.logic.nodes.customer.CustomerNameNode;
import seedu.address.logic.nodes.customer.CustomerTagNode;
import seedu.address.model.Model;
import seedu.address.model.customer.Customer;

/**
 * Represents a {@code Graph} used to support autocomplete for {@code AddCustomerCommand}.
 */
public class AddCustomerGraph extends GraphWithStartNode {

    public AddCustomerGraph(Model model) {
        super(model);
    }

    @Override
    protected void build(Model model) {
        List<Customer> customerList = model.getCustomerBook().getList();
        AutoCompleteNode<?> addCustomerStartNode = EmptyAutoCompleteNode.getInstance();
        setStartingNode(addCustomerStartNode);
        CustomerContactNumberNode customerContactNumberNode = new CustomerContactNumberNode(customerList);
        CustomerEmailNode customerEmailNode = new CustomerEmailNode(customerList);
        CustomerNameNode customerNameNode = new CustomerNameNode(customerList);
        CustomerTagNode customerTagNode = new CustomerTagNode(customerList);
        edgeList.addAll(Arrays.asList(
                new AutoCompleteEdge<>(PREFIX_NAME, addCustomerStartNode, customerNameNode),
                new AutoCompleteEdge<>(PREFIX_CONTACT, customerNameNode, customerContactNumberNode),
                new AutoCompleteEdge<>(PREFIX_EMAIL, customerContactNumberNode, customerEmailNode),
                new AutoCompleteEdge<>(PREFIX_TAG, customerEmailNode, customerTagNode),
                new AutoCompleteEdge<>(PREFIX_TAG, customerTagNode, customerTagNode)
        ));
    }

}
