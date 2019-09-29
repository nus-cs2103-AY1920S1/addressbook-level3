package seedu.jarvis.commons.util.andor;

import seedu.jarvis.model.course.Course;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AndNode extends AndOrNode {
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
        return "all of";
    }
}
