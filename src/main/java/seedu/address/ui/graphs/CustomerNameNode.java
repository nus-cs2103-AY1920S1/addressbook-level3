package seedu.address.ui.graphs;

import seedu.address.model.customer.Customer;
import seedu.address.ui.Node;

import java.util.Arrays;
import java.util.List;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT;

public class CustomerNameNode extends Node<Customer> {

    private static CustomerNameNode theOne = null;

    public static CustomerNameNode getInstance(List<Customer> backingList) {
        if (theOne == null) {
            theOne = new CustomerNameNode(backingList);
        }
        return theOne;
    }

    private CustomerNameNode(List<Customer> backingList) {
        super(backingList);
        this.getEdges().put(PREFIX_CONTACT, CustomerContactNumberNode.getInstance(backingList));
        this.getValues().addAll(Arrays.asList("Test name 1", "Albert"));
    }

}
