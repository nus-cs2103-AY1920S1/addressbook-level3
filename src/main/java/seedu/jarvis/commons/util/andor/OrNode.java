package seedu.jarvis.commons.util.andor;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OrNode<T> extends AndOrNode<T> {
    protected OrNode(T data, AndOrNode<T> parent, List<AndOrNode<T>> children) {
        super(data, parent, children);
    }

    protected OrNode(T data, AndOrNode parent) {
        super(data, parent);
    }

    @Override
    public boolean hasFulfilledCondition(Collection<T> collection) {
        Set<Boolean> bool = new HashSet<>();
        for (AndOrNode<T> node : children) {
            bool.add(node.hasFulfilledCondition(collection));
        }
        return bool.contains(true);
    }

    @Override
    public String toString() {
        return "one of";
    }
}
