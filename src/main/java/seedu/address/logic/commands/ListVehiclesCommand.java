package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.address.model.Model;
import seedu.address.model.vehicle.Vehicle;

/**
 * Lists all incidents in the address book to the user.
 */
public class ListVehiclesCommand extends Command {

    public static final String COMMAND_WORD = "list-v";

    public static final String MESSAGE_SUCCESS = "Listed all vehicles";

    private final Predicate<Vehicle> predicate;

    public ListVehiclesCommand(Predicate<Vehicle> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredVehicleList(predicate);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListVehiclesCommand
                && ((predicate == null && ((ListVehiclesCommand) other).predicate == null)
                || (predicate != null && ((ListVehiclesCommand) other).predicate.equals(predicate))));

    }
}
