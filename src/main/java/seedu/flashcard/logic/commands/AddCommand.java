package seedu.flashcard.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.flashcard.model.flashcard.Flashcard;
import seedu.flashcard.model.FlashcardList;

/**
 * Command to add a new model.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    // TODO: Specify the message of usage of add command.
    //  This will be displayed to the user when the help command is called
    public static final String MESSAGE_USAGE = "";

    private final Flashcard toAdd;

    public AddCommand (Flashcard flashcard) {
        requireNonNull(flashcard);
        toAdd = flashcard;
    }

    // TODO: implement the following execution method for add command, beware to generate a good command result.
    // TODO: write corresponding tests to test out this execution methods.
    @Override
    public CommandResult execute(FlashcardList flashcardList) {
        return null;
    }

}
