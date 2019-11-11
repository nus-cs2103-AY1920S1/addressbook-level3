package seedu.flashcard.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.flashcard.logic.CommandHistory;
import seedu.flashcard.logic.commands.exceptions.CommandException;
import seedu.flashcard.model.Model;
import seedu.flashcard.model.tag.Tag;

/**
 * Represent a command that removes a tag, but the cards under this tag will be kept.
 */
public class DeleteTagCommand extends Command {

    public static final String COMMAND_WORD = "deletetag";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Deletes the tag identified by the index number used in the displayed flashcard list.\n"
        + "Parameters: TAG NAME (must exist in the flashcards)\n"
        + "Example: " + COMMAND_WORD + "t/software engineering";
    public static final String MESSAGE_DELETE_TAG_SUCCESS = "This tag has been deleted, "
        + "the cards under this tag are kept.";
    public static final String MESSAGE_TAG_DOES_NOT_EXIST = "This tag does not exist in the flashcards.";

    private final Tag target;

    public DeleteTagCommand(Tag target) {
        this.target = target;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        if (!model.systemHasTag(target)) {
            throw new CommandException(MESSAGE_TAG_DOES_NOT_EXIST);
        }
        model.systemRemoveTag(target);
        model.commitFlashcardList();
        return new CommandResult(MESSAGE_DELETE_TAG_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteTagCommand // instanceof handles nulls
                && target.equals(((DeleteTagCommand) other).target));
    }
}
