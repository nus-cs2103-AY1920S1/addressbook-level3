package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.deadline.Deadline;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DUEDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;

/**
 * Shows the details of upcoming deadlines.
 */
public class DeadlineCommand extends Command {

    public static final String COMMAND_WORD = "deadline";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Add upcoming deadlines into personal schedule"
            + "Parameters: "
            + PREFIX_TASK + "TASK "
            + PREFIX_DUEDATE + "DUE DATE \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TASK + "Complete CS2100 Test "
            + PREFIX_DUEDATE + "22/10/2019";

    public static final String MESSAGE_SUCCESS = "New Deadline added: %1$s";
    public static final String MESSAGE_DUPLICATE_DEADLINE = "This Deadline already exists in the address book";

    private final Deadline toAdd;


    /**
     * @param deadline entered into schedule with task and due date
     */
    public DeadlineCommand(Deadline deadline) {
        requireNonNull(deadline);
        toAdd = deadline;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasDeadline(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_DEADLINE);
        }

        model.addDeadline(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeadlineCommand // instanceof handles nulls
                && toAdd.equals(((DeadlineCommand) other).toAdd));
    }

    @Override
    public String toString() {
        return toAdd.toString();
    }
}
