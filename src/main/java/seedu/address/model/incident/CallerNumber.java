package seedu.address.model.incident;

import seedu.address.model.person.Phone;

/**
 * Represents a Person's CallerNumber in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class CallerNumber extends Phone {

    public static final String MESSAGE_CONSTRAINTS =
            "Caller number should be of length 8, only contain numerical digits and should not be blank";

    private static final int CALLER_NUMBER_LENGTH = 8;

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

    /**
     * Checks if {@code caller} is a valid {@code CallerNumber}.
     */
    public static boolean isValidCaller(String caller) {
        boolean correctLength = caller.length() == CALLER_NUMBER_LENGTH;
        boolean isNumber;
        try {
            Integer.parseInt(caller);
            isNumber = true;
        } catch (NumberFormatException e) {
            isNumber = false;
        }
        return correctLength & isNumber;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CallerNumber)) {
            return false;
        }

        // state check
        CallerNumber e = (CallerNumber) other;

        return e.value.equals(this.value);
    }
}

