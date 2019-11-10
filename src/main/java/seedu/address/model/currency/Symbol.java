package seedu.address.model.currency;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Generic abstraction of currency symbol.
 */
public class Symbol {
    public static final String MESSAGE_CONSTRAINTS = "Symbol can take any integer between 1 to 7."
            + " Alternatively, it can contain a non-numerical string with no more than 3 characters";

    public static final String VALIDATION_REGEX = "^[1-7]";

    public static final String VALIDATION_REGEX_STRING = "([^\\w0-9]|[A-Za-z]){1,3}";

    public final String sign;

    /**
     * Constructs an {@code Symbol}.
     *
     * @param value A valid symbol.
     */
    public Symbol(String value) {
        requireNonNull(value);
        try {
            Integer.parseInt(value);
        } catch (NumberFormatException e) {
            checkArgument(isValidSymbol(value), MESSAGE_CONSTRAINTS);
            this.sign = value;
            return;
        }
        checkArgument(isValidIndex(value), MESSAGE_CONSTRAINTS);
        this.sign = getSign(Integer.parseInt(value));
    }

    /**
     * Returns true if a given string is a valid symbol.
     */
    public static boolean isValidIndex(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string is a valid symbol.
     */
    public static boolean isValidSymbol(String test) {
        return test.matches(VALIDATION_REGEX_STRING);
    }

    private String getSign(int index) {
        String symbol;
        switch (index) {
        case 1:
            symbol = "$";
            break;
        case 2:
            symbol = "£";
            break;
        case 3:
            symbol = "¥";
            break;
        case 4:
            symbol = "€";
            break;
        case 5:
            symbol = "฿";
            break;
        case 6:
            symbol = "₹";
            break;
        case 7:
            symbol = "₩";
            break;
        default:
            symbol = this.sign;
        }
        return symbol;
    }

    @Override
    public String toString() {
        return sign;
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Symbol // instanceof handles nulls
                && sign.equals(((Symbol) other).sign)); // state check
    }

    @Override
    public int hashCode() {
        return sign.hashCode();
    }

}
