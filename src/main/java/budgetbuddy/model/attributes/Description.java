package budgetbuddy.model.attributes;

import static budgetbuddy.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

/**
 * A stub class to represent a description of an entity.
 */
public class Description implements Comparable<Description> {

    public static final int MAX_LENGTH = 180;

    public static final String MESSAGE_CONSTRAINTS =
            "Description should be no more than " + MAX_LENGTH + " characters.";

    private String description;

    public Description(String description) {
        requireNonNull(description);
        checkArgument(isValidDescription(description), MESSAGE_CONSTRAINTS);
        this.description = description;
    }

    /**
     * Returns true if the given string description is no more than {@link #MAX_LENGTH} characters.
     */
    public static boolean isValidDescription(String description) {
        requireNonNull(description);
        return description.length() <= MAX_LENGTH;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Returns true if the test description is contained within the description.
     */
    public boolean contains(Description test) {
        return description.contains(test.description);
    }
    @Override
    public int compareTo(Description other) {
        return description.compareTo(other.description);
    }

    @Override
    public String toString() {
        return description;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Description
                    && description.equals(((Description) other).description));
    }

    @Override
    public int hashCode() {
        return description.hashCode();
    }
}
