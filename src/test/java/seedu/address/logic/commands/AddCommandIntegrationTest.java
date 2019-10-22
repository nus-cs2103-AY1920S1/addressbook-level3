package seedu.address.logic.commands;

import static seedu.address.logic.commands.AddCommand.MESSAGE_UNDO_SUCCESS;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.UndoableCommand.MESSAGE_NOT_EXECUTED_BEFORE;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalUndoableCommands.TYPICAL_ADD_COMMAND;
import static seedu.address.testutil.TypicalUndoableCommands.TYPICAL_BODY;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model and UndoCommand) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() {
        Person validPerson = new PersonBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addEntity(validPerson);

        assertCommandSuccess(new AddCommand(validPerson), model,
                String.format(AddCommand.MESSAGE_SUCCESS, validPerson), expectedModel);
    }

    //@@author ambervoong
    @Test
    public void undo_previouslyExecuted_success() throws CommandException {
        UndoableCommand addCommand = TYPICAL_ADD_COMMAND;
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        addCommand.execute(model);

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.addExecutedCommand(addCommand);

        UndoCommand undoCommand = new UndoCommand();

        String expectedMessage = String.format(MESSAGE_UNDO_SUCCESS, TYPICAL_BODY);
        assertCommandSuccess(undoCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void undo_notExecutedBefore_undoFailureException() {
        UndoableCommand addCommand = TYPICAL_ADD_COMMAND;

        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        assertThrows(CommandException.class, MESSAGE_NOT_EXECUTED_BEFORE, () -> addCommand.undo(model));
    }

    //@@author

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person personInList = model.getAddressBook().getPersonList().get(0);
        assertCommandFailure(new AddCommand(personInList), model, AddCommand.MESSAGE_DUPLICATE_ENTITY);
    }

}
