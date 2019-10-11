package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUESTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DIFFICULTY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ANSWERABLE;

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
import seedu.address.model.answerable.Answer;
import seedu.address.model.answerable.Answerable;
import seedu.address.model.answerable.Category;
import seedu.address.model.answerable.Difficulty;
import seedu.address.model.answerable.Mcq;
import seedu.address.model.answerable.AnswerSet;
import seedu.address.model.answerable.Question;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing answerable in the test bank.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the answerable identified "
            + "by the index number used in the displayed answerable list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_QUESTION + "QUESTION] "
            + "[" + PREFIX_DIFFICULTY + "DIFFICULTY] "
            + "[" + PREFIX_CATEGORY + "ADDRESS] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DIFFICULTY + "91234567 ";

    public static final String MESSAGE_EDIT_ANSWERABLE_SUCCESS = "Edited Answerable: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_ANSWERABLE = "This question already exists in the test bank.";

    private final Index index;
    private final EditAnswerableDescriptor editAnswerableDescriptor;

    /**
     * @param index of the answerable in the filtered answerable list to edit
     * @param editAnswerableDescriptor details to edit the answerable with
     */
    public EditCommand(Index index, EditAnswerableDescriptor editAnswerableDescriptor) {
        requireNonNull(index);
        requireNonNull(editAnswerableDescriptor);

        this.index = index;
        this.editAnswerableDescriptor = new EditAnswerableDescriptor(editAnswerableDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Answerable> lastShownList = model.getFilteredAnswerableList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ANSWERABLE_DISPLAYED_INDEX);
        }

        Answerable answerableToEdit = lastShownList.get(index.getZeroBased());
        Answerable editedAnswerable = createEditedAnswerable(answerableToEdit, editAnswerableDescriptor);

        if (!answerableToEdit.isSameAnswerable(editedAnswerable) && model.hasAnswerable(editedAnswerable)) {
            throw new CommandException(MESSAGE_DUPLICATE_ANSWERABLE);
        }

        model.setAnswerable(answerableToEdit, editedAnswerable);
        model.updateFilteredAnswerableList(PREDICATE_SHOW_ALL_ANSWERABLE);
        return new CommandResult(String.format(MESSAGE_EDIT_ANSWERABLE_SUCCESS, editedAnswerable));
    }

    /**
     * Creates and returns a {@code Answerable} with the details of {@code answerableToEdit}
     * edited with {@code editAnswerableDescriptor}.
     */
    private static Answerable createEditedAnswerable(Answerable answerableToEdit, EditAnswerableDescriptor editAnswerableDescriptor) {
        assert answerableToEdit != null;

        Question updatedQuestion = editAnswerableDescriptor.getQuestion().orElse(answerableToEdit.getQuestion());
        AnswerSet updatedAnswerSet = editAnswerableDescriptor.getAnswerSet().orElse(answerableToEdit.getAnswerSet());
        Difficulty updatedDifficulty = editAnswerableDescriptor.getDifficulty().orElse(answerableToEdit.getDifficulty());
        Category updatedCategory = editAnswerableDescriptor.getCategory().orElse(answerableToEdit.getCategory());
        Set<Tag> updatedTags = editAnswerableDescriptor.getTags().orElse(answerableToEdit.getTags());

        AnswerSet mcqAnswer = (AnswerSet) updatedAnswerSet;

        return new Mcq(updatedQuestion, mcqAnswer, updatedDifficulty, updatedCategory, updatedTags);
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
                && editAnswerableDescriptor.equals(e.editAnswerableDescriptor);
    }

    /**
     * Stores the details to edit the answerable with. Each non-empty field value will replace the
     * corresponding field value of the answerable.
     */
    public static class EditAnswerableDescriptor {
        private Question question;
        private AnswerSet answerSet;
        private Difficulty difficulty;
        private Category category;
        private Set<Tag> tags;

        public EditAnswerableDescriptor() {
            answerSet = new AnswerSet();
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditAnswerableDescriptor(EditAnswerableDescriptor toCopy) {
            setQuestion(toCopy.question);
            setAnswerSet(toCopy.answerSet);
            setDifficulty(toCopy.difficulty);
            setCategory(toCopy.category);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(question, answerSet, difficulty, category, tags);
        }

        public void setQuestion(Question question) {
            this.question = question;
        }

        public Optional<Question> getQuestion() {
            return Optional.ofNullable(question);
        }

        public void setAnswerSet(AnswerSet answerSet) {
            this.answerSet = answerSet;
        }

        public void setCorrectAnswerSet(Set<Answer> correctAnswerSet) {
            answerSet = new AnswerSet();
            this.answerSet.setCorrectAnswerSet(correctAnswerSet);
        }

        public void setWrongAnswerSet(Set<Answer> wrongAnswerSet) {
            this.answerSet.setWrongAnswerSet(wrongAnswerSet);
        }

        public Optional<AnswerSet> getAnswerSet() {
            return Optional.ofNullable(answerSet);
        }

        public void setDifficulty(Difficulty difficulty) {
            this.difficulty = difficulty;
        }

        public Optional<Difficulty> getDifficulty() {
            return Optional.ofNullable(difficulty);
        }

        public void setCategory(Category category) {
            this.category = category;
        }

        public Optional<Category> getCategory() {
            return Optional.ofNullable(category);
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
            if (!(other instanceof EditAnswerableDescriptor)) {
                return false;
            }

            // state check
            EditAnswerableDescriptor e = (EditAnswerableDescriptor) other;

            return getQuestion().equals(e.getQuestion())
                    && getAnswerSet().equals(e.getAnswerSet())
                    && getDifficulty().equals(e.getDifficulty())
                    && getCategory().equals(e.getCategory())
                    && getTags().equals(e.getTags());
        }
    }
}
