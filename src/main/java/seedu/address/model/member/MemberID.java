package seedu.address.model.member;

public class MemberID {
    public static final String MESSAGE_CONSTRAINTS =
            "Invalid member ID, please enter a alphanumeric code";

    private String displayName;

    /**
     * Enum constructor to give UI-friendly display names.
     * Cannot be called by other components.
     *
     * @param displayName an alternate name for the task status
     */
    MemberID(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return this.displayName;
    }
}
