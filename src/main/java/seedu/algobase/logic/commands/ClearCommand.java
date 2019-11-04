package seedu.algobase.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.algobase.logic.CommandHistory;
import seedu.algobase.model.AlgoBase;
import seedu.algobase.model.Model;

/**
 * Clears the algobase.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + "Clears all entries from AlgoBase.\n"
            + "Example: \n"
            + COMMAND_WORD;
    public static final String MESSAGE_SUCCESS = "AlgoBase has been cleared.";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.setAlgoBase(new AlgoBase());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
