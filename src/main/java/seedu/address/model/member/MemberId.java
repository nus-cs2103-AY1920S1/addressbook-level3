package seedu.address.model.member;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a Member's ID in the address book.
 * Guarantees: Field values are validated, immutable.
 */
public class MemberId {
    public static final String MESSAGE_CONSTRAINTS =
            "Invalid member ID, please enter a alphanumeric code";
    public static final String VALIDATION_REGEX = "[\\p{Alpha}]*";

    private String displayId;

    /**
     * Enum constructor to give UI-friendly display names.
     * Cannot be called by other components.
     *
     * @param displayId an alternate name for the member ID
     */
    @JsonCreator
    public MemberId(String displayId) {
        this.displayId = displayId;
    }

    /**
     * Default MemberId constructor for Json support
     */
    public MemberId() {}

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidId(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @JsonValue
    public String getDisplayId() {
        return displayId;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MemberId // instanceof handles nulls
                && displayId.equals(((MemberId) other).displayId)); // state check
    }
}
