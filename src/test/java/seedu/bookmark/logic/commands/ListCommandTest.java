package seedu.bookmark.logic.commands;

import static seedu.bookmark.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.bookmark.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.bookmark.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.bookmark.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.bookmark.model.Model;
import seedu.bookmark.model.ModelManager;
import seedu.bookmark.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
