package seedu.address.ui.graphs;

import seedu.address.model.customer.Customer;
import seedu.address.ui.Node;

import java.util.List;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

public class AddCustomerStartNode extends Node<Customer> {

    private static AddCustomerStartNode theOne = null;

    public static AddCustomerStartNode getInstance(List<Customer> backingList) {
        if (theOne == null) {
            theOne = new AddCustomerStartNode(backingList);
        }
        return theOne;
    }

    private AddCustomerStartNode(List<Customer> backingList) {
        super(backingList);
        this.getEdges().put(PREFIX_NAME, CustomerNameNode.getInstance(backingList));
    }

}
