package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.View;

/**
 * Contains integration tests (interaction with the Model) and unit tests for GotoCommand.
 */
public class GotoCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getFinSec(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() throws ParseException {
        assertCommandSuccess(new GotoCommand(new View("contacts", 1)), model,
                GotoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() throws ParseException {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        assertCommandSuccess(new GotoCommand(new View("contacts", 1)), model,
                GotoCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
