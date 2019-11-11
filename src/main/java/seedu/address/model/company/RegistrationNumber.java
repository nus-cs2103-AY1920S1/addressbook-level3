package seedu.address.model.company;

import java.util.Objects;

/**
 * Represents the company's registration number which is also known as the Unique Entity Number (UEN)
 * which every companies have to register when they set up.
 */
public class RegistrationNumber {

    public static final String MESSAGE_CONSTRAINTS =
            "Company Registration Number should only contain 8 or 9 numbers followed by "
                    + "1 alphabet character in capitals behind "
                    + "and it should not be blank. \n"
                    + "For example: 200912345A";

    /**
     * The first 9 characters must be numeric and the last character has to be from A to Z.
     * For example, 200912345D
     */
    public static final String VALIDATION_REGEX = "^(\\d{8}|\\d{9})[A-Z]$";

    private String registrationNumber;

    public RegistrationNumber(String companyRegistrationNumber) {
        this.registrationNumber = companyRegistrationNumber;
    }

    public static boolean isValidRegistrationNumber(String registrationNumber) {
        return registrationNumber.matches(VALIDATION_REGEX);
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RegistrationNumber other = (RegistrationNumber) o;
        return registrationNumber.equals(other.registrationNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(registrationNumber);
    }

    @Override
    public String toString() {
        return registrationNumber;
    }
}
