package seedu.jarvis.commons.util.andor;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.jarvis.model.course.Course;

/**
 * Represents an {@code OR} node of an And-Or Tree.
 *
 * @author ryanYtan
 */
public class OrNode extends AndOrNode {
    private static final String STRING_FORM = "one of";

    protected OrNode(Course data, AndOrNode parent, List<AndOrNode> children) {
        super(data, parent, children);
    }

    protected OrNode(Course data, AndOrNode parent) {
        super(data, parent);
    }

    @Override
    public boolean hasFulfilledCondition(Collection<Course> collection) {
        Set<Boolean> bool = new HashSet<>();
        for (AndOrNode node : children) {
            bool.add(node.hasFulfilledCondition(collection));
        }
        return bool.contains(true);
    }

    @Override
    public String toString() {
        return STRING_FORM;
    }
}
