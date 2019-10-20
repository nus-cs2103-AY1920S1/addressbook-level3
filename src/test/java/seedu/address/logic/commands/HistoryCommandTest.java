package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertHistoryCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.logic.commands.CommandTestUtil.showPolicyAtIndex;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_POLICY;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class HistoryCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertHistoryCommandSuccess(new HistoryCommand(), model, HistoryCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        showPolicyAtIndex(model, INDEX_FIRST_POLICY);
        showPersonAtIndex(expectedModel, INDEX_FIRST_PERSON);
        showPolicyAtIndex(expectedModel, INDEX_FIRST_POLICY);
        assertHistoryCommandSuccess(new HistoryCommand(), model, HistoryCommand.MESSAGE_SUCCESS, expectedModel);
        assertHistoryCommandSuccess(new HistoryCommand(), model, HistoryCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
