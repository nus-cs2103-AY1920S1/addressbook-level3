package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AUTO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DISTRICT;

import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.incident.Incident;
import seedu.address.model.person.Person;
import seedu.address.model.vehicle.District;
import seedu.address.model.vehicle.DistrictKeywordsPredicate;

/**
 * Generates a new incident report.
 */
public class NewCommand extends Command {

    public static final String COMMAND_WORD = "new";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Drafts an incident report to the incident management "
            + "system. \n" + "Parameters: "
            + PREFIX_DISTRICT + "DISTRICT_NUMBER "
            + PREFIX_AUTO + "[y/n]";

    public static final String MESSAGE_SUCCESS = "New incident drafted!";

    private final District district;
    private final boolean isAuto;
    // 0 for auto, -1 for prompt needed, 1 ... for allocation, need check oob
    private final int indexOfV;

    private Incident draft;

    /**
     * Creates a NewCommand to generate a new {@code Incident}.
     * If isAuto is false, it means the user did not key in index for vehicle to dispatch.
     */

    public NewCommand(District district, boolean isAuto, int indexOfV) {
        requireNonNull(district);
        this.district = district;
        this.isAuto = isAuto;
        this.indexOfV = indexOfV;
    }

    /**
     * Assigns vehicle directly to draft if isAuto is true.
     * Else, lists nearby vehicles as per normal vehicle search,
     * and expects user to have also keyed in index of vehicle.
     * @param draft
     * @param isAuto
     * @param model
     */
    public void dispatchVehicle(Incident draft, boolean isAuto, Model model) throws CommandException {
        VehicleAssignmentCommand vehicleAssignmentCommand = new VehicleAssignmentCommand(draft, isAuto, indexOfV);
        vehicleAssignmentCommand.execute(model);
    }

    /**
     * Called regardless.
     * @param model {@code Model} which the command should operate on.
     * @return
     * @throws CommandException
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Person operator = model.getLoggedInPerson();
        Incident draft = new Incident(operator, district);
        this.draft = draft; // draft created here because need operator data from model

        /*
        // unnecessary because incidents are compared based on incidentId, which depends on
        // incident datetime, which cannot be the same.
        if (model.hasIncident(draft)) {
            throw new CommandException(MESSAGE_DUPLICATE_REPORT);
        }
        */

        dispatchVehicle(draft, isAuto, model);
        model.addIncident(draft);
        model.updateFilteredVehicleList(new DistrictKeywordsPredicate(List.of(draft.getDistrict())));

        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NewCommand // instanceof handles nulls
                && district.equals(((NewCommand) other).district))
                && isAuto == (((NewCommand) other).isAuto);
    }
}
