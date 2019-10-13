package seedu.flashcard.logic.commands;

import seedu.flashcard.model.FlashcardList;

/**
 * The command to give a flashcard a tag.
 */
public class TagCommand extends Command {

    public static final String COMMAND_WORD = "tag";

    private final int tagFlashcardId;
    private final String tagName;

    public TagCommand(int tagFlashcardId, String tagName) {
        this.tagFlashcardId = tagFlashcardId;
        this.tagName = tagName;
    }

    // TODO: implement the following execution method for tag method
    // TODO: Write corresponding tests for the execution method
    @Override
    public CommandResult execute(FlashcardList flashcardList) {
        return null;
    }
}
