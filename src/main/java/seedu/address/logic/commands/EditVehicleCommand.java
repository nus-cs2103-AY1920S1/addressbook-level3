package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DISTRICT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VNUM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VTYPE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_VEHICLES;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.vehicle.Availability;
import seedu.address.model.vehicle.District;
import seedu.address.model.vehicle.Vehicle;
import seedu.address.model.vehicle.VehicleNumber;
import seedu.address.model.vehicle.VehicleType;

/**
 * Edits details of existing vehicle in the IMS.
 */
public class EditVehicleCommand extends Command {

    public static final String COMMAND_WORD = "editv";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the vehicle identified "
            + "by the index number used in the displayed vehicle list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_DISTRICT + "VEHICLE DISTRICT] "
            + "[" + PREFIX_VTYPE + "VEHICLE NUMBER] "
            + "[" + PREFIX_VNUM + "VEHICLE TYPE] "
            + "Example: " + COMMAND_WORD + "1"
            + PREFIX_DISTRICT + "2"
            + PREFIX_VTYPE + "Ambulance"
            + PREFIX_VNUM + "5903S";


    public static final String MESSAGE_DUPLICATE_VEHICLE = "This vehicle already exists in the address book.";
    public static final String MESSAGE_EDIT_VEHICLE_SUCCESS = "Edited Vehicle: %1$s";
    public static final String MESSAGE_VEHICLE_NOT_EDITED = "No new fields were provided, vehicle is not edited.";

    private final Index index;
    private final EditVehicle editVehicle;

    public EditVehicleCommand(Index index, EditVehicle editVehicle) {
        requireNonNull(index);
        requireNonNull(editVehicle);

        this.index = index;
        this.editVehicle = new EditVehicle(editVehicle);
    }

    @Override

    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Vehicle> listOfVehicle = model.getFilteredVehicleList();

        if (index.getZeroBased() >= listOfVehicle.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_VEHICLE_INDEX);
        }

        Vehicle vehicleToEdit = listOfVehicle.get(index.getZeroBased());
        Vehicle editedVehicle = createEditedVehicle(vehicleToEdit, editVehicle);

        if (!editedVehicle.equals(vehicleToEdit) && model.hasVehicle(vehicleToEdit)) {
            throw new CommandException(MESSAGE_DUPLICATE_VEHICLE);
        }
        if (editedVehicle.equals(vehicleToEdit)) {
            throw new CommandException(MESSAGE_VEHICLE_NOT_EDITED);
        }

        model.setVehicle(vehicleToEdit, editedVehicle);
        model.updateFilteredVehicleList(PREDICATE_SHOW_ALL_VEHICLES);
        return new CommandResult(String.format(MESSAGE_EDIT_VEHICLE_SUCCESS, editedVehicle));
    }

    /**
     * Creates and returns a {@code Vehicle} object with the details from {@code vehicleToEdit}
     * Edit by {@code EditVehicle}
     */
    public Vehicle createEditedVehicle(Vehicle vehicleToEdit, EditVehicle editDetails) {
        assert vehicleToEdit != null;
        District vehicleDistrict = editDetails.getVehicleDistrict().orElse(vehicleToEdit.getDistrict());
        VehicleNumber vehicleNumber = editDetails.getVehicleNumber().orElse(vehicleToEdit.getVehicleNumber());
        VehicleType vehicleType = editDetails.getVehicleType().orElse(vehicleToEdit.getVehicleType());
        Availability vehicleAvailability = vehicleToEdit.getAvailability();

        return new Vehicle(vehicleType, vehicleNumber, vehicleDistrict, vehicleAvailability);
    }

    /**
     * Stores the details to edit the vehicle with. Each non-empty field value will replace the
     * corresponding field value of the the vehicle.
     */
    public static class EditVehicle {
        private VehicleType vehicleType;
        private VehicleNumber vehicleNumber;
        private District vehicleDistrict;

        public EditVehicle() {
        }

        public EditVehicle(EditVehicle copy) {
            this.vehicleDistrict = copy.vehicleDistrict;
            this.vehicleType = copy.vehicleType;
            this.vehicleNumber = copy.vehicleNumber;
        }

        public void setVehicleType(VehicleType vt) {
            this.vehicleType = vt;
        }

        public Optional<VehicleType> getVehicleType() {
            return Optional.ofNullable(this.vehicleType);
        }

        public void setVehicleNumber(VehicleNumber vn) {
            this.vehicleNumber = vn;
        }

        public Optional<VehicleNumber> getVehicleNumber() {
            return Optional.ofNullable(this.vehicleNumber);
        }

        public void setVehicleDistrict(District d) {
            this.vehicleDistrict = d;
        }

        public Optional<District> getVehicleDistrict() {
            return Optional.ofNullable(this.vehicleDistrict);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            if (!(other instanceof EditVehicle)) {
                return false;
            }

            EditVehicle e = (EditVehicle) other;
            return e.vehicleNumber.equals(this.vehicleNumber)
                    && e.vehicleType.equals(this.vehicleType)
                    && e.vehicleDistrict.equals(this.vehicleDistrict);
        }
    }

}

