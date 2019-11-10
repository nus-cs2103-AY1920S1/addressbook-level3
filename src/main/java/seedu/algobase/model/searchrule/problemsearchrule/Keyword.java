package seedu.algobase.model.searchrule.problemsearchrule;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.commons.util.AppUtil.checkArgument;

/**
 * Represents a Predicate's Keyword in the algobase.
 * Guarantees: immutable; is valid as declared in {@link #isValidKeyword(String)}
 */
public class Keyword {

    public static final String MESSAGE_CONSTRAINTS = "Keyword should not be blank";

    public final String keyword;

    /**
     * Constructs a {@code Keyword}.
     *
     * @param keyword the keyword in {@code String}
     */
    public Keyword(String keyword) {
        requireNonNull(keyword);
        checkArgument(isValidKeyword(keyword), MESSAGE_CONSTRAINTS);
        this.keyword = keyword;
    }

    /**
     * Returns true if a given string is a valid keyword.
     */
    public static boolean isValidKeyword(String test) {
        requireNonNull(test);
        return !test.isBlank();
    }


    @Override
    public String toString() {
        return keyword;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Keyword // instanceof handles nulls
            && keyword.equals(((Keyword) other).keyword)); // state check
    }

    @Override
    public int hashCode() {
        return keyword.hashCode();
    }
}
