package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_COMPLETE_INCIDENT_REPORTS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.incident.Incident;

/**
 * Terminates the program.
 */
public class SubmitCommand extends Command {

    public static final String COMMAND_WORD = "submit";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Submits completed incident reports.\n"
            + "Use " + COMMAND_WORD + " without parameters to list all completed reports.\n"
            + "Use " + COMMAND_WORD + " with parameters:"
            + COMMAND_WORD + " INDEX (positive integer) to submit the specified draft.";

    public static final String MESSAGE_SUBMIT_SUCCESS = "New incident report submitted: %1$s";
    public static final String MESSAGE_NO_COMPLETED_REPORTS_TO_SUBMIT = "No reports ready for submission";
    public static final String MESSAGE_DRAFT_IS_INCOMPLETE = "This draft is incomplete and is not ready for submission"
            + " Please use the 'Fill' command to first complete the report";
    public static final String MESSAGE_REPORT_HAS_BEEN_SUBMITTED = "This report has already been submitted";

    private final Index targetIndex;

    /**
     * Constructor for submit command with parameters.
     * @param index {@code Index} of completed report to submit.
     */
    public SubmitCommand(Index index) {
        requireNonNull(index);
        this.targetIndex = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.ifAnyIncidentsSatisfyPredicate(PREDICATE_SHOW_COMPLETE_INCIDENT_REPORTS)) {
            return new CommandResult(MESSAGE_NO_COMPLETED_REPORTS_TO_SUBMIT);
        }

        // there are drafts to be submitted. Get the last shown list.
        List<Incident> lastShownList = model.getFilteredIncidentList();

        // throw exception if index specified is out of bounds
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_INCIDENT_INDEX);
        }

        // retrieve incident and try to submit it
        Incident toSubmit = lastShownList.get(targetIndex.getZeroBased());
        return new CommandResult(processReportSubmission(model, toSubmit));
    }

    /**
     * Processes submission of given incident report. The incident report is submitted if it is a complete draft and
     * not submitted if it is an incomplete draft or if it has already been submitted.
     * @param model current model to be modified by command
     * @param toSubmit incident report to be submitted
     * @return the string representing the command result
     */
    private String processReportSubmission(Model model, Incident toSubmit) {
        // if incident is not a complete draft, do not allow submission
        if (toSubmit.isIncompleteDraft()) {
            return MESSAGE_DRAFT_IS_INCOMPLETE;
        } else if (toSubmit.isSubmitted()) {
            return MESSAGE_REPORT_HAS_BEEN_SUBMITTED;
        }

        // submit incident as it is a complete draft
        Incident updatedIncident = submitReport(toSubmit);

        // old incident is removed from the list, and replaced by the updated incident
        // updated incident is appended to front of the list
        model.removeIncident(toSubmit);
        model.addIncident(updatedIncident);
        return String.format(MESSAGE_SUBMIT_SUCCESS, toSubmit);
    }

    /**
     * Submits specified incident by returning a new incident which is a copy of the old incident with 'Submitted'
     * status.
     * Triggered by 'submit' command.
     * @param toSubmit the incident report to be submitted.
     * @return updated incident report.
     */
    public Incident submitReport(Incident toSubmit) {
        Incident updatedIncident = new Incident(toSubmit.getOperator(), toSubmit.getDistrict(),
                toSubmit.getIncidentDateTime(), toSubmit.getIncidentId(), toSubmit.getCallerNumber(),
                toSubmit.getDesc(), Incident.Status.SUBMITTED_REPORT, toSubmit.getVehicle()); // set status to submitted
        return updatedIncident;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SubmitCommand // instanceof handles nulls
                && targetIndex.equals(((SubmitCommand) other).targetIndex));
    }
}
