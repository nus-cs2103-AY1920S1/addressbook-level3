package seedu.address.model.cap.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's phone number in the address book.
 * Guarantees: immutable.
 */
public class Credit {

    private final int credit;
    /**
     * Constructs a {@code Phone}.
     *
     * @param credit A valid phone number.
     */
    public Credit(int credit) {
        requireNonNull(credit);
        this.credit = credit;
    }

    public int getCredit() {
        return credit;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Credit // instanceof handles nulls
                && this.credit == credit); // state check
    }

}
