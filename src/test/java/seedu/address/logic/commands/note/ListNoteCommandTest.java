package seedu.address.logic.commands.note;

import static seedu.address.logic.commands.CommandTestUtil.EXPECTED_LIST_RESULT;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showNoteAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_NOTE;
import static seedu.address.testutil.TypicalNotes.getTypicalNoteList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.commandresults.NoteCommandResult;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListNoteCommand.
 */
public class ListNoteCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalNoteList(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        NoteCommandResult expectedCommandResult = new NoteCommandResult(EXPECTED_LIST_RESULT);
        assertCommandSuccess(new ListNoteCommand(), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showNoteAtIndex(model, INDEX_FIRST_NOTE);
        NoteCommandResult expectedCommandResult = new NoteCommandResult(EXPECTED_LIST_RESULT);
        assertCommandSuccess(new ListNoteCommand(), model, expectedCommandResult, expectedModel);
    }
}
