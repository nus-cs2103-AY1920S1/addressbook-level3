package seedu.address.ui.graphs;

import seedu.address.ui.Node;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

public class AddCustomerStartNode extends Node {

    private static AddCustomerStartNode theOne = null;

    public static AddCustomerStartNode getInstance() {
        if (theOne == null) {
            theOne = new AddCustomerStartNode();
        }
        return theOne;
    }

    private AddCustomerStartNode() {
        super();
        this.getEdges().put(PREFIX_NAME, CustomerNameNode.getInstance());
    }

}
