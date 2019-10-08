package seedu.algobase.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.model.Model.PREDICATE_SHOW_ALL_TAGS;

import seedu.algobase.model.Model;

/**
 * Lists all tags in the algobase to the user.
 */
public class ListTagCommand extends Command {

    public static final String COMMAND_WORD = "lstag";

    public static final String MESSAGE_SUCCESS = "Listed all tags";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredTagList(PREDICATE_SHOW_ALL_TAGS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
