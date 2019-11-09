package seedu.address.logic.graphs;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.List;

import seedu.address.logic.Edge;
import seedu.address.logic.GraphWithStartNodeAndPreamble;
import seedu.address.logic.Node;
import seedu.address.logic.nodes.customer.CustomerContactNumberNode;
import seedu.address.logic.nodes.customer.CustomerEmailNode;
import seedu.address.logic.nodes.customer.CustomerNameNode;
import seedu.address.logic.nodes.customer.CustomerTagNode;
import seedu.address.model.Model;
import seedu.address.model.customer.Customer;

/**
 * Represents a {@code Graph} used to support autocomplete for {@code EditCustomerCommand}.
 */
public class EditCustomerGraph extends GraphWithStartNodeAndPreamble {

    public EditCustomerGraph(Model model) {
        super(model);
    }

    @Override
    protected void build(Model model) {
        List<Customer> customerList = model.getFilteredCustomerList();
        setDataList(customerList);
        Node<?> editCustomerStartNode = Node.emptyNode();
        setStartingNode(editCustomerStartNode);
        Node<Customer> customerContactNumberNode = new CustomerContactNumberNode(customerList);
        Node<Customer> customerEmailNode = new CustomerEmailNode(customerList);
        Node<Customer> customerNameNode = new CustomerNameNode(customerList);
        Node<Customer> customerTagNode = new CustomerTagNode(customerList);
        edges.addAll(
                new Edge(PREFIX_CONTACT, editCustomerStartNode, customerContactNumberNode),
                new Edge(PREFIX_EMAIL, editCustomerStartNode, customerEmailNode),
                new Edge(PREFIX_NAME, editCustomerStartNode, customerNameNode),
                new Edge(PREFIX_TAG, editCustomerStartNode, customerTagNode),
                new Edge(PREFIX_CONTACT, customerContactNumberNode, customerContactNumberNode),
                new Edge(PREFIX_EMAIL, customerContactNumberNode, customerEmailNode),
                new Edge(PREFIX_NAME, customerContactNumberNode, customerNameNode),
                new Edge(PREFIX_TAG, customerContactNumberNode, customerTagNode),
                new Edge(PREFIX_CONTACT, customerEmailNode, customerContactNumberNode),
                new Edge(PREFIX_EMAIL, customerEmailNode, customerEmailNode),
                new Edge(PREFIX_NAME, customerEmailNode, customerNameNode),
                new Edge(PREFIX_TAG, customerEmailNode, customerTagNode),
                new Edge(PREFIX_CONTACT, customerNameNode, customerContactNumberNode),
                new Edge(PREFIX_EMAIL, customerNameNode, customerEmailNode),
                new Edge(PREFIX_NAME, customerNameNode, customerNameNode),
                new Edge(PREFIX_TAG, customerNameNode, customerTagNode),
                new Edge(PREFIX_CONTACT, customerTagNode, customerContactNumberNode),
                new Edge(PREFIX_EMAIL, customerTagNode, customerEmailNode),
                new Edge(PREFIX_NAME, customerTagNode, customerNameNode),
                new Edge(PREFIX_TAG, customerTagNode, customerTagNode)
        );
    }

}
