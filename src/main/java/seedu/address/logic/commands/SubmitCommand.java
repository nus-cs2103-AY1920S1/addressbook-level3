package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_COMPLETE_INCIDENT_REPORTS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.incident.Incident;

//@@author atharvjoshi
/**
 * Submits a completed incident report, if specified, otherwise lists all completed incident reports ready
 * for submission.
 */
public class SubmitCommand extends Command {

    public static final String COMMAND_WORD = "submit";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Submits completed incident reports.\n"
            + "Use " + COMMAND_WORD + " without parameters to list all completed reports.\n"
            + "Use " + COMMAND_WORD + " with parameters:"
            + COMMAND_WORD + " INDEX (positive integer) to submit the specified draft.";

    public static final String MESSAGE_SUBMIT_SUCCESS = "New incident report submitted: %1$s";

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
            throw new CommandException(Messages.MESSAGE_NO_INCIDENT_TO_SUBMIT);
        }

        // there are drafts to be submitted. Get the last shown list.
        List<Incident> lastShownList = model.getFilteredIncidentList();

        // throw exception if index specified is out of bounds
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_INCIDENT_INDEX);
        }

        // retrieve incident and try to submit it
        Incident toSubmit = lastShownList.get(targetIndex.getZeroBased());
        assert toSubmit != null : "Retrieved incident must not be null.";

        // check if operating executing the command is the same as the operator who created this draft
        if (!model.canLoggedInPersonModifyIncidentStatus(toSubmit)) {
            throw new CommandException(Messages.MESSAGE_NO_ACCESS_TO_SUBMIT_REPORT);
        }

        return new CommandResult(processReportSubmission(model, toSubmit));
    }

    /**
     * Processes submission of given incident report. The incident report is submitted if it is a complete draft and
     * not submitted if it is an incomplete draft or if it has already been submitted.
     * @param model current model to be modified by command
     * @param toSubmit incident report to be submitted
     * @return the string representing the command result
     */
    private String processReportSubmission(Model model, Incident toSubmit) throws CommandException {
        // if incident is not a complete draft, do not allow submission
        if (toSubmit.isIncompleteDraft()) {
            throw new CommandException(Messages.MESSAGE_DRAFT_IS_INCOMPLETE);
        } else if (toSubmit.isSubmitted()) {
            throw new CommandException(Messages.MESSAGE_INCIDENT_HAS_BEEN_SUBMITTED);
        } else if (!toSubmit.areAllFieldsFilled()) {
            // defensive programming
            // confirm that the incident marked as 'complete' is actually fully filled
            throw new CommandException(Messages.MESSAGE_REPORT_COMPLETE_BUT_UNFILLED);
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
     * @param toSubmit the incident report to be submitted.
     * @return updated incident report.
     */
    private Incident submitReport(Incident toSubmit) {
        assert toSubmit.isCompleteDraft() : "Only completed incidents can be submitted.";
        return new Incident(toSubmit.getOperator(), toSubmit.getDistrict(),
                toSubmit.getIncidentDateTime(), toSubmit.getIncidentId(), toSubmit.getCallerNumber(),
                toSubmit.getDesc(), Incident.Status.SUBMITTED_REPORT, toSubmit.getVehicle());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SubmitCommand // instanceof handles nulls
                && targetIndex.equals(((SubmitCommand) other).targetIndex));
    }
}
