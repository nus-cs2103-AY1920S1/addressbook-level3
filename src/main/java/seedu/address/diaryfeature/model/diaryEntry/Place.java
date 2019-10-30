package seedu.address.diaryfeature.model.diaryEntry;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;


public class Place {


    public static final String MESSAGE_CONSTRAINTS = "Place can be any string";


    public final String value;

    /**
     * Constructs an {@code Address}.
     *
     * @param input is a valid place.
     */
    public Place(String input)  {
        requireNonNull(input);
        String place = input.trim();

        //If the length of the remaining string
        //(after the trim in parser) is 0, which means an empty string,
        //then nothing is left and
        //this is a faulty place
        if (place.equalsIgnoreCase("")) {
            Logger.getLogger("Place is empty");
        }
        value = place;
    }


    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Place // instanceof handles nulls
                && value.equals(((Place) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}

