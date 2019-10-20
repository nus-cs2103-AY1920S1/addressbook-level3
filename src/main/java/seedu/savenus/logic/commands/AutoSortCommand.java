package seedu.savenus.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.savenus.model.Model;

/**
 * An Extended Command to do Sorting based on user specified custom fields, everytime a user adds a new item.
 */
public class AutoSortCommand extends Command {
    public static final String COMMAND_WORD = "autosort";
    public static final String AUTO_SORT_ON_MESSAGE_SUCCESS = "You have turned auto-sorting on!";
    public static final String AUTO_SORT_OFF_MESSAGE_SUCCESS = "You have turned auto-sorting off!";
    public static final String MESSAGE_ERROR = "Please input a valid flag, either: ON or OFF";

    private boolean shouldAutoSort;

    /**
     * Creates a new AutoSort Command which turns auto-sorting on or off.
     * @param shouldAutoSort the flag to turn auto-sorting on or off.
     */
    public AutoSortCommand(boolean shouldAutoSort) {
        this.shouldAutoSort = shouldAutoSort;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        if (shouldAutoSort) {
            model.setAutoSortFlag(true);
            return new CommandResult(AUTO_SORT_ON_MESSAGE_SUCCESS);
        } else {
            model.setAutoSortFlag(false);
            return new CommandResult(AUTO_SORT_OFF_MESSAGE_SUCCESS);
        }
    }
}
