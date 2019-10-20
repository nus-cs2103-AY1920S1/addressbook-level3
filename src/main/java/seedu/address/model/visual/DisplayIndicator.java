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
    public static final String AGE_GROUP_BREAKDOWN = "age-group-breakdown";
    public static final String GENDER_BREAKDOWN = "gender-breakdown";
    private static final Set<String> VALID_DISPLAY_INDICATORS = initializeValidDisplayIndicators();
    private static final String MESSAGE_CONSTRAINTS = initializeMessageConstraints();
    public final String value;

    /**
     * Constructs an {@code display indicator}.
     *
     * @param displayIndicator A valid display indicator.
     */
    public DisplayIndicator(String displayIndicator) {
        requireNonNull(displayIndicator);
        this.value = displayIndicator;
    }

    /**
     * Initializes valid display indicators.
     *
     * @return a set of valid display indicators
     */
    private static Set<String> initializeValidDisplayIndicators() {
        HashSet<String> result = new HashSet<>();
        result.add(POLICY_POPULARITY_BREAKDOWN);
        result.add(CONTACT_LIST_GROWTH_RATE);
        result.add(AGE_GROUP_BREAKDOWN);
        result.add(GENDER_BREAKDOWN);
        return result;
    }

    /**
     * Initialises message constraints based on valid display indicators.
     *
     * @return String.
     */
    private static String initializeMessageConstraints() {
        String message = "Only the following indicators are allowed: ";
        StringJoiner result = new StringJoiner(", ");
        result.add(message);
        VALID_DISPLAY_INDICATORS.forEach(displayIndicator -> result.add(displayIndicator));
        return result.toString();
    }

    /**
     * Returns true if a given string is a valid indicator.
     */
    public static boolean isValidDisplayIndicator(String test) {
        return VALID_DISPLAY_INDICATORS.contains(test);
    }

    public static Set<String> getValidDisplayIndicators() {
        return VALID_DISPLAY_INDICATORS;
    }

    public static String getMessageConstraints() {
        return MESSAGE_CONSTRAINTS;
    }

    @Override
    public String toString() {
        return String.join(" ", value.split("-"));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof DisplayIndicator // instanceof handles nulls
            && value.equals(((DisplayIndicator) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

