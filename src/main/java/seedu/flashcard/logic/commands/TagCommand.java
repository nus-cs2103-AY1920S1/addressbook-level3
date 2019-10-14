package seedu.flashcard.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.flashcard.model.FlashcardList;
import seedu.flashcard.model.flashcard.Flashcard;


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
        requireNonNull(flashcardList);

        Flashcard targetFlashcard = flashcardList.getFlashcard(tagFlashcardId);

        if (targetFlashcard == null) {
            return new CommandResult("Flashcard doesn't exist" , false, true);
        }

        if (targetFlashcard.hasTag(tagName)) {
            return new CommandResult("Flashcard already has that tag", false, true);
        }

        flashcardList.tagFlashcard(tagFlashcardId, tagName);

        return new CommandResult("Successfully tagged " + targetFlashcard.getId().getIdentityNumber()
                + " with " + tagName, false, true);

    }
}
