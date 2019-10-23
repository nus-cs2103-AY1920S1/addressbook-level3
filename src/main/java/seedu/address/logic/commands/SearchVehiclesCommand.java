package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.SEARCH_PREFIX_DISTRICT;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.incident.Incident;
import seedu.address.model.vehicle.DistrictKeywordsPredicate;
import seedu.address.model.vehicle.Vehicle;

/**
 * Finds and lists all incidents in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class SearchVehiclesCommand extends Command {

    public static final String COMMAND_WORD = "vSearch";

    /**
     * Searches by district now. TODO: search by number and type
     */
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Searches for vehicles by district "
            + "in the same district as entered by user. \n"
            + "Parameters: "
            + SEARCH_PREFIX_DISTRICT + "districtNumber";

    private final Predicate<Vehicle> predicate;
    private Incident draft; // only if this command is used by NewCommand
    private boolean isAuto;

    /**
     * Used only to add vehicles to newly created incidents.
     * @param draft
     */
    public SearchVehiclesCommand(Incident draft, boolean isAuto) {
        this.draft = draft;
        this.predicate = new DistrictKeywordsPredicate(draft.getDistrict());
        this.isAuto = isAuto;
    }

    /**
     * Used only when simply listing nearby vehicles.
     * @param districtKeywordsPredicate
     */
    public SearchVehiclesCommand(DistrictKeywordsPredicate districtKeywordsPredicate) {
        this.predicate = districtKeywordsPredicate;
    }

    /**
     * Automatically assigns vehicle to newly created incident reports.
     * @param model
     */
    public void autoAssign(Model model) {
        ObservableList<Vehicle> allVehicles = model.getFilteredVehicleList();
        ObservableList<Vehicle> nearbyVehicles = allVehicles.filtered(vehicle -> {
            if (vehicle.getDistrict() == draft.getDistrict()) {
                return true;
            }
            return false;
        });
        Vehicle vehicle = nearbyVehicles.get(0);
        draft.addVehicle(vehicle);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        // if vehicle assignment is involved
        if (this.draft != null && isAuto) {
            autoAssign(model);
        }

        // will list regardless
        model.updateFilteredVehicleList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_VEHICLES_LISTED_OVERVIEW, model.getFilteredVehicleList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SearchVehiclesCommand // instanceof handles nulls
                && (draft == ((SearchVehiclesCommand) other).draft)); // state check
    }
}
