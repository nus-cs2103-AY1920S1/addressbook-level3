package seedu.flashcard.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.util.CollectionUtil;
import seedu.flashcard.model.FlashcardList;
import seedu.flashcard.model.flashcard.Answer;
import seedu.flashcard.model.flashcard.CardId;
import seedu.flashcard.model.flashcard.Flashcard;
import seedu.flashcard.model.flashcard.McqFlashcard;
import seedu.flashcard.model.flashcard.Question;
import seedu.flashcard.model.flashcard.ShortAnswerFlashcard;

/**
 * Command to edit a flashcard or tag
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    // TODO: specify the following usage message for edit command.
    //  Note that this will be displayed to the user when the help message is called.
    public static final String MESSAGE_USAGE = "";
    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Flashcard: %1$s";


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
        requireNonNull(flashcardList);
        Flashcard flashcardToEdit = flashcardList.getFlashcard(toEditId);
        Flashcard editedFlashcard = createEditedFlashcard(flashcardToEdit, editFlashCardDescriptor);
        flashcardList.setFlashcard(toEditId, editedFlashcard);

        return new CommandResult(flashcardList.getFlashcard(toEditId).toString());
    }

    private static Flashcard createEditedFlashcard(Flashcard flashcardToEdit,
                                                    EditFlashCardDescriptor editFlashcardDescriptor) {
        assert flashcardToEdit != null;

        CardId currentId = flashcardToEdit.getId();
        Question updatedQuestion = editFlashcardDescriptor.getQuestion().orElse(flashcardToEdit.getQuestion());
        Answer updatedAnswer = editFlashcardDescriptor.getAnswer().orElse(flashcardToEdit.getAnswer());

        if (flashcardToEdit instanceof McqFlashcard) {
            return new McqFlashcard(currentId, updatedQuestion, updatedAnswer);
        } else {
            return new ShortAnswerFlashcard(currentId, updatedQuestion, updatedAnswer);
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return toEditId == e.toEditId
                && editFlashCardDescriptor.equals(e.editFlashCardDescriptor);
    }


    /**
     * TODO: implement the following edit flashcard descriptor class.
     * Stores the details to edit the flashcard with.
     * corresponding field value of the person.
     */
    public static class EditFlashCardDescriptor {

        private Question question;
        private Answer answer;

        public EditFlashCardDescriptor() {}

        public EditFlashCardDescriptor(EditFlashCardDescriptor toCopy) {
            setQuestion(toCopy.question);
            setAnswer(toCopy.answer);
        }

        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(question, answer);
        }

        public void setQuestion(Question question) {
            this.question = question;
        }

        public Optional<Question> getQuestion () {
            return Optional.ofNullable(question);
        }

        public void setAnswer(Answer answer) {
            this.answer = answer;
        }

        public Optional<Answer> getAnswer() {
            return Optional.ofNullable(answer);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditFlashCardDescriptor)) {
                return false;
            }

            // state check
            EditFlashCardDescriptor e = (EditFlashCardDescriptor) other;

            return getQuestion().equals(e.getQuestion())
                    && getAnswer().equals(e.getAnswer());
        }

    }
}
