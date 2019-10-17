package seedu.savenus.logic.commands;

import seedu.savenus.model.Model;

public class CollapseCommand extends Command {

    public static final String COMMAND_WORD = "collapse";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Collapses or expands the Food item list.\n" +
            "Example: " + COMMAND_WORD;

    public static boolean COLLAPSE = false;

    public static final String COLLAPSE_COMMAND_SUCCESS = "The list of items has been collapsed.";

    public static final String EXPAND_COMMAND_SUCCESS = "The list of items has been expanded";

    @Override
    public CommandResult execute(Model model) {
        COLLAPSE = !COLLAPSE;
        if (COLLAPSE) {
            return new CommandResult(COLLAPSE_COMMAND_SUCCESS, false, false, false);
        } else {
            return new CommandResult(EXPAND_COMMAND_SUCCESS, false, false, false);
        }
    }
}
