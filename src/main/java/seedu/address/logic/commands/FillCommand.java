package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CALLER_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;

import java.util.function.Predicate;

import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.incident.CallerNumber;
import seedu.address.model.incident.Description;
import seedu.address.model.incident.Incident;

/**
 * Generates a new incident report.
 */
public class FillCommand extends Command {

    public static final String COMMAND_WORD = "fill";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Fills incident report drafts.\n"
            + "Use " + COMMAND_WORD + " without parameters to list all drafts.\n"
            + "Use " + COMMAND_WORD + " with parameters: "
            + COMMAND_WORD + " i/INDEX (positive integer) " + PREFIX_CALLER_NUMBER + "CALLER NUMBER "
            + PREFIX_DESCRIPTION + "DESCRIPTION " + "to fill a given draft.\n"
            + "Existing completed reports will be overwritten. Submitted reports can be edited using 'edit' command.";

    public static final String MESSAGE_FILL_DRAFT_SUCCESS = "Incident report filled: %1$s";
    public static final String MESSAGE_DRAFTS_LISTED_SUCCESS = "Incident report drafts listed!";
    public static final String MESSAGE_NO_DRAFTS_TO_FILL = "No drafts present in the system!";

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

    /**
     * Creates a FillCommand to list {@code Incident} drafts.
     */
    // TODO merge with listIncident command in the future, remove null assignments.
    public FillCommand() {
        this.targetIndex = null;
        this.callerNumber = null;
        this.description = null;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // predicate to obtain draft incident reports
        Predicate<Incident> predicate = Incident::isDraft;

        // show filtered drafts
        model.updateFilteredIncidentList(predicate);

        // get filtered list
        FilteredList<Incident> temp = model.getFilteredIncidentList().filtered(predicate);

        if (temp.isEmpty()) {
            return new CommandResult(MESSAGE_NO_DRAFTS_TO_FILL);
        }

        // fill chosen draft
        if (this.targetIndex != null && this.callerNumber != null && this.description != null) {

            if (targetIndex.getZeroBased() >= temp.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_INCIDENT_INDEX);
            }

            Incident toFill = temp.get(targetIndex.getZeroBased());
            Incident updatedIncident = Incident.updateReport(toFill, callerNumber, description);
            model.setIncident(toFill, updatedIncident);
            return new CommandResult(String.format(MESSAGE_FILL_DRAFT_SUCCESS, toFill));
        }

        return new CommandResult(MESSAGE_DRAFTS_LISTED_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FillCommand // instanceof handles nulls
                && callerNumber.equals(((FillCommand) other).callerNumber)
                && description.equals(((FillCommand) other).description));
    }
}
