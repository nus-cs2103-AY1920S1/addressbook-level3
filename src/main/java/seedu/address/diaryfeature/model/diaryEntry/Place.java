package seedu.address.diaryfeature.model.diaryEntry;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;


public class Place {


    public static final String MESSAGE_CONSTRAINTS = "" +
            "Place, while optional, if input, " +
            "can't be the empty string, can't only be spaces ";

    public final String place;

    /**
     * Constructs an {@code Address}.
     *
     * @param input is a valid place.
     */
    public Place(String input)  {
        place = input;
    }


    @Override
    public String toString() {
        return place;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Place // instanceof handles nulls
                && place.equals(((Place) other).place)); // state check
    }

    @Override
    public int hashCode() {
        return place.hashCode();
    }

}

