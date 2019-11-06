package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_VEHICLE_NUMBER;
import static seedu.address.logic.commands.AddVehicleCommand.MESSAGE_ADD_VEHICLE_SUCCESS;
import static seedu.address.logic.commands.AddVehicleCommand.MESSAGE_DUPLICATE_VEHICLE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.IncidentBuilder.DEFAULT_VEHICLE;
import static seedu.address.testutil.TypicalEntities.getTypicalIncidentManager;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ENTITY;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.vehicle.Vehicle;
import seedu.address.testutil.VehicleBuilder;

public class AddVehicleCommandTest {

    private Model model = new ModelManager(getTypicalIncidentManager(), new UserPrefs());

    @Test
    public void execute_allValidFields_success() {
        Vehicle toAdd = DEFAULT_VEHICLE;
        AddVehicleCommand addVehicleCommand = new AddVehicleCommand(toAdd);

        String expectedMessage = String.format(MESSAGE_ADD_VEHICLE_SUCCESS, toAdd);
        Model expectedModel = new ModelManager(model.getIncidentManager(), new UserPrefs());
        expectedModel.addVehicle(toAdd);

        assertCommandSuccess(addVehicleCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateVNum_failure() {
        Vehicle toCopy = model.getFilteredVehicleList().get(INDEX_FIRST_ENTITY.getZeroBased());
        Vehicle toAdd = new VehicleBuilder(DEFAULT_VEHICLE).withVNum(toCopy.getVehicleNumber().toString()).build();

        AddVehicleCommand addVehicleCommand = new AddVehicleCommand(toAdd);

        assertCommandFailure(addVehicleCommand, model, MESSAGE_DUPLICATE_VEHICLE_NUMBER);
    }

    @Test
    public void execute_duplicateVehicle_failure() {
        Vehicle toCopy = model.getFilteredVehicleList().get(INDEX_FIRST_ENTITY.getZeroBased());
        Vehicle toAdd = new VehicleBuilder(toCopy).build();

        AddVehicleCommand addVehicleCommand = new AddVehicleCommand(toCopy);

        assertCommandFailure(addVehicleCommand, model, MESSAGE_DUPLICATE_VEHICLE);
    }
}
