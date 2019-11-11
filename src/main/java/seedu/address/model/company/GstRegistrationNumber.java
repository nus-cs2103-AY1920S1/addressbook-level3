package seedu.address.model.company;

import java.util.Objects;

/**
 * Represents company's GST Registration Number where it only applies to companies who have more than S$1 million of
 * taxable turnover.
 */
public class GstRegistrationNumber {

    /**
     * Empty Representation represents that the company doesn't have a GST Reg. No.
     * Used for users to signify that they do not have it.
     */
    public static final String EMPTY_REPRESENTATION = "-";

    public static final String MESSAGE_CONSTRAINTS =
            "GST Registration Number should only contain 8 or 9 numbers "
                    + "followed by 1 alphabet character in capitals behind. \n"
                    + "Old Gst Reg. No. should only contain 8 numbers followed by "
                    + "1 number / alphabet character in the FRONT and BEHIND.\n"
                    + "Insert `-` if your company doesn't have a GST Reg. No. \n"
                    + "For example: 200912345A | M12345678N | M123456789 | 123456789M | 1234567890 | -";

    /**
     * The first 9 characters must be numeric and the last character has to be from A to Z.
     * For example, 200912345A | M12345678N | M123456789 | 123456789M | 1234567890
     */
    public static final String VALIDATION_REGEX = "^(\\d{10})|(\\d{8}[A-Z])|(\\d{9}[A-Z])|"
            + "([A-Z]\\d{9})|([A-Z]\\d{8}[A-Z])$";

    private String gstRegistrationNumber;

    public GstRegistrationNumber(String gstRegistrationNumber) {
        this.gstRegistrationNumber = gstRegistrationNumber;
    }

    public String getGstRegistrationNumber() {
        return gstRegistrationNumber;
    }

    public void setGstRegistrationNumber(String gstRegistrationNumber) {
        this.gstRegistrationNumber = gstRegistrationNumber;
    }

    public static boolean isValidGstRegistrationNumber(String gstRegistrationNumber) {
        return gstRegistrationNumber.matches(VALIDATION_REGEX);
    }

    public static boolean isEmptyRepresentation(String gstRegistrationNumber) {
        return gstRegistrationNumber.equals(EMPTY_REPRESENTATION);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GstRegistrationNumber other = (GstRegistrationNumber) o;
        return gstRegistrationNumber.equals(other.gstRegistrationNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gstRegistrationNumber);
    }

    @Override
    public String toString() {
        //if company's gstRegNo is the newly registered, then don't add dash in between
        //e.g 200912345D
        if (gstRegistrationNumber.matches(RegistrationNumber.VALIDATION_REGEX)) {
            return gstRegistrationNumber;
        }

        //if company's is using the outdated gstRegNo, then add dash in between
        //e.g M0-1234567-8
        String firstTwoChars = gstRegistrationNumber.substring(0, 2);
        String nextSevenChars = gstRegistrationNumber.substring(2, 9);
        String lastChar = gstRegistrationNumber.substring(9);
        String gstPrintFormat = firstTwoChars + "-" + nextSevenChars + "-" + lastChar;

        return gstPrintFormat;
    }
}
