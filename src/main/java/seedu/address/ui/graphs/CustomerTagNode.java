package seedu.address.ui.graphs;

import seedu.address.ui.Node;

import java.util.Arrays;

import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

public class CustomerTagNode extends Node {

    private static CustomerTagNode theOne = null;

    public static CustomerTagNode getInstance() {
        if (theOne == null) {
            theOne = new CustomerTagNode();
        }
        return theOne;
    }

    private CustomerTagNode() {
        super();
        this.getEdges().put(PREFIX_TAG, this);
        this.getValues().addAll(Arrays.asList("Friend"));
    }

}
