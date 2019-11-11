package seedu.ifridge.logic.commands.wastelist;

import seedu.ifridge.logic.commands.Command;
import seedu.ifridge.logic.commands.CommandResult;
import seedu.ifridge.logic.commands.exceptions.CommandException;
import seedu.ifridge.model.Model;

/**
 * Displays which food items the user most commonly wastes.
 */
public class FoodWasteCommand extends Command {

    public static final String COMMAND_WORD = "food";

    public static final String MESSAGE_USAGE = "wlist " + COMMAND_WORD
            + ": Shows you the food you tend to most commonly waste.\n"
            + "Example: wlist " + COMMAND_WORD;

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        /*
        v1.3 or v1.4
        Not a very high priority. Use Frequency Analyser from Kumos library.
         */
        return null;
    }
}
