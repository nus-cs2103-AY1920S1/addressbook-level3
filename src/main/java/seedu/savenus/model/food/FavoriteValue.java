package seedu.savenus.model.food;

import static java.util.Objects.requireNonNull;
import static seedu.savenus.commons.util.AppUtil.checkArgument;

/**
 * Represents a Favorite Value where it is either 1 or 0. Where 1 means that it is the favorite,
 * and 0 means that it is not.
 */
public class FavoriteValue implements Comparable<FavoriteValue> {
    public static final String MESSAGE_CONSTRAINTS = "This value can only either be \"1\" or \"0\"";
    private String value;

    /**
     * Creates a FavouriteValue holder which stores the value.
     * @param value the value to be stored.
     */
    public FavoriteValue(String value) {
        requireNonNull(value);
        checkArgument(isValidValue(value), MESSAGE_CONSTRAINTS);
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    /**
     * Checks if the value is valid. It being 1 or 0.
     * @param favoriteValue the favourite value.
     * @return true if the value is valid. False if otherwise.
     */
    public static boolean isValidValue(String favoriteValue) {
        return favoriteValue.equals("0") || favoriteValue.equals("1");
    }

    /**
     * Reverses the value which it is stored.
     * If it is 1, change to zero. Likewise vice versa.
     */
    public void reverseValue() {
        if (this.value.equals("0")) {
            this.value = "1";
        } else {
            this.value = "0";
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FavoriteValue // instanceof handles nulls
                && value.equals(((FavoriteValue) other).getValue())); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public int compareTo(FavoriteValue other) {
        if (other == null) {
            return 1;
        } else {
            return Integer.parseInt(this.value) - Integer.parseInt(other.getValue());
        }
    }
}
