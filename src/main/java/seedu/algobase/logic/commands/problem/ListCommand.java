package seedu.algobase.logic.commands.problem;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.model.Model.PREDICATE_SHOW_ALL_PROBLEMS;

import seedu.algobase.logic.CommandHistory;
import seedu.algobase.logic.commands.Command;
import seedu.algobase.logic.commands.CommandResult;
import seedu.algobase.model.Model;
import seedu.algobase.model.ModelType;

/**
 * Lists all problems in the algobase to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "listprob";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": lists all problems in AlgoBase\n"
            + "Example: "
            + COMMAND_WORD;
    public static final String MESSAGE_SUCCESS = "All problems listed.";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredProblemList(PREDICATE_SHOW_ALL_PROBLEMS);
        model.getGuiState().getTabManager().switchDisplayTab(ModelType.PROBLEM.getDisplayTabPaneIndex());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
