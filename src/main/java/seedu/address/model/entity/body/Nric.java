package seedu.address.model.entity.body;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Objects;
import java.util.regex.Pattern;

//@@author ambervoong
/**
 * Represents a National Registration Identity Card number in Mortago.
 * Guarantees: immutable; is valid as declared in {@link #isValidNric(String)}
 */
public class Nric {

    private static final String VALID_NRIC = "A valid NRIC number has 9 digits in total, with a letter at the start and"
            + " end. Singaporeans either have the starting alphabets 'S' or 'T' while foreigners have the alphabets "
            + "F' or 'G'. The ending character of each NRIC number can be any alphabet.";

    private final String nric;

    public Nric(String nric) {
        requireNonNull(nric);
        checkArgument(isValidNric(nric), VALID_NRIC);
        this.nric = nric;
    }

    public String getNric() {
        return nric;
    }

    /**
     * Returns if an object is equal to this NRIC. Two Nric objects are equal
     * if their nric Strings are equal. A null object is not considered equal.
     * @param o Any object.
     * @return a boolean representing the equality of this and o.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Nric nric1 = (Nric) o;
        return nric.equals(nric1.getNric());
    }

    @Override
    public int hashCode() {
        return Objects.hash(nric);
    }

    /**
     * Checks if a given string is a valid NRIC number. A valid NRIC number has 9 digits in total, with a letter at the
     * start and end. Singaporeans either have the starting alphabets 'S' or 'T' while foreigners have the alphabets
     * 'F' or 'G'. The ending character of each NRIC number can be any alphabet.
     * @param nric String that represents a given NRIC number.
     * @return if the string is a valid NRIC number.
     */
    public static boolean isValidNric(String nric) {
        if (nric.length() != 9) {
            return false;
        }

        return isValidStartEndAlphabet(nric.charAt(0), nric.charAt(nric.length() - 1))
                && isDigitAndLengthSeven(nric.substring(1, nric.length() - 1));
    }

    /**
     * Checks if the given string only contains digits and is of length seven.
     * @param nricDigits
     * @return
     */
    public static boolean isDigitAndLengthSeven(String nricDigits) {
        boolean isLengthSeven = nricDigits.length() == 7;

        String regex = "\\d+";
        Pattern pattern = Pattern.compile(regex);
        boolean isDigits = (pattern.matcher(nricDigits).matches());

        return isLengthSeven && isDigits;
    }

    /**
     * Checks if the given character is a valid starting alphabet for a Singaporean NRIC and if the given endCharacter
     * is a letter. Singaporeans either have the starting alphabets 'S' or 'T' while foreigners
     * have the alphabets 'F' or 'G'.
     * @param character First character of a given NRIC String.
     * @param endCharacter Last character of a given NRIC String.
     * @return if the first char is a valid alphabet or not.
     */
    public static boolean isValidStartEndAlphabet(char character, char endCharacter) {
        boolean isLetter = Character.isLetter(endCharacter);

        switch (character) {
        case 'S': // Fallthrough
        case 'T': // Fallthrough
        case 'F': // Fallthrough
        case 'G': // Fallthrough
            return isLetter;
        default:
            return false;
        }
    }
}
