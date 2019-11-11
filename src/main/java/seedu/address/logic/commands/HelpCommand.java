package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";

    @Override
    public CommandResult execute(Model model) {
        // Print out all the add command message.
        CommandUtils.ALL_COMMANDS_IN_HELP.stream().forEach(tuple -> {
            System.out.println(tuple.getZero());
            System.out.println(tuple.getOne());
        });

        model.updateHistory(this);
        model.recordCommandExecution(this.getCommandInputString());
        return new CommandResult(SHOWING_HELP_MESSAGE, true, false);
    }
}
