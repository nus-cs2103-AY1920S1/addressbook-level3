package seedu.address.model.password.analyser.result;

import java.util.Objects;

import seedu.address.model.password.Password;

/**
 * Represents a {@code Result} produced from an {@code StrengthAnalyser}.
 */
public class StrengthResult extends Result {

    private static final String MESSAGE_RECOMMEND_LENGTH = "[-] Try to have at least 8 character for password.\n";
    private static final String MESSAGE_RECOMMEND_LOWER_AND_UPPER = "[-] Try to include a mix of "
            + "both upper and lower case letters.\n";
    private static final String MESSAGE_RECOMMEND_NUM = "[-] Try to include numerals.\n";
    private static final String MESSAGE_RECOMMEND_SPECIAL = "[-] Try to include special characters.\n";
    private static final String MESSAGE_STRONG_PASSWORD = "Password is strong.\n";

    private boolean hasMinimumLength = false;
    private boolean hasLower = false;
    private boolean hasUpper = false;
    private boolean hasNum = false;
    private boolean hasSpecial = false;

    public StrengthResult(Password password, ResultOutcome description, boolean hasMinimumLength,
                          boolean hasLower, boolean hasUpper, boolean hasNum, boolean hasSpecial) {
        super(password, description);
        this.hasMinimumLength = hasMinimumLength;
        this.hasLower = hasLower;
        this.hasUpper = hasUpper;
        this.hasNum = hasNum;
        this.hasSpecial = hasSpecial;
    }

    @Override
    public String getGreaterDetail() {
        StringBuilder report = new StringBuilder("Result : " + description + "\n");
        if (!hasMinimumLength) {
            report.append(MESSAGE_RECOMMEND_LENGTH);
        }
        if (!hasLower || !hasUpper) {
            report.append(MESSAGE_RECOMMEND_LOWER_AND_UPPER);
        }
        if (!hasNum) {
            report.append(MESSAGE_RECOMMEND_NUM);
        }
        if (!hasSpecial) {
            report.append(MESSAGE_RECOMMEND_SPECIAL);
        }
        if (hasMinimumLength && hasLower && hasUpper && hasNum && hasSpecial) {
            report.append(MESSAGE_STRONG_PASSWORD);
        }
        return report.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        StrengthResult that = (StrengthResult) o;
        return hasMinimumLength == that.hasMinimumLength
                && hasLower == that.hasLower
                && hasUpper == that.hasUpper
                && hasNum == that.hasNum
                && hasSpecial == that.hasSpecial
                && this.description.equals(that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hasMinimumLength, hasLower, hasUpper, hasNum, hasSpecial);
    }

}
