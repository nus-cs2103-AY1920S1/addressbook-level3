package seedu.address.testutil.student;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.student.Name;
import seedu.address.model.student.Student;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help build students.
 */
public class StudentBuilder {
    public static final Name DEFAULT_NAME = new Name("Jonathan Del");
    public static final Set<Tag> DEFAULT_TAGS = new HashSet<>();
    public static final boolean DEFAULT_MARK = false;

    private Name name;
    private Set<Tag> tags;
    private Boolean isMarked;

    public StudentBuilder() {
        name = DEFAULT_NAME;
        tags = DEFAULT_TAGS;
        isMarked = DEFAULT_MARK;
    }

    /**
     * Initializes the StudentBuilder with the data of {@code studentToCopy}.
     */
    public StudentBuilder(Student studentToCopy) {
        name = studentToCopy.getName();
        tags = studentToCopy.getTags();
        isMarked = studentToCopy.getIsMarked();
    }

    /**
     * Sets the {@code name} of the {@code Student} that we are building.
     */
    public StudentBuilder withName(Name name) {
        this.name = name;
        return this;
    }

    /**
     * Sets the {@code tags} of the {@code Student} that we are building.
     */
    public StudentBuilder withTags(HashSet<Tag> tags) {
        this.tags = tags;
        return this;
    }

    /**
     * Sets the {@code isMarked} of the {@code Student} that we are building.
     */
    public StudentBuilder withMark(boolean isMarked) {
        this.isMarked = isMarked;
        return this;
    }

    public Student build() {
        return new Student(name, tags, isMarked);
    }
}
