package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_ENTITY_DISPLAYED_ID;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.UndoableCommand.MESSAGE_NOT_EXECUTED_BEFORE;
import static seedu.address.logic.commands.UpdateCommand.MESSAGE_UNDO_SUCCESS;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalUndoableCommands.TYPICAL_BODY;
import static seedu.address.testutil.TypicalUndoableCommands.TYPICAL_UPDATE_COMMAND;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.utility.UpdateBodyDescriptor;
import seedu.address.logic.parser.utility.UpdateFridgeDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.entity.IdentificationNumber;
import seedu.address.model.entity.Sex;
import seedu.address.model.entity.body.Body;
import seedu.address.model.entity.fridge.Fridge;
import seedu.address.model.entity.fridge.FridgeStatus;
import seedu.address.testutil.BodyBuilder;
import seedu.address.testutil.FridgeBuilder;

//@@author ambervoong
/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * UpdateCommand.
 */
public class UpdateCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void executeBody_allFieldsSpecifiedFilteredList_success() throws CommandException {
        Body body = new BodyBuilder().build();
        model.addEntity(body);
        UpdateBodyDescriptor descriptor = new UpdateBodyDescriptor(body);

        UpdateCommand updateCommand = new UpdateCommand(body.getIdNum(), descriptor);
        updateCommand.execute(model);

        String expectedMessage = String.format(UpdateCommand.MESSAGE_UPDATE_ENTITY_SUCCESS, body);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setEntity(model.getFilteredBodyList().get(0), body);

