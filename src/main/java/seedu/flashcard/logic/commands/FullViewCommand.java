package seedu.flashcard.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.flashcard.commons.core.index.Index;
import seedu.flashcard.logic.commands.exceptions.CommandException;
import seedu.flashcard.model.Model;
import seedu.flashcard.model.flashcard.Flashcard;

/**
 * Command to view the MCQ choicecs as well as the answer in a flashcard.
 */
public class FullViewCommand extends Command {

    public static final String COMMAND_WORD = "fullview";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": View the flashcard identified by the index number used in the displayed flashcard list.\n"
        + "Both the choices and the answer will be shown.\n"
        + "Parameters: INDEX (must be a positive integer)\n"
        + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_INVALID_FLASHCARD_INDEX = "The index you entered is invalid!";
    public static final String MESSAGE_DELETE_FLASHCARD_SUCCESS = "The flashcard is shown in full below.";
    private final Index targetIndex;

    public FullViewCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    /**
     * Showing a whole flashcard in the command bot with the answer.
     * @param model {@code Model} which the command should operate on.
     * @return the execution result containing the full flashcard.
     * @throws CommandException error encountered during execution of the command.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Flashcard> lastShownList = model.getFilteredFlashcardList();
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_FLASHCARD_INDEX);
        }
        Flashcard cardToView = lastShownList.get(targetIndex.getZeroBased());
        model.updateLastViewedFlashcard(cardToView);
        return new CommandResult(cardToView.fullString());
    }

    @Override
    public boolean equals(Object other) {
        return other == this
            || (other instanceof FullViewCommand
            && targetIndex.equals(((FullViewCommand) other).targetIndex));
    }
}
