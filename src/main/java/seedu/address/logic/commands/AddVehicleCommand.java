package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AVAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DISTRICT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VNUM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VTYPE;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.vehicle.Availability;
import seedu.address.model.vehicle.Vehicle;

/**
 * Adds a vehicle to the IMS.
 */
public class AddVehicleCommand extends Command {

    public static final String COMMAND_WORD = "add-v";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " Adds a vehicle to the Incident Managment System.\n"
            + "Parameters: "
            + "[" + PREFIX_DISTRICT + "DISTRICT] & "
            + "[" + PREFIX_VNUM + "[VEHICLE NUMBER] & "
            + "[" + PREFIX_VTYPE + "[VEHICLE TYPE (Only Ambulance/Patrol Car)] & "
            + "[" + PREFIX_AVAIL + "[AVAILABILITY( " + Availability.MESSAGE_CONSTRAINTS + ")] \n"
            + "Example: " + COMMAND_WORD
            + " " + PREFIX_DISTRICT + "2 "
            + " " + PREFIX_VTYPE + "Ambulance "
            + " " + PREFIX_VNUM + "SFD5903S";

    public static final String MESSAGE_ADD_VEHICLE_SUCCESS = "New vehicle added: %1$s";
    public static final String MESSAGE_DUPLICATE_VEHICLE = "This vehicle already exists in the system.";

    private Vehicle toAdd;

    public AddVehicleCommand(Vehicle vehicle) {
        requireNonNull(vehicle);
        this.toAdd = vehicle;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (model.hasVehicle(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_VEHICLE);
        }
        if (model.hasVNum(toAdd.getVehicleNumber().toString())) {
            throw new CommandException(Messages.MESSAGE_DUPLICATE_VEHICLE_NUMBER);
        }

        model.addVehicle(toAdd);
        return new CommandResult(String.format(MESSAGE_ADD_VEHICLE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AddVehicleCommand)) {
            return false;
        }
        return toAdd.equals(((AddVehicleCommand) other).toAdd);
    }

}

