package seedu.address.model.module;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The acad year of the module
 */
public class AcadYear {
    public static final String MESSAGE_CONSTRAINTS =
            "AcadYear should be in the format YYYY/YYYY, and it should not be blank";

    public static final String VALIDATION_REGEX = "(\\d{4})/(\\d{4})";

    private String acadYear;

    public AcadYear(String acadYear) {
        requireNonNull(acadYear);
        checkArgument(isValidAcadYear(acadYear), MESSAGE_CONSTRAINTS);
        this.acadYear = acadYear;
    }

    @Override
    public String toString() {
        return acadYear;
    }

    public String toStringDashed() {
        return acadYear.replace("/", "-");
    }

    /**
     * Returns true if a given string is a valid academic year.
     */
    public static boolean isValidAcadYear(String test) {
        Pattern p = Pattern.compile(VALIDATION_REGEX);
        Matcher m = p.matcher(test);
        if (m.matches()) {
            int beforeYear = Integer.parseInt(m.group(1));
            int afterYear = Integer.parseInt(m.group(2));
            return beforeYear == (afterYear - 1);
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof AcadYear)) {
            return false;
        }
        AcadYear ay = (AcadYear) other;
        if (ay == this) {
            return true;
        } else if (ay.acadYear.equals(this.acadYear)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(acadYear);
    }
}
