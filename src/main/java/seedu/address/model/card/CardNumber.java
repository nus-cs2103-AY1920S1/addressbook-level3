package seedu.address.model.card;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Cards's number in the card book.
 * Guarantees: immutable; is valid as declared in {@link #isValidCardNumber(String)}
 */
public class CardNumber {

    public static final String MESSAGE_CONSTRAINTS =
            "Card numbers should only contain 16 numeric characters, and it should not be blank";

    public static final String VALIDATION_REGEX = "5[1-5]\\ d{14}";

    public final String value;

    /**
     * Constructs a {@code CardNumber}.
     *
     * @param cardNumber A valid card number.
     */
    public CardNumber(String cardNumber) {
        requireNonNull(cardNumber);
        checkArgument(isValidCardNumber(cardNumber), MESSAGE_CONSTRAINTS);
        this.value = cardNumber;
    }

    /**
     * Returns true if a given string is a valid card number.
     */
    public static boolean isValidCardNumber(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CardNumber // instanceof handles nulls
                && value.equals(((CardNumber) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
