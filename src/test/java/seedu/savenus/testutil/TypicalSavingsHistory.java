package seedu.savenus.testutil;

import seedu.savenus.model.savings.Savings;
import seedu.savenus.model.savings.SavingsHistory;

//@@author fatclarence

/**
 * A utility class containing a list of {@code Savings} objects to be used in tests.
 */
public class TypicalSavingsHistory {
    private static final String VALID_TIMESTAMP_1 = "1570680000000";
    private static final String VALID_TIMESTAMP_2 = "1570976664361";

    private TypicalSavingsHistory() {} // prevents instantiation

    public static SavingsHistory getTypicalSavingsHistory() {
        SavingsHistory savingsHistory = new SavingsHistory();
        savingsHistory.addToHistory(new Savings("50.00", VALID_TIMESTAMP_1, false));
        savingsHistory.addToHistory(new Savings("25.00", VALID_TIMESTAMP_2, true));
        return savingsHistory;
    }
}
