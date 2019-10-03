package seedu.address.model.entity.body;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Objects;
import java.util.regex.Pattern;

//@@author ambervoong-unused
/**
 *  Represents a body's identification number in Mortago.
 */
public class BodyIdentificationNumber {

    public static final String VALID_ID = "A valid body identification number has a length of 9, "
            + "starts with 'B', which is followed by 8 digits.";
    private final String identificationNumber;

    public BodyIdentificationNumber(String identificationNumber) {
        requireNonNull(identificationNumber);
        checkArgument(isValidIdentificationNumber(identificationNumber), VALID_ID);
        this.identificationNumber = identificationNumber;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BodyIdentificationNumber that = (BodyIdentificationNumber) o;
        return getIdentificationNumber().equals(that.getIdentificationNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdentificationNumber());
    }

    /**
     * Checks if a given string is a valid body identification number. A valid body identification number has a
     * length of 9, starts with 'B', which is followed by 8 digits.
     * @param identificationNumber String that represents a given body identification number.
     * @return true if the string is a valid body identification number, false otherwise.
     */
    public static boolean isValidIdentificationNumber(String identificationNumber) {
        if (identificationNumber.length() != 9) {
            return false;
        }

        String digits = identificationNumber.substring(1);

        boolean isStartB = identificationNumber.charAt(0) == 'B';
        boolean isEightLength = digits.length() == 8;

        String regex = "\\d+";
        Pattern pattern = Pattern.compile(regex);
        boolean isDigits = (pattern.matcher(digits).matches());

        return isStartB && isEightLength && isDigits;
    }

}
