package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.assertUndoCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertUndoCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    @Test
    public void execute_newPerson_success() {
        Person validPerson = new PersonBuilder().build();
        AddCommand command = new AddCommand(validPerson);

        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel1 = new ModelManager(model.getAddressBook(), new UserPrefs());
        Model expectedModel2 = new ModelManager(model.getAddressBook(), new UserPrefs());
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

        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        assertUndoCommandFailure(command, model,
                String.format(AddCommand.MESSAGE_UNDO_ADD_ERROR, validPerson));

        // same object -> returns true
        assertTrue(expectedModel.equals(model));
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Person personInList = model.getAddressBook().getPersonList().get(0);
        assertCommandFailure(new AddCommand(personInList), model, AddCommand.MESSAGE_DUPLICATE_PERSON);
    }

}
