package mams.logic.commands;

import static mams.commons.util.CollectionUtil.requireAllNonNull;
import static mams.logic.history.InputOutput.PREDICATE_SHOW_ALL;
import static mams.logic.history.InputOutput.PREDICATE_SHOW_ONLY_SUCCESSFUL;
import static mams.logic.parser.HistoryCommandParser.OPTION_HIDE_OUTPUT;
import static mams.logic.parser.HistoryCommandParser.OPTION_HIDE_UNSUCCESSFUL;

import mams.logic.history.FilterOnlyCommandHistory;
import mams.logic.history.HistoryFilterSettings;
import mams.model.Model;

/**
 * Opens a new tab displaying the command history.
 */
public class HistoryCommand extends Command {

    public static final String COMMAND_WORD = "history";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays the command history window, with several display options.\n"
            + "Parameters: KEYWORD "
            + "[" + OPTION_HIDE_OUTPUT + "] "
            + "[" + OPTION_HIDE_UNSUCCESSFUL + "]\n"
            + "Example: " + COMMAND_WORD + " "
            + OPTION_HIDE_UNSUCCESSFUL + " " + OPTION_HIDE_OUTPUT;

    public static final String SHOWING_HISTORY_MESSAGE = "Opened command history window.";
    public static final String HIDING_COMMAND_OUTPUT_MESSAGE = "All command outputs have been hidden.";
    public static final String SHOW_ONLY_SUCCESSFUL_MESSAGE = "Only successful commands will be shown.";

    private final boolean hideCommandFeedback;
    private final HistoryFilterSettings displaySettings;

    /**
     * Constructs a HistoryCommand object. The reason why {@code displayOptions} is not of type boolean
     * is to allow of addition of more options in the future (eg. hide all unsuccessful)
     * @param hideCommandFeedback whether command feedback (output) will be hidden in the window.
     * @param displaySettings filtering options for the shown history
     */
    public HistoryCommand(boolean hideCommandFeedback, HistoryFilterSettings displaySettings) {
        this.hideCommandFeedback = hideCommandFeedback;
        this.displaySettings = displaySettings;
    }

    @Override
    public CommandResult execute(Model model, FilterOnlyCommandHistory commandHistory) {
        // this command requires commandHistory to be non-null
        requireAllNonNull(model, commandHistory);
        StringBuilder userFeedback = new StringBuilder(SHOWING_HISTORY_MESSAGE);

        if (hideCommandFeedback) {
            userFeedback.append("\n" + HIDING_COMMAND_OUTPUT_MESSAGE);
        }

        switch (displaySettings) {
        case SHOW_ALL:
            commandHistory.updateFilteredCommandHistory(PREDICATE_SHOW_ALL);
            break;
        case SHOW_ONLY_SUCCESSFUL:
            commandHistory.updateFilteredCommandHistory(PREDICATE_SHOW_ONLY_SUCCESSFUL);
            userFeedback.append("\n" + SHOW_ONLY_SUCCESSFUL_MESSAGE);
            break;
        default:
            break;
        }
        return new CommandResult(userFeedback.toString().trim(), true, hideCommandFeedback,
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
        return hideCommandFeedback == v.hideCommandFeedback
                && displaySettings == v.displaySettings;
    }
}
