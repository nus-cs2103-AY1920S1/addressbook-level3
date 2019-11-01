package seedu.algobase.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.algobase.commons.core.Messages;
import seedu.algobase.commons.core.index.Index;
import seedu.algobase.logic.CommandHistory;
import seedu.algobase.logic.commands.exceptions.CommandException;
import seedu.algobase.model.Model;
import seedu.algobase.model.tag.Tag;

/**
 * Deletes a Tag identified using it's displayed index from the algobase.
 */
public class DeleteTagCommand extends Command {

    public static final String COMMAND_WORD = "deletetag";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the Tag identified by the index number used in the displayed Tag list.\n"
            + "Parameters:\n"
            + "INDEX (must be a positive integer)\n"
            + "Example:\n"
            + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_TAG_SUCCESS = "Tag [%1$s] deleted.";

    private final Index targetIndex;

    public DeleteTagCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Tag> lastShownList = model.getFilteredTagList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TAG_DISPLAYED_INDEX);
        }

        Tag tagToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteTags(tagToDelete);
        model.deleteTag(tagToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_TAG_SUCCESS, tagToDelete.getName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteTagCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteTagCommand) other).targetIndex)); // state check
    }
}
