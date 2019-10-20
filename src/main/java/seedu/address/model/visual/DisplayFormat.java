package seedu.address.model.visual;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;

/**
 * Represents an indicator for the display command
 * Guarantees: immutable; is valid as declared in {@link #isValidDisplayFormat(String)}
 */
public class DisplayFormat {

    public static final String DATA_TYPE = "Indicator";
    public static final String PIECHART = "piechart";
    public static final String BARCHART = "barchart";
    private static final Set<String> VALID_DISPLAY_FORMAT = initializeValidDisplayFormat();
    private static final String MESSAGE_CONSTRAINTS = initializeMessageConstraints();
    public final String value;

    /**
     * Constructs an {@code display format}.
     *
     * @param displayFormat A valid display format.
     */
    public DisplayFormat(String displayFormat) {
        requireNonNull(displayFormat);
        this.value = displayFormat;
    }

    /**
     * Initializes valid display format.
     *
     * @return a set of valid display format
     */
    private static Set<String> initializeValidDisplayFormat() {
        HashSet<String> result = new HashSet<>();
        result.add(PIECHART);
        result.add(BARCHART);
        return result;
    }

    /**
     * Initialises message constraints based on valid display format.
     *
     * @return String.
     */
    private static String initializeMessageConstraints() {
        String result = "Only the following format are allowed: ";
        StringJoiner examples = new StringJoiner(", ");
        VALID_DISPLAY_FORMAT.forEach(displayFormat -> examples.add(displayFormat));
        return result + examples.toString();
    }

    /**
     * Returns true if a given string is a valid indicator.
     */
    public static boolean isValidDisplayFormat(String test) {
        return VALID_DISPLAY_FORMAT.contains(test);
    }

    public static Set<String> getValidDisplayFormat() {
        return VALID_DISPLAY_FORMAT;
    }

    public static String getMessageConstraints() {
        return MESSAGE_CONSTRAINTS;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof DisplayFormat // instanceof handles nulls
            && value.equals(((DisplayFormat) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

