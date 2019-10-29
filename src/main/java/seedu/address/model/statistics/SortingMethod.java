package seedu.address.model.statistics;

public class SortingMethod {
    public static final String MESSAGE_CONSTRAINTS =
            "Invalid member ID, please enter a alphanumeric code";

    private String sortBy;

    /**
     * Enum constructor to give UI-friendly display names.
     * Cannot be called by other components.
     *
     * @param sortBy an alternate name for the member ID
     */
    public SortingMethod(String sortBy) {
        this.sortBy = sortBy;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidSortingMethod(String test) {
        return test.equals("task-status") || test.equals("member-tasks") || test.equals("member-claims");
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortingMethod // instanceof handles nulls
                && sortBy.equals(((SortingMethod) other).sortBy)); // state check
    }
}
