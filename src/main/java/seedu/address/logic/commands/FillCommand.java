package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CALLER_NUMBER;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.incident.Incident;

/**
 * Generates a new incident report.
 */
public class FillCommand extends Command {

    public static final String COMMAND_WORD = "fill";

    // TODO - add params description, district, callerNumber if we are going with single-step fill instead of prompts
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an incident report to the incident management "
            + "system." + "Parameters: "
            + PREFIX_CALLER_NUMBER + "CALLER NUMBER ";

    public static final String MESSAGE_SUCCESS = "New incident report added: %1$s";
    public static final String MESSAGE_DUPLICATE_REPORT = "This report already exists in the incident "
            + "management system";

    private final Incident newIncident;

    /**
     * Creates a FillCommand to generate a new {@code Incident}
     */
    public FillCommand(Incident newIncident) {
        requireNonNull(newIncident);
        this.newIncident = newIncident;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasIncident(newIncident)) {
            throw new CommandException(MESSAGE_DUPLICATE_REPORT);
        }

        model.addIncident(newIncident);
        return new CommandResult(String.format(MESSAGE_SUCCESS, newIncident));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FillCommand // instanceof handles nulls
                && newIncident.equals(((FillCommand) other).newIncident));
    }
}
