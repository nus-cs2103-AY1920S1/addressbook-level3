package seedu.weme.logic.commands;

import static seedu.weme.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.weme.logic.commands.CommandTestUtil.showMemeAtIndex;
import static seedu.weme.testutil.TypicalIndexes.INDEX_FIRST_MEME;
import static seedu.weme.testutil.TypicalMemes.getTypicalMemeBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        model = new ModelManager(getTypicalMemeBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getMemeBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new MemeListCommand(), model, MemeListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showMemeAtIndex(model, INDEX_FIRST_MEME);
        assertCommandSuccess(new MemeListCommand(), model, MemeListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
