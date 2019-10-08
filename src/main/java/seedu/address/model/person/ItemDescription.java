package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Item's description in ELISA.
 * Guarantees: immutable; is valid as declared in {@link #isValidItemDescription(String)}
 */
public class ItemDescription {
    public static final String MESSAGE_CONSTRAINTS =
        "ItemDescription should only contain characters and spaces, and it should not be blank";

    private final String description;

    /**
     * Constructs an {@code ItemDescription}.
     *
     * @param description A valid item description.
     */
    public ItemDescription(String description) {
        requireNonNull(description);
        checkArgument(isValidItemDescription(description), MESSAGE_CONSTRAINTS);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Returns true if a given string is a valid item description.
     */
    public static boolean isValidItemDescription(String test) {
        return test.trim().length() > 0;
    }


    @Override
    public String toString() {
        return description;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ItemDescription // instanceof handles nulls
                && getDescription().equals(((ItemDescription) other).getDescription())); // state check
    }

    @Override
    public int hashCode() {
        return description.hashCode();
    }

}
