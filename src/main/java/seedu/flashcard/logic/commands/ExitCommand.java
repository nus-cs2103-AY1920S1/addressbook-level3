package seedu.flashcard.logic.commands;

import seedu.flashcard.model.FlashcardList;

public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";

    @Override
    public CommandResult execute(FlashcardList flashcardList) {
        return null;
    }
}
