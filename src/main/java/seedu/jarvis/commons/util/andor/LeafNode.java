package seedu.jarvis.commons.util.andor;

import seedu.jarvis.model.course.Course;

import java.util.Collection;
import java.util.List;

public class LeafNode extends AndOrNode {
    protected LeafNode(Course data, AndOrNode parent, List<AndOrNode> children) {
        super(data, parent, children);
    }

    protected LeafNode(Course data, AndOrNode parent) {
        super(data, parent);
    }

    @Override
    public boolean hasFulfilledCondition(Collection<Course> collection) {
        return collection.contains(data);
    }

    @Override
    public String toString() {
        return (this.data == null) ? "null" : this.getData().getCourseCode().toString();
    }
}
