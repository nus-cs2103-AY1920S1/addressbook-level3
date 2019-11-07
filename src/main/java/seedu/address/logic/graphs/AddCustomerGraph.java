package seedu.address.logic.graphs;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.List;

import seedu.address.logic.Edge;
import seedu.address.logic.GraphWithStartNode;
import seedu.address.logic.Node;
import seedu.address.logic.nodes.customer.CustomerContactNumberNode;
import seedu.address.logic.nodes.customer.CustomerEmailNode;
import seedu.address.logic.nodes.customer.CustomerNameNode;
import seedu.address.logic.nodes.customer.CustomerTagNode;
import seedu.address.model.Model;
import seedu.address.model.customer.Customer;

public class AddCustomerGraph extends GraphWithStartNode {

    public AddCustomerGraph(Model model) {
        super(model);
    }

    @Override
    protected void build(Model model) {
        List<Customer> customerList = model.getCustomerBook().getList();
        Node<?> addCustomerStartNode = Node.emptyNode();
        setStartingNode(addCustomerStartNode);
        Node<Customer> customerContactNumberNode = new CustomerContactNumberNode(customerList);
        Node<Customer> customerEmailNode = new CustomerEmailNode(customerList);
        Node<Customer> customerNameNode = new CustomerNameNode(customerList);
        Node<Customer> customerTagNode = new CustomerTagNode(customerList);
        edges.addAll(Arrays.asList(
                new Edge(PREFIX_NAME, addCustomerStartNode, customerNameNode),
                new Edge(PREFIX_CONTACT, customerNameNode, customerContactNumberNode),
                new Edge(PREFIX_EMAIL, customerContactNumberNode, customerEmailNode),
                new Edge(PREFIX_TAG, customerEmailNode, customerTagNode),
                new Edge(PREFIX_TAG, customerTagNode, customerTagNode)
        ));
    }

}
