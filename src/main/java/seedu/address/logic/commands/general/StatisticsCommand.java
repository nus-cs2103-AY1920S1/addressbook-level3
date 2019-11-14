package seedu.address.logic.commands.general;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

/**
 * Command to jump tab to the schedule tab
 */
public class StatisticsCommand extends Command {

    public static final String COMMAND_WORD = "statistics";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": jumps to the Statistics Tab\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Currently on the Statistics Tab";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_SUCCESS, "Statistics_Tab");
    }
}
