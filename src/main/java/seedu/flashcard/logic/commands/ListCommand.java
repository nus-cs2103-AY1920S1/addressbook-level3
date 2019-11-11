package seedu.flashcard.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.flashcard.model.Model.PREDICATE_SHOW_ALL_FLASHCARDS;

import seedu.flashcard.logic.CommandHistory;
import seedu.flashcard.model.Model;

/**
 * The command to list out all flashcards.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all flashcards";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredFlashcardList(PREDICATE_SHOW_ALL_FLASHCARDS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
