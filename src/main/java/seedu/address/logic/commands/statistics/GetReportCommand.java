package seedu.address.logic.commands.statistics;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

public class GetReportCommand extends Command {
    public static final String COMMAND_WORD = "report";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Returns a report of how well you have "
            + "answered a particular question,\n"
            + "the number of times the question has been attempted and the past answers to the question.\n"
            + "Parameters: INDEX"
            + "Example: " + COMMAND_WORD + " "
            + "5";

    public static final String MESSAGE_SUCCESS = "Here is a report of the question:";

    private final int index;

    public GetReportCommand(int index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        // need to extract data from storage
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
