package seedu.guilttrip.logic.commands;

import seedu.guilttrip.logic.CommandHistory;
import seedu.guilttrip.logic.commands.exceptions.CommandException;
import seedu.guilttrip.model.Model;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {
    public static final String COMMAND_WORD = "command";
    public static final String ONE_LINER_DESC = COMMAND_WORD + ": If you see this, contact the developers.";
    public static final String MESSAGE_USAGE = ONE_LINER_DESC;

    /**
     * Executes the command and returns the result message.
     *
     * @param model   {@code Model} which the command should operate on.
     * @param history {@code CommandHistory} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute(Model model, CommandHistory history)
            throws CommandException;

    /**
     * @return a one-liner describing the command.
     */
    public static String getOneLinerDesc() {
        return ONE_LINER_DESC;
    }

    /**
     * @return the command word for this command
     */
    public static String getCommandWord() {
        return COMMAND_WORD;
    }

    /**
     * @return the usage string containing explanation, parameters and example.
     */
    public static String getUsage() {
        return MESSAGE_USAGE;
    }
}
