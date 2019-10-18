package seedu.savenus.logic.commands;

import seedu.savenus.model.Model;

/**
 * Makes the food card more compact.
 */
public class CollapseCommand extends Command {

    public static final String COMMAND_WORD = "collapse";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Collapses the Food item list.\n"
            + "Example: " + COMMAND_WORD;

    public static final String COLLAPSE_COMMAND_SUCCESS = "The list of items has been collapsed.";

    public static final String COLLAPSE_COMMAND_FAIL = "The list of items is already collapsed!";

    private static boolean isCollapse = true;

    /**
     * Used to obtain the status of food card.
     * @return Collapse status.
     */
    public static boolean getStatus() {
        return isCollapse;
    }

    /**
     * Reverses the status of the food card.
     */
    public static void setStatus() {
        isCollapse = !isCollapse;
    }

    @Override
    public CommandResult execute(Model model) {
        if (!isCollapse) {
            setStatus();
            ExpandCommand.setStatus();
            return new CommandResult(COLLAPSE_COMMAND_SUCCESS, false, false, false);
        } else {
            return new CommandResult(COLLAPSE_COMMAND_FAIL, false, false, false);
        }
    }
}
