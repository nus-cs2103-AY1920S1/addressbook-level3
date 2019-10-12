package seedu.jarvis.model.course;

/**
 * Represents a Course's prerequisite tree in the course planner component.
 *
 * @author ryanYtan
 */
public class PrereqTree {
    public final String tree;

    /**
     * Constructs a {@code PrereqTree}
     *
     * @param tree of the course
     */
    public PrereqTree(String tree) {
        this.tree = tree;
    }

    @Override
    public String toString() {
        return tree;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof PrereqTree
                && tree.equals(((PrereqTree) other).tree));
    }

    @Override
    public int hashCode() {
        return tree.hashCode();
    }
}
