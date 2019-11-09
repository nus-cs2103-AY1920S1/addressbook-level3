package seedu.address.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AutoCompleteGraph<T> implements Graph<T>, AutoCompleteResultProvider {

    protected final List<AutoCompleteEdge<T, ?, ?>> edgeList;

    protected AutoCompleteGraph() {
        this.edgeList = new ArrayList<>();
    }

    protected AutoCompleteGraph(List<AutoCompleteEdge<T, ?, ?>> edgeList) {
        this.edgeList = edgeList;
    }

    public List<T> getWeights(Node<?> sourceNode) {
        List<T> weights = new ArrayList<>();
        for (AutoCompleteEdge<T, ?, ?> edge : edgeList) {
            if (edge.hasSource(sourceNode)) {
                weights.add(edge.getWeight());
            }
        }
        return weights;
    }

    public Optional<AutoCompleteNode<?>> traverse(Node<?> sourceNode, T weight) {
        for (AutoCompleteEdge<T, ?, ?> edge : edgeList) {
            if (edge.hasSource(sourceNode) && edge.hasWeight(weight)) {
                return Optional.of(edge.getDestination());
            }
        }
        return Optional.empty();
    }

}
