package seedu.algobase.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.model.Model.PREDICATE_SHOW_ALL_PLANS;

import seedu.algobase.logic.CommandHistory;
import seedu.algobase.model.Model;
import seedu.algobase.model.ModelType;


/**
 * Lists all plans in the algobase to the user.
 */
public class ListPlanCommand extends Command {

    public static final String COMMAND_WORD = "listplan";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays a list of all existing plans.\n"
            + "Example:\n"
            + COMMAND_WORD;
    public static final String MESSAGE_SUCCESS = "All plans listed.";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredPlanList(PREDICATE_SHOW_ALL_PLANS);
        model.getGuiState().getTabManager().switchDisplayTab(ModelType.PLAN.getDisplayTabPaneIndex());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
