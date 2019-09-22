package seedu.tarence.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.tarence.commons.util.AppUtil.checkArgument;

public class MatricNum {

    public static final String MESSAGE_CONSTRAINTS =
            "Matriculation number should be an A, followed by 7 numbers, followed by a letter.";

    /*
     * Matriculation number is an "A", followed by exactly 7 numbers, followed by a letter.
     */
    public static final String VALIDATION_REGEX = "[aZ][0-9]{7}[a-zA-Z]";

    public final String matricNum;

    /**
     * Constructs a {@code MatricNum}.
     *
     * @param matricNum A matriculation number.
     */
    public MatricNum(String matricNum) {
        requireNonNull(matricNum);
        checkArgument(isValidMatricNum(matricNum), MESSAGE_CONSTRAINTS);
        this.matricNum = matricNum;
    }

    /**
     * Returns true if a given string is a valid matriculation number.
     */
    public static boolean isValidMatricNum(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return matricNum;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MatricNum // instanceof handles nulls
                && matricNum.equals(((MatricNum) other).matricNum)); // state check
    }

    @Override
    public int hashCode() {
        return matricNum.hashCode();
    }

}
