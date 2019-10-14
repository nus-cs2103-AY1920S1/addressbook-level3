package seedu.address.financialtracker.Model.Expense;

import static java.util.Objects.requireNonNull;

public class Country {

    public static final String MESSAGE_CONSTRAINTS =
            "Country name should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the country must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";
    private String country;

    public Country(String country) {
        requireNonNull(country);
        this.country = country;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidCountry(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return country;
    }
}
