package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_FRIDGE_DOES_NOT_EXIST;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_ENTITY_DISPLAYED_ID;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.UndoableCommand.MESSAGE_NOT_EXECUTED_BEFORE;
import static seedu.address.logic.commands.UpdateCommand.MESSAGE_CANNOT_ASSIGN_FRIDGE;
import static seedu.address.logic.commands.UpdateCommand.MESSAGE_UNDO_SUCCESS;
import static seedu.address.model.entity.body.BodyStatus.ARRIVED;
import static seedu.address.model.entity.body.BodyStatus.CLAIMED;
import static seedu.address.model.entity.body.BodyStatus.CONTACT_POLICE;
import static seedu.address.model.entity.body.BodyStatus.DONATED;
import static seedu.address.model.entity.fridge.FridgeStatus.OCCUPIED;
import static seedu.address.model.entity.fridge.FridgeStatus.UNOCCUPIED;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalUndoableCommands.TYPICAL_BODY;
import static seedu.address.testutil.TypicalUndoableCommands.TYPICAL_UPDATE_COMMAND;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.utility.UpdateBodyDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.entity.IdentificationNumber;
import seedu.address.model.entity.Sex;
import seedu.address.model.entity.UniqueIdentificationNumberMaps;
import seedu.address.model.entity.body.Body;
import seedu.address.model.entity.fridge.Fridge;
import seedu.address.model.entity.fridge.FridgeStatus;
import seedu.address.model.notif.Notif;
import seedu.address.testutil.BodyBuilder;
import seedu.address.testutil.NotifBuilder;
import seedu.address.ui.GuiUnitTest;

//@@author ambervoong
/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * UpdateCommand.
 */
