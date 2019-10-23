package seedu.address.ui.graphs;

import seedu.address.ui.Node;

import java.util.HashMap;
import java.util.Map;

public class CustomerContactNumberNode extends Node {

    public CustomerContactNumberNode() {
        super(() -> {
            Map<String, Node> edges = new HashMap<>();
            edges.put("e/", new CustomerEmailNode());
            return edges;
        }, "98765432", "99999999", "88888888");
    }

}
