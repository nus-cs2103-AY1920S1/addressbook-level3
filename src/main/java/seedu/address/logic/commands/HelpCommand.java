package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_UNUSED_ARGUMENT;

import seedu.address.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "Help window opened.";
    private String unusedArguments = null;

    public HelpCommand(String unusedArguments) {
        if (!unusedArguments.equals("")) {
            this.unusedArguments = unusedArguments;
        }
    }

    public HelpCommand() {}

    @Override
    public CommandResult execute(Model model) {
        if (unusedArguments != null) {
            return CommandResult.commandResultHelp(String.format(SHOWING_HELP_MESSAGE
                    + MESSAGE_UNUSED_ARGUMENT, unusedArguments, COMMAND_WORD));
        }
        return CommandResult.commandResultHelp(SHOWING_HELP_MESSAGE);
    }
}
