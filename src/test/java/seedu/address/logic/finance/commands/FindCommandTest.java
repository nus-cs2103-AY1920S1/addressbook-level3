package seedu.address.logic.finance.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static seedu.address.testutil.TypicalLogEntries.getTypicalFinanceLog;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.finance.Model;
import seedu.address.model.finance.ModelFinanceManager;
import seedu.address.model.finance.UserPrefs;
import seedu.address.model.finance.logentry.LogEntryContainsCategoriesPredicate;
import seedu.address.model.finance.logentry.LogEntryContainsKeywordsPredicate;
import seedu.address.model.finance.logentry.LogEntryMatchesLogEntryTypesPredicate;

/**
 * Contains integration tests (interaction with the CalendarModel) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model financeModel = new ModelFinanceManager(getTypicalFinanceLog(), new UserPrefs());
    private Model expectedModel = new ModelFinanceManager(getTypicalFinanceLog(), new UserPrefs());

    @Test
    public void equals() {
        LogEntryMatchesLogEntryTypesPredicate firstEntryTypePredicate =
                new LogEntryMatchesLogEntryTypesPredicate(Collections.singletonList("spend"));
        LogEntryMatchesLogEntryTypesPredicate secondEntryTypePredicate =
                new LogEntryMatchesLogEntryTypesPredicate(Collections.singletonList("income"));

        LogEntryContainsKeywordsPredicate firstKeywordPredicate =
                new LogEntryContainsKeywordsPredicate(Collections.singletonList("first"));
        LogEntryContainsKeywordsPredicate secondKeywordPredicate =
                new LogEntryContainsKeywordsPredicate(Collections.singletonList("second"));

        LogEntryContainsCategoriesPredicate firstCatPredicate =
                new LogEntryContainsCategoriesPredicate(Collections.singletonList("firstCat"));
        LogEntryContainsCategoriesPredicate secondCatPredicate =
                new LogEntryContainsCategoriesPredicate(Collections.singletonList("secondCat"));

        FindCommand findFirstCommand =
                new FindCommand(firstEntryTypePredicate,
                        firstKeywordPredicate, firstCatPredicate);
        FindCommand findSecondCommand =
                new FindCommand(secondEntryTypePredicate,
                        secondKeywordPredicate, secondCatPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy =
                new FindCommand(firstEntryTypePredicate,
                firstKeywordPredicate, firstCatPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different find commands -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    /**
     * Parses {@code userInput} into a {@code LogEntryMatchesLogEntryTypesPredicate}.
     */
    private LogEntryMatchesLogEntryTypesPredicate prepareEntryTypesPredicate(String userInput) {
        return new LogEntryMatchesLogEntryTypesPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a {@code LogEntryContainsKeywordsPredicate}.
     */
    private LogEntryContainsKeywordsPredicate prepareKeywordPredicate(String userInput) {
        return new LogEntryContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a {@code LogEntryContainsCategoriesPredicate}.
     */
    private LogEntryContainsCategoriesPredicate prepareCategoriesPredicate(String userInput) {
        return new LogEntryContainsCategoriesPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
