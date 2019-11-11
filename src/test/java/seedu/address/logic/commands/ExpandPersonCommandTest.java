package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertExpandPersonCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
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

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        ExpandPersonCommand expandPersonCommand = new ExpandPersonCommand(outOfBoundIndex);

        assertCommandFailure(expandPersonCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        ExpandPersonCommand expandPersonCommand = new ExpandPersonCommand(outOfBoundIndex);

        assertCommandFailure(expandPersonCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        ExpandPersonCommand expandFirstCommand = new ExpandPersonCommand(INDEX_FIRST_PERSON);
        ExpandPersonCommand expandSecondCommand = new ExpandPersonCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(expandFirstCommand.equals(expandFirstCommand));

        // same values -> returns true
        ExpandPersonCommand expandPersonCommandCopy = new ExpandPersonCommand(INDEX_FIRST_PERSON);
        assertTrue(expandFirstCommand.equals(expandPersonCommandCopy));

        // different types -> returns false
        assertFalse(expandFirstCommand.equals(1));

        // null -> returns false
        assertFalse(expandFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(expandFirstCommand.equals(expandSecondCommand));
    }
}
