package seedu.jarvis.commons.util.andor;

import java.util.ArrayList;
import java.util.List;

/**
 * A utility class representing a Node in this And-Or Tree.
 *
 * @param <T> a generic type
 */
class AndOrNode<T> {
    private T data;
    private AndOrOperation type;
    private AndOrNode parent;
    private List<AndOrNode> children;

    AndOrNode(T data, AndOrOperation type, AndOrNode parent, List<AndOrNode> children) {
        this.data = data;
        this.type = type;
        this.parent = parent;
        this.children = children;
    }

    AndOrNode(T data, AndOrOperation type) {
        this.data = data;
        this.type = type;
        this.parent = null;
        this.children = new ArrayList<>();
    }

     void insert(AndOrNode node) {
        this.children.add(node);
    }

    T getData() {
         return this.data;
    }

    AndOrNode getParent() {
         return this.parent;
    }

    @Override
     String toString() {
        return String.format("{%s: %s}", type, children.toString());
    }
}
