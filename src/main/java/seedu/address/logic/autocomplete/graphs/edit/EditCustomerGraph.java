package seedu.address.logic.autocomplete.graphs.edit;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;

import seedu.address.logic.autocomplete.graphs.Edge;
import seedu.address.logic.autocomplete.graphs.GraphWithStartNodeAndPreamble;
import seedu.address.logic.autocomplete.nodes.customer.CustomerContactNumberNode;
import seedu.address.logic.autocomplete.nodes.customer.CustomerEmailNode;
import seedu.address.logic.autocomplete.nodes.customer.CustomerNameNode;
import seedu.address.logic.autocomplete.nodes.customer.CustomerTagNode;
import seedu.address.model.Model;
import seedu.address.model.customer.Customer;

/**
 * Represents a {@code Graph} used to support autocomplete for {@code EditCustomerCommand}.
 */
public class EditCustomerGraph extends GraphWithStartNodeAndPreamble {

    public EditCustomerGraph(Model model) {
        super(model.getFilteredCustomerList());
        initialise(model);
    }

    /**
     * Initialises this graph's {@code Node}s.
     */
    private void initialise(Model model) {
        List<Customer> customerList = model.getFilteredCustomerList();
        CustomerContactNumberNode customerContactNumberNode = new CustomerContactNumberNode(customerList);
        CustomerEmailNode customerEmailNode = new CustomerEmailNode(customerList);
        CustomerNameNode customerNameNode = new CustomerNameNode(customerList);
        CustomerTagNode customerTagNode = new CustomerTagNode(customerList);
        addEdges(
                new Edge<>(PREFIX_CONTACT, startingNode, customerContactNumberNode),
                new Edge<>(PREFIX_EMAIL, startingNode, customerEmailNode),
                new Edge<>(PREFIX_NAME, startingNode, customerNameNode),
                new Edge<>(PREFIX_TAG, startingNode, customerTagNode),
                new Edge<>(PREFIX_CONTACT, customerContactNumberNode, customerContactNumberNode),
                new Edge<>(PREFIX_EMAIL, customerContactNumberNode, customerEmailNode),
                new Edge<>(PREFIX_NAME, customerContactNumberNode, customerNameNode),
                new Edge<>(PREFIX_TAG, customerContactNumberNode, customerTagNode),
                new Edge<>(PREFIX_CONTACT, customerEmailNode, customerContactNumberNode),
                new Edge<>(PREFIX_EMAIL, customerEmailNode, customerEmailNode),
                new Edge<>(PREFIX_NAME, customerEmailNode, customerNameNode),
                new Edge<>(PREFIX_TAG, customerEmailNode, customerTagNode),
                new Edge<>(PREFIX_CONTACT, customerNameNode, customerContactNumberNode),
                new Edge<>(PREFIX_EMAIL, customerNameNode, customerEmailNode),
                new Edge<>(PREFIX_NAME, customerNameNode, customerNameNode),
                new Edge<>(PREFIX_TAG, customerNameNode, customerTagNode),
                new Edge<>(PREFIX_CONTACT, customerTagNode, customerContactNumberNode),
                new Edge<>(PREFIX_EMAIL, customerTagNode, customerEmailNode),
                new Edge<>(PREFIX_NAME, customerTagNode, customerNameNode),
                new Edge<>(PREFIX_TAG, customerTagNode, customerTagNode)
        );
    }

}
