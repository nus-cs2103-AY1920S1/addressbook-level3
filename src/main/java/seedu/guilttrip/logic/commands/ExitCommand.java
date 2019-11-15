package seedu.guilttrip.logic.commands;

import seedu.guilttrip.logic.CommandHistory;
import seedu.guilttrip.model.Model;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";
    public static final String ONE_LINER_DESC = COMMAND_WORD + ": Terminates guiltTrip.";
    public static final String MESSAGE_USAGE = ONE_LINER_DESC;

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting GuiltTrip as requested ...";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true);
    }

}
