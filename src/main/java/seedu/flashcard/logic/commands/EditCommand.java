package seedu.flashcard.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.flashcard.logic.parser.CliSyntax.PREFIX_CHOICE;
import static seedu.flashcard.logic.parser.CliSyntax.PREFIX_DEFINITION;
import static seedu.flashcard.logic.parser.CliSyntax.PREFIX_QUESTION;
import static seedu.flashcard.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.flashcard.model.Model.PREDICATE_SHOW_ALL_FLASHCARDS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.flashcard.commons.core.Messages;
import seedu.flashcard.commons.core.index.Index;
import seedu.flashcard.commons.util.CollectionUtil;
import seedu.flashcard.logic.CommandHistory;
import seedu.flashcard.logic.commands.exceptions.CommandException;
import seedu.flashcard.model.Model;
import seedu.flashcard.model.flashcard.Answer;
import seedu.flashcard.model.flashcard.Choice;
import seedu.flashcard.model.flashcard.Definition;
import seedu.flashcard.model.flashcard.Flashcard;
import seedu.flashcard.model.flashcard.McqFlashcard;
import seedu.flashcard.model.flashcard.Question;
import seedu.flashcard.model.flashcard.ShortAnswerFlashcard;
import seedu.flashcard.model.tag.Tag;

/**
 * Command to edit a flashcard or tag
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the flashcard identified "
        + "by the index number used in the displayed flashcard list. "
        + "Existing values will be overwritten by the input values.\n"
        + "Parameters: INDEX (must be a positive integer) "
        + "[" + PREFIX_QUESTION + "QUESTION] "
        + "[" + PREFIX_CHOICE + "CHOICE] "
        + "[" + PREFIX_DEFINITION + "DEFINITION] "
        + "[" + PREFIX_TAG + "TAG]...\n"
        + "Example: " + COMMAND_WORD + " 1 "
        + PREFIX_CHOICE + "Sample "
        + PREFIX_QUESTION + "A* Search "
        + PREFIX_TAG + "Artificial Intelligence";

    public static final String MESSAGE_EDIT_FLASHCARD_SUCCESS = "Edited Flashcard: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_FLASHCARD = "This flashcard already exists in the flashcard list.";
    public static final String MESSAGE_INVALID_EDIT_CHOICE_ANSWER = "For MCQ flashcard,"
        + " the answer must match one of its choices.";

    private final Index index;
    private final EditFlashcardDescriptor editFlashcardDescriptor;

    /**
     * @param index                   of the flashcard in the filtered flashcard list to edit
     * @param editFlashCardDescriptor details to edit the flashcard with
     */
    public EditCommand(Index index, EditFlashcardDescriptor editFlashCardDescriptor) {
        requireNonNull(index);
        requireNonNull(editFlashCardDescriptor);
        this.index = index;
        this.editFlashcardDescriptor = new EditFlashcardDescriptor(editFlashCardDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Flashcard> lastShownList = model.getFilteredFlashcardList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_FLASHCARD_ID_NUMBER);
        }
        Flashcard flashcardToEdit = lastShownList.get(index.getZeroBased());
        Flashcard editedFlashcard;
        if (!flashcardToEdit.isMcq()) {
            editedFlashcard = createEditedShortAnswerFlashcard(flashcardToEdit, editFlashcardDescriptor);
        } else {
            editedFlashcard = createEditedMcqFlashcard((McqFlashcard) flashcardToEdit, editFlashcardDescriptor);
        }
        if (!flashcardToEdit.isSameFlashcard(editedFlashcard) && model.hasFlashcard(editedFlashcard)) {
            throw new CommandException(MESSAGE_DUPLICATE_FLASHCARD);
        }
        if (!editedFlashcard.isValidFlashcard()) {
            throw new CommandException(MESSAGE_INVALID_EDIT_CHOICE_ANSWER);
        }
        model.setFlashcard(flashcardToEdit, editedFlashcard);
        model.updateFilteredFlashcardList(PREDICATE_SHOW_ALL_FLASHCARDS);
        model.commitFlashcardList();
        return new CommandResult(String.format(MESSAGE_EDIT_FLASHCARD_SUCCESS, editedFlashcard));
    }

    /**
     * Creates and returns a {@code McqFlashcard} with the details of {@code flashcardToEdit}
     * edited with {@code editFlashcardDescriptor}.
     */
    private static Flashcard createEditedMcqFlashcard(
        McqFlashcard flashcardToEdit, EditFlashcardDescriptor editFlashcardDescriptor) {
        assert flashcardToEdit != null;
        Question updatedQuestion = editFlashcardDescriptor.getQuestion().orElse(flashcardToEdit.getQuestion());
        List<Choice> updatedChoices = editFlashcardDescriptor.getChoices().orElse(flashcardToEdit.getChoices());
        Definition updatedDefinition = editFlashcardDescriptor.getDefinition().orElse(flashcardToEdit.getDefinition());
        Set<Tag> updatedTags = editFlashcardDescriptor.getTags().orElse(flashcardToEdit.getTags());
        Answer updatedAnswer = editFlashcardDescriptor.getAnswer().orElse(flashcardToEdit.getAnswer());
        return new McqFlashcard(updatedQuestion, updatedChoices, updatedDefinition, updatedTags, updatedAnswer);
    }

    /**
     * Creates and returns a {@code ShortAnswerFlashcard} with the details of {@code flashcardToEdit}
     * edited with {@code editFlashcardDescriptor}.
     */
    private static Flashcard createEditedShortAnswerFlashcard(Flashcard flashcardToEdit,
                                                   EditFlashcardDescriptor editFlashcardDescriptor) {
        assert flashcardToEdit != null;
        Question updatedQuestion = editFlashcardDescriptor.getQuestion().orElse(flashcardToEdit.getQuestion());
        Definition updatedDefinition = editFlashcardDescriptor.getDefinition().orElse(flashcardToEdit.getDefinition());
        Set<Tag> updatedTags = editFlashcardDescriptor.getTags().orElse(flashcardToEdit.getTags());
        Answer updatedAnswer = editFlashcardDescriptor.getAnswer().orElse(flashcardToEdit.getAnswer());
        return new ShortAnswerFlashcard(updatedQuestion, updatedDefinition, updatedTags, updatedAnswer);
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
        return index.equals(e.index) && editFlashcardDescriptor.equals(e.editFlashcardDescriptor);
    }

    /**
     * Stores the details to edit the flashcard with.
     * corresponding field value of the person.
     */
    public static class EditFlashcardDescriptor {

        private Question question;
        private Definition definition;
        private List<Choice> choices;
        private Set<Tag> tags;
        private Answer answer;

        public EditFlashcardDescriptor() {
        }

        public EditFlashcardDescriptor(EditFlashcardDescriptor toCopy) {
            setQuestion(toCopy.question);
            setChoices(toCopy.choices);
            setDefinition(toCopy.definition);
            setTags(toCopy.tags);
            setAnswer(toCopy.answer);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(question, definition, tags, choices, answer);
        }

        public void setQuestion(Question question) {
            this.question = question;
        }

        public Optional<Question> getQuestion() {
            return Optional.ofNullable(question);
        }

        /**
         * Sets {@code choices} to this object's {@code choices}.
         * A defensive copy of {@code choices} is used internally.
         */
        public void setChoices(List<Choice> choices) {
            this.choices = (choices != null) ? new ArrayList<>(choices) : null;
        }

        /**
         * Returns an unmodifiable choice set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code choice} is null.
         */
        public Optional<List<Choice>> getChoices() {
            return (choices != null) ? Optional.of(Collections.unmodifiableList(choices)) : Optional.empty();
        }

        public void setDefinition(Definition definition) {
            this.definition = definition;
        }

        public Optional<Definition> getDefinition() {
            return Optional.ofNullable(definition);
        }

        public void setAnswer(Answer answer) {
            this.answer = answer;
        }

        public Optional<Answer> getAnswer() {
            return Optional.ofNullable(answer);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditFlashcardDescriptor)) {
                return false;
            }

            // state check
            EditFlashcardDescriptor e = (EditFlashcardDescriptor) other;

            return getQuestion().equals(e.getQuestion())
                && getDefinition().equals(e.getDefinition())
                && getTags().equals(e.getTags());
        }
    }
}
