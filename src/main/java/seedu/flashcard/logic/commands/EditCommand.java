package seedu.flashcard.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.flashcard.model.FlashcardList;
import seedu.flashcard.model.flashcard.Answer;
import seedu.flashcard.model.flashcard.Question;

/**
 * Command to edit a flashcard or tag
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    // TODO: specify the following usage message for edit command.
    //  Note that this will be displayed to the user when the help message is called.
    public static final String MESSAGE_USAGE = "";

    private final int toEditId;
    private final EditFlashCardDescriptor editFlashCardDescriptor;

    public EditCommand(int toEditId, EditFlashCardDescriptor editFlashCardDescriptor) {
        requireNonNull(toEditId);
        requireNonNull(editFlashCardDescriptor);
        this.toEditId = toEditId;
        this.editFlashCardDescriptor = editFlashCardDescriptor;
    }

    // TODO: implement the following execution method for edit command, beware to generate a good command result.
    // TODO: write corresponding tests to test out this execution methods.
    @Override
    public CommandResult execute(FlashcardList flashcardList) {
        return null;
    }

    /**
     * TODO: implement the following edit flashcard descriptor class.
     * Stores the details to edit the flashcard with.
     * corresponding field value of the person.
     */
    public class EditFlashCardDescriptor {

        private Question question;
        private Answer answer;
    }
}
