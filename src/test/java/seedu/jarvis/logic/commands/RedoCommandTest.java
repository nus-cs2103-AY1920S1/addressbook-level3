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
 * Tests the behaviour of redo command.
 */
public class RedoCommandTest {
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
     * Verifies that redo command has no inverse.
     */
    @Test
    public void test_hasInverseExecution() {
        RedoCommand redoCommand = new RedoCommand();
        assertFalse(redoCommand::hasInverseExecution);
    }

    /**
     * Verifies that if there are no commands available to redo, the correct exception is thrown.
     */
    @Test
    public void execute_noCommand_exceptionThrown() {
        RedoCommand redoCommand = new RedoCommand();
        assertThrows(CommandException.class, RedoCommand.MESSAGE_NO_COMMAND_TO_REDO, () -> redoCommand.execute(model));
    }

    /**
     * Verifies that the operation of executing a redo command for one redo is correct.
     */
    @Test
    public void execute_oneRedo_success() {
        Person person = new PersonBuilder().build();
        AddAddressCommand addAddressCommand = new AddAddressCommand(person);
        int numberOfCommandsToRedo = 1;

        assertDoesNotThrow(() -> VersionControl.INSTANCE.addInverselyExecutedCommand(addAddressCommand));

        expectedModel.addPerson(person);

        String expectedMessage = String.format(AddAddressCommand.MESSAGE_SUCCESS, person) + "\n"
                + String.format(RedoCommand.MESSAGE_SUCCESS, numberOfCommandsToRedo);

        assertCommandSuccess(new RedoCommand(), model, expectedMessage, expectedModel);
    }

    /**
     * Verifies that redo command for multiple commands works.
     */
    @Test
    public void execute_multipleRedo_success() {
        Person amy = new PersonBuilder().withName(VALID_NAME_AMY).build();
        Person bob = new PersonBuilder().withName(VALID_NAME_BOB).build();
        AddAddressCommand addAddressCommand1 = new AddAddressCommand(amy);
        AddAddressCommand addAddressCommand2 = new AddAddressCommand(bob);
        int numberOfCommandsToRedo = 2;

        assertDoesNotThrow(() -> VersionControl.INSTANCE.addInverselyExecutedCommand(addAddressCommand1));
        assertDoesNotThrow(() -> VersionControl.INSTANCE.addInverselyExecutedCommand(addAddressCommand2));

        expectedModel.addPerson(bob);
        expectedModel.addPerson(amy);

        String expectedMessage = String.format(AddAddressCommand.MESSAGE_SUCCESS, bob) + "\n"
                + String.format(AddAddressCommand.MESSAGE_SUCCESS, amy) + "\n"
                + String.format(RedoCommand.MESSAGE_SUCCESS, numberOfCommandsToRedo);

        assertCommandSuccess(new RedoCommand(numberOfCommandsToRedo), model, expectedMessage, expectedModel);
    }

    /**
     * Verifies that if redo command is given more commands to redo than possible, it will just redo all the commands
     * and return the correct command result.
     */
    @Test
    public void execute_exceedNumber_success() {
        Person person = new PersonBuilder().build();
        AddAddressCommand addAddressCommand = new AddAddressCommand(person);
        int numberOfCommandsToRedo = 1;

        assertDoesNotThrow(() -> VersionControl.INSTANCE.addInverselyExecutedCommand(addAddressCommand));

        expectedModel.addPerson(person);

        // redo command given more commands to redo than available.
        RedoCommand redoCommand = new RedoCommand(numberOfCommandsToRedo * 100);

        String expectedMessage = String.format(AddAddressCommand.MESSAGE_SUCCESS, person) + "\n"
                + String.format(RedoCommand.MESSAGE_SUCCESS, numberOfCommandsToRedo);

        assertCommandSuccess(new RedoCommand(numberOfCommandsToRedo), model, expectedMessage, expectedModel);
    }
}
