package seedu.jarvis.commons.util.andor;

import java.util.ArrayList;
import java.util.Iterator;
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

    AndOrNode(T data, String type, AndOrNode parent, List<AndOrNode> children) {
        this.data = data;
        this.type = AndOrOperationMapper.resolveType(type);
        this.parent = parent;
        this.children = children;
    }

    AndOrNode(T data, String type, AndOrNode parent) {
        this.data = data;
        this.type = AndOrOperationMapper.resolveType(type);
        this.parent = parent;
        this.children = new ArrayList<>();
    }

    void insert(AndOrNode node) {
        this.children.add(node);
    }

    T getData() {
        return this.data;
    }

    String getDataAsString() {
        String typeAsString = AndOrOperationMapper.asString(type);
        return typeAsString == null ? getData().toString() : typeAsString;
    }

    /**
     * @@author ryanYtan-reused
     * Reused from https://stackoverflow.com/a/8948691 with minor modifications
     *
     * @return a String containing the String representation of this {@code AndOrNode} object,
     * and its sub-trees
     */
    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        asStringTreeForm(buffer, "", "");
        return buffer.toString();
    }

    /**
     * Helper method to get this node plus its sub-trees in String form.
     *
     * @@author ryanYtan-reused
     * Reused from https://stackoverflow.com/a/8948691 with minor modifications
     */
    private void asStringTreeForm(StringBuilder buffer, String prefix, String childrenPrefix) {
        buffer.append(prefix).append(getDataAsString()).append("\n");
        for (Iterator<AndOrNode> it = children.iterator(); it.hasNext();) {
            AndOrNode child = it.next();
            if (it.hasNext()) {
                child.asStringTreeForm(buffer, childrenPrefix
                        + "├── ", childrenPrefix + "│   ");
            } else {
                child.asStringTreeForm(buffer, childrenPrefix
                        + "└── ", childrenPrefix + "    ");
            }
        }
    }
}