public class UpdateCommandTest extends GuiUnitTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @BeforeEach
    public void setUp() {
        UniqueIdentificationNumberMaps.clearAllEntries();
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void executeBody_allFieldsSpecifiedFilteredList_success() throws CommandException {
        Body body = new BodyBuilder().build();
        model.addEntity(body);
        UpdateBodyDescriptor descriptor = new UpdateBodyDescriptor(body);

        UpdateCommand updateCommand = new UpdateCommand(body.getIdNum(), descriptor);
        updateCommand.execute(model);

        String expectedMessage = String.format(UpdateCommand.MESSAGE_UPDATE_ENTITY_SUCCESS, body.getIdNum());

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

        String expectedMessage = String.format(UpdateCommand.MESSAGE_UPDATE_ENTITY_SUCCESS, body.getIdNum());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setEntity(model.getFilteredBodyList().get(0), body);

        assertCommandSuccess(updateCommand, model, expectedMessage, expectedModel);
    }

    //@@author arjavibahety
    @Test
    public void executeBody_fridgeIdInFilteredList_success() throws CommandException {
        Fridge f1 = new Fridge();
        Fridge f2 = new Fridge();
        model.addEntity(f1);
        model.addEntity(f2);

        // initally a fridge was specified
        Body body = new BodyBuilder().build();
        body.setFridgeId(f1.getIdNum());
        f1.setBody(body);
        model.addEntity(body);

        assertEquals(f1.getFridgeStatus(), OCCUPIED);

        UpdateBodyDescriptor descriptor = new UpdateBodyDescriptor(body);
        descriptor.setFridgeId(f2.getIdNum());

        UpdateCommand updateCommand = new UpdateCommand(body.getIdNum(), descriptor);
        updateCommand.execute(model);

        String expectedMessage = String.format(UpdateCommand.MESSAGE_UPDATE_ENTITY_SUCCESS, body.getIdNum());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setEntity(model.getFilteredBodyList().get(0), body);

        assertCommandSuccess(updateCommand, model, expectedMessage, expectedModel);
        assertEquals(f1.getFridgeStatus(), FridgeStatus.UNOCCUPIED);
        assertEquals(f2.getFridgeStatus(), OCCUPIED);

    }

    @Test
    public void executeBody_fridgeIdNotSpecifiedInitially_success() throws CommandException {
        // initally a fridge was not specified
        Body body = new BodyBuilder().build();
        model.addEntity(body);

        Fridge f1 = new Fridge();
        model.addEntity(f1);

        UpdateBodyDescriptor descriptor = new UpdateBodyDescriptor(body);
        descriptor.setFridgeId(f1.getIdNum());

        UpdateCommand updateCommand = new UpdateCommand(body.getIdNum(), descriptor);
        updateCommand.execute(model);

        String expectedMessage = String.format(UpdateCommand.MESSAGE_UPDATE_ENTITY_SUCCESS, body.getIdNum());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setEntity(model.getFilteredBodyList().get(0), body);

        assertCommandSuccess(updateCommand, model, expectedMessage, expectedModel);
        assertEquals(f1.getFridgeStatus(), OCCUPIED);

    }

    @Test
    public void executeBody_fridgeIdNotInFilteredList_failure() throws CommandException {
        Fridge f1 = new Fridge();
        model.addEntity(f1);

        Body body = new BodyBuilder().build();
        body.setFridgeId(f1.getIdNum());
        f1.setBody(body);
        model.addEntity(body);

        assertEquals(f1.getFridgeStatus(), OCCUPIED);

        UpdateBodyDescriptor descriptor = new UpdateBodyDescriptor(body);
        descriptor.setFridgeId(IdentificationNumber.customGenerateId("F", 10));

        UpdateCommand updateCommand = new UpdateCommand(body.getIdNum(), descriptor);

        String expectedMessage = MESSAGE_FRIDGE_DOES_NOT_EXIST;
        assertCommandFailure(updateCommand, model, expectedMessage);

        assertEquals(f1.getFridgeStatus(), OCCUPIED);
    }

    @Test
    public void executeBody_removeNotifAfterContactPolice_success() throws CommandException {
        Body body = new BodyBuilder().withStatus("contact police").build();
        Notif notif = new NotifBuilder().withBody(body).build();
        model.addEntity(body);
        model.addNotif(notif);

        UpdateBodyDescriptor descriptor = new UpdateBodyDescriptor(body);
        descriptor.setBodyStatus(CLAIMED);

        UpdateCommand updateCommand = new UpdateCommand(body.getIdNum(), descriptor);
        updateCommand.execute(model);

        assertEquals(model.getFilteredNotifList().size(), 0);
    }

    @Test
    public void executeBody_removeNotifAfterArrived_success() throws CommandException {
        Body body = new BodyBuilder().build();
        Notif notif = new NotifBuilder().withBody(body).build();
        model.addEntity(body);
        model.addNotif(notif);

        UpdateBodyDescriptor descriptor = new UpdateBodyDescriptor(body);
        descriptor.setBodyStatus(CLAIMED);

        UpdateCommand updateCommand = new UpdateCommand(body.getIdNum(), descriptor);
        updateCommand.execute(model);

        assertEquals(model.getFilteredNotifList().size(), 0);

    }

    @Test
    public void executeBody_removeNotifAfterArrivedAndCop_success() throws CommandException {
        Body body = new BodyBuilder().build();
        Notif notif = new NotifBuilder().withBody(body).build();
        model.addEntity(body);
        model.addNotif(notif);

        UpdateBodyDescriptor descriptor = new UpdateBodyDescriptor(body);
        descriptor.setBodyStatus(CONTACT_POLICE);

        UpdateCommand updateCommand = new UpdateCommand(body.getIdNum(), descriptor);
        updateCommand.execute(model);

        assertEquals(model.getFilteredNotifList().size(), 1);
    }

    @Test
    public void executeBody_addNotifOnChangeToArrival_success() throws CommandException, InterruptedException {
        Body body = new BodyBuilder().withStatus("pending police report").build();
        model.addEntity(body);

        UpdateBodyDescriptor descriptor = new UpdateBodyDescriptor(body);
        descriptor.setBodyStatus(ARRIVED);

        UpdateCommand updateCommand = new UpdateCommand(body.getIdNum(), descriptor);
        updateCommand.execute(model);

        assertEquals(1, model.getFilteredNotifList().size());
        model.deleteNotif(model.getFilteredNotifList().get(0));
        model.deleteEntity(body);
    }

    @Test
    public void executeBody_removeBodyFromFridgeClaimed_success() throws CommandException {
        Fridge f1 = new Fridge();
        model.addEntity(f1);

        Body body = new BodyBuilder().withStatus("pending police report").build();
        body.setFridgeId(f1.getIdNum());
        model.addEntity(body);
        f1.setBody(body);

        assertEquals(f1.getFridgeStatus(), OCCUPIED);

        UpdateBodyDescriptor descriptor = new UpdateBodyDescriptor(body);
        descriptor.setBodyStatus(CLAIMED);

        UpdateCommand updateCommand = new UpdateCommand(body.getIdNum(), descriptor);
        updateCommand.execute(model);

        assertEquals(f1.getFridgeStatus(), UNOCCUPIED);
    }

    @Test
    public void executeBody_removeBodyFromFridgeDonated_success() throws CommandException {
        Fridge f1 = new Fridge();
        model.addEntity(f1);

        Body body = new BodyBuilder().withStatus("pending police report").build();
        body.setFridgeId(f1.getIdNum());
        model.addEntity(body);
        f1.setBody(body);

        assertEquals(f1.getFridgeStatus(), OCCUPIED);

        UpdateBodyDescriptor descriptor = new UpdateBodyDescriptor(body);
        descriptor.setBodyStatus(DONATED);

        UpdateCommand updateCommand = new UpdateCommand(body.getIdNum(), descriptor);
        updateCommand.execute(model);

        assertEquals(f1.getFridgeStatus(), UNOCCUPIED);
    }

    @Test
    public void executeBody_assignFridgeToClaimedBody_failure() throws CommandException {
        Fridge f1 = new Fridge();
        model.addEntity(f1);

        Body body = new BodyBuilder().withStatus("claimed").build();
        model.addEntity(body);

        UpdateBodyDescriptor descriptor = new UpdateBodyDescriptor(body);
        descriptor.setFridgeId(f1.getIdNum());

        UpdateCommand updateCommand = new UpdateCommand(body.getIdNum(), descriptor);

        String expectedMessage = MESSAGE_CANNOT_ASSIGN_FRIDGE;
        assertCommandFailure(updateCommand, model, expectedMessage);
        assertEquals(f1.getFridgeStatus(), UNOCCUPIED);
    }

    @Test
    public void executeBody_assignFridgeToDonatedBody_failure() throws CommandException {
        Fridge f1 = new Fridge();
        model.addEntity(f1);

        Body body = new BodyBuilder().withStatus("donated").build();
        model.addEntity(body);

        UpdateBodyDescriptor descriptor = new UpdateBodyDescriptor(body);
        descriptor.setFridgeId(f1.getIdNum());

        UpdateCommand updateCommand = new UpdateCommand(body.getIdNum(), descriptor);

        String expectedMessage = MESSAGE_CANNOT_ASSIGN_FRIDGE;
        assertCommandFailure(updateCommand, model, expectedMessage);
        assertEquals(f1.getFridgeStatus(), UNOCCUPIED);
    }

    @Test
    public void executeBody_setBodyStatusToCop_success() throws CommandException, InterruptedException {
        Body body = new BodyBuilder().build();
        model.addEntity(body);

        UpdateBodyDescriptor descriptor = new UpdateBodyDescriptor(body);
        descriptor.setBodyStatus(CONTACT_POLICE);

        UpdateCommand updateCommand = new UpdateCommand(body.getIdNum(), descriptor);
        updateCommand.execute(model);

        Thread.sleep(100);
        assertEquals(model.getNumberOfNotifs(), 1);
    }
    //@@author

    @Test
    public void executeBody_bodyIdNotInFilteredList_failure() throws CommandException {
        //UniqueIdentificationNumberMaps.clearAllEntries();
        // Fails because the Body was not added to the model.
        Body body = new BodyBuilder().build();

        UpdateCommand updateCommand = new UpdateCommand(body.getIdNum(), new UpdateBodyDescriptor());

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
    public void getEntityFromId_validBodyId_success() throws CommandException {
        UniqueIdentificationNumberMaps.clearAllEntries();
        Body body = new BodyBuilder().build();
        model.addEntity(body);
        IdentificationNumber id = IdentificationNumber.customGenerateId("B", 1);
        UpdateBodyDescriptor descriptor = new UpdateBodyDescriptor();
        UpdateCommand updateCommand = new UpdateCommand(
                IdentificationNumber.customGenerateId("B", 1), descriptor);

        assertEquals(body, updateCommand.getEntityFromId(model, id, descriptor));
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
        model.addEntity(TYPICAL_BODY);
        updateCommand.execute(model);

        UniqueIdentificationNumberMaps.clearAllEntries();
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.addEntity(TYPICAL_BODY);
        expectedModel.addExecutedCommand(updateCommand);

        UndoCommand undoCommand = new UndoCommand();

        String expectedMessage = String.format(MESSAGE_UNDO_SUCCESS, TYPICAL_BODY.getIdNum());
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
        UniqueIdentificationNumberMaps.clearAllEntries();
        Body body = new BodyBuilder().build();
        UpdateBodyDescriptor descriptor = new UpdateBodyDescriptor(body);
        UpdateCommand updateCommand = new UpdateCommand(body.getIdNum(), descriptor);

        // same values -> returns true
        UpdateBodyDescriptor copyDescriptor = new UpdateBodyDescriptor(body);
        UpdateCommand commandWithSameValues = new UpdateCommand(body.getIdNum(),
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
//@@author
