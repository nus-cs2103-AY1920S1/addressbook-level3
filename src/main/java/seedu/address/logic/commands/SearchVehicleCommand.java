package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;

import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.incident.Incident;
import seedu.address.model.vehicle.District;
import seedu.address.model.vehicle.Vehicle;


/**
 * Finds and lists all incidents in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class SearchVehicleCommand extends Command {

    public static final String COMMAND_WORD = "vSearch";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Searches for vehicles "
            + "in the same district as entered by user. \n"
            + "Parameters: "
            + PREFIX_LOCATION + "districtNumber";

    private final District district;
    private Incident draft; // only if this command is used by NewCommand
    private boolean isAuto;

    /**
     * Used to add vehicles to newly created incidents.
     * @param draft
     */
    public SearchVehicleCommand(Incident draft, boolean isAuto) {
        this.draft = draft;
        this.district = draft.getDistrict();
    }

    /**
     * Used only when simply listing nearby vehicles.
     * @param district
     */
    public SearchVehicleCommand(District district) {
        this.district = district;
    }

    /**
     * Assigns vehicle to newly created incident reports.
     * @param nearbyVehicles
     * @param isAuto
     */
    public void assignVehicle(ObservableList<Vehicle> nearbyVehicles, boolean isAuto) {
        if (isAuto) {
            Vehicle vehicle = nearbyVehicles.get(0);
            draft.addVehicle(vehicle);
        } else {
            // just list
        }

    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        ObservableList<Vehicle> allVehicles = model.getFilteredVehicleList();
        ObservableList<Vehicle> nearbyVehicles = allVehicles.filtered(vehicle -> {
            if (vehicle.getDistrict() == district) {
                return true;
            }
            return false;
        });

        // if vehicle assignment is involved
        if (this.draft != null) {
            assignVehicle(nearbyVehicles, isAuto);
        }

        // vehicle assignment not involved; just list
        return new CommandResult(
                String.format(Messages.MESSAGE_VEHICLES_LISTED_OVERVIEW, nearbyVehicles.size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SearchVehicleCommand // instanceof handles nulls
                && (district == ((SearchVehicleCommand) other).district)); // state check
    }
}
