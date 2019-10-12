package seedu.flashcard.logic.commands;

import seedu.flashcard.model.FlashcardList;

public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    // TODO: Specify the message of usage of add command.
    //  This will be displayed to the user when the help command is called
    public final String MESSAGE_USAGE = "";

    private final String findMessage;

    public FindCommand(String findMessage) {
        this.findMessage = findMessage;
    }

    // TODO: implement the following execution method for add command, beware to generate a good command result.
    // TODO: write corresponding tests to test out this execution methods.
    @Override
    public CommandResult execute(FlashcardList flashcardList) {
        return null;
    }
}
