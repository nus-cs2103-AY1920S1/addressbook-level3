package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

class RedoCommandTest {
    private static final String REDONE_COMMAND_TEXT = "add someone";

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Person validPerson = new PersonBuilder().build();

    @Test
    void execute_hasUndo_success() {
        model.addPerson(validPerson);

        String expectedMessage = String.format(RedoCommand.MESSAGE_SUCCESS, REDONE_COMMAND_TEXT);

        Model updatedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        updatedModel.addPerson(validPerson);
        updatedModel.notifyChange(REDONE_COMMAND_TEXT);
        updatedModel.undo();

        assertCommandSuccess(new RedoCommand(), updatedModel, expectedMessage, model);
    }

    @Test
    void execute_noUndo_throwsCommandException() {
        model.addPerson(validPerson);
        model.notifyChange(REDONE_COMMAND_TEXT);
        assertCommandFailure(new RedoCommand(), model, RedoCommand.MESSAGE_NOTHING_TO_REDO);
    }
}
