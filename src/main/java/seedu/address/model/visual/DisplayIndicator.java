package seedu.address.model.visual;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;

/**
 * Represents an indicator for the display command
 * Guarantees: immutable; is valid as declared in {@link #isValidIndicator(String)}
 */
public class DisplayIndicator {

    public static final String DATA_TYPE = "Indicator";
    private static final String CONTACT_LIST_GROWTH_RATE = "contact-list-growth-rate";
    public static final String MESSAGE_CONSTRAINTS = "Only the following are allowed: " + CONTACT_LIST_GROWTH_RATE;
    private static Set<String> VALID_DISPLAY_INDICATORS = initializeValidDisplayIndicators();
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

    private static Set<String> initializeValidDisplayIndicators() {
        Set<String> validIndicators = new HashSet<>();
        validIndicators.add(CONTACT_LIST_GROWTH_RATE);
        return validIndicators;
    }

    /**
     * Returns true if a given string is a valid indicator.
     */
    public static boolean isValidDisplayIndicator(String test) {
        return VALID_DISPLAY_INDICATORS.contains(test);
    }

    public static String getValidDisplayIndicators() {
        StringJoiner result = new StringJoiner(" ");
        VALID_DISPLAY_INDICATORS.forEach(displayIndicator -> result.add(displayIndicator));
        return result.toString();
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

