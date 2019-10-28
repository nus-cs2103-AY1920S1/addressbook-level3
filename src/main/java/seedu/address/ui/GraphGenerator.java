package seedu.address.ui;

import seedu.address.logic.Logic;
import seedu.address.model.customer.Customer;
import seedu.address.ui.graphs.AddCustomerStartNode;
import seedu.address.ui.graphs.CustomerContactNumberNode;
import seedu.address.ui.graphs.CustomerEmailNode;
import seedu.address.ui.graphs.CustomerNameNode;
import seedu.address.ui.graphs.CustomerTagNode;

import java.util.Arrays;
import java.util.Optional;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

public class GraphGenerator {

    private static GraphGenerator theOne;

    private final Logic logic;

    private Graph<Customer> addCustomerGraph;

    public static GraphGenerator getInstance() {
        if (theOne == null) {
            throw new NullPointerException();
        } else {
            return theOne;
        }
    }

    public GraphGenerator(Logic logic) {
        this.logic = logic;
        setAddCustomerGraph();
        theOne = this;
    }

    private void setAddCustomerGraph() {
        Node<Customer> addCustomerStartNode = new AddCustomerStartNode(logic.getFilteredCustomerList());
        Node<Customer> customerContactNumberNode = new CustomerContactNumberNode(logic.getFilteredCustomerList());
        Node<Customer> customerEmailNode = new CustomerEmailNode(logic.getFilteredCustomerList());
        Node<Customer> customerNameNode = new CustomerNameNode(logic.getFilteredCustomerList());
        Node<Customer> customerTagNode = new CustomerTagNode(logic.getFilteredCustomerList());
        addCustomerGraph = new Graph<>(addCustomerStartNode, Arrays.asList(
                new Edge<>(PREFIX_NAME, addCustomerStartNode, customerNameNode),
                new Edge<>(PREFIX_CONTACT, customerNameNode, customerContactNumberNode),
                new Edge<>(PREFIX_EMAIL, customerContactNumberNode, customerEmailNode),
                new Edge<>(PREFIX_TAG, customerEmailNode, customerTagNode)
        ));
    }

    public Optional<Graph<?>> getGraph(String commandWord) {
        switch (commandWord) {
        case "add-c":
            return Optional.of(addCustomerGraph);
        default:
            return Optional.empty();
        }
    }

}
