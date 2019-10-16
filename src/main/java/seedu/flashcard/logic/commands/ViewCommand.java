package seedu.flashcard.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.flashcard.commons.core.index.Index;
import seedu.flashcard.logic.commands.exceptions.CommandException;
import seedu.flashcard.model.Model;
import seedu.flashcard.model.flashcard.Flashcard;

/**
 * Command to view the MCQ choices in a flashcard.
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": View the flashcard identified by the index number used in the displayed flashcard list.\n"
        + "Parameters: INDEX (must be a positive integer)\n"
        + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_INVALID_FLASHCARD_INDEX = "The index you entered is invalid!";
    public static final String MESSAGE_DELETE_FLASHCARD_SUCCESS = "The flashcard is shown below.";
    private final Index targetIndex;

    public ViewCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    /**
     * Showing a whole flashcard in the command bot.
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
        Flashcard cardToView = lastShownList.get(targetIndex.getZeroBased());
        return new CommandResult(cardToView.toString());
    }

    @Override
    public boolean equals(Object other) {
        return other == this
            || (other instanceof ViewCommand
            && targetIndex.equals(((ViewCommand) other).targetIndex));
    }
}
