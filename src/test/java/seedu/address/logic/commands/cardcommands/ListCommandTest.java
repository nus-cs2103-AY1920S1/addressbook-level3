package seedu.address.logic.commands.cardcommands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showCardAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CARD;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    void setUp() {
        model = new ModelManager();
        expectedModel = new ModelManager();
    }

    @Test
    void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    void execute_listIsFiltered_showsEverything() {
        showCardAtIndex(model, INDEX_FIRST_CARD);
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
