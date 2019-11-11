package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_NOTES_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalNotes.getTypicalTutorAid;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.note.FindNotesCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.note.NotesContainsKeywordsPredicate;

public class FindNotesCommandTest {
    private Model model = new ModelManager(getTypicalTutorAid(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalTutorAid(), new UserPrefs());

    @Test
    public void equals() {
        NotesContainsKeywordsPredicate firstPredicate =
                new NotesContainsKeywordsPredicate(Collections.singletonList("CS2100"));
        NotesContainsKeywordsPredicate secondPredicate =
                new NotesContainsKeywordsPredicate(Collections.singletonList("CS2103T"));

        FindNotesCommand firstNotesCommand = new FindNotesCommand(firstPredicate);
        FindNotesCommand secondNotesCommand = new FindNotesCommand(secondPredicate);

        // same object -> returns true
        assertTrue(firstNotesCommand.equals(firstNotesCommand));

        // same values -> returns true
        FindNotesCommand findFirstNotesCommandCopy = new FindNotesCommand(firstPredicate);
        assertTrue(firstNotesCommand.equals(firstNotesCommand));

        // different types -> returns false
        assertFalse(firstNotesCommand.equals(1));

        // null -> returns false
        assertFalse(firstNotesCommand.equals(null));

        // different person -> returns false
        assertFalse(firstNotesCommand.equals(secondNotesCommand));
    }

    @Test
    public void execute_zeroKeywords_noNotesFound() {
        String expectedMessage = String.format(MESSAGE_NOTES_LISTED_OVERVIEW, 0);
        NotesContainsKeywordsPredicate predicate = getPredicate(" ");
        FindNotesCommand command = new FindNotesCommand(predicate);
        expectedModel.updateFilteredNotesList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredNotesList());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NotesContainsKeywordsPredicate getPredicate(String userInput) {
        return new NotesContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
