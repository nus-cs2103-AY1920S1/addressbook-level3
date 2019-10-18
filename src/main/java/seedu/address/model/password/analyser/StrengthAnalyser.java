package seedu.address.model.password.analyser;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.model.password.Password;
import seedu.address.model.password.analyser.analysis.StrengthResult;

/**
 * Represents analyser object that analyses passwords in password book for password strength.
 */
public class StrengthAnalyser implements Analyser {
    private static final String DESC_WEAK = "weak";
    private static final String DESC_MODERATE = "moderate";
    private static final String DESC_STRONG = "strong";
    private static final String MESSAGE_HEADER = "Analysing passwords for strength: \n";

    private ArrayList<StrengthResult> analysisObjects;

    @Override
    public void analyse(List<Password> accountList) {
        ArrayList<StrengthResult> analysisObjects = new ArrayList<>();
        for (Password p : accountList) {
            StrengthResult o = calculateStrength(p);
            analysisObjects.add(o);
        }
        this.analysisObjects = analysisObjects;
    }

    /**
     * Calculates the strength of a given password
     * @param passwordObject is the password
     * @return the result of the strength analysis
     */
    public static StrengthResult calculateStrength(Password passwordObject) { //TODO reccomend();
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
        if (password.matches("(?=.*[~!@#$%^&*()_-]).*")) {
            passwordScore += 1;
            hasSpecial = true;
        }
        return generateAnalysisObject(passwordObject, passwordScore, hasMinimumLength, hasLower,
                hasUpper, hasNum, hasSpecial);
    }

    /**
     * Creates the strength result object based on the arguments provided.
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
            return new StrengthResult(passwordObject, DESC_WEAK, hasMinimumLength, hasLower,
                    hasUpper, hasNum, hasSpecial);
        } else if (passwordScore <= 4) {
            return new StrengthResult(passwordObject, DESC_MODERATE, hasMinimumLength, hasLower,
                    hasUpper, hasNum, hasSpecial);
        } else {
            return new StrengthResult(passwordObject, DESC_STRONG, hasMinimumLength, hasLower,
                    hasUpper, hasNum, hasSpecial);
        }
    }

    @Override
    public String outputSummaryReport() {
        StringBuilder reportBuilder = new StringBuilder();
        reportBuilder.append(MESSAGE_HEADER);
        reportBuilder.append(MESSAGE_COLUMNS);
        for (StrengthResult o : analysisObjects) {
            reportBuilder.append(o);
        }
        return reportBuilder.toString();
    }

    @Override
    public String outputDetailedReport(Index index) {
        StringBuilder report = new StringBuilder();
        report.append(MESSAGE_INIT);
        report.append(MESSAGE_HEADER);
        StrengthResult target = analysisObjects.get(index.getZeroBased());
        report.append(target.getGreaterDetail());
        return report.toString();
    }
}
