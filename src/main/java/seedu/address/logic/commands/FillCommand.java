package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CALLER_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;

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

    // TODO - add params description, district, callerNumber if we are going with single-step fill instead of prompts
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an incident report to the incident management "
            + "system." + "Parameters: "
            + PREFIX_CALLER_NUMBER + "CALLER NUMBER "
            + PREFIX_DESCRIPTION + "DESCRIPTION ";

    public static final String MESSAGE_SUCCESS = "New incident report added: %1$s";
    public static final String MESSAGE_DUPLICATE_REPORT = "This report already exists in the incident "
            + "management system";

    private final CallerNumber callerNumber;
    private final Description description;

    /**
     * Creates a FillCommand to generate a new {@code Incident}
     */
    public FillCommand(CallerNumber callerNumber, Description description) {
        requireAllNonNull(callerNumber, description);
        this.callerNumber = callerNumber;
        this.description = description;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // todo
        return null;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FillCommand // instanceof handles nulls
                && callerNumber.equals(((FillCommand) other).callerNumber)
                && description.equals(((FillCommand) other).description));
    }
}
