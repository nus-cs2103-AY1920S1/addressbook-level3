package seedu.address.model.password.analyser;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.password.Password;
import seedu.address.model.password.analyser.result.Result;
import seedu.address.model.password.analyser.result.ResultOutcome;
import seedu.address.model.password.analyser.result.StrengthResult;

/**
 * Represents an {@code Analyser} that analyses passwords in password book for password strength.
 */
public class StrengthAnalyser implements Analyser {
    private static final String MESSAGE_HEADER = "Analysing passwords for strength: \n";

    @Override
    public List<Result> analyse(List<Password> accountList) {
        ArrayList<Result> results = new ArrayList<>();
        for (Password p : accountList) {
            StrengthResult o = calculateStrength(p);
            results.add(o);
        }
        return results;
    }

    /**
     * Calculates the strength of a given password
     * @param passwordObject is the password
     * @return the result of the strength analysis
     */
    public static StrengthResult calculateStrength(Password passwordObject) {
        String password = passwordObject.getPasswordValue().value;
        int passwordScore = 0;
        boolean hasMinimumLength = false;
        boolean hasLower = false;
        boolean hasUpper = false;
        boolean hasNum = false;
        boolean hasSpecial = false;

        if (password.length() >= 8) {
            passwordScore += 1;
            hasMinimumLength = true;
        }
        //if it contains one digit
        if (password.matches("(?=.*[0-9]).*")) {
            passwordScore += 1;
            hasNum = true;
        }
        //if it contains one lower case letter
        if (password.matches("(?=.*[a-z]).*")) {
            passwordScore += 1;
            hasLower = true;
        }
        //if it contains one upper case letter
        if (password.matches("(?=.*[A-Z]).*")) {
            passwordScore += 1;
            hasUpper = true;
        }
        //if it contains one special character
        if (password.matches("(?=.*[^a-zA-Z0-9]).*")) {
            passwordScore += 1;
            hasSpecial = true;
        }
        return generateAnalysisObject(passwordObject, passwordScore, hasMinimumLength, hasLower,
                hasUpper, hasNum, hasSpecial);
    }

    /**
     * Creates the strength result object based on the arguments provided.
     *
     * @param passwordObject the password
     * @param passwordScore the calculated strength score for password
     * @param hasMinimumLength the minimum length required for strong password
     * @param hasLower the presence of lower case character
     * @param hasUpper the presence of upper case character
     * @param hasNum the presence of numerals
     * @param hasSpecial the presence of special character
     * @return
     */
    private static StrengthResult generateAnalysisObject(Password passwordObject, int passwordScore,
                                                         boolean hasMinimumLength, boolean hasLower,
                                                         boolean hasUpper, boolean hasNum, boolean hasSpecial) {
        if (passwordScore <= 2) {
            return new StrengthResult(passwordObject, ResultOutcome.WEAK, hasMinimumLength, hasLower,
                    hasUpper, hasNum, hasSpecial);
        } else if (passwordScore <= 4) {
            return new StrengthResult(passwordObject, ResultOutcome.MODERATE, hasMinimumLength, hasLower,
                    hasUpper, hasNum, hasSpecial);
        } else {
            return new StrengthResult(passwordObject, ResultOutcome.STRONG, hasMinimumLength, hasLower,
                    hasUpper, hasNum, hasSpecial);
        }
    }

    @Override
    public String getHeader() {
        return MESSAGE_HEADER;
    }
}
