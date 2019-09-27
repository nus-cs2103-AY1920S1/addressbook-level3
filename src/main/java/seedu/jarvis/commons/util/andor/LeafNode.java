package seedu.jarvis.commons.util.andor;

import java.util.Collection;
import java.util.List;

public class LeafNode<T> extends AndOrNode<T> {
    protected LeafNode(T data, AndOrNode<T> parent, List<AndOrNode<T>> children) {
        super(data, parent, children);
    }

    protected LeafNode(T data, AndOrNode<T> parent) {
        super(data, parent);
    }

    @Override
    public boolean hasFulfilledCondition(Collection<T> collection) {
        System.out.println("=================");
        System.out.println(data);
        System.out.println(collection.toString());
        System.out.println(collection.contains(data));
        System.out.println("=================");
        return collection.contains(data);
    }

    @Override
    public String toString() {
        return this.getData().toString();
    }
}
