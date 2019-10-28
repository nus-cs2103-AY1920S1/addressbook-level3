package seedu.address.ui;

import seedu.address.logic.parser.Prefix;

public class Edge<T> {

    private final Prefix weight;
    private final Node<T> source;
    private final Node<T> destination;

    public Edge(Prefix weight, Node<T> source, Node<T> destination) {
        this.weight = weight;
        this.source = source;
        this.destination = destination;
    }

    public Prefix getWeight() {
        return weight;
    }

    public Node<T> getSource() {
        return source;
    }

    public Node<T> getDestination() {
        return destination;
    }

}
