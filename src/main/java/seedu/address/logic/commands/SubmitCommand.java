package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import javafx.collections.transformation.FilteredList;
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
            + COMMAND_WORD + " i/INDEX (positive integer) to submit the specified draft.";

    public static final String MESSAGE_SUBMIT_SUCCESS = "New incident report submitted: %1$s";
    public static final String MESSAGE_COMPLETED_REPORTS_LISTED_SUCCESS = "Completed incident reports listed!";
    public static final String MESSAGE_NO_COMPLETED_REPORTS_TO_SUBMIT = "No reports ready for submission!";

    private final Index targetIndex;

    /**
     * Constructor for submit command without parameters.
     */
    public SubmitCommand() {
        this.targetIndex = null;
    }

    /**
     * Constructor for submit command with parameters.
     * @param index {@code Index} of completed report to submit.
     */
    public SubmitCommand(Index index) {
        this.targetIndex = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // predicate to obtain completed incident reports
        Predicate<Incident> predicate = Incident::isComplete;

        // show filtered drafts
        model.updateFilteredIncidentList(predicate);

        // get filtered list
        FilteredList<Incident> temp = model.getFilteredIncidentList().filtered(predicate);

        if (temp.isEmpty()) {
            return new CommandResult(MESSAGE_NO_COMPLETED_REPORTS_TO_SUBMIT);
        }

        // fill chosen draft
        if (this.targetIndex != null) {

            if (targetIndex.getZeroBased() >= temp.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_INCIDENT_INDEX);
            }

            Incident toSubmit = temp.get(targetIndex.getZeroBased());
            Incident updatedIncident = Incident.submitReport(toSubmit);
            model.setIncident(toSubmit, updatedIncident);

            return new CommandResult(String.format(MESSAGE_SUBMIT_SUCCESS, toSubmit));
        }

        return new CommandResult(MESSAGE_COMPLETED_REPORTS_LISTED_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SubmitCommand // instanceof handles nulls
                && targetIndex.equals(((SubmitCommand) other).targetIndex));
    }
}
