package thrift.logic.commands;

import thrift.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends NonScrollingCommand {

    public static final String COMMAND_WORD = "help";

    public static final String HELP_MESSAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Valid commands are: add_expense, add_income, budget, clone, convert, delete, find, help, "
            + "list, redo, tag, undo, untag, update.\n"
            + "Format: " + COMMAND_WORD + " [COMMAND]\n"
            + "Possible usages of " + COMMAND_WORD + ": \n"
            + "To view the usage of all possible commands: " + COMMAND_WORD + "\n"
            + "To view the usage of a specific command: " + COMMAND_WORD + " add_income";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Parameters: [COMMAND]\n"
            + "Example: " + COMMAND_WORD + " " + AddExpenseCommand.COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";

    private String commandUsage;

    public HelpCommand(String commandUsage) {
        this.commandUsage = commandUsage;
    }

    @Override
    public CommandResult execute(Model model) {
        if (commandUsage.equals("")) {
            return new CommandResult(SHOWING_HELP_MESSAGE, true, false);
        } else {
            return new CommandResult(commandUsage);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof HelpCommand // instanceof handles nulls
                && commandUsage.equals(((HelpCommand) other).commandUsage)); // state check
    }
}
