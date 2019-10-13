package seedu.flashcard.logic.commands;

import seedu.flashcard.model.FlashcardList;

/**
 * Command to exit the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";

    // TODO: Specify the message of usage of exit command.
    //  Note that this will be displayed to the user when the help command is called.
    public static final String MESSAGE_USAGE = "";

    // TODO: implement the following execution method for add command, beware to generate a good command result.
    // TODO: write corresponding tests to test out this execution methods.
    @Override
    public CommandResult execute(FlashcardList flashcardList) {
        return null;
    }
}
