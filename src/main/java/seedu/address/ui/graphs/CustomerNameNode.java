package seedu.address.ui.graphs;

import seedu.address.ui.Node;

import java.util.Arrays;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT;

public class CustomerNameNode extends Node {

    private static CustomerNameNode theOne = null;

    public static CustomerNameNode getInstance() {
        if (theOne == null) {
            theOne = new CustomerNameNode();
        }
        return theOne;
    }

    private CustomerNameNode() {
        super();
        this.getEdges().put(PREFIX_CONTACT, CustomerContactNumberNode.getInstance());
        this.getValues().addAll(Arrays.asList("Test name 1", "Albert"));
    }

}
