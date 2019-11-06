package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showVehicleAtIndex;
import static seedu.address.logic.commands.EditVehicleCommand.MESSAGE_EDIT_VEHICLE_SUCCESS;
import static seedu.address.logic.commands.EditVehicleCommand.MESSAGE_VEHICLE_NOT_EDITED;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ENTITY;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ENTITY;
import static seedu.address.testutil.VehicleBuilder.DEFAULT_AVAILABILITY;
import static seedu.address.testutil.VehicleBuilder.DEFAULT_DISTRICT;
import static seedu.address.testutil.VehicleBuilder.DEFAULT_VTYPE;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditVehicleCommand.EditVehicle;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.vehicle.District;
import seedu.address.model.vehicle.Vehicle;
import seedu.address.testutil.EditVehicleBuilder;
import seedu.address.testutil.TypicalEntities;
import seedu.address.testutil.VehicleBuilder;

public class EditVehicleCommandTest {
    private Model model = new ModelManager(TypicalEntities.getTypicalIncidentManager(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        //Set up the vehicle that has been edited
        Vehicle editedVehicle = new VehicleBuilder().build();
        EditVehicle editor = new EditVehicleBuilder(editedVehicle).build();

        //edit the first entity in the list to resemble be the edited vehicle
        EditVehicleCommand editVehicleCommand = new EditVehicleCommand(INDEX_FIRST_ENTITY, editor);

        //Expected results from command execution
        String expectedMessage = String.format(EditVehicleCommand.MESSAGE_EDIT_VEHICLE_SUCCESS, editedVehicle);
        Model expectedModel = new ModelManager(model.getIncidentManager(), new UserPrefs());
        expectedModel.setVehicle(model.getFilteredVehicleList().get(0), editedVehicle);

        assertCommandSuccess(editVehicleCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexOfLastVehicle = Index.fromOneBased(model.getFilteredVehicleList().size());
        Vehicle lastVehicle = model.getFilteredVehicleList().get(indexOfLastVehicle.getZeroBased());

        //create the edited vehicle
        VehicleBuilder vehicleBuilder = new VehicleBuilder(lastVehicle);
        Vehicle editedVehicle = vehicleBuilder.withAvail(DEFAULT_AVAILABILITY).withDistrict(DEFAULT_DISTRICT).build();

        //create parameters for edit command
        EditVehicle editor = new EditVehicleBuilder().withAvail(DEFAULT_AVAILABILITY).withDistrict(DEFAULT_DISTRICT)
                .build();
        EditVehicleCommand editVehicleCommand = new EditVehicleCommand(indexOfLastVehicle, editor);

        //Expected results
        String expectedMessage = String.format(EditVehicleCommand.MESSAGE_EDIT_VEHICLE_SUCCESS, editedVehicle);
        Model expectedModel = new ModelManager(model.getIncidentManager(), new UserPrefs());

        assertCommandSuccess(editVehicleCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        //filter the list to show only the first vehicle
        showVehicleAtIndex(model, INDEX_FIRST_ENTITY);

        Vehicle vehicleInFilteredList = model.getFilteredVehicleList().get(INDEX_FIRST_ENTITY.getZeroBased());


        //creating the the command
        EditVehicle editor = new EditVehicleBuilder().withVType(DEFAULT_VTYPE).withDistrict(DEFAULT_DISTRICT).build();
        EditVehicleCommand editVehicleCommand = new EditVehicleCommand(INDEX_FIRST_ENTITY, editor);

        //expected results
        Vehicle editedVehicle = new VehicleBuilder(vehicleInFilteredList).withVType(DEFAULT_VTYPE)
                .withDistrict(DEFAULT_DISTRICT).build();
        Model expectedModel = new ModelManager(model.getIncidentManager(), new UserPrefs());
        String expectedMessage = String.format(MESSAGE_EDIT_VEHICLE_SUCCESS, editedVehicle);
        expectedModel.setVehicle(model.getFilteredVehicleList().get(INDEX_FIRST_ENTITY.getZeroBased()), editedVehicle);

        assertCommandSuccess(editVehicleCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecified_failure() {
        EditVehicle editVehicle = new EditVehicle();

        //EditVehicleCommand created with no fields to update in editVehicle
        EditVehicleCommand editVehicleCommand = new EditVehicleCommand(INDEX_FIRST_ENTITY, editVehicle);
        assertCommandFailure(editVehicleCommand, model, MESSAGE_VEHICLE_NOT_EDITED);
    }

    @Test
    public void execute_invalidIncidentIndexFilteredList_failure() {
        showVehicleAtIndex(model, INDEX_FIRST_ENTITY);
        Index outOfBoundIndex = INDEX_SECOND_ENTITY;

        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getIncidentManager().getVehicleList().size());
        EditVehicleCommand editVehicleCommand = new EditVehicleCommand(outOfBoundIndex,
                new EditVehicleBuilder().withDistrict(DEFAULT_DISTRICT).build());

        assertCommandFailure(editVehicleCommand, model, Messages.MESSAGE_INVALID_VEHICLE_INDEX);
    }

    @Test
    public void equals() {
        EditVehicle editor = new EditVehicleBuilder().withDistrict(DEFAULT_DISTRICT).withVType(DEFAULT_VTYPE).build();
        final EditVehicleCommand standardCommand = new EditVehicleCommand(INDEX_FIRST_ENTITY, editor);

        // same values -> returns true
        EditVehicle copyEditor = new EditVehicle(editor);
        EditVehicleCommand commandWithSameValues = new EditVehicleCommand(INDEX_FIRST_ENTITY, copyEditor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditVehicleCommand(INDEX_SECOND_ENTITY, editor)));

        // different descriptor -> returns false
        editor.setVehicleDistrict(new District(22)); //different district from default
        assertFalse(standardCommand.equals(new EditVehicleCommand(INDEX_FIRST_ENTITY, editor)));
    }
}
