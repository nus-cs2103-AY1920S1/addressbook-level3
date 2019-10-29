package seedu.address.logic;

import seedu.address.logic.parser.Prefix;

public class Edge {

    private final Prefix weight;
    private final Node<?> source;
    private final Node<?> destination;

    public Edge(Prefix weight, Node<?> source, Node<?> destination) {
        this.weight = weight;
        this.source = source;
        this.destination = destination;
    }

    public Prefix getWeight() {
        return weight;
    }

    public Node<?> getSource() {
        return source;
    }

    public Node<?> getDestination() {
        return destination;
    }

}
