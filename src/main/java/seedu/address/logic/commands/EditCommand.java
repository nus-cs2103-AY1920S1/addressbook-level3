package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ANSWER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUESTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATING;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_FLASHCARDS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.category.Category;
import seedu.address.model.flashcard.Answer;
import seedu.address.model.flashcard.FlashCard;
import seedu.address.model.flashcard.Question;
import seedu.address.model.flashcard.Rating;

/**
 * Edits the details of an existing flashCard in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the flashCard identified "
            + "by the index number used in the displayed flashCard list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_QUESTION + "QUESTION] "
            + "[" + PREFIX_ANSWER + "ANSWER] "
            + "[" + PREFIX_RATING + "RATING] "
            + "[" + PREFIX_CATEGORY + "CATEGORY]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_ANSWER + "91234567 ";

    public static final String MESSAGE_EDIT_FLASHCARD_SUCCESS = "Edited FlashCard: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_FLASHCARD = "This flashCard already exists in the address book.";

    private final Index index;
    private final EditFlashCardDescriptor editFlashCardDescriptor;

    /**
     * @param index of the flashCard in the filtered flashCard list to edit
     * @param editFlashCardDescriptor details to edit the flashCard with
     */
    public EditCommand(Index index, EditFlashCardDescriptor editFlashCardDescriptor) {
        requireNonNull(index);
        requireNonNull(editFlashCardDescriptor);

        this.index = index;
        this.editFlashCardDescriptor = new EditFlashCardDescriptor(editFlashCardDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<FlashCard> lastShownList = model.getFilteredFlashCardList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_FLASHCARD_DISPLAYED_INDEX);
        }

        FlashCard flashCardToEdit = lastShownList.get(index.getZeroBased());
        FlashCard editedFlashCard = createEditedFlashCard(flashCardToEdit, editFlashCardDescriptor);

        if (!flashCardToEdit.isSameFlashCard(editedFlashCard) && model.hasFlashcard(editedFlashCard)) {
            throw new CommandException(MESSAGE_DUPLICATE_FLASHCARD);
        }

        model.setFlashCard(flashCardToEdit, editedFlashCard);
        model.updateFilteredFlashCardList(PREDICATE_SHOW_ALL_FLASHCARDS);
        return new CommandResult(String.format(MESSAGE_EDIT_FLASHCARD_SUCCESS, editedFlashCard));
    }

    /**
     * Creates and returns a {@code FlashCard} with the details of {@code flashCardToEdit}
     * edited with {@code editFlashCardDescriptor}.
     */
    private static FlashCard createEditedFlashCard(FlashCard flashCardToEdit,
                                                   EditFlashCardDescriptor editFlashCardDescriptor) {
        assert flashCardToEdit != null;

        Question updatedQuestion = editFlashCardDescriptor.getQuestion().orElse(flashCardToEdit.getQuestion());
        Answer updatedAnswer = editFlashCardDescriptor.getAnswer().orElse(flashCardToEdit.getAnswer());
        Rating updatedRating = editFlashCardDescriptor.getRating().orElse(flashCardToEdit.getRating());
        Set<Category> updatedCategories =
                editFlashCardDescriptor.getCategories().orElse(flashCardToEdit.getCategories());

        return new FlashCard(updatedQuestion, updatedAnswer, updatedRating, updatedCategories);
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
        return index.equals(e.index)
                && editFlashCardDescriptor.equals(e.editFlashCardDescriptor);
    }

    /**
     * Stores the details to edit the flashCard with. Each non-empty field value will replace the
     * corresponding field value of the flashCard.
     */
    public static class EditFlashCardDescriptor {
        private Question question;
        private Answer answer;
        private Rating rating;
        private Set<Category> categories;

        public EditFlashCardDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code categories} is used internally.
         */
        public EditFlashCardDescriptor(EditFlashCardDescriptor toCopy) {
            setQuestion(toCopy.question);
            setAnswer(toCopy.answer);
            setRating(toCopy.rating);
            setCategories(toCopy.categories);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(question, answer, rating, categories);
        }

        public void setQuestion(Question question) {
            this.question = question;
        }

        public Optional<Question> getQuestion() {
            return Optional.ofNullable(question);
        }

        public void setAnswer(Answer answer) {
            this.answer = answer;
        }

        public Optional<Answer> getAnswer() {
            return Optional.ofNullable(answer);
        }

        public void setRating(Rating rating) {
            this.rating = rating;
        }

        public Optional<Rating> getRating() {
            return Optional.ofNullable(rating);
        }

        /**
         * Sets {@code categories} to this object's {@code categories}.
         * A defensive copy of {@code categories} is used internally.
         */
        public void setCategories(Set<Category> categories) {
            this.categories = (categories != null) ? new HashSet<>(categories) : null;
        }

        /**
         * Returns an unmodifiable category set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code categories} is null.
         */
        public Optional<Set<Category>> getCategories() {
            return (categories != null) ? Optional.of(Collections.unmodifiableSet(categories)) : Optional.empty();
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
                    && getAnswer().equals(e.getAnswer())
                    && getRating().equals(e.getRating())
                    && getCategories().equals(e.getCategories());
        }
    }
}
