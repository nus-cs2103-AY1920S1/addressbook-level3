package seedu.jarvis.commons.util.andor;

import java.util.List;

public class AndOrNode {
    private final String value;
    private final AndOrOperation type;
    private final AndOrNode parent;
    private final List<AndOrNode> children;

    /**
     * Nullary constructor to prevent creating of an empty AndOrNode.
     */
    private AndOrNode() {
        this.value = null;
        this.type = null;
        this.parent = null;
        this.children = null;
    }

    /**
     * Creates a new AndOrNode with the given parameters.
     *
     * @param value of the current node
     * @param type of the current node
     * @param children the node's children
     */
    private AndOrNode(String value, AndOrOperation type, AndOrNode parent, List<AndOrNode> children) {
        this.value = value;
        this.type = type;
        this.parent = parent;
        this.children = children;
    }

    static AndOrNode createLeafNode(String value, AndOrNode parent) {
        return new AndOrNode(value, AndOrOperation.NONE, parent, null);
    }

    static AndOrNode createAndNode(AndOrNode parent, List<AndOrNode> children) {
        return new AndOrNode(null, AndOrOperation.AND, parent, children);
    }

    static AndOrNode createOrNode(AndOrNode parent, List<AndOrNode> children) {
        return new AndOrNode(null, AndOrOperation.OR, parent, children);
    }
}
