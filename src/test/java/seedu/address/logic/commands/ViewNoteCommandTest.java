package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_MATCHING_NOTE_FOUND;
import static seedu.address.commons.core.Messages.MESSAGE_NOTES_LISTED_OVERVIEW;
import static seedu.address.commons.core.Messages.MESSAGE_NO_MATCHING_NOTE_FOUND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalNotes.PIPELINE;
import static seedu.address.testutil.TypicalNotes.POTATO;
import static seedu.address.testutil.TypicalNotes.SAMPLE;
import static seedu.address.testutil.TypicalNotes.getTypicalNoteList;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.note.TitleMatchesKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code ViewNoteCommand}.
 */
public class ViewNoteCommandTest {
    private Model model = new ModelManager(getTypicalNoteList(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalNoteList(), new UserPrefs());

    @Test
    public void equals() {
        TitleMatchesKeywordsPredicate firstPredicate = new TitleMatchesKeywordsPredicate("first");
        TitleMatchesKeywordsPredicate secondPredicate = new TitleMatchesKeywordsPredicate("second");

        ViewNoteCommand viewNoteFirstCommand = new ViewNoteCommand(firstPredicate);
        ViewNoteCommand viewNoteSecondCommand = new ViewNoteCommand(secondPredicate);

        // same object -> returns true
        assertTrue(viewNoteFirstCommand.equals(viewNoteFirstCommand));

        // same values -> returns true
        ViewNoteCommand viewNoteFirstCommandCopy = new ViewNoteCommand(firstPredicate);
        assertTrue(viewNoteFirstCommand.equals(viewNoteFirstCommandCopy));

        // different types -> returns false
        assertFalse(viewNoteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(viewNoteFirstCommand.equals(null));

        // different note -> returns false
        assertFalse(viewNoteFirstCommand.equals(viewNoteSecondCommand));
    }

    @Test
    public void execute_emptyKeywords_throwsIllegalArgumentException() {
        TitleMatchesKeywordsPredicate predicate = preparePredicate(" ");
        assertThrows(IllegalArgumentException.class,() -> model.updateFilteredNoteList(predicate));
    }

    @Test
    public void execute_partialKeywordsMatch_noNoteFound() {
        String expectedMessage = MESSAGE_NO_MATCHING_NOTE_FOUND;
        TitleMatchesKeywordsPredicate predicate = preparePredicate("pipe");
        ViewNoteCommand command = new ViewNoteCommand(predicate);
        expectedModel.updateFilteredNoteList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredNoteList());
    }

    @Test
    public void execute_excessKeywordsMatch_noNoteFound() {
        String expectedMessage = MESSAGE_NO_MATCHING_NOTE_FOUND;
        TitleMatchesKeywordsPredicate predicate = preparePredicate("sample title in excess");
        ViewNoteCommand command = new ViewNoteCommand(predicate);
        expectedModel.updateFilteredNoteList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredNoteList());
    }

    @Test
    public void execute_exactKeywordsMatchWrongLetterCase_oneNoteFound() {
        String expectedMessage = MESSAGE_MATCHING_NOTE_FOUND;
        TitleMatchesKeywordsPredicate predicate = preparePredicate("potatoes");
        ViewNoteCommand command = new ViewNoteCommand(predicate);
        expectedModel.updateFilteredNoteList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.singletonList(POTATO), model.getFilteredNoteList());
    }

    /**
     * Parses {@code userInput} into a {@code TitleMatchesKeywordsPredicate}.
     */
    private TitleMatchesKeywordsPredicate preparePredicate(String userInput) {
        return new TitleMatchesKeywordsPredicate(userInput);
    }
}
