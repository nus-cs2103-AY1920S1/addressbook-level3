package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.*;

import java.util.ArrayList;
import java.util.List;
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
     * @param model
     */
    public void autoAssign(Model model) throws CommandException {
        ObservableList<Vehicle> nearbyVehicles = model.getFilteredVehicleList();
        List<Vehicle> toAdd = new ArrayList<>();

        if (nearbyVehicles.size() > 0) {
            for (Vehicle v: nearbyVehicles) {
                if (v.isAvailable() && v.getDistrict().equals(draft.getDistrict())) {
                    toAdd.add(v);
                }
            }
            if (!toAdd.isEmpty()) {
                draft.addVehicle(toAdd.get(0));
            } else {
                throw new CommandException(MESSAGE_VEHICLE_BUSY);
            }
        } // 0 vehicles taken care of in execute
    }

    /**
     * Manual assignment of vehicle to newly created incident reports.
     * @param model
     * @param indexOfV
     */
    public void manualAssign(Model model, int indexOfV) throws CommandException {
        requireNonNull(model);

        if (indexOfV > 0) {
            ObservableList<Vehicle> nearbyVehicles = model.getFilteredVehicleList();
            // ObservableList<Vehicle> availableVehicles = nearbyVehicles
                    // .filtered(predicate);

            if (indexOfV < nearbyVehicles.size()) {
                Vehicle vehicle = nearbyVehicles.get(indexOfV);

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

        if (isAuto) {
            autoAssign(model);
        } else {
            manualAssign(model, indexOfV);
        }

        if (model.getFilteredVehicleList().size() == 0) {
            throw new CommandException(Messages.MESSAGE_NO_AVAILABLE_VEHICLE);
        } else if (model.getFilteredVehicleList().size() == 1) {
            return new CommandResult(Messages.MESSAGE_SINGLE_VEHICLE_LISTED);
        } else {
            return new CommandResult(
                    String.format(Messages.MESSAGE_VEHICLES_LISTED_OVERVIEW, model.getFilteredVehicleList().size()));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof VehicleAssignmentCommand // instanceof handles nulls
                && predicate == ((VehicleAssignmentCommand) other).predicate); // state check
    }
}
