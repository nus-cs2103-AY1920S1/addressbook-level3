package seedu.address.model.mapping;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.model.task.Task;
import seedu.address.model.member.Member;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


public class MappingByIndex {

    private final int memberIndex;
    private final int taskIndex;

    public MappingByIndex (int memberIndex, int taskIndex) {
        this.memberIndex = memberIndex;
        this.taskIndex = taskIndex;
    }

    public int getMemberIndex() {
        return memberIndex;
    }

    public int getTaskIndex() {
        return taskIndex;
    }

    /**
     * Returns true if both tasks of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameMapping(MappingByIndex otherMapping) {
        if (otherMapping == this) {
            return true;
        }

        // TODO change the logic to check for the identity fields of status and member
        // basically the name cannot be the same, that's it
        return memberIndex == otherMapping.getMemberIndex()
                && taskIndex == otherMapping.getTaskIndex();
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

        if (!(other instanceof MappingByIndex)) {
            return false;
        }

        MappingByIndex otherMapping = (MappingByIndex) other;

        return otherMapping != null
                && memberIndex == otherMapping.getMemberIndex()
                && taskIndex == otherMapping.getTaskIndex();
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(taskIndex, memberIndex);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Placeholder string for Mapping toString method");
        return builder.toString();
    }
}

