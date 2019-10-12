package seedu.address.model.incident;

import seedu.address.model.person.Phone;

/**
 * Represents a Person's CallerNumber in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class CallerNumber extends Phone {

    public static final String MESSAGE_CONSTRAINTS =
            "Caller numbers should only contain alphanumeric characters and spaces, and it should not be blank";

    /**
     * Constructs a {@code CallerNumber}.
     *
     * @param phone A valid phone number.
     */
    public CallerNumber(String phone) {
        // requireNonNull(callerNumber);
        // checkArgument(isValidCallerNumber(callerNumber), MESSAGE_CONSTRAINTS);
        // this.callerNumber = callerNumber;
        super(phone);
    }
}

