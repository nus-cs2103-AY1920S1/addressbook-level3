package seedu.jarvis.model.finance.installment;

import static java.util.Objects.requireNonNull;
import static seedu.jarvis.commons.util.AppUtil.checkArgument;

/**
 * Represents the description of an installment in the finance tracker.
 * Guarantees: immutable; is valid as declared in {@link #isValidDescription(String)}
 */
public class InstallmentDescription {

    public static final String MESSAGE_CONSTRAINTS =
            "Installment descriptions should only contain alphanumeric characters and spaces, and it should not be "
                    + "blank.";

    /*
     * The first character of the description must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String installmentDescription;

    /**
     * Constructs a {@code InstallmentDescription}.
     *
     * @param description A valid description
     */
    public InstallmentDescription(String description) {
        requireNonNull(description);

        checkArgument(isValidDescription(description), MESSAGE_CONSTRAINTS);
        installmentDescription = description;
    }

    /**
     * Returns true if the given string is a valid name.
     */
    public static boolean isValidDescription(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public String getInstallmentDescription() {
        return installmentDescription;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof InstallmentDescription
                && installmentDescription.equals(((InstallmentDescription) other).installmentDescription));
    }

    @Override
    public int hashCode() {
        return installmentDescription.hashCode();
    }
}
