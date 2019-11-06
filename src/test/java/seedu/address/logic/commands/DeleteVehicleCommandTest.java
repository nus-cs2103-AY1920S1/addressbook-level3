package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_ACCESS_ADMIN;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_VEHICLE_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showVehicleAtIndex;
import static seedu.address.logic.commands.DeleteVehicleCommand.MESSAGE_DELETE_VEHICLE_ERROR;
import static seedu.address.logic.commands.DeleteVehicleCommand.MESSAGE_DELETE_VEHICLE_SUCCESS;
import static seedu.address.testutil.TypicalEntities.FIONA;
import static seedu.address.testutil.TypicalEntities.NOT_ADMIN;
import static seedu.address.testutil.TypicalEntities.getTypicalIncidentManager;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ENTITY;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ENTITY;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_ENTITY;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.vehicle.Vehicle;

public class DeleteVehicleCommandTest {
    private Model model = new ModelManager(getTypicalIncidentManager(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        model.setSession(FIONA);
        Index index = INDEX_SECOND_ENTITY;
        Vehicle toDelete = model.getFilteredVehicleList().get(index.getZeroBased());
        DeleteVehicleCommand deleteVehicleCommand = new DeleteVehicleCommand(index);

        String expectedMessage = String.format(MESSAGE_DELETE_VEHICLE_SUCCESS, toDelete);
        Model expectedModel = new ModelManager(model.getIncidentManager(), new UserPrefs());
        expectedModel.deleteVehicle(toDelete);

        assertCommandSuccess(deleteVehicleCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexFliteredList_success() {
        showVehicleAtIndex(model, INDEX_SECOND_ENTITY);
        model.setSession(FIONA);

        Index index = INDEX_FIRST_ENTITY;
        Vehicle toDelete = model.getFilteredVehicleList().get(index.getZeroBased());
        DeleteVehicleCommand deleteVehicleCommand = new DeleteVehicleCommand(index);

        String expectedMessage = String.format(MESSAGE_DELETE_VEHICLE_SUCCESS, toDelete);
        Model expectedModel = new ModelManager(model.getIncidentManager(), new UserPrefs());
        expectedModel.deleteVehicle(toDelete);

        assertCommandSuccess(deleteVehicleCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_success() {
        model.setSession(FIONA);

        Index index = INDEX_THIRD_ENTITY;
        DeleteVehicleCommand deleteVehicleCommand = new DeleteVehicleCommand(index);

        assertCommandFailure(deleteVehicleCommand, model, MESSAGE_INVALID_VEHICLE_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredList_success() {
       model.setSession(FIONA);

       Index index = INDEX_SECOND_ENTITY;
       showVehicleAtIndex(model, index);

       DeleteVehicleCommand deleteVehicleCommand = new DeleteVehicleCommand(index);

       assertCommandFailure(deleteVehicleCommand, model, MESSAGE_INVALID_VEHICLE_INDEX);
    }

    @Test
    public void execute_unauthorisedCommand_failure() {
        model.setSession(NOT_ADMIN);

        Index index = INDEX_SECOND_ENTITY;
        DeleteVehicleCommand deleteVehicleCommand = new DeleteVehicleCommand(index);

        assertCommandFailure(deleteVehicleCommand, model, MESSAGE_ACCESS_ADMIN);
    }

    @Test
    public void execute_deleteDispatchedVehicle_failure() {
        model.setSession(FIONA);

        //first vehicle in list is busy, second in list is not
        Index index = INDEX_FIRST_ENTITY;
        DeleteVehicleCommand deleteVehicleCommand = new DeleteVehicleCommand(index);

        assertCommandFailure(deleteVehicleCommand, model, MESSAGE_DELETE_VEHICLE_ERROR);
    }

}
