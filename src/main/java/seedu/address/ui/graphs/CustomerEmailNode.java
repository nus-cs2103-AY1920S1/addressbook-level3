package seedu.address.ui.graphs;

import seedu.address.model.customer.Customer;
import seedu.address.ui.Node;

import java.util.Arrays;
import java.util.List;

import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

public class CustomerEmailNode extends Node<Customer> {

    private static CustomerEmailNode theOne = null;

    public static CustomerEmailNode getInstance(List<Customer> backingList) {
        if (theOne == null) {
            theOne = new CustomerEmailNode(backingList);
        }
        return theOne;
    }

    private CustomerEmailNode(List<Customer> backingList) {
        super(backingList);
        this.getEdges().put(PREFIX_TAG, CustomerTagNode.getInstance(backingList));
        this.getValues().addAll(Arrays.asList("Example@example.com", "Place@holder.com, Over@engineered.com"));
    }
}
