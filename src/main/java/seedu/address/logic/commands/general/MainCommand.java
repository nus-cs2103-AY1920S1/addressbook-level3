package seedu.address.logic.commands.general;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

/**
 * Command to jump tab to the Main tab
 */
public class MainCommand extends Command {

    public static final String COMMAND_WORD = "main";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": jumps to the Main Tab\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Currently on the Main Tab";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_SUCCESS, "Main_Tab");
    }
}
