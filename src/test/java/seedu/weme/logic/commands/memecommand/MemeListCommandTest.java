package seedu.weme.logic.commands.memecommand;

import static seedu.weme.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.weme.logic.commands.CommandTestUtil.showMemeAtIndex;
import static seedu.weme.testutil.TypicalIndexes.INDEX_FIRST_MEME;
import static seedu.weme.testutil.TypicalWeme.getTypicalWeme;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.weme.logic.commands.CommandTestUtil;
import seedu.weme.model.Model;
import seedu.weme.model.ModelManager;
import seedu.weme.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for MemeListCommand.
 */
public class MemeListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalWeme(), new UserPrefs());
        expectedModel = new ModelManager(model.getWeme(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        CommandTestUtil.assertCommandSuccess(new MemeListCommand(), model, MemeListCommand.MESSAGE_SUCCESS,
                expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showMemeAtIndex(model, INDEX_FIRST_MEME);
        assertCommandSuccess(new MemeListCommand(), model, MemeListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
