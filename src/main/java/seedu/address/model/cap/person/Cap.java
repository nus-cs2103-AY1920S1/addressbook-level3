package seedu.address.model.cap.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's phone number in the address book.
 * Guarantees: immutable.
 */
public class Cap {

    private final double cap;
    /**
     * Constructs a {@code Phone}.
     *
     * @param credit A valid phone number.
     */
    public Cap(double credit) {
        requireNonNull(credit);
        this.cap = credit;
    }

    public double getCap() {
        return cap;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Cap // instanceof handles nulls
                && this.cap == cap); // state check
    }

    @Override
    public String toString() {
        return String.valueOf(cap);
    }
}
