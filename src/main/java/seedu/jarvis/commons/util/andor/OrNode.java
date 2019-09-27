package seedu.jarvis.commons.util.andor;

import java.util.List;

public class OrNode<T> extends AndOrNode<T> {
    protected OrNode(T data, AndOrNode parent, List<AndOrNode> children) {
        super(data, parent, children);
    }

    protected OrNode(T data, AndOrNode parent) {
        super(data, parent);
    }

    @Override
    public String toString() {
        return "one of";
    }
}
