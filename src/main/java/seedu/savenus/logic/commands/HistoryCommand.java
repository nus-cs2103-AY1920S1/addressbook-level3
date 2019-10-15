package seedu.savenus.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collections;

import seedu.savenus.logic.commands.exceptions.CommandException;
import seedu.savenus.model.Model;

/**
 * Shows the history of the user's typed commands.
 */
public class HistoryCommand extends Command {

    public static final String COMMAND_WORD = "history";
    public static final String COMMAND_ALIAS = "h";

    public static final String MESSAGE_SUCCESS = "You have entered these commands:\n";
    public static final String MESSAGE_NO_HISTORY = "You have not entered any commands.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        ArrayList<String> histories = new ArrayList<>(model.getCommandHistory());

        if (histories.size() == 0) {
            // no commands were typed
            return new CommandResult(MESSAGE_NO_HISTORY);
        }

        Collections.reverse(histories);
        StringBuilder sb = new StringBuilder(MESSAGE_SUCCESS);

        for (String history : histories) {
            sb.append(history);
            sb.append("\n");
        }

        return new CommandResult(sb.toString());
    }
}
