package seedu.address.logic.commands;

import seedu.address.logic.commands.result.CommandResult;
import seedu.address.logic.commands.util.HelpExplanation;
import seedu.address.model.Model;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";
    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Address Book as requested ...";

    public static final HelpExplanation MESSAGE_USAGE = new HelpExplanation(
            COMMAND_WORD,
            "Exits from the program.",
            COMMAND_WORD,
            COMMAND_WORD
    );

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true);
    }

}
