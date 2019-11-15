package seedu.guilttrip.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collections;

import seedu.guilttrip.logic.CommandHistory;
import seedu.guilttrip.model.Model;

/**
 * Lists all the commands entered by user from the start of app launch.
 */
public class HistoryCommand extends Command {

    public static final String COMMAND_WORD = "history";
    public static final String ONE_LINER_DESC = COMMAND_WORD + ": Lists all the commands entered by user from"
            + " the start of app launch.";
    public static final String MESSAGE_USAGE = ONE_LINER_DESC;
    public static final String MESSAGE_SUCCESS = "Entered commands (from most recent to earliest):\n";
    public static final String MESSAGE_NO_HISTORY = "You have not yet entered any commands.";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(history);
        ArrayList<String> previousCommands = new ArrayList<>(history.getHistory());

        if (previousCommands.isEmpty()) {
            return new CommandResult(MESSAGE_NO_HISTORY);
        }

        Collections.reverse(previousCommands);
        return new CommandResult(MESSAGE_SUCCESS, true, "history");
    }
}

