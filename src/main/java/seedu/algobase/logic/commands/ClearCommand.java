package seedu.algobase.logic.commands;

import seedu.algobase.model.AlgoBase;
import seedu.algobase.model.Model;

import static java.util.Objects.requireNonNull;

/**
 * Clears the algobase.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "AlgoBase has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setAlgoBase(new AlgoBase());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