        // Checks integration with UndoableCommand
        assertEquals(updateCommand.getCommandState(), UndoableCommand.UndoableCommandState.UNDOABLE);
        assertCommandSuccess(updateCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void executeBody_someFieldsSpecifiedFilteredList_success() throws CommandException {
        Body body = new BodyBuilder().build();
        body.setSex(null);
        body.setCauseOfDeath(null);
        model.addEntity(body);

        UpdateBodyDescriptor descriptor = new UpdateBodyDescriptor(body);
        descriptor.setSex(null);
        descriptor.setCauseOfDeath(null);

        UpdateCommand updateCommand = new UpdateCommand(body.getIdNum(), descriptor);
        updateCommand.execute(model);

        String expectedMessage = String.format(UpdateCommand.MESSAGE_UPDATE_ENTITY_SUCCESS, body);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setEntity(model.getFilteredBodyList().get(0), body);

        assertCommandSuccess(updateCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void executeBody_bodyIdNotInFilteredList_failure() throws CommandException {
        // Fails because the Body was not added to the model.
        Body body = new BodyBuilder().build();

        UpdateCommand updateCommand = new UpdateCommand(body.getIdNum(), new UpdateBodyDescriptor());

        String expectedMessage = MESSAGE_INVALID_ENTITY_DISPLAYED_ID;

        assertCommandFailure(updateCommand, model, expectedMessage);
    }

    // Note that a Fridge's status is automatically set to UNOCCUPIED if does not contain a body.
    @Test
    public void executeFridge_fridgeStatusSpecifiedFilteredList_success() throws CommandException {
        Fridge fridge = new FridgeBuilder().build();
        model.addEntity(fridge);

        UpdateFridgeDescriptor descriptor = new UpdateFridgeDescriptor(fridge);
        descriptor.setFridgeStatus(FridgeStatus.OCCUPIED);

        UpdateCommand updateCommand = new UpdateCommand(fridge.getIdNum(), descriptor);
        updateCommand.execute(model);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        Fridge otherFridge = new FridgeBuilder().build();
        otherFridge.setFridgeStatus(FridgeStatus.OCCUPIED);
        expectedModel.addEntity(otherFridge);

        String expectedMessage = String.format(UpdateCommand.MESSAGE_UPDATE_ENTITY_SUCCESS, fridge);

        assertCommandSuccess(updateCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void getEntityFromId_invalidBodyId_failure() throws CommandException {

        UpdateCommand updateCommand = new UpdateCommand(
                IdentificationNumber.customGenerateId("B", 2), new UpdateBodyDescriptor());

        String expectedMessage = MESSAGE_INVALID_ENTITY_DISPLAYED_ID;

        assertCommandFailure(updateCommand, model, expectedMessage);
    }

    @Test
    public void getEntityFromId_validBodyId_success() throws CommandException {
        Body body = new BodyBuilder().build();
        model.addEntity(body);
        IdentificationNumber id = IdentificationNumber.customGenerateId("B", 1);
        UpdateBodyDescriptor descriptor = new UpdateBodyDescriptor();
        UpdateCommand updateCommand = new UpdateCommand(
                IdentificationNumber.customGenerateId("B", 1), descriptor);

        assertEquals(body, updateCommand.getEntityFromId(model, id, descriptor));
    }


    @Test
    public void getBodyFromId_validBodyId_failure() throws CommandException {
        Body body = new BodyBuilder().build();
        model.addEntity(body);
        IdentificationNumber id = IdentificationNumber.customGenerateId("B", 1);
        UpdateFridgeDescriptor descriptor = new UpdateFridgeDescriptor();
        UpdateCommand updateCommand = new UpdateCommand(
                IdentificationNumber.customGenerateId("F", 1), descriptor);

        UpdateFridgeDescriptor descriptorCopy = new UpdateFridgeDescriptor();
        descriptorCopy.setNewBody(body);
        assertEquals(descriptorCopy, updateCommand.getBodyFromId(model, id, descriptor));
    }

    @Test
    public void saveOriginalFields_body_success() throws CommandException {
        Body body = new BodyBuilder().build();
        UpdateBodyDescriptor descriptor = (UpdateBodyDescriptor) UpdateCommand.saveOriginalFields(body);
        assertEquals(descriptor.getCauseOfDeath().get(), body.getCauseOfDeath().get());
    }

    @Test
    public void undo_previouslyExecuted_success() throws CommandException {
        UndoableCommand updateCommand = TYPICAL_UPDATE_COMMAND;
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        model.addEntity(TYPICAL_BODY);
        updateCommand.execute(model);

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.addEntity(TYPICAL_BODY);
        expectedModel.addExecutedCommand(updateCommand);

        UndoCommand undoCommand = new UndoCommand();

        String expectedMessage = String.format(MESSAGE_UNDO_SUCCESS, TYPICAL_BODY);
        assertCommandSuccess(undoCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void undo_notExecutedBefore_undoFailureException() {
        UndoableCommand updateCommand = TYPICAL_UPDATE_COMMAND;
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        assertThrows(CommandException.class, () -> updateCommand.undo(model), MESSAGE_NOT_EXECUTED_BEFORE);
    }

    @Test
    public void equals() {
        Body body = new BodyBuilder().build();
        UpdateBodyDescriptor descriptor = new UpdateBodyDescriptor(body);
        UpdateCommand updateCommand = new UpdateCommand(body.getIdNum(), descriptor);

        // same values -> returns true
        UpdateBodyDescriptor copyDescriptor = new UpdateBodyDescriptor(body);
        UpdateCommand commandWithSameValues = new UpdateCommand(new BodyBuilder().build().getIdNum(),
                copyDescriptor);
        assertTrue(updateCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(updateCommand.equals(updateCommand));

        // null -> returns false
        assertFalse(updateCommand.equals(null));

        // different types -> returns false
        assertFalse(updateCommand.equals(new ExitCommand()));

        // different identification number -> returns false
        IdentificationNumber diffId = IdentificationNumber.customGenerateId("B", 2);
        assertFalse(updateCommand.equals(new UpdateCommand(diffId, copyDescriptor)));

        // different descriptor -> returns false
        copyDescriptor.setSex(Sex.FEMALE);
        assertFalse(updateCommand.equals(new UpdateCommand(body.getIdNum(), copyDescriptor)));

        commandWithSameValues = new UpdateCommand(body.getIdNum(), descriptor);
        assertEquals(updateCommand.hashCode(), commandWithSameValues.hashCode());
    }
}
