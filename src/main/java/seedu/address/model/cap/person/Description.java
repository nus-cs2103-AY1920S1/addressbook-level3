package seedu.address.model.cap.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Description of a module in the CAP log.
 * Guarantees: immutable.
 */
public class Description {

    private final String description;
    /**
     * Constructs a {@code Phone}.
     *
     * @param description A valid phone number.
     */
    public Description(String description) {
        requireNonNull(description);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Description// instanceof handles nulls
                && description.equals(((Description) other).description)); // state check
    }

    @Override
    public String toString() {
        return description;
    }
}
