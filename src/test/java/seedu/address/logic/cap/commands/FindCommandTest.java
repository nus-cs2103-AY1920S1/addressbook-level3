package seedu.address.logic.cap.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_MODULES_LISTED_OVERVIEW;
import static seedu.address.logic.cap.commands.CommandTestUtil.assertCommandSuccess;
//import static seedu.address.testutil.TypicalModule.CARL;
//import static seedu.address.testutil.TypicalModule.ELLE;
import static seedu.address.testutil.TypicalModule.getTypicalCapLog;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.cap.CapUserPrefs;
import seedu.address.model.cap.Model;
import seedu.address.model.cap.ModelCapManager;
import seedu.address.model.cap.module.NameContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelCapManager(getTypicalCapLog(), new CapUserPrefs());
    private Model expectedModel = new ModelCapManager(getTypicalCapLog(), new CapUserPrefs());

    @Test
    public void equals() {
        NameContainsKeywordsPredicate firstPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        NameContainsKeywordsPredicate secondPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("second"));

        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noModuleFound() {
        String expectedMessage = String.format(MESSAGE_MODULES_LISTED_OVERVIEW, 0);
        NameContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredModuleList(predicate);
        assertCommandSuccess(command, model, expectedMessage, model);
        assertEquals(Collections.emptyList(), model.getFilteredModuleList());
    }

    //    @Test
    //    public void execute_multipleKeywords_multipleModuleFound() {
    //        String expectedMessage = String.format(MESSAGE_MODULES_LISTED_OVERVIEW, 3);
    //        NameContainsKeywordsPredicate predicate = preparePredicate("CS2103 CS2100 CS2101");
    //        FindCommand command = new FindCommand(predicate);
    //        expectedModel.updateFilteredModuleList(predicate);
    //        assertCommandSuccess(command, model, expectedMessage, model);
    //        assertEquals(Arrays.asList(CARL, ELLE), model.getFilteredModuleList());
    //    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
