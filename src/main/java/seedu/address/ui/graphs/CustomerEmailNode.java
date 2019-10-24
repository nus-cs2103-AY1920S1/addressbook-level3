package seedu.address.ui.graphs;

import seedu.address.ui.Node;

import java.util.Arrays;

import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

public class CustomerEmailNode extends Node {

    private static CustomerEmailNode theOne = null;

    public static CustomerEmailNode getInstance() {
        if (theOne == null) {
            theOne = new CustomerEmailNode();
        }
        return theOne;
    }

    private CustomerEmailNode() {
        super();
        this.getEdges().put(PREFIX_TAG, CustomerTagNode.getInstance());
        this.getValues().addAll(Arrays.asList("Example@example.com", "Place@holder.com, Over@engineered.com"));
    }
}
