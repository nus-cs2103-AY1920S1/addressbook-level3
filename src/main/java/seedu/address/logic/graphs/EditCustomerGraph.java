package seedu.address.logic.graphs;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.List;

import seedu.address.logic.AutoCompleteEdge;
import seedu.address.logic.AutoCompleteNode;
import seedu.address.logic.GraphWithStartNodeAndPreamble;
import seedu.address.logic.nodes.EmptyAutoCompleteNode;
import seedu.address.logic.nodes.customer.CustomerContactNumberNode;
import seedu.address.logic.nodes.customer.CustomerEmailNode;
import seedu.address.logic.nodes.customer.CustomerNameNode;
import seedu.address.logic.nodes.customer.CustomerTagNode;
import seedu.address.model.Model;
import seedu.address.model.customer.Customer;

/**
 * Represents a {@code Graph} used to support autocomplete for {@code EditCustomerCommand}.
 */
public class EditCustomerGraph extends GraphWithStartNodeAndPreamble {

    public EditCustomerGraph(Model model) {
        super(model);
    }

    @Override
    protected void build(Model model) {
        List<Customer> customerList = model.getFilteredCustomerList();
        setDataList(customerList);
        AutoCompleteNode<?> editCustomerStartNode = EmptyAutoCompleteNode.getInstance();
        setStartingNode(editCustomerStartNode);
        CustomerContactNumberNode customerContactNumberNode = new CustomerContactNumberNode(customerList);
        CustomerEmailNode customerEmailNode = new CustomerEmailNode(customerList);
        CustomerNameNode customerNameNode = new CustomerNameNode(customerList);
        CustomerTagNode customerTagNode = new CustomerTagNode(customerList);
        edgeList.addAll(Arrays.asList(
                new AutoCompleteEdge<>(PREFIX_CONTACT, editCustomerStartNode, customerContactNumberNode),
                new AutoCompleteEdge<>(PREFIX_EMAIL, editCustomerStartNode, customerEmailNode),
                new AutoCompleteEdge<>(PREFIX_NAME, editCustomerStartNode, customerNameNode),
                new AutoCompleteEdge<>(PREFIX_TAG, editCustomerStartNode, customerTagNode),
                new AutoCompleteEdge<>(PREFIX_CONTACT, customerContactNumberNode, customerContactNumberNode),
                new AutoCompleteEdge<>(PREFIX_EMAIL, customerContactNumberNode, customerEmailNode),
                new AutoCompleteEdge<>(PREFIX_NAME, customerContactNumberNode, customerNameNode),
                new AutoCompleteEdge<>(PREFIX_TAG, customerContactNumberNode, customerTagNode),
                new AutoCompleteEdge<>(PREFIX_CONTACT, customerEmailNode, customerContactNumberNode),
                new AutoCompleteEdge<>(PREFIX_EMAIL, customerEmailNode, customerEmailNode),
                new AutoCompleteEdge<>(PREFIX_NAME, customerEmailNode, customerNameNode),
                new AutoCompleteEdge<>(PREFIX_TAG, customerEmailNode, customerTagNode),
                new AutoCompleteEdge<>(PREFIX_CONTACT, customerNameNode, customerContactNumberNode),
                new AutoCompleteEdge<>(PREFIX_EMAIL, customerNameNode, customerEmailNode),
                new AutoCompleteEdge<>(PREFIX_NAME, customerNameNode, customerNameNode),
                new AutoCompleteEdge<>(PREFIX_TAG, customerNameNode, customerTagNode),
                new AutoCompleteEdge<>(PREFIX_CONTACT, customerTagNode, customerContactNumberNode),
                new AutoCompleteEdge<>(PREFIX_EMAIL, customerTagNode, customerEmailNode),
                new AutoCompleteEdge<>(PREFIX_NAME, customerTagNode, customerNameNode),
                new AutoCompleteEdge<>(PREFIX_TAG, customerTagNode, customerTagNode)
        ));
    }

}
