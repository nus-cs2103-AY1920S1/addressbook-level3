package seedu.address.model.mapping;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class InvMemMapping {

    private final int memberIndex;
    private final int inventoryIndex;

    public InvMemMapping(int memberIndex, int inventoryIndex) {
        this.memberIndex = memberIndex;
        this.inventoryIndex = inventoryIndex;
    }

    public int getMemberIndex() {
        return memberIndex;
    }

    public int getInventoryIndex() {
        return inventoryIndex;
    }

    /**
     * Returns true if both tasks of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameMapping(InvMemMapping otherMapping) {
        if (otherMapping == this) {
            return true;
        }

        // TODO change the logic to check for the identity fields of status and member
        // basically the name cannot be the same, that's it
        return memberIndex == otherMapping.getMemberIndex()
                && inventoryIndex == otherMapping.getInventoryIndex();
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

        if (!(other instanceof InvMemMapping)) {
            return false;
        }

        InvMemMapping otherMapping = (InvMemMapping) other;

        return otherMapping != null
                && memberIndex == otherMapping.getMemberIndex()
                && inventoryIndex == otherMapping.getInventoryIndex();
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(memberIndex, inventoryIndex);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Placeholder string for InvMemMapping toString method");
        return builder.toString();
    }
}

