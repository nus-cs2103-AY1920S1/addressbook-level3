package budgetbuddy.model.attributes;

import static budgetbuddy.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

/**
 * A stub class to represent a description of an entity.
 */
public class Description {

    public static final int MAX_LENGTH = 180;

    public static final String MESSAGE_CONSTRAINTS =
            "Description should not be blank and should be no more than " + MAX_LENGTH + " characters.";

    private String description;

    public Description(String description) {
        requireNonNull(description);
        checkArgument(isValidDescription(description), MESSAGE_CONSTRAINTS);
        this.description = description;
    }

    public static boolean isValidDescription(String description) {
        return description != null
                && description.length() <= MAX_LENGTH;
    }

    public String getDescription() {
        return description;
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

}
