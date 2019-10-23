package seedu.address.ui;

import seedu.address.ui.graphs.AddCustomerGraph;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class GraphGenerator {

    private List<Graph> graphs;

    public GraphGenerator() {
        graphs = Arrays.asList(
                new AddCustomerGraph()
        );
    }

    public Optional<Graph> getGraph(String name) {
        return graphs.stream()
                .filter(graph -> graph.getName().equals(name))
                .findFirst();
    }

}
