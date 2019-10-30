package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertExpandPersonCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ExpandPersonCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_expandsFirstPersonInNotFilteredList() {
        Person person = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        assertExpandPersonCommandSuccess(new ExpandPersonCommand(INDEX_FIRST_PERSON),
            model,
            String.format(ExpandPersonCommand.MESSAGE_SUCCESS, person), expectedModel, person);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        showPersonAtIndex(expectedModel, INDEX_FIRST_PERSON);
        Person person = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        assertExpandPersonCommandSuccess(new ExpandPersonCommand(INDEX_FIRST_PERSON),
            model,
            String.format(ExpandPersonCommand.MESSAGE_SUCCESS, person), expectedModel, person);
    }
}
