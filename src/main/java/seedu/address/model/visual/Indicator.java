package seedu.address.model.visual;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;

/**
 * Represents an indicator for the display command
 * Guarantees: immutable; is valid as declared in {@link #isValidIndicator(String)}
 */
public class Indicator {

    public static final String DATA_TYPE = "Indicator";
    // TODO: Add in compiled list of indicators
    private static final String CONTACT_LIST_GROWTH_RATE = "contact-list-growth-rate";
    public static final String MESSAGE_CONSTRAINTS = "Only the following are allowed: " + CONTACT_LIST_GROWTH_RATE;
    private static Set<String> VALID_INDICATORS = initializeValidIndicators();
    public final String indicator;

    /**
     * Constructs an {@code Indicator}.
     *
     * @param indicator A valid Indicator.
     */
    public Indicator(String indicator) {
        requireNonNull(indicator);
        this.indicator = indicator;
    }

    private static Set<String> initializeValidIndicators() {
        Set<String> validIndicators = new HashSet<>();
        validIndicators.add(CONTACT_LIST_GROWTH_RATE);
        return validIndicators;
    }

    /**
     * Returns true if a given string is a valid indicator.
     */
    public static boolean isValidIndicator(String test) {
        return VALID_INDICATORS.contains(test);
    }

    public static String getValidIndicators() {
        StringJoiner result = new StringJoiner(" ");
        VALID_INDICATORS.forEach(indicator -> result.add(indicator));
        return result.toString();
    }

    @Override
    public String toString() {
        return indicator;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Indicator // instanceof handles nulls
            && indicator.equals(((Indicator) other).indicator)); // state check
    }

    @Override
    public int hashCode() {
        return indicator.hashCode();
    }
}

