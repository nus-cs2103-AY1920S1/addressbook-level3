package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.flashcard.Flashcard;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class ViewFlashcardCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays a flashcard.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String VIEW_FLASHCARD_SUCCESS = "Viewing flashcard: %1$s";

    private final Index targetIndex;

    public ViewFlashcardCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Flashcard> lastShownList = model.getFilteredFlashcardList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_FLASHCARD_DISPLAYED_INDEX);
        }

        Flashcard flashcard = lastShownList.get(targetIndex.getZeroBased());

        return new CommandResult(String.format(VIEW_FLASHCARD_SUCCESS, flashcard), false, false,
                false, Optional.empty(), Optional.of(flashcard));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewFlashcardCommand // instanceof handles nulls
                && targetIndex.equals(((ViewFlashcardCommand) other).targetIndex)); // state check
    }
}
