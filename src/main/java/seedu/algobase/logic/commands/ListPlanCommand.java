package seedu.algobase.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.model.Model.PREDICATE_SHOW_ALL_PLANS;

import seedu.algobase.model.Model;


/**
 * Lists all plans in the algobase to the user.
 */
public class ListPlanCommand extends Command {

    public static final String COMMAND_WORD = "listplan";

    public static final String MESSAGE_SUCCESS = "Listed all plans";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPlanList(PREDICATE_SHOW_ALL_PLANS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
