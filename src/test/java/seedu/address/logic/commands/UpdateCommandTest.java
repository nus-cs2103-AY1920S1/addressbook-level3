package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_ENTITY_DISPLAYED_ID;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.utility.UpdateBodyDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.entity.IdentificationNumber;
import seedu.address.model.entity.Sex;
import seedu.address.model.entity.body.Body;
import seedu.address.testutil.BodyBuilder;

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

        UpdateCommand updateCommand = new UpdateCommand(body.getBodyIdNum(), descriptor);
        updateCommand.execute(model);

        String expectedMessage = String.format(UpdateCommand.MESSAGE_UPDATE_ENTITY_SUCCESS, body);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setEntity(model.getFilteredBodyList().get(0), body);

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

        UpdateCommand updateCommand = new UpdateCommand(body.getBodyIdNum(), descriptor);
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

        UpdateCommand updateCommand = new UpdateCommand(body.getBodyIdNum(), new UpdateBodyDescriptor());

        String expectedMessage = MESSAGE_INVALID_ENTITY_DISPLAYED_ID;

        assertCommandFailure(updateCommand, model, expectedMessage);
    }

    @Test
    public void getEntityFromId_invalidBodyId_failure() throws CommandException {
        UpdateCommand updateCommand = new UpdateCommand(
                IdentificationNumber.customGenerateId("B", 2), new UpdateBodyDescriptor());

        String expectedMessage = MESSAGE_INVALID_ENTITY_DISPLAYED_ID;

        assertCommandFailure(updateCommand, model, expectedMessage);
    }

    @Test
    public void saveOriginalFields_body_success() throws CommandException {
        Body body = new BodyBuilder().build();
        UpdateBodyDescriptor descriptor = (UpdateBodyDescriptor) UpdateCommand.saveOriginalFields(body);
        assertEquals(descriptor.getCauseOfDeath().get(), body.getCauseOfDeath());
    }


    @Test
    public void equals() {
        Body body = new BodyBuilder().build();
        UpdateBodyDescriptor descriptor = new UpdateBodyDescriptor(body);
        UpdateCommand updateCommand = new UpdateCommand(body.getBodyIdNum(), descriptor);

        // same values -> returns true
        UpdateBodyDescriptor copyDescriptor = new UpdateBodyDescriptor(body);
        UpdateCommand commandWithSameValues = new UpdateCommand(new BodyBuilder().build().getBodyIdNum(),
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
        assertFalse(updateCommand.equals(new UpdateCommand(body.getBodyIdNum(), copyDescriptor)));

        commandWithSameValues = new UpdateCommand(body.getBodyIdNum(), descriptor);
        assertEquals(updateCommand.hashCode(), commandWithSameValues.hashCode());
    }
}
