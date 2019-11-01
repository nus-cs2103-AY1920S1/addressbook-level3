package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_SINGLE_VEHICLE_LISTED;
import static seedu.address.commons.core.Messages.MESSAGE_VEHICLES_LISTED_OVERVIEW;
import static seedu.address.commons.core.Messages.MESSAGE_VEHICLE_ASSIGNMENT_PROMPT;
import static seedu.address.commons.core.Messages.MESSAGE_VEHICLE_BUSY;
import static seedu.address.commons.core.Messages.MESSAGE_VEHICLE_OOB;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.incident.Incident;
import seedu.address.model.vehicle.AvailableInDistrictPredicate;
import seedu.address.model.vehicle.Vehicle;

/**
 * Assigns vehicles to newly created incidents.
 * Cannot be changed once assigned.
 * TODO: consider vtype for auto assign.
 */
public class VehicleAssignmentCommand extends Command {

    private final Incident draft;
    private final boolean isAuto;
    // 0 for auto, +ve int for manual, -1 for prompting required.
    // based on list filtered w availability predicate.
    private final int indexOfV;
    private final Predicate<Vehicle> predicate;

    public VehicleAssignmentCommand(Incident draft, boolean isAuto, int indexOfV) {
        this.draft = draft;
        this.isAuto = isAuto;
        this.indexOfV = indexOfV;
        this.predicate = new AvailableInDistrictPredicate(draft.getDistrict());
    }

    /**
     * Automatic assignment of vehicle to newly created incident reports.
     * Assumes instance of no available vehicle is alr taken care of.
     * Assumes predicate of available in district works.
     * @param nearbyVehicles
     */
    private void autoAssign(ObservableList<Vehicle> nearbyVehicles) throws CommandException {
        if (!nearbyVehicles.isEmpty()) {
            draft.addVehicle(nearbyVehicles.get(0));
        } else {
            throw new CommandException(MESSAGE_VEHICLE_BUSY);
        }
    }

    /**
     * Manual assignment of vehicle to newly created incident reports.
     * @param  nearbyVehicles
     * @param indexOfV
     */
    private void manualAssign(ObservableList<Vehicle> nearbyVehicles, int indexOfV) throws CommandException {
        if (indexOfV > 0) {
            if (indexOfV <= nearbyVehicles.size()) {
                Vehicle vehicle = nearbyVehicles.get(indexOfV - 1);

                if (vehicle.isAvailable()) {
                    draft.addVehicle(vehicle);
                } else {
                    throw new CommandException(MESSAGE_VEHICLE_BUSY);
                }
            } else {
                throw new CommandException(MESSAGE_VEHICLE_OOB);
            }

        } else {
            throw new CommandException(MESSAGE_VEHICLE_ASSIGNMENT_PROMPT);
        }
    }

    /**
     * Command result will not actually be printed, is intermediate return value.
     * @param model
     * @return
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredVehicleList(predicate);
        ObservableList<Vehicle> nearbyVehicles = model.getFilteredVehicleList();

        if (nearbyVehicles.size() == 0) {
            throw new CommandException(Messages.MESSAGE_NO_AVAILABLE_VEHICLE);
        }

        // should only reach here if there is at least 1 nearby vehicle.
        if (isAuto) {
            autoAssign(nearbyVehicles);
        } else {
            manualAssign(nearbyVehicles, indexOfV);
        }

        if (nearbyVehicles.size() == 1) {
            return new CommandResult(MESSAGE_SINGLE_VEHICLE_LISTED);
        } else {
            return new CommandResult(String.format(MESSAGE_VEHICLES_LISTED_OVERVIEW, nearbyVehicles.size()));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof VehicleAssignmentCommand // instanceof handles nulls
                && predicate == ((VehicleAssignmentCommand) other).predicate); // state check
    }
}
