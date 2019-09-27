package seedu.jarvis.commons.util.andor;

import java.util.List;

public class AndNode<T> extends AndOrNode<T> {
    protected AndNode(T data, AndOrNode parent, List<AndOrNode> children) {
        super(data, parent, children);
    }

    protected AndNode(T data, AndOrNode parent) {
        super(data, parent);
    }

    @Override
    public String toString() {
        return "all of";
    }
}
