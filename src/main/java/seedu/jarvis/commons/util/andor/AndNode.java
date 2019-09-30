package seedu.jarvis.commons.util.andor;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.jarvis.model.course.Course;


/**
 * Represents an {@code AND} node of an And-Or Tree.
 *
 * @author ryanYtan
 */
public class AndNode extends AndOrNode {
    private static final String STRING_FORM = "all of";

    protected AndNode(Course data, AndOrNode parent, List<AndOrNode> children) {
        super(data, parent, children);
    }

    protected AndNode(Course data, AndOrNode parent) {
        super(data, parent);
    }

    @Override
    public boolean hasFulfilledCondition(Collection<Course> collection) {
        Set<Boolean> bool = new HashSet<>();
        for (AndOrNode node : children) {
            bool.add(node.hasFulfilledCondition(collection));
        }
        return !bool.contains(false);
    }

    @Override
    public String toString() {
        return STRING_FORM;
    }
}
