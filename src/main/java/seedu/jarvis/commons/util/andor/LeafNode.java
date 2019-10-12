package seedu.jarvis.commons.util.andor;

import java.util.Collection;
import java.util.List;

import seedu.jarvis.model.course.Course;

/**
 * Represents an {@code LEAF} node of an OR-Or Tree.
 *
 * @author ryanYtan
 */
public class LeafNode extends AndOrNode {
    /** Return if data is {@code null} */
    private static final String EMPTY_FORM = "null";

    protected LeafNode(Course data, AndOrNode parent, List<AndOrNode> children) {
        super(data, parent, children);
    }

    protected LeafNode(Course data, AndOrNode parent) {
        super(data, parent);
    }

    private boolean isNull() {
        return this.data == null;
    }

    @Override
    public boolean hasFulfilledCondition(Collection<Course> collection) {
        return collection.contains(data);
    }

    @Override
    public String toString() {
        return isNull() ? EMPTY_FORM : this.getData().getCourseCode().toString();
    }
}
