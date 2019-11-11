package seedu.address.logic.autocomplete.graphs.add;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;

import seedu.address.logic.autocomplete.graphs.Edge;
import seedu.address.logic.autocomplete.graphs.GraphWithStartNode;
import seedu.address.logic.autocomplete.nodes.customer.CustomerContactNumberNode;
import seedu.address.logic.autocomplete.nodes.customer.CustomerEmailNode;
import seedu.address.logic.autocomplete.nodes.customer.CustomerNameNode;
import seedu.address.logic.autocomplete.nodes.customer.CustomerTagNode;
import seedu.address.model.Model;
import seedu.address.model.customer.Customer;

/**
 * Represents a {@code Graph} used to support autocomplete for {@code AddCustomerCommand}.
 */
public class AddCustomerGraph extends GraphWithStartNode {

    public AddCustomerGraph(Model model) {
        super();
        initialise(model);
    }

    /**
     * Initialises this graph's {@code Node}s.
     */
    private void initialise(Model model) {
        List<Customer> customerList = model.getCustomerBook().getList();
        CustomerContactNumberNode customerContactNumberNode = new CustomerContactNumberNode(customerList);
        CustomerEmailNode customerEmailNode = new CustomerEmailNode(customerList);
        CustomerNameNode customerNameNode = new CustomerNameNode(customerList);
        CustomerTagNode customerTagNode = new CustomerTagNode(customerList);
        addEdges(
                new Edge<>(PREFIX_NAME, startingNode, customerNameNode),
                new Edge<>(PREFIX_CONTACT, customerNameNode, customerContactNumberNode),
                new Edge<>(PREFIX_EMAIL, customerContactNumberNode, customerEmailNode),
                new Edge<>(PREFIX_TAG, customerEmailNode, customerTagNode),
                new Edge<>(PREFIX_TAG, customerTagNode, customerTagNode)
        );
    }

}
