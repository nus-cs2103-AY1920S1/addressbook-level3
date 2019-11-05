package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_EARNINGS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEarnings.getTypicalTutorAid;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.earnings.ClassIdContainKeywordPredicate;

public class FindEarningsCommandTest {

    private Model model = new ModelManager(getTypicalTutorAid(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalTutorAid(), new UserPrefs());

    @Test
    public void equals() {
        ClassIdContainKeywordPredicate firstPredicate =
                new ClassIdContainKeywordPredicate(Collections.singletonList("CS2100"));
        ClassIdContainKeywordPredicate secondPredicate =
                new ClassIdContainKeywordPredicate(Collections.singletonList("CS2103T"));

        FindEarningsCommand findFirstEarningsCommand = new FindEarningsCommand(firstPredicate);
        FindEarningsCommand findSecondEarningsCommand = new FindEarningsCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstEarningsCommand.equals(findFirstEarningsCommand));

        // same values -> returns true
        FindEarningsCommand findFirstEarningsCommandCopy = new FindEarningsCommand(firstPredicate);
        assertTrue(findFirstEarningsCommand.equals(findFirstEarningsCommandCopy));

        // different types -> returns false
        assertFalse(findFirstEarningsCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstEarningsCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstEarningsCommand.equals(findSecondEarningsCommand));
    }

    @Test
    public void execute_zeroKeywords_noEarningsFound() {
        String expectedMessage = String.format(MESSAGE_EARNINGS_LISTED_OVERVIEW, 0);
        ClassIdContainKeywordPredicate predicate = preparePredicate(" ");
        FindEarningsCommand command = new FindEarningsCommand(predicate);
        expectedModel.updateFilteredEarningsList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredEarningsList());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private ClassIdContainKeywordPredicate preparePredicate(String userInput) {
        return new ClassIdContainKeywordPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
