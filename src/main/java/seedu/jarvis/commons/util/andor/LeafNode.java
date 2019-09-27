package seedu.jarvis.commons.util.andor;

import java.util.List;

public class LeafNode<T> extends AndOrNode<T> {
    protected LeafNode(T data, AndOrNode parent, List<AndOrNode> children) {
        super(data, parent, children);
    }

    protected LeafNode(T data, AndOrNode parent) {
        super(data, parent);
    }

    @Override
    public String toString() {
        return this.getData().toString();
    }
}
