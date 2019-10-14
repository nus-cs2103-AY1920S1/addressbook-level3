package seedu.flashcard.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.flashcard.commons.core.index.Index;
import seedu.flashcard.logic.commands.exceptions.CommandException;
import seedu.flashcard.model.Model;
import seedu.flashcard.model.flashcard.Flashcard;

/**
 * Command to delete a flashcard or tag
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the flashcard identified by the index number used in the displayed flashcard list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_INVALID_FLASHCARD_INDEX = "The index you entered is invalid!";
    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "The card has been deleted!";
    private final Index targetIndex;

    public DeleteCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    /**
     * Return the result from executing the delete command.
     * @param model list of flashcards
     * @return the execution result
     * @throws CommandException error encountered during execution of command
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Flashcard> lastShownList = model.getFilteredFlashcardList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_FLASHCARD_INDEX);
        }

        Flashcard cardToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteFlashcard(cardToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, cardToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof DeleteCommand
                && targetIndex.equals(((DeleteCommand) other).targetIndex));
    }

}

