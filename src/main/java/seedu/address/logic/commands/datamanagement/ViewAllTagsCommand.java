package seedu.address.logic.commands.datamanagement;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.ui.ResultViewType;

/**
 * Shows all tags in the active study plan.
 */
public class ViewAllTagsCommand extends Command {

    public static final String COMMAND_WORD = "viewalltags";
    public static final String HELP_MESSAGE = COMMAND_WORD + ": Viewing all tags";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " : Shows all tags in the study plan. "
            + "Example: "
            + "viewalltags";

    public static final String MESSAGE_SUCCESS = "All tags shown";

    /**
     * Creates an {@code ViewAllTagsCommand} to show all tags in the active study plan.
     */
    public ViewAllTagsCommand() {
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        UniqueTagList uniqueTagList = model.getTagsFromActiveSp();

        return new CommandResult(MESSAGE_SUCCESS, ResultViewType.TAG, uniqueTagList.asUnmodifiableObservableList());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewAllTagsCommand); // instanceof handles nulls and type check
    }

}
