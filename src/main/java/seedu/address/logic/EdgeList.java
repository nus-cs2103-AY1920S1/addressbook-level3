package seedu.address.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class EdgeList implements Iterable<Edge> {

    private List<Edge> edges;

    public EdgeList() {
        this.edges = new ArrayList<>();
    }

    public void addAll(Edge... edges) {
        this.edges.addAll(Arrays.asList(edges));
    }

    @Override
    public Iterator<Edge> iterator() {
        return this.edges.iterator();
    }

}
