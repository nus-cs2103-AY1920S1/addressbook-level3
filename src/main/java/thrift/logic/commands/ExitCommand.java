package thrift.logic.commands;

import thrift.model.Model;

/**
 * Terminates the program.
 */
public class ExitCommand extends NonScrollingCommand {

    public static final String COMMAND_WORD = "exit";

    public static final String HELP_MESSAGE = COMMAND_WORD + ": Exit THRIFT application.\n"
            + "Format: " + COMMAND_WORD;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exit THRIFT application.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting THRIFT as requested ...";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true);
    }

}
