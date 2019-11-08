package seedu.ezwatchlist.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.ezwatchlist.logic.commands.exceptions.CommandException;
import seedu.ezwatchlist.model.Model;

/**
 *  Go To short cut key for watchlist
 */
public class GoToCommand extends Command {

    public static final String COMMAND_WORD = "";
    public static final String MESSAGE_USAGE = "Go to page: %1$s";
    public static final String MESSAGE_SUCCESS = "Go to page: %1$s";
    public static final String MESSAGE_UNSUCCESSFUL_INPUT = "wrong input";

    private final String shortcut;
    /**
     * Creates an GoToCommand to add the specified {@code shortcut}
     * @param shortcut
     */
    public GoToCommand(String shortcut) {
        requireNonNull(shortcut);
        this.shortcut = shortcut;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        String pageTitle = model.getPage(shortcut);
        return new CommandResult(pageTitle, false, false, true, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GoToCommand // instanceof handles nulls
                && shortcut.equals(((GoToCommand) other).shortcut));
    }

}
