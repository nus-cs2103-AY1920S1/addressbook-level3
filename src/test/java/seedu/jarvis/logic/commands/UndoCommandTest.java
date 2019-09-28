package seedu.jarvis.logic.commands;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.jarvis.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.jarvis.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.jarvis.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.jarvis.testutil.Assert.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import seedu.jarvis.logic.commands.address.AddAddressCommand;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.logic.version.VersionControl;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.ModelManager;
import seedu.jarvis.model.person.Person;
import seedu.jarvis.testutil.PersonBuilder;

/**
 * Tests the behaviour of undo command.
 */
public class UndoCommandTest {
    private Model model;
    private Model expectedModel;

    /**
     * Resets version control and model and expected model before each test.
     */
    @BeforeEach
    public void setUpBefore() {
        VersionControl.INSTANCE.hardReset();
        model = new ModelManager();
        expectedModel = new ModelManager();
    }

    /**
     * Resets version control and model and expected model after each test.
     */
    @AfterEach
    public void setUpAfter() {
        VersionControl.INSTANCE.hardReset();
        model = new ModelManager();
        expectedModel = new ModelManager();
    }

    /**
     * Verifies that undo command has no inverse.
     */
    @Test
    public void test_hasInverseExecution() {
        UndoCommand undoCommand = new UndoCommand();
        assertFalse(undoCommand::hasInverseExecution);
    }

    /**
     * Verifies that if there are no commands available to undo, the correct exception is thrown.
     */
    @Test
    public void execute_noCommand_exceptionThrown() {
        UndoCommand undoCommand = new UndoCommand();
        assertThrows(CommandException.class, UndoCommand.MESSAGE_NO_COMMAND_TO_UNDO, () -> undoCommand.execute(model));
    }

    /**
     * Verifies that the operation of executing a undo command for one undo is correct.
     */
    @Test
    public void execute_oneUndo_success() {
        Person person = new PersonBuilder().build();
        AddAddressCommand addAddressCommand = new AddAddressCommand(person);
        int numberOfCommandsToUndo = 1;

        expectedModel.addPerson(person);

        assertCommandSuccess(addAddressCommand, model, String.format(AddAddressCommand.MESSAGE_SUCCESS, person),
                expectedModel);
        // updates version control for undo functionality.
        assertDoesNotThrow(() -> VersionControl.INSTANCE.addExecutedCommand(addAddressCommand));

        expectedModel.deletePerson(person);

        String expectedMessage = String.format(AddAddressCommand.MESSAGE_INVERSE_SUCCESS_DELETE, person) + "\n"
                + String.format(UndoCommand.MESSAGE_SUCCESS, numberOfCommandsToUndo);
        assertCommandSuccess(new UndoCommand(), model, expectedMessage, expectedModel);
    }

    /**
     * Verifies that undo command for multiple commands works.
     */
    @Test
    public void execute_multipleUndo_success() {
        Person amy = new PersonBuilder().withName(VALID_NAME_AMY).build();
        Person bob = new PersonBuilder().withName(VALID_NAME_BOB).build();
        AddAddressCommand addAddressCommand1 = new AddAddressCommand(amy);
        AddAddressCommand addAddressCommand2 = new AddAddressCommand(bob);
        int numberOfCommandsToUndo = 2;

        expectedModel.addPerson(amy);
        assertCommandSuccess(addAddressCommand1, model, String.format(AddAddressCommand.MESSAGE_SUCCESS, amy),
                expectedModel);
        assertDoesNotThrow(() -> VersionControl.INSTANCE.addExecutedCommand(addAddressCommand1));

        expectedModel.addPerson(bob);
        assertCommandSuccess(addAddressCommand2, model, String.format(AddAddressCommand.MESSAGE_SUCCESS, bob),
                expectedModel);
        // updates version control for undo functionality.
        assertDoesNotThrow(() -> VersionControl.INSTANCE.addExecutedCommand(addAddressCommand2));

        expectedModel.deletePerson(bob);
        expectedModel.deletePerson(amy);

        String expectedMessage = String.format(AddAddressCommand.MESSAGE_INVERSE_SUCCESS_DELETE, bob) + "\n"
                + String.format(AddAddressCommand.MESSAGE_INVERSE_SUCCESS_DELETE, amy) + "\n"
                + String.format(UndoCommand.MESSAGE_SUCCESS, numberOfCommandsToUndo);

        assertCommandSuccess(new UndoCommand(numberOfCommandsToUndo), model, expectedMessage, expectedModel);
    }

    /**
     * Verifies that if undo command is given more commands to undo than possible, it will just undo all the commands
     * and return the correct command result.
     */
    @Test
    public void execute_exceedNumber_success() {
        Person person = new PersonBuilder().build();
        AddAddressCommand addAddressCommand = new AddAddressCommand(person);
        int numberOfCommandsToUndo = 1;

        expectedModel.addPerson(person);
        assertCommandSuccess(addAddressCommand, model, String.format(AddAddressCommand.MESSAGE_SUCCESS, person),
                expectedModel);
        // updates version control for undo functionality.
        assertDoesNotThrow(() -> VersionControl.INSTANCE.addExecutedCommand(addAddressCommand));

        // undo command given more commands to undo than available.
        UndoCommand undoCommand = new UndoCommand(numberOfCommandsToUndo * 100);

        String expectedMessage = String.format(AddAddressCommand.MESSAGE_INVERSE_SUCCESS_DELETE, person) + "\n"
                + String.format(UndoCommand.MESSAGE_SUCCESS, numberOfCommandsToUndo);

        expectedModel.deletePerson(person);
        assertCommandSuccess(undoCommand, model, expectedMessage, expectedModel);
    }
}
