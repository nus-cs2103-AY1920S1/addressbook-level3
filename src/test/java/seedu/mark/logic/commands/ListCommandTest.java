package seedu.mark.logic.commands;

import static seedu.mark.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.mark.logic.commands.CommandTestUtil.showBookmarkAtIndex;
import static seedu.mark.testutil.TypicalBookmarks.getTypicalMark;
import static seedu.mark.testutil.TypicalIndexes.INDEX_FIRST_BOOKMARK;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.mark.model.Model;
import seedu.mark.model.ModelManager;
import seedu.mark.model.UserPrefs;
import seedu.mark.storage.StorageStub;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalMark(), new UserPrefs());
        expectedModel = new ModelManager(model.getMark(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, new StorageStub(),
                ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showBookmarkAtIndex(model, INDEX_FIRST_BOOKMARK);
        assertCommandSuccess(new ListCommand(), model, new StorageStub(),
                ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
