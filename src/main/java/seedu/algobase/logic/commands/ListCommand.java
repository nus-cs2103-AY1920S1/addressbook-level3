package seedu.algobase.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.model.Model.PREDICATE_SHOW_ALL_PROBLEMS;

import seedu.algobase.model.Model;

/**
 * Lists all problems in the algobase to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all problems";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredProblemList(PREDICATE_SHOW_ALL_PROBLEMS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
