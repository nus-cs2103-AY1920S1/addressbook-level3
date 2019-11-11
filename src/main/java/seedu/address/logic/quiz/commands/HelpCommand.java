package seedu.address.logic.quiz.commands;

import seedu.address.model.quiz.Model;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    public static final String LIST_OF_INSTRUCTIONS = "add, clear, delete, detail, edit, exit, find, help, list,"
            + " redo, switch, undo, showAnswer";
    public static final String SHOWING_HELP_MESSAGE = "Opened help window.\n"
            + "List of instructions for quiz are as follows: " + LIST_OF_INSTRUCTIONS;

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(SHOWING_HELP_MESSAGE, true, false);
    }
}
