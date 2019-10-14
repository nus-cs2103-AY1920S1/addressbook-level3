package seedu.flashcard.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.flashcard.logic.commands.exceptions.CommandException;
import seedu.flashcard.model.FlashcardList;
import seedu.flashcard.model.flashcard.CardId;
import seedu.flashcard.model.flashcard.Flashcard;

/**
 * Command to delete a flashcard or tag
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the flashcard identified by the flashcard ID used.\n"
            + "Parameters: ID (must be a positive 6-digit string)\n"
            + "Example: " + COMMAND_WORD + "/d 000001";

    public static final String MESSAGE_INVALID_FLASHCARD_ID = "The ID you entered is invalid!";
    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "The card has been deleted!";
    private final CardId toDeleteId;

    /**
     * Construct a new add flashcard command.
     * @param toDeleteId the ID of the flashcard to be deleted
     */
    public DeleteCommand(CardId toDeleteId) {
        requireNonNull(toDeleteId);
        this.toDeleteId = toDeleteId;
    }

    /**
     * Return the result from executing the delete command.
     * @param flashcardList list of flashcards
     * @return the execution result
     * @throws CommandException error encountered during execution of command
     */
    @Override
    public CommandResult execute(FlashcardList flashcardList) throws CommandException {
        requireNonNull(flashcardList);
        List<Flashcard> lastShownList = flashcardList.getAllFlashcards();

        if (toDeleteId.getIdentityNumber() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_FLASHCARD_ID);
        }

        Flashcard cardToDelete = lastShownList.get(toDeleteId.getIdentityNumber());
        flashcardList.deleteFlashcard(cardToDelete.getId().getIdentityNumber());
        return new CommandResult(MESSAGE_DELETE_PERSON_SUCCESS, false, false);
    }

}

