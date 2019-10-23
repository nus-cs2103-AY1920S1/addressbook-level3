package seedu.address.ui.graphs;

import seedu.address.ui.Node;

import java.util.HashMap;
import java.util.Map;

public class CustomerEmailNode extends Node {

    public CustomerEmailNode() {
        super(() -> {
            Map<String, Node> edges = new HashMap<>();
            edges.put("t/", new CustomerTagNode());
            return edges;
        }, "Example@example.com", "Place@holder.com, Over@engineered.com");
    }
}
