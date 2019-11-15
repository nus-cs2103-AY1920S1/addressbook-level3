package seedu.address.diaryfeature.model.diaryEntry;

/**
 * Place
 */
public class Place {

    public static final String MESSAGE_CONSTRAINTS = ""
            + "Place, while optional, if input, "
            + "can't be the empty string, can't only be spaces ";

    public static final int PLACE_MAX_LENGTH = 100;

    public final String place;

    /**
     * Constructs an {@code Place}.
     *
     * @param input is a valid place.
     */
    public Place(String input) {
        place = input;
    }

    /**
     * To defend against any malfunctions
     * @return a copy of this place
     */
    public Place copy () {
        return new Place(place);
    }


    @Override
    public String toString() {
        return place;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Place // instanceof handles nulls
                && place.equalsIgnoreCase(((Place) other).place)); // state check
    }

    @Override
    public int hashCode() {
        return place.hashCode();
    }

}

