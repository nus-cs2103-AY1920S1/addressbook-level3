package seedu.pluswork.model.mapping;

import java.util.Objects;

public class TasMemMapping extends Mapping {

    private final int memberIndex;
    private final int taskIndex;

    public TasMemMapping(int taskIndex, int memberIndex) {
        this.memberIndex = memberIndex;
        this.taskIndex = taskIndex;
    }

    public int getMemberIndex() {
        return memberIndex;
    }

    public boolean hasMember(int memberIndex) {
        return this.memberIndex == memberIndex;
    }

    public int getTaskIndex() {
        return taskIndex;
    }

    public boolean hasTask(int taskIndex) {
        return this.taskIndex == taskIndex;
    }

    /**
     * Returns true if both tasks of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameMapping(TasMemMapping otherMapping) {
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

        if (!(other instanceof TasMemMapping)) {
            return false;
        }

        TasMemMapping otherMapping = (TasMemMapping) other;

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
        builder.append("Mapping between task with index ");
        builder.append(taskIndex);
        builder.append(" and member with index");
        builder.append(memberIndex);
        return builder.toString();
    }
}

