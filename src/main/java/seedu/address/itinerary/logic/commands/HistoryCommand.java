package seedu.address.itinerary.logic.commands;

import java.util.ArrayList;
import java.util.Collections;

import seedu.address.itinerary.model.Model;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;

/**
 * Give a history on the previous action being called in the itinerary.
 */
public class HistoryCommand extends Command<Model> {
    public static final String COMMAND_WORD = "history";
    public static final String MESSAGE_SUCCESS = "Commands called for this session (Most recent → Earliest):\n%1$s";
    private static final String MESSAGE_NO_REDO = "You have not yet entered any commands yet for this session.\n"
            + "Let's start using TravEzy now! Use help for more information! ٩◔‿◔۶";

    @Override
    public CommandResult execute(Model model) {
        ArrayList<String> previousCommands = new ArrayList<>(model.getActionList());

        if (previousCommands.isEmpty()) {
            return new CommandResult(MESSAGE_NO_REDO);
        }

        Collections.reverse(previousCommands);
        return new CommandResult(String.format(MESSAGE_SUCCESS, String.join("\n", previousCommands)));
    }
}
