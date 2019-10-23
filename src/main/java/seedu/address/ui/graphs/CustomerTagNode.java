package seedu.address.ui.graphs;

import seedu.address.ui.Node;

import java.util.HashMap;
import java.util.Map;

public class CustomerTagNode extends Node {

    public CustomerTagNode() {
        super(() -> {
            Map<String, Node> edges = new HashMap<>();
            edges.put("t/", null);
            return edges;
        }, "Friend", "Elderly");
    }

}
