package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.assertUndoCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertUndoCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAppointmentBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.QueueList;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.queue.QueueManager;
import seedu.address.model.userprefs.UserPrefs;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TestUtil;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    @Test
    public void execute_newPerson_success() {
        Person validPerson = new PersonBuilder().build();
        AddCommand command = new AddCommand(validPerson);

        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new QueueManager());
        Model expectedModel1 = new ModelManager(model.getAddressBook(), new UserPrefs(), new QueueManager());
        Model expectedModel2 = new ModelManager(model.getAddressBook(), new UserPrefs(), new QueueManager());
        expectedModel1.addPerson(validPerson);

        assertCommandSuccess(command, model,
                String.format(AddCommand.MESSAGE_SUCCESS, validPerson), expectedModel1);

        assertUndoCommandSuccess(command, model,
                String.format(AddCommand.MESSAGE_UNDO_ADD_SUCCESS, validPerson), expectedModel2);
    }

    @Test
    public void execute_undoNewPerson_throwsCommandException() {
        Person validPerson = new PersonBuilder().build();
        AddCommand command = new AddCommand(validPerson);

        Model model = TestUtil.getTypicalModelManager();
        Model expectedModel = TestUtil.getTypicalModelManager();

        assertUndoCommandFailure(command, model,
                String.format(AddCommand.MESSAGE_UNDO_ADD_ERROR, validPerson));

        // same object -> returns true
        assertTrue(expectedModel.equals(model));
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Model model = TestUtil.getTypicalModelManager();
        Person personInList = model.getAddressBook().getPersonList().get(0);
        assertCommandFailure(new AddCommand(personInList), model, AddCommand.MESSAGE_DUPLICATE_PERSON);
    }

}
