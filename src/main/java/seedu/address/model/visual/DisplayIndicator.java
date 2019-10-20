package seedu.address.model.visual;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;

/**
 * Represents an indicator for the display command
 * Guarantees: immutable; is valid as declared in {@link #isValidDisplayIndicator(String)}
 */
public class DisplayIndicator {

    public static final String DATA_TYPE = "Indicator";
    public static final String CONTACT_LIST_GROWTH_RATE = "contact-list-growth-rate";
    public static final String POLICY_POPULARITY_BREAKDOWN = "policy-popularity-breakdown";
    private static String MESSAGE_CONSTRAINTS = "Only the following indicators are allowed: ";
    private static Set<String> VALID_DISPLAY_INDICATORS = new HashSet<>();

    static {
        VALID_DISPLAY_INDICATORS.add(POLICY_POPULARITY_BREAKDOWN);
        VALID_DISPLAY_INDICATORS.add(CONTACT_LIST_GROWTH_RATE);

        MESSAGE_CONSTRAINTS += getValidDisplayIndicators();
    }

    public final String displayIndicator;

    /**
     * Constructs an {@code display indicator}.
     *
     * @param displayIndicator A valid display indicator.
     */
    public DisplayIndicator(String displayIndicator) {
        requireNonNull(displayIndicator);
        this.displayIndicator = displayIndicator;
    }

    /**
     * Returns true if a given string is a valid indicator.
     */
    public static boolean isValidDisplayIndicator(String test) {
        return VALID_DISPLAY_INDICATORS.contains(test);
    }

    public static String getValidDisplayIndicators() {
        StringJoiner result = new StringJoiner(", ");
        VALID_DISPLAY_INDICATORS.forEach(displayIndicator -> result.add(displayIndicator));
        return result.toString();
    }

    public static String getMessageConstraints() {
        return MESSAGE_CONSTRAINTS;
    }

    @Override
    public String toString() {
        return displayIndicator;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof DisplayIndicator // instanceof handles nulls
            && displayIndicator.equals(((DisplayIndicator) other).displayIndicator)); // state check
    }

    @Override
    public int hashCode() {
        return displayIndicator.hashCode();
    }
}

