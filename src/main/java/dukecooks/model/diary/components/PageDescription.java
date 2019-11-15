package dukecooks.model.diary.components;

import static java.util.Objects.requireNonNull;

import dukecooks.commons.util.AppUtil;


/**
 * Represents a Page's description.
 * Guarantees: immutable; is valid as declared in {@link #isValidPageDescription(String)}
 */
public class PageDescription {

    public static final String MESSAGE_CONSTRAINTS = "Page description should not be empty and it should be within "
            + "500 characters";

    /*
     * The page description must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String fullPageDescription;

    /**
     * Constructs a {@code PageDescription}.
     *
     * @param pageDescription A valid page description.
     */
    public PageDescription(String pageDescription) {
        requireNonNull(pageDescription);
        AppUtil.checkArgument(isValidPageDescription(pageDescription), MESSAGE_CONSTRAINTS);
        fullPageDescription = pageDescription;
    }

    /**
     * Returns true if a given string is a valid page description.
     */
    public static boolean isValidPageDescription(String test) {
        return test.matches(VALIDATION_REGEX) && test.length() <= 500;
    }


    @Override
    public String toString() {
        return fullPageDescription;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PageDescription // instanceof handles nulls
                && fullPageDescription.equals(((PageDescription) other).fullPageDescription)); // state check
    }

    @Override
    public int hashCode() {
        return fullPageDescription.hashCode();
    }

}
