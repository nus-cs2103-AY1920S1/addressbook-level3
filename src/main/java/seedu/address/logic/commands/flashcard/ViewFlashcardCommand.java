package seedu.address.logic.commands.flashcard;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.VIEW;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
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
public class ViewFlashcardCommand extends Command {
    public static final String COMMAND_WORD = VIEW;

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays a flashcard.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String VIEW_FLASHCARD_SUCCESS = "Viewing flashcard: %1$s";

    private static final Logger logger = LogsCenter.getLogger(ViewFlashcardCommand.class);

    private final Index targetIndex;

    public ViewFlashcardCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        assert (targetIndex.getZeroBased() >= 0);

        List<Flashcard> lastShownList = model.getFilteredFlashcardList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_FLASHCARD_DISPLAYED_INDEX);
        }

        Flashcard flashcard = lastShownList.get(targetIndex.getZeroBased());

        logger.info("Executing ViewFlashcardCommand for flashcard: "
                + flashcard.getTitle());

        return new FlashcardCommandResult(String.format(VIEW_FLASHCARD_SUCCESS, flashcard.toString()),
                Optional.of(flashcard));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewFlashcardCommand // instanceof handles nulls
                && targetIndex.equals(((ViewFlashcardCommand) other).targetIndex)); // state check
    }
}
