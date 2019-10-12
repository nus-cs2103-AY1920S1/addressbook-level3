package seedu.jarvis.commons.util.andor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import seedu.jarvis.model.course.Course;

/**
 * Represents an And-Or Tree node, with each node representing either:
 *
 * 1. AND
 * Represents a conjunction. Is {@code true} if all nodes of its children satisfy
 * their conditions.
 *
 * 2. OR
 * Represents a disjunction. Is {@code true} if at least one of its children satisfy
 * their conditions.
 *
 * 3. LEAF
 * Generic node to hold data.
 *
 * All sub-classes of this class representing the three types of nodes.
 *
 * @author ryanYtan
 */
public abstract class AndOrNode {
    protected Course data;
    protected AndOrNode parent;
    protected List<AndOrNode> children;

    protected AndOrNode(Course data, AndOrNode parent, List<AndOrNode> children) {
        this.data = data;
        this.parent = parent;
        this.children = children;
    }

    protected AndOrNode(Course data, AndOrNode parent) {
        this.data = data;
        this.parent = parent;
        this.children = new ArrayList<>();
    }

    /**
     * Checks if this node fulfills the condition.
     *
     * @param collection of courses to check against
     * @return true if the condition is specified
     */
    protected abstract boolean hasFulfilledCondition(Collection<Course> collection);

    public abstract String toString();

    protected Course getData() {
        return this.data;
    }

    protected List<AndOrNode> getChildren() {
        return this.children;
    }

    protected void insert(AndOrNode node) {
        this.children.add(node);
    }

    /**
     * Returns an {@code AND} or {@code OR} node.
     *
     * @param parent of this node to be created
     * @param type of the node as a {@code String}
     * @return a new {@AndOr} node
     */
    public static AndOrNode createAndOrNode(AndOrNode parent, String... type) {
        String nodeType = type.length == 0 ? "" : type[0];
        AndOrOperation andOrNodeType = AndOrOperationMapperUtil.resolveType(nodeType);
        switch (andOrNodeType) {
        case AND:
            return new AndNode(null, parent);
        case OR:
            return new OrNode(null, parent);
        default:
            return null;
        }
    }

    /**
     * Returns a {@code LEAF} node.
     *
     * @param data course data of the node
     * @param parent of this node to be created
     * @return a new {@AndOr} node
     */
    public static AndOrNode createLeafNode(Course data, AndOrNode parent) {
        return new LeafNode(data, parent);
    }

    /**
     * Returns the {@code String} representation of this {@code AndOrNode} object and its
     * sub-trees. The tree is rendered akin to the {@code tree} command in Windows
     * {@code cmd} or bash.
     *
     * @@author ryanYtan-reused
     * Reused from https://stackoverflow.com/a/8948691 along with its helper function with
     * minor modifications.
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
     * Reused from https://stackoverflow.com/a/8948691 with minor modifications.
     */
    private void asStringTreeForm(StringBuilder buffer, String prefix, String childrenPrefix) {
        buffer.append(prefix).append(toString()).append("\n");
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
