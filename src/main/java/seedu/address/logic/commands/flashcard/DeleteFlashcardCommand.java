package seedu.address.logic.commands.flashcard;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.DELETE;
import static seedu.address.commons.core.Messages.MESSAGE_ARE_YOU_SURE_WANT_TO_DELETE_FLASHCARD;
import static seedu.address.commons.core.Messages.MESSAGE_CONFIRM_DELETE;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.commandresults.FlashcardCommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.flashcard.Flashcard;

/**
 * Deletes a person identified using it's displayed index from StudyBuddyPro.
 */
public class DeleteFlashcardCommand extends Command {
    public static final String COMMAND_WORD = DELETE;

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the flashcard identified by the index number used in the displayed flashcard list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_FLASHCARD_SUCCESS = "Deleted Flashcard: %1$s";

    /**
     * The successfulDeletionOnPreviousCommand is to prevent the user from calling, for instance,
     * 'delete 1' twice in a row and not get a prompt.
     */
    private static boolean successfulDeletionOnPreviousCommand = false;

    private final Index targetIndex;

    /**
     * @param targetIndex of the flashcard to be deleted
     */
    public DeleteFlashcardCommand(Index targetIndex) {
        requireAllNonNull(targetIndex);

        this.targetIndex = targetIndex;
    }

    public Index getTargetIndex() {
        return targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Flashcard> lastShownList = model.getFilteredFlashcardList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_FLASHCARD_DISPLAYED_INDEX);
        }
        Flashcard flashcardToDelete = lastShownList.get(targetIndex.getZeroBased());
        FlashcardCommandResult commandResult = new FlashcardCommandResult ((
                MESSAGE_ARE_YOU_SURE_WANT_TO_DELETE_FLASHCARD
                + "\n" + flashcardToDelete
                + "\n" + String.format(MESSAGE_CONFIRM_DELETE, this.targetIndex.getOneBased())));

        if (CommandHistory.getLastCommand().isPresent()) {
            if (CommandHistory.getLastCommand().get() instanceof DeleteFlashcardCommand) {
                if (((DeleteFlashcardCommand) CommandHistory.getLastCommand().get()).getTargetIndex()
                        .equals(this.targetIndex)) {
                    // correct. allow delete
                    model.deleteFlashcard(flashcardToDelete);

                    // item has been deleted, set this to true
                    successfulDeletionOnPreviousCommand = true;

                    commandResult = new FlashcardCommandResult(String.format
                            (MESSAGE_DELETE_FLASHCARD_SUCCESS, flashcardToDelete));
                } else {
                    // nothing has been deleted, set back to false
                    successfulDeletionOnPreviousCommand = false;
                }
            } else {
                successfulDeletionOnPreviousCommand = false;
            }
        } else {
            successfulDeletionOnPreviousCommand = false;
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
