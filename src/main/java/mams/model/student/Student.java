package mams.model.student;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import mams.commons.util.CollectionUtil;
import mams.model.tag.Tag;

/**
 * Represents a Student in MAMS
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Student {

    // Identity fields
    private final Name name;
    private final Credits credits;
    private final PrevMods prevMods;

    // Data fields
    private final MatricId matricId;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Student(Name name, Credits credits, PrevMods prevMods, MatricId matricId, Set<Tag> tags) {
        CollectionUtil.requireAllNonNull(name, credits, prevMods, matricId, tags);
        this.name = name;
        this.credits = credits;
        this.prevMods = prevMods;
        this.matricId = matricId;
        this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }

    public Credits getCredits() {
        return credits;
    }

    public PrevMods getPrevMods() {
        return prevMods;
    }

    public MatricId getMatricId() {
        return matricId;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public Set<Tag> getCurrentModules() {
        Set<Tag> ret = new HashSet<>();
        for (Tag tag : tags) {
            if (tag.type.equals("module")) {
                ret.add(tag);
            }
        }
        return Collections.unmodifiableSet(ret);
    }

    public int getNumberOfMods() {
        int count = 0;
        for (Tag tag : tags) {
            if (tag.type.equals("module")) {
                count++;
            }
        }
        return count;
    }

    public Set<Tag> getCurrentAppeals() {
        Set<Tag> ret = new HashSet<>();
        for (Tag tag : tags) {
            if (tag.type.equals("appeal")) {
                ret.add(tag);
            }
        }
        return Collections.unmodifiableSet(ret);
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
                && otherStudent.getName().equals(getName())
                && (otherStudent.getCredits().equals(getCredits()) || otherStudent.getPrevMods().equals(getPrevMods()));
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
        return otherStudent.getName().equals(getName())
                && otherStudent.getMatricId().equals(getMatricId());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, credits, prevMods, matricId, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Credits: ")
                .append(getCredits())
                .append(" PrevMods: ")
                .append(getPrevMods())
                .append(" Matric Id: ")
                .append(getMatricId())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
}
