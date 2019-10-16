package seedu.address.model.member;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a Member's ID in the address book.
 * Guarantees: Field values are validated, immutable.
 */
public class MemberId {
    public static final String MESSAGE_CONSTRAINTS = "Invalid memberID, please enter a alphanumeric code";

    private String displayId;

    /**
     * Enum constructor to give UI-friendly display names.
     * Cannot be called by other components.
     *
     * @param displayId an alternate name for the member ID
     */
    public MemberId(String displayId) {
        this.displayId = displayId;
    }

    /**
     * Default MemberId constructor for Json support
     */
    public MemberId() {}

    @JsonValue
    public String getDisplayId() {
        return displayId;
    }
}
