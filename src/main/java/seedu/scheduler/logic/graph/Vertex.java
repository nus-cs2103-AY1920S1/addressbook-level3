package seedu.scheduler.logic.graph;

import java.util.Objects;

/**
 * Represents a vertex in a bipartite graph.
 * @param <U> the item that this vertex holds or encapsulates.
 * @param <V> the vertex that this vertex is matched to.
 */
public abstract class Vertex<U, V> {
    private int index;
    private U item;
    private V partner;

    public Vertex(U item, int index) {
        this.item = item;
        this.index = index;
    }

    public void match(V partner) {
        this.partner = partner;
    }

    public void unmatch() {
        this.partner = null;
    }

    public boolean isMatched() {
        return this.partner != null;
    }

    public boolean isMatchedTo(Vertex v) {
        return this.partner == v;
    }

    public int getIndex() {
        return index;
    }

    public U getItem() {
        return item;
    }

    public V getPartner() {
        return partner;
    }

    /**
     * Returns true if the index and the item and its partner that the vertex is holding is equal to those of other.
     */
    @Override
    public boolean equals(Object other) {
        return other == this
            || other instanceof Vertex && Objects.equals(item, ((Vertex) other).item)
                && Objects.equals(partner, ((Vertex) other).partner)
                && index == ((Vertex) other).index;
    }

    @Override
    public String toString() {
        return String.format("U: %s", item.toString());
    }
}
