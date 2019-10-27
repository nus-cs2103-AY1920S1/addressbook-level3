package seedu.algobase.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.model.Model.PREDICATE_SHOW_ALL_TAGS;

import seedu.algobase.model.Model;
import seedu.algobase.model.ModelType;

/**
 * Lists all tags in the algobase to the user.
 */
public class ListTagCommand extends Command {

    public static final String COMMAND_WORD = "listtag";

    public static final String MESSAGE_SUCCESS = "Listed all tags";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredTagList(PREDICATE_SHOW_ALL_TAGS);
        model.getGuiState().getTabManager().setDisplayTabPaneIndex(ModelType.TAG.getDisplayTabPaneIndex());
        return new CommandResult(MESSAGE_SUCCESS + "\n" + model.getFilteredTagList().toString());
    }
}
