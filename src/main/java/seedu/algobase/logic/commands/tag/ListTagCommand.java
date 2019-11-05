package seedu.algobase.logic.commands.tag;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.model.Model.PREDICATE_SHOW_ALL_TAGS;

import seedu.algobase.logic.CommandHistory;
import seedu.algobase.logic.commands.Command;
import seedu.algobase.logic.commands.CommandResult;
import seedu.algobase.model.Model;
import seedu.algobase.model.ModelType;

/**
 * Lists all tags in the algobase to the user.
 */
public class ListTagCommand extends Command {

    public static final String COMMAND_WORD = "listtag";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays a list of all existing tags.\n"
            + "Example:\n"
            + COMMAND_WORD;
    public static final String MESSAGE_SUCCESS = "All tags listed.";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredTagList(PREDICATE_SHOW_ALL_TAGS);
        model.getGuiState().getTabManager().switchDisplayTab(ModelType.TAG.getDisplayTabPaneIndex());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
