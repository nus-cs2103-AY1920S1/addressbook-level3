package seedu.savenus.logic.commands;

import seedu.savenus.model.Model;

/**
 * Makes the food card more expanded.
 */
public class ExpandCommand extends Command {

    public static final String COMMAND_WORD = "expand";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Expands the Food item list.\n"
            + "Example: " + COMMAND_WORD;

    public static final String EXPAND_COMMAND_SUCCESS = "The list of items has been expanded.";

    public static final String EXPAND_COMMAND_FAIL = "The list of items is already expanded!";

    private static boolean isExpand = false;

    /**
     * Obtains the status of the food card.
     * @return Expand status.
     */
    public static boolean getStatus() {
        return isExpand;
    }

    /**
     * Reverses the status of the food card.
     */
    public static void setStatus() {
        isExpand = !isExpand;
    }

    @Override
    public CommandResult execute(Model model) {
        if (!isExpand) {
            setStatus();
            CollapseCommand.setStatus();
            return new CommandResult(EXPAND_COMMAND_SUCCESS, false, false, false);
        } else {
            return new CommandResult(EXPAND_COMMAND_FAIL, false, false, false);
        }
    }
}
