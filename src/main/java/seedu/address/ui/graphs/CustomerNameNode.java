package seedu.address.ui.graphs;

import seedu.address.ui.Node;

import java.util.HashMap;
import java.util.Map;

public class CustomerNameNode extends Node {

    public CustomerNameNode() {
        super(() -> {
            Map<String, Node> edges = new HashMap<>();
            edges.put("c/", new CustomerContactNumberNode());
            return edges;
        }, "John Doe", "Felix Kjellberg", "The Cat");
    }

}
