package seedu.algobase.model.problem;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.commons.util.AppUtil.checkArgument;

/**
 * Represents a Problem's source in the algobase.
 * Guarantees: immutable; is valid as declared in {@link #isValidSource(String)}
 */
public class Source implements Comparable<Source> {

    public static final String MESSAGE_CONSTRAINTS = "Source should be alphanumeric.";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+";
    public static final String DEFAULT_SOURCE_STRING = "";
    public static final Source DEFAULT_SOURCE = new Source();

    public final String value;

    /**
     * Constructs a {@code Source}.
     *
     * @param source A valid source.
     */
    public Source(String source) {
        requireNonNull(source);
        checkArgument(isValidSource(source), MESSAGE_CONSTRAINTS);
        value = source;
    }

    /**
     * Constructs an empty {@code Source}.
     */
    private Source() {
        value = DEFAULT_SOURCE_STRING;
    }

    /**
     * Returns true if a given string matches the default source string.
     */
    public static boolean isDefaultSource(String test) {
        return test.equals(DEFAULT_SOURCE_STRING);
    }

    /**
     * Returns true if a given {@code Source} is the default source.
     */
    public static boolean isDefaultSource(Source test) {
        return test == DEFAULT_SOURCE;
    }

    /**
     * Returns true if a given string is a valid source.
     */
    public static boolean isValidSource(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Source // instanceof handles nulls
                && value.equals(((Source) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public int compareTo(Source o) {
        if (this == o) {
            return 0;
        }
        return this.value.compareTo(o.value);
    }
}
