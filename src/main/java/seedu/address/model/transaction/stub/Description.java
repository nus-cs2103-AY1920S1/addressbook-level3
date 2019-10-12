package seedu.address.model.transaction.stub;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * A stub class to represent a description of a Transaction.
 */
public class Description {

    public static final String MESSAGE_CONSTRAINTS =
            "Description should not be null.";

    private String description;

    public Description(String description) {
        requireNonNull(description);
        checkArgument(isValidDescription(description), MESSAGE_CONSTRAINTS);
        this.description = description;
    }

    boolean isValidDescription(String description) {
        return description != null;
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
                || (other instanceof Description && description == ((Description) other).description);
    }

}
