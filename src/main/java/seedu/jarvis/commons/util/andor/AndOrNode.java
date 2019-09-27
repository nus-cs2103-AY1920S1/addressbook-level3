package seedu.jarvis.commons.util.andor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class AndOrNode<T> {
    protected T data;
    protected AndOrNode parent;
    protected List<AndOrNode> children;

    public abstract String toString();

    protected AndOrNode(T data, AndOrNode parent, List<AndOrNode> children) {
        this.data = data;
        this.parent = parent;
        this.children = children;
    }

    protected AndOrNode(T data, AndOrNode parent) {
        this.data = data;
        this.parent = parent;
        this.children = new ArrayList<>();
    }

    public T getData() {
        return this.data;
    }

    void insert(AndOrNode node) {
        this.children.add(node);
    }

    public static <T> AndOrNode<T> createAndOrNode(T data, AndOrNode parent, String... type) {
        String nodeType = type.length == 0 ? "" : type[0];
        AndOrOperation andOrNodeType = AndOrOperationMapper.resolveType(nodeType);
        switch (andOrNodeType) {
        case AND:
            return new AndNode<T>(data, parent);
        case OR:
            return new OrNode<T>(data, parent);
        default:
            return new LeafNode<T>(data, parent);
        }
    }

    /**
     * Returns the {@code String} representation of this {@code AndOrNode} object and its
     * sub-trees.
     *
     * @@author ryanYtan-reused
     * Reused from https://stackoverflow.com/a/8948691 with minor modifications
     *
     * @return a String containing the String representation of this {@code AndOrNode} object,
     * and its sub-trees
     */
    public String toTreeString() {
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
        buffer.append(prefix).append(this.toString()).append("\n");
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
