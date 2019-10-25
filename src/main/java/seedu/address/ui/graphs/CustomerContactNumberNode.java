package seedu.address.ui.graphs;

import seedu.address.model.customer.Customer;
import seedu.address.ui.Node;

import java.util.Arrays;
import java.util.List;

import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;

public class CustomerContactNumberNode extends Node<Customer> {

    private static CustomerContactNumberNode theOne = null;

    public static CustomerContactNumberNode getInstance(List<Customer> backingList) {
        if (theOne == null) {
            theOne = new CustomerContactNumberNode(backingList);
        }
        return theOne;
    }

    private CustomerContactNumberNode(List<Customer> backingList) {
        super(backingList);
        this.getEdges().put(PREFIX_EMAIL, CustomerEmailNode.getInstance(backingList));
        this.getValues().addAll(Arrays.asList("98765432", "99999999", "88888888"));
    }

}
