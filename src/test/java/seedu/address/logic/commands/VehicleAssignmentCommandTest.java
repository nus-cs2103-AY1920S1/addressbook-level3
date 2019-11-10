package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_NO_AVAILABLE_VEHICLE;
import static seedu.address.commons.core.Messages.MESSAGE_VEHICLE_ASSIGNMENT_PROMPT;
import static seedu.address.commons.core.Messages.MESSAGE_VEHICLE_OOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalVehicles.getTypicalIncidentManager;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.incident.Incident;
import seedu.address.model.person.Person;
import seedu.address.model.vehicle.District;
import seedu.address.testutil.TypicalPersons;

public class VehicleAssignmentCommandTest {
    private Model model = new ModelManager(getTypicalIncidentManager(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalIncidentManager(), new UserPrefs());
    private List<Person> operators = TypicalPersons.getTypicalPersons();

    private Person operator = operators.get(0);
    private Incident draft1 = new Incident(operator, new District(1));
    private Incident draft2 = new Incident(operator, new District(2));
    private Incident draft3 = new Incident(operator, new District(1));

    private VehicleAssignmentCommand c1 = new VehicleAssignmentCommand(draft1, true, 0);
    private VehicleAssignmentCommand c2 = new VehicleAssignmentCommand(draft2, true, 0);
    private VehicleAssignmentCommand c3 = new VehicleAssignmentCommand(draft3, false, -1);
    private VehicleAssignmentCommand c4 = new VehicleAssignmentCommand(draft1, false, 1);
    private VehicleAssignmentCommand c5 = new VehicleAssignmentCommand(draft2, false, 1);
    private VehicleAssignmentCommand c6 = new VehicleAssignmentCommand(draft1, false, 10);

    @Test
    public void equals() {
        // same object -> returns true
        assertTrue(c1.equals(c1));

        // different drafts -> return false
        assertFalse(c1.equals(c2));

        // same draft different auto -> returns true, shld be unaffected
        assertTrue(c1.equals(c4));

        // same district but different draft -> return false
        assertFalse(c1.equals(c3));
    }

    @Test
    public void autoAssign_vehicleNotAvailable_exceptionThrown() {
        assertCommandFailure(c2, model, MESSAGE_NO_AVAILABLE_VEHICLE);
    }

    @Test
    public void manualAssign_vehicleAvailableAndIndexValid_vehicleAddedToDraft() {
        try {
            assert(draft1.getVehicle() == null);
            c4.execute(expectedModel);
            assert(draft1.getVehicle() != null);
        } catch (CommandException e) {
            assert(false);
        }
    }

    @Test
    public void manualAssign_vehicleNotAvailable_exceptionThrown() {
        assertCommandFailure(c5, model, MESSAGE_NO_AVAILABLE_VEHICLE);
    }

    @Test
    public void manualAssign_indexNotEntered_exceptionThrownToPrompt() {
        assertCommandFailure(c3, model, MESSAGE_VEHICLE_ASSIGNMENT_PROMPT);
    }

    @Test
    public void manualAssign_indexOob_exceptionThrown() {
        assertCommandFailure(c6, model, MESSAGE_VEHICLE_OOB);
    }
}
