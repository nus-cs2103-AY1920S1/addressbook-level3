package seedu.jarvis.commons.util.andor;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;

import static seedu.jarvis.commons.util.andor.AndOrOperation.AND;
import static seedu.jarvis.commons.util.andor.AndOrOperation.OR;
import static seedu.jarvis.commons.util.andor.AndOrOperation.LEAF;

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

    AndOrNode(T data, String type, AndOrNode parent, List<AndOrNode> children) {
        this.data = data;
        this.type = resolveType(type);
        this.parent = parent;
        this.children = children;
    }

    AndOrNode(T data, String type, AndOrNode parent) {
        this.data = data;
        this.type = resolveType(type);
        this.parent = parent;
        this.children = new ArrayList<>();
    }

    AndOrOperation resolveType(String type) {
        switch (type) {
            case "and":
                return AND;
            case "or":
                return OR;
            default:
                return LEAF;
        }
    }

    void insert(AndOrNode node) {
        this.children.add(node);
    }

    T getData() {
         return this.data;
    }

    List<AndOrNode> getChildren() {
        return this.children;
    }

    AndOrNode getParent() {
         return this.parent;
    }

    @Override
    public String toString() {
        return String.format("{%s: %s}", type, data);
    }
}
