package mams.logic.commands;

import static java.util.Objects.requireNonNull;

import mams.model.Model;

/**
 * Opens a new tab displaying the command history.
 */
public class HistoryCommand extends Command {

    public static final String COMMAND_WORD = "history";

    public static final String SHOWING_HISTORY_MESSAGE = "Opened command history window.";

    private final boolean hideCommandFeedback;

    public HistoryCommand() {
        this.hideCommandFeedback = false;
    }

    public HistoryCommand(boolean hideCommandFeedback) {
        this.hideCommandFeedback = hideCommandFeedback;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        return new CommandResult(SHOWING_HISTORY_MESSAGE, true, hideCommandFeedback,
                false, false);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof HistoryCommand)) {
            return false;
        }

        // state check
        HistoryCommand v = (HistoryCommand) other;
        return hideCommandFeedback == v.hideCommandFeedback;
    }
}
