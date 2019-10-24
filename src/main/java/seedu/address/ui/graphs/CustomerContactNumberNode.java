package seedu.address.ui.graphs;

import seedu.address.ui.Node;

import java.util.Arrays;

import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;

public class CustomerContactNumberNode extends Node {

    private static CustomerContactNumberNode theOne = null;

    public static CustomerContactNumberNode getInstance() {
        if (theOne == null) {
            theOne = new CustomerContactNumberNode();
        }
        return theOne;
    }

    private CustomerContactNumberNode() {
        super();
        this.getEdges().put(PREFIX_EMAIL, CustomerEmailNode.getInstance());
        this.getValues().addAll(Arrays.asList("98765432", "99999999", "88888888"));
    }

}
