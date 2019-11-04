package mams.logic.commands;

import static java.util.Objects.requireNonNull;

import static mams.logic.history.InputOutput.PREDICATE_SHOW_ALL;
import static mams.logic.history.InputOutput.PREDICATE_SHOW_ONLY_SUCCESSFUL;
import static mams.logic.history.InputOutput.PREDICATE_SHOW_ONLY_UNSUCCESSFUL;

import static mams.logic.parser.HistoryCommandParser.OPTION_HIDE_OUTPUT;
import static mams.logic.parser.HistoryCommandParser.OPTION_HIDE_SUCCESSFUL;
import static mams.logic.parser.HistoryCommandParser.OPTION_HIDE_UNSUCCESSFUL;

import mams.logic.commands.exceptions.CommandException;
import mams.logic.history.FilterOnlyCommandHistory;
import mams.logic.history.HistoryFilterSettings;
import mams.model.Model;

/**
 * Opens a new tab displaying the command history.
 */
public class HistoryCommand extends Command {

    public static final String COMMAND_WORD = "history";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays the command history window, with several filtering options."
            + "Parameters: KEYWORD "
            + " [" + OPTION_HIDE_OUTPUT + "] "
            + " [" + OPTION_HIDE_SUCCESSFUL + "] "
            + " [" + OPTION_HIDE_UNSUCCESSFUL + "]\n"
            + "Note that [" + OPTION_HIDE_UNSUCCESSFUL + "] " + "and " + "[" + OPTION_HIDE_SUCCESSFUL + "] "
            + "cannot be used at the same time.\n"
            + "Example: " + COMMAND_WORD + " "
            + OPTION_HIDE_SUCCESSFUL + " " + OPTION_HIDE_OUTPUT;

    public static final String SHOWING_HISTORY_MESSAGE = "Opened command history window.";

    public static final String ERROR_HIDE_BOTH_SUCCESSFUL_UNSUCCESSFUL =
            "Successful and unsuccessful commands cannot both be hidden at the "
                    + "same time. \n That'll just be an empty window.";

    public static final String ERROR_NO_UNSUCCESSFUL_COMMANDS = "There are no unsuccessful commands to display\n"
            + "If you hide all successful commands, it'll just be an empty window.";

    private final boolean hideCommandFeedback;
    private final HistoryFilterSettings displayOptions;

    public HistoryCommand(boolean hideCommandFeedback, HistoryFilterSettings displayOptions) {
        this.hideCommandFeedback = hideCommandFeedback;
        this.displayOptions = displayOptions;
    }

    @Override
    public CommandResult execute(Model model, FilterOnlyCommandHistory commandHistory) throws CommandException {
        requireNonNull(model);

        switch (displayOptions) {
        case SHOW_ALL:
            commandHistory.updateFilteredCommandHistory(PREDICATE_SHOW_ALL);
            break;
        case SHOW_ONLY_SUCCESSFUL:
            commandHistory.updateFilteredCommandHistory(PREDICATE_SHOW_ONLY_SUCCESSFUL);
            break;
        case SHOW_ONLY_UNSUCCESSFUL:
            if (commandHistory.getNumberOfUnsuccessfulCommands() == 0) {
                throw new CommandException(ERROR_NO_UNSUCCESSFUL_COMMANDS);
            }
            commandHistory.updateFilteredCommandHistory(PREDICATE_SHOW_ONLY_UNSUCCESSFUL);
            break;
        default:
            break;
        }

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
