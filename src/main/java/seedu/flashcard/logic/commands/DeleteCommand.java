package seedu.flashcard.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.flashcard.model.flashcard.Flashcard;
import seedu.flashcard.model.FlashcardList;

/**
 * Command to delete a model
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    // TODO: Specify the message of usage of delete command.
    //  This will be displayed to the user when the help command is called
    public static final String MESSAGE_USAGE = "";

    private final int toDeleteId;

    public DeleteCommand (int toDeleteId) {
        requireNonNull(toDeleteId);
        this.toDeleteId = toDeleteId;
    }

    // TODO: Implement the following execution method for delete command, beware to generate a goof command result.
    // TODO: werite corresponding tests to test out this execution method.
    @Override
    public CommandResult execute(FlashcardList flashcardList) {
        return null;
    }

}

