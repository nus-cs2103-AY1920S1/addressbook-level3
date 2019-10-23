package seedu.address.ui.graphs;

import seedu.address.ui.Graph;
import seedu.address.ui.Node;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a graph used for autocomplete in adding a customer.
 */
public class AddCustomerGraph extends Graph {

    public AddCustomerGraph() {
        super("add-c", new Node(() -> {
            Map<String, Node> edges = new HashMap<>();
            edges.put("n/", new CustomerNameNode());
            return edges;
        }));
    }

}
