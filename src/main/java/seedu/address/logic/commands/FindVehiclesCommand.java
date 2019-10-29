package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.SEARCH_PREFIX_DISTRICT;

import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.incident.Incident;
import seedu.address.model.vehicle.DistrictKeywordsPredicate;
import seedu.address.model.vehicle.VNumKeywordsPredicate;
import seedu.address.model.vehicle.VTypeKeywordsPredicate;
import seedu.address.model.vehicle.Vehicle;
import seedu.address.model.vehicle.exceptions.VehicleNotFoundException;

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
            + SEARCH_PREFIX_DISTRICT + "[district numbers separated by whitespace]";

    private final Predicate<Vehicle> predicate;
    private Incident draft; // only if this command is used by NewCommand
    private boolean isAuto;

    /**
     * Used only to add vehicles to newly created incidents.
     * @param draft
     */
    public FindVehiclesCommand(Incident draft, boolean isAuto) {
        this.draft = draft;
        this.predicate = new DistrictKeywordsPredicate(List.of(draft.getDistrict()));
        this.isAuto = isAuto;
    }

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
     * Automatically assigns vehicle to newly created incident reports.
     * @param model
     */
    public void autoAssign(Model model) {
        assert(this.draft != null && isAuto);
        ObservableList<Vehicle> nearbyVehicles = model.getFilteredVehicleList()
                .filtered(vehicle -> vehicle.getDistrict().equals(draft.getDistrict())
        );

        if (nearbyVehicles.size() > 0) {
            Vehicle vehicle = nearbyVehicles.get(0);
            draft.addVehicle(vehicle);
        } else if (nearbyVehicles.size() == 0) {
            throw new VehicleNotFoundException();
        }

        model.updateFilteredVehicleList(predicate);
    }

    /**
     * Only called when listing.
     * @param model {@code Model} which the command should operate on.
     * @return
     */
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        model.updateFilteredVehicleList(predicate);

        if (this.draft != null && isAuto) {
            // TODO: Messages.PROMPT_FOR_V
            return new CommandResult("prompt for v");
        }

        return new CommandResult(
                String.format(Messages.MESSAGE_VEHICLES_LISTED_OVERVIEW, model.getFilteredVehicleList().size()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindVehiclesCommand // instanceof handles nulls
                && (draft != null && (draft == ((FindVehiclesCommand) other).draft)
                || predicate == ((FindVehiclesCommand) other).predicate)); // state check
    }
}
