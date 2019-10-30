package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CALLER_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.model.Model.PREDICATE_SHOW_DRAFT_INCIDENT_REPORTS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.incident.CallerNumber;
import seedu.address.model.incident.Description;
import seedu.address.model.incident.Incident;

/**
 * Fills a draft incident report, if specified, otherwise lists all draft incident reports ready for filling.
 */
public class FillCommand extends Command {

    public static final String COMMAND_WORD = "fill";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Fills incident report drafts.\n"
            + "Use " + COMMAND_WORD + " without parameters to list all drafts.\n"
            + "Use " + COMMAND_WORD + " with parameters: "
            + COMMAND_WORD + " INDEX (must be a positive integer) " + PREFIX_CALLER_NUMBER + "CALLER NUMBER "
            + PREFIX_DESCRIPTION + "DESCRIPTION " + "to fill a given draft.\n"
            + "Existing completed reports will be overwritten. Submitted reports can be edited using 'edit' command.";

    private static final String MESSAGE_FILL_DRAFT_SUCCESS = "Incident report filled: %1$s";
    private static final String MESSAGE_NO_DRAFTS_TO_FILL = "No drafts present in the system";
    private static final String MESSAGE_INCIDENT_HAS_BEEN_SUBMITTED = "This incident cannot be filled as it has been"
            + " submitted. Please use the 'edit' command instead.";

    private final Index targetIndex;
    private final CallerNumber callerNumber;
    private final Description description;

    /**
     * Creates a FillCommand to fill a draft {@code Incident}.
     */
    public FillCommand(Index index, CallerNumber callerNumber, Description description) {
        requireAllNonNull(index, callerNumber, description);
        this.targetIndex = index;
        this.callerNumber = callerNumber;
        this.description = description;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.ifAnyIncidentsSatisfyPredicate(PREDICATE_SHOW_DRAFT_INCIDENT_REPORTS)) {
            return new CommandResult(MESSAGE_NO_DRAFTS_TO_FILL);
        }

        // there are drafts to be filled. Get the last shown list.
        List<Incident> lastShownList = model.getFilteredIncidentList();

        // throw exception if index specified is out of bounds
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_INCIDENT_INDEX);
        }

        Incident toFill = lastShownList.get(targetIndex.getZeroBased());
        return new CommandResult(processReportFilling(model, toFill));
    }

    /**
     * Processes filling of the given incident report. The report is filled if it is a draft and not filled if it has
     * already been submitted.
     * @param model current model to be modified by command
     * @param toFill incident report to be filled
     * @return the string representing the command result
     */
    private String processReportFilling(Model model, Incident toFill) {
        // if incident has already been submitted, do not allow update
        if (toFill.isSubmitted()) {
            return MESSAGE_INCIDENT_HAS_BEEN_SUBMITTED;
        }

        // update incident as it is a draft
        Incident updatedIncident = fillReport(toFill, callerNumber, description);

        // old incident is removed from the list, and replaced by the updated incident
        // updated incident is appended to front of the list
        model.removeIncident(toFill);
        model.addIncident(updatedIncident);
        return String.format(MESSAGE_FILL_DRAFT_SUCCESS, toFill);
    }

    /**
     * Returns a new updated incident report by filling callerNumber and description fields.
     * Triggered by 'fill' command.
     * @param toUpdate the incident to be filled.
     * @param callerNumber phone number of the caller reporting the incident.
     * @param description description of this incident.
     * @return updated incident report.
     */
    private Incident fillReport(Incident toUpdate, CallerNumber callerNumber, Description description) {
        Incident updatedIncident = new Incident(toUpdate.getOperator(), toUpdate.getDistrict(),
                toUpdate.getIncidentDateTime(), toUpdate.getIncidentId(), callerNumber, description,
                Incident.Status.COMPLETE_DRAFT, toUpdate.getVehicle()); // set status as complete
        return updatedIncident;
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FillCommand // instanceof handles nulls
                && callerNumber.equals(((FillCommand) other).callerNumber)
                && description.equals(((FillCommand) other).description));
    }
}
