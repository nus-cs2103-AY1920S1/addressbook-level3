package seedu.address.financialtracker.model.expense;

import static java.util.Objects.requireNonNull;

/**
 * An expense associated country.
 */
public class Country {

    public static final String MESSAGE_CONSTRAINTS =
            "Country name should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the country must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";
    public final String value;

    public Country(String country) {
        requireNonNull(country);
        this.value = country;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidCountry(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return value;
    }
}
