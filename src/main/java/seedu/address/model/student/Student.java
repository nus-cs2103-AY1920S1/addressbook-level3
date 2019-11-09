package seedu.address.model.student;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Student in the student record.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Student {

    // Identity fields
    private Name name;
    private final Set<Tag> tags = new HashSet<>();
    private boolean isMarked = false;

    /**
     * Creates student with name, tags.
     */
    public Student(Name name, Set<Tag> tags) {
        requireAllNonNull(name);
        this.name = name;
        this.tags.addAll(tags);
    }

    /**
     * Creates a student with name, tags and isMarked field.
     */
    public Student(Name name, Set<Tag> tags, boolean isMarked) {
        requireAllNonNull(name);
        this.name = name;
        this.tags.addAll(tags);
        this.isMarked = isMarked;
    }

    public Name getName() {
        return name;
    }

    /**
     * Returns true if both students of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two students.
     */
    public boolean isSameStudent(Student otherStudent) {
        if (otherStudent == this) {
            return true;
        }

        return otherStudent != null
                && otherStudent.getName().equals(getName());
    }

    /**
     * Returns true if both students have the same identity and data fields.
     * This defines a stronger notion of equality between two students.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Student)) {
            return false;
        }

        Student otherStudent = (Student) other;
        return otherStudent.getName().equals(getName());
    }

    public void setName(String name) {
        Name studentName = new Name(name);
        this.name = studentName;
    }

    public Student setMarked() {
        this.isMarked = true;
        return this;
    }

    public Student setUnmarked() {
        this.isMarked = false;
        return this;
    }

    public boolean getIsMarked() {
        return this.isMarked;
    }

    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName());
        return builder.toString();
    }


}
