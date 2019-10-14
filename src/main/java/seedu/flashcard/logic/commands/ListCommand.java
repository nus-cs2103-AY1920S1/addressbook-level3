package seedu.flashcard.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.flashcard.model.FlashcardList;

/**
 * The command to list out all flashcards or the flashcards under a given tag.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all flashcards";

    // TODO: Implement the following execution command for list command.
    // TODO: write corresponding tests to test out the execution command.
    @Override
    public CommandResult execute(FlashcardList flashcardList) {
        requireNonNull(flashcardList);
        return new CommandResult(flashcardList.listFlashcards());
    }
}
