package seedu.flashcard.logic.commands;

import seedu.flashcard.model.FlashcardList;

/**
 * Command to see the answer of a flashcard, only to be used in the quiz mode
 */
public class FlipCommand extends Command {

    public static final String COMMAND_WORD = "flip";

    // TODO: Specify the message of usage of flip command.
    //  This will be displayed to the user when the help command is called
    public static final String MESSAGE_USAGE = "";

    // TODO: implement the following execution method for add command, beware to generate a good command result.
    // TODO: write corresponding tests to test out this execution methods.
    @Override
    public CommandResult execute(FlashcardList flashcardList) {
        return null;
    }
}
