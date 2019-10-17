package seedu.jarvis.model.financetracker.purchase;

import static java.util.Objects.requireNonNull;
import static seedu.jarvis.commons.util.AppUtil.checkArgument;

/**
 * Represents the description of a purchase in the finance tracker.
 * Guarantees: immutable; is valid as declared in {@link #isValidDescription(String)}
 */
public class PurchaseDescription {

    public static final String MESSAGE_CONSTRAINTS =
            "Names should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the description must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String purchaseDescription;

    /**
     * Constructs a {@code PurchaseDescription}.
     *
     * @param description A valid description
     */
    public PurchaseDescription(String description) {
        requireNonNull(description);
        checkArgument(isValidDescription(description), MESSAGE_CONSTRAINTS);
        purchaseDescription = description;
    }

    /**
     * Returns true if the given string is a valid name.
     */
    public static boolean isValidDescription(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return purchaseDescription;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof PurchaseDescription
                && purchaseDescription.equals(((PurchaseDescription) other).purchaseDescription));
    }

    @Override
    public int hashCode() {
        return purchaseDescription.hashCode();
    }
}
