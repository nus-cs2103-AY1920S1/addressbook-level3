package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.commons.core.Messages.MESSAGE_NOTES_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAppData.CARL;
import static seedu.address.testutil.TypicalAppData.ELLE;
import static seedu.address.testutil.TypicalAppData.FIONA;
import static seedu.address.testutil.TypicalAppData.getTypicalAppData;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.note.FindNoteCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.note.TitleContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindNoteCommand}.
 */
class FindNoteCommandTest {
    private Model model = new ModelManager(getTypicalAppData(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAppData(), new UserPrefs());

    @Test
    void equals() {
        TitleContainsKeywordsPredicate firstPredicate =
                new TitleContainsKeywordsPredicate(Collections.singletonList("first"));
        TitleContainsKeywordsPredicate secondPredicate =
                new TitleContainsKeywordsPredicate(Collections.singletonList("second"));

        FindNoteCommand findFirstCommand = new FindNoteCommand(firstPredicate);
        FindNoteCommand findSecondCommand = new FindNoteCommand(secondPredicate);

        // same object -> returns true
        assertEquals(findFirstCommand, findFirstCommand);

        // same values -> returns true
        FindNoteCommand findFirstCommandCopy = new FindNoteCommand(firstPredicate);
        assertEquals(findFirstCommand, findFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(1, findFirstCommand);

        // null -> returns false
        assertNotEquals(null, findFirstCommand);

        // different note -> returns false
        assertNotEquals(findFirstCommand, findSecondCommand);
    }

    @Test
    void execute_zeroKeywords_noNoteFound() {
        String expectedMessage = String.format(MESSAGE_NOTES_LISTED_OVERVIEW, 0);
        TitleContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindNoteCommand command = new FindNoteCommand(predicate);
        expectedModel.updateFilteredNoteList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredNoteList());
    }

    @Test
    void execute_multipleKeywords_multipleNotesFound() {
        String expectedMessage = String.format(MESSAGE_NOTES_LISTED_OVERVIEW, 3);
        TitleContainsKeywordsPredicate predicate = preparePredicate("Kurz Elle Kunz");
        FindNoteCommand command = new FindNoteCommand(predicate);
        expectedModel.updateFilteredNoteList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredNoteList());
    }

    /**
     * Parses {@code userInput} into a {@code TitleContainsKeywordsPredicate}.
     */
    private TitleContainsKeywordsPredicate preparePredicate(String userInput) {
        return new TitleContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
