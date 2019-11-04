package dukecooks.model.diary.components;

import static java.util.Objects.requireNonNull;

import dukecooks.commons.util.AppUtil;



/**
 * Represents a Page's type.
 * Guarantees: immutable; is valid as declared in {@link #isValidPageType(String)}
 */
public class PageType {

    public static final String MESSAGE_CONSTRAINTS = "Page type should be one of the following: health, exercise or "
            + "food";

    /*
     * The PageType must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String pageType;

    /**
     * Constructs a {@code PageType}.
     *
     * @param pageType A valid page type.
     */
    public PageType(String pageType) {
        requireNonNull(pageType);
        AppUtil.checkArgument(isValidPageType(pageType), MESSAGE_CONSTRAINTS);
        this.pageType = pageType;
    }

    /**
     * Returns true if a given string is a valid page type.
     */
    public static boolean isValidPageType(String test) {
        return test.matches(VALIDATION_REGEX)
                && (test.equals("health") || test.equals("exercise") || test.equals("food"));
    }


    @Override
    public String toString() {
        return pageType;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PageType // instanceof handles nulls
                && pageType.equals(((PageType) other).pageType)); // state check
    }

    @Override
    public int hashCode() {
        return pageType.hashCode();
    }

}
