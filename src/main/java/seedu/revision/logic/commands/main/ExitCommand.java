package seedu.revision.logic.commands.main;

import seedu.revision.logic.commands.Command;
import seedu.revision.model.Model;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting quiz as requested ...";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult().withFeedBack(MESSAGE_EXIT_ACKNOWLEDGEMENT).withHelp(false).withExit(true).build();
    }

}
