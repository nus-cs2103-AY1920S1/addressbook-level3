package seedu.ichifund.model.repeater;

import static java.util.Objects.requireNonNull;
import static seedu.ichifund.commons.util.AppUtil.checkArgument;

/**
 * Represents a Unique Id for a Repeater in IchiFund.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class RepeaterUniqueId {

    public static final String MESSAGE_CONSTRAINTS =
            "Repeater unique ids should either be empty or only contain numbers";
    public static final String VALIDATION_REGEX = "[0-9]*";

    public final Integer id;

    /**
     * Constructs a {@code RepeaterUniqueId}.
     *
     * @param id A valid id.
     */
    public RepeaterUniqueId(String id) {
        requireNonNull(id);
        checkArgument(isValidRepeaterUniqueId(id), MESSAGE_CONSTRAINTS);
        if (!id.equals("")) {
            this.id = Integer.parseInt(id);
        } else {
            this.id = null;
        }
    }

    /**
     * Returns true if a given string is a valid repeater unique id.
     */
    public static boolean isValidRepeaterUniqueId(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public boolean isEmpty() {
        return id == null;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (other instanceof RepeaterUniqueId) {
            if (id == null && ((RepeaterUniqueId) other).id == null) {
                return true;
            } else if (id == null || ((RepeaterUniqueId) other).id == null) {
                return false;
            } else {
                return id.equals(((RepeaterUniqueId) other).id);
            }
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        if (id == null) {
            return 0;
        } else {
            return id.hashCode();
        }
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        if (id == null) {
            return "";
        } else {
            return id.toString();
        }
    }
}
