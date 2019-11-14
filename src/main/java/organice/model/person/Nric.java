package organice.model.person;

import static java.util.Objects.requireNonNull;
import static organice.commons.util.AppUtil.checkArgument;

import java.util.AbstractMap;
import java.util.Map;

/**
 * Represents a Person's NRIC in ORGANice.
 * Guarantees: immutable; is valid as declared in {@link #isValidNric(String)} (String)}
 */
public class Nric {

    public static final String MESSAGE_CONSTRAINTS = "Nric must be a valid Singaporean Nric that starts with any of "
            + "'S/T/F/G', has 7 numbers afterward and ends with a valid checksum letter.";

    public static final String VALIDATION_REGEX = "^[STFG]\\d{7}[A-Z]$";

    //  If the IC starts with S or T: 0=J, 1=Z, 2=I, 3=H, 4=G, 5=F, 6=E, 7=D, 8=C, 9=B, 10=A
    private static final Map<Integer, Character> CHECKSUM_FOR_NRIC_BEGINS_WITH_S_OR_T = Map.ofEntries(
            new AbstractMap.SimpleEntry<>(0, 'J'),
            new AbstractMap.SimpleEntry<>(1, 'Z'),
            new AbstractMap.SimpleEntry<>(2, 'I'),
            new AbstractMap.SimpleEntry<>(3, 'H'),
            new AbstractMap.SimpleEntry<>(4, 'G'),
            new AbstractMap.SimpleEntry<>(5, 'F'),
            new AbstractMap.SimpleEntry<>(6, 'E'),
            new AbstractMap.SimpleEntry<>(7, 'D'),
            new AbstractMap.SimpleEntry<>(8, 'C'),
            new AbstractMap.SimpleEntry<>(9, 'B'),
            new AbstractMap.SimpleEntry<>(10, 'A'));

    //  If the IC starts with F or G: 0=X, 1=W, 2=U, 3=T, 4=R, 5=Q, 6=P, 7=N, 8=M, 9=L, 10=K
    private static final Map<Integer, Character> CHECKSUM_FOR_NRIC_BEGINS_WITH_F_OR_G = Map.ofEntries(
            new AbstractMap.SimpleEntry<>(0, 'X'),
            new AbstractMap.SimpleEntry<>(1, 'W'),
            new AbstractMap.SimpleEntry<>(2, 'U'),
            new AbstractMap.SimpleEntry<>(3, 'T'),
            new AbstractMap.SimpleEntry<>(4, 'R'),
            new AbstractMap.SimpleEntry<>(5, 'Q'),
            new AbstractMap.SimpleEntry<>(6, 'P'),
            new AbstractMap.SimpleEntry<>(7, 'N'),
            new AbstractMap.SimpleEntry<>(8, 'M'),
            new AbstractMap.SimpleEntry<>(9, 'L'),
            new AbstractMap.SimpleEntry<>(10, 'K'));

    public final String value;

    /**
     * Constructs a {@code Nric}.
     *
     * @param nric A valid nric.
     */
    public Nric(String nric) {
        requireNonNull(nric);
        checkArgument(isValidNric(nric), MESSAGE_CONSTRAINTS);
        value = nric.toUpperCase();
    }

    /**
     * Returns true if a given string is a valid Nric
     */
    public static boolean isValidNric(String test) {
        requireNonNull(test);
        if (!test.toUpperCase().matches(VALIDATION_REGEX)) {
            return false;
        }

        return test.toUpperCase().charAt(test.length() - 1) == calculateChecksumLetter(test);
    }

    // Nric checksum calculation referenced from:
    // https://ayumilovemaple.wordpress.com/2008/09/24/validation-singapore-nric-number-verification/

    /**
     * Calculates the checksum letter of a given Nric.
     */
    public static char calculateChecksumLetter(String nric) {
        requireNonNull(nric);
        checkArgument(nric.toUpperCase().matches(VALIDATION_REGEX));

        String nricCopy = nric.toUpperCase();
        char[] nricChars = nricCopy.toCharArray();

        char firstLetter = nricChars[0];
        int firstDigit = Integer.parseInt(String.valueOf(nricChars[1]));
        int secondDigit = Integer.parseInt(String.valueOf(nricChars[2]));
        int thirdDigit = Integer.parseInt(String.valueOf(nricChars[3]));
        int fourthDigit = Integer.parseInt(String.valueOf(nricChars[4]));
        int fifthDigit = Integer.parseInt(String.valueOf(nricChars[5]));
        int sixthDigit = Integer.parseInt(String.valueOf(nricChars[6]));
        int seventhDigit = Integer.parseInt(String.valueOf(nricChars[7]));

        int checksumValue = (firstDigit * 2 + secondDigit * 7 + thirdDigit * 6 + fourthDigit * 5 + fifthDigit * 4
                + sixthDigit * 3 + seventhDigit * 2 + (firstLetter == 'T' || firstLetter == 'G' ? 4 : 0)) % 11;

        char validChecksumLetter = firstLetter == 'S' || firstLetter == 'T'
                ? CHECKSUM_FOR_NRIC_BEGINS_WITH_S_OR_T.get(checksumValue)
                : CHECKSUM_FOR_NRIC_BEGINS_WITH_F_OR_G.get(checksumValue);

        return validChecksumLetter;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Nric // instanceof handles nulls
            && value.equals(((Nric) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
