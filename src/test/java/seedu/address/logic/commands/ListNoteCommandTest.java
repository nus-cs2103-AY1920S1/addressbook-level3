package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showNoteAtIndex;
import static seedu.address.testutil.TypicalAppData.getTypicalAppData;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.note.ListNoteCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListNoteCommand.
 */
class ListNoteCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    void setUp() {
        model = new ModelManager(getTypicalAppData(), new UserPrefs());
        expectedModel = new ModelManager(model.getAppData(), new UserPrefs());
    }

    @Test
    void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListNoteCommand(), model, ListNoteCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    void execute_listIsFiltered_showsEverything() {
        showNoteAtIndex(model, INDEX_FIRST);
        assertCommandSuccess(new ListNoteCommand(), model, ListNoteCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
