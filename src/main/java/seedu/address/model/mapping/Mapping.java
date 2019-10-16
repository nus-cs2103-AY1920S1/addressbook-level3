package seedu.address.model.mapping;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.model.task.Task;
import seedu.address.model.member.Member;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


public class Mapping {

    private final Member member;
    private final Task task;

    public Mapping(Member member, Task task) {
        requireAllNonNull(member, task);
        this.member = member;
        this.task = task;
    }

    public Member getMember() {
        return member;
    }

    public boolean hasMember(Member otherMember) {
        if (otherMember == member) {
            return true;
        }

        return otherMember != null
                && member.equals(otherMember);
    }

    public Task getTask() {
        return task;
    }

    public boolean hasTask(Task otherTask) {
        if (otherTask == task) {
            return true;
        }

        return otherTask != null
                && task.equals(otherTask);
    }

    /**
     * Returns true if both tasks of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameMapping(Mapping otherMapping) {
        if (otherMapping == this) {
            return true;
        }

        // TODO change the logic to check for the identity fields of status and member
        // basically the name cannot be the same, that's it
        return member.equals(otherMapping.getMember())
                && task.equals(otherMapping.getTask());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Mapping)) {
            return false;
        }

        Mapping otherMapping = (Mapping) other;

        return otherMapping != null
                && member.equals(otherMapping.getMember())
                && task.equals(otherMapping.getTask());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(task, member);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Placeholder string for Mapping toString method");
        return builder.toString();
    }
}

