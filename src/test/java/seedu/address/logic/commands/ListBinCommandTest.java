package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertListBinCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showBinItemAtIndex;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListBinCommand.
 */
public class ListBinCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertListBinCommandSuccess(new ListBinCommand(),
            model,
            ListBinCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showBinItemAtIndex(model, INDEX_FIRST_PERSON);
        assertListBinCommandSuccess(new ListBinCommand(),
            model,
            ListBinCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
