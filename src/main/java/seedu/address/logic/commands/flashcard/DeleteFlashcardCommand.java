package seedu.address.logic.commands.flashcard;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.DELETE;
import static seedu.address.commons.core.Messages.MESSAGE_ARE_YOU_SURE_WANT_TO_DELETE_FLASHCARD;
import static seedu.address.commons.core.Messages.MESSAGE_HIT_ENTER_TO_DELETE;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.commandresults.FlashcardCommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.flashcard.Flashcard;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteFlashcardCommand extends Command {
    public static final String COMMAND_WORD = DELETE;

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the flashcard identified by the index number used in the displayed flashcard list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_FLASHCARD_SUCCESS = "Deleted Flashcard: %1$s";

    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "DeleteFlashcardCommand not implemented yet";

    public static boolean isSure = false;

    // negative marked index to prevent access
    public static int markedIndex = -1;

    private final Index targetIndex;

    /**
     * @param targetIndex of the flashcard to be deleted
     */
    public DeleteFlashcardCommand(Index targetIndex) {
        requireAllNonNull(targetIndex);

        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Flashcard> lastShownList = model.getFilteredFlashcardList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_FLASHCARD_DISPLAYED_INDEX);
        }
        Flashcard flashcardToDelete = lastShownList.get(targetIndex.getZeroBased());
        FlashcardCommandResult commandResult = new FlashcardCommandResult("");
        if (!isSure) {
            isSure = true;
            // one prompt for index
            markedIndex = this.targetIndex.getOneBased();
            throw new CommandException(MESSAGE_ARE_YOU_SURE_WANT_TO_DELETE_FLASHCARD
                    + "\n" + flashcardToDelete
                    + "\n" + MESSAGE_HIT_ENTER_TO_DELETE);
        }
        if (isSure && markedIndex == this.targetIndex.getOneBased()) {
            // if this was marked
            // this is to prevent calling delete 1 then
            // calling delete 2
            // user is forced to delete the same index twice in a row.
            model.deleteFlashcard(flashcardToDelete);
            isSure = false;
            markedIndex = -1; // reset to -1 to prevent wrong access
            commandResult = new FlashcardCommandResult(String.format(MESSAGE_DELETE_FLASHCARD_SUCCESS, flashcardToDelete));
        }
        if (isSure) {
            // user is sure he wants to delete but changed the index
            markedIndex = this.targetIndex.getOneBased();
            throw new CommandException(MESSAGE_ARE_YOU_SURE_WANT_TO_DELETE_FLASHCARD
                    + "\n" + flashcardToDelete
                    + "\n" + MESSAGE_HIT_ENTER_TO_DELETE);
        }
        return commandResult;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteFlashcardCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteFlashcardCommand) other).targetIndex)); // state check
    }
}
