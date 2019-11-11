package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Navigates to the specified tab.
 */
public class GoCommand extends Command {

    public static final String DEFAULT_TAB = "default";
    public static final String HOME_TAB = "home";
    public static final String HISTORY_TAB = "history";
    public static final String STATISTIC_TAB = "statistic";

    public static final String COMMAND_WORD = "go";

    public static final String MESSAGE_GO_SUCCESS = "Navigated to %s tab!";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Navigates to the specified tab bar "
            + "the specified keywords (case-insensitive) "
            + "\n"
            + "Parameters: TAB_NAME"
            + "\n"
            + "Example: " + COMMAND_WORD + " history";

    private String tabType;

    public GoCommand(String tabType) {
        this.tabType = tabType;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return new CommandResult(String.format(MESSAGE_GO_SUCCESS, tabType), false, false, tabType);
    }

}
