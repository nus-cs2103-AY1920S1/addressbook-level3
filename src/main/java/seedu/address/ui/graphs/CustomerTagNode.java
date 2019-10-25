package seedu.address.ui.graphs;

import seedu.address.model.customer.Customer;
import seedu.address.ui.Node;

import java.util.Arrays;
import java.util.List;

import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

public class CustomerTagNode extends Node<Customer> {

    private static CustomerTagNode theOne = null;

    public static CustomerTagNode getInstance(List<Customer> backingList) {
        if (theOne == null) {
            theOne = new CustomerTagNode(backingList);
        }
        return theOne;
    }

    private CustomerTagNode(List<Customer> backingList) {
        super(backingList);
        this.getEdges().put(PREFIX_TAG, this);
        this.getValues().addAll(Arrays.asList("Friend"));
    }

}
