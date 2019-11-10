package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DISTRICT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VNUM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VTYPE;

import java.util.function.Predicate;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.vehicle.DistrictKeywordsPredicate;
import seedu.address.model.vehicle.VNumKeywordsPredicate;
import seedu.address.model.vehicle.VTypeKeywordsPredicate;
import seedu.address.model.vehicle.Vehicle;

/**
 * Finds and lists all incidents in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindVehiclesCommand extends Command {

    public static final String COMMAND_WORD = "find-v";

    /**
     * Searches by district now.
     */
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Searches for vehicles by district "
            + "in the same district as entered by user. \n"
            + "Parameters: "
            + PREFIX_DISTRICT + "[district numbers separated by whitespace] \n"
            + PREFIX_VNUM + "vehicle number \n"
            + PREFIX_VTYPE + "vehicle type";

    private final Predicate<Vehicle> predicate;

    /**
     * Used only when simply listing nearby vehicles.
     * @param districtKeywordsPredicate
     */
    public FindVehiclesCommand(DistrictKeywordsPredicate districtKeywordsPredicate) {
        this.predicate = districtKeywordsPredicate;
    }

    /**
     * Used only when simply listing nearby vehicles.
     * @param vTypeKeywordsPredicate
     */
    public FindVehiclesCommand(VTypeKeywordsPredicate vTypeKeywordsPredicate) {
        this.predicate = vTypeKeywordsPredicate;
    }

    /**
     * Used only when simply listing nearby vehicles.
     * @param vNumKeywordsPredicate
     */
    public FindVehiclesCommand(VNumKeywordsPredicate vNumKeywordsPredicate) {
        this.predicate = vNumKeywordsPredicate;
    }

    /**
     * Only called for normal listing.
     * @param model {@code Model} which the command should operate on.
     * @return
     */
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        model.updateFilteredVehicleList(predicate);

        if (model.getFilteredVehicleList().size() == 0) {
            return new CommandResult(Messages.MESSAGE_NO_VEHICLES_FOUND);
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
                || (other instanceof FindVehiclesCommand // instanceof handles nulls
                && predicate.equals(((FindVehiclesCommand) other).predicate)); // state check
    }
}
