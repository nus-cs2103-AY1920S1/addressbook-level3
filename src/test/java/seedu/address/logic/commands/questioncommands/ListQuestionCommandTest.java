package seedu.address.logic.commands.questioncommands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showQuestionAtIndex;
import static seedu.address.testutil.TypicalAppData.getTypicalAppData;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListQuestionCommand.
 */
public class ListQuestionCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAppData(), new UserPrefs());
        expectedModel = new ModelManager(model.getAppData(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListQuestionCommand(), model, ListQuestionCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showQuestionAtIndex(model, INDEX_FIRST);
        assertCommandSuccess(new ListQuestionCommand(), model, ListQuestionCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
