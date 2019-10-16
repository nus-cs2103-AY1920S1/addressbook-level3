package seedu.address.logic.quiz.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.quiz.parser.CliSyntax.PREFIX_ANSWER;
import static seedu.address.logic.quiz.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.quiz.parser.CliSyntax.PREFIX_QUESTION;
import static seedu.address.logic.quiz.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.quiz.parser.CliSyntax.PREFIX_TYPE;
import static seedu.address.model.quiz.Model.PREDICATE_SHOW_ALL_QUESTIONS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.quiz.commands.exceptions.CommandException;
import seedu.address.model.quiz.Model;
import seedu.address.model.quiz.person.Answer;
import seedu.address.model.quiz.person.Category;
import seedu.address.model.quiz.person.Name;
import seedu.address.model.quiz.person.Question;
import seedu.address.model.quiz.person.Type;
import seedu.address.model.quiz.tag.Tag;

/**
 * Edits the details of an existing question in modulo quiz.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the question identified "
            + "by the index number used in the displayed question list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: " + COMMAND_WORD + " CATEGORY "
            + "INDEX (must be a positive integer) "
            + PREFIX_QUESTION + "NAME "
            + PREFIX_ANSWER + "PHONE "
            + PREFIX_CATEGORY + "EMAIL "
            + PREFIX_TYPE + "ADDRESS "
            + "[" + PREFIX_TAG + "TAG] \n"
            + "Example: " + COMMAND_WORD + "CS2103 1 "
            + PREFIX_QUESTION + "How many mammals are there in the universe? "
            + PREFIX_TYPE + "low";

    public static final String MESSAGE_EDIT_QUESTION_SUCCESS = "Edited Question: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_QUESTION = "This question already exists in the modulo quiz.";

    private final Index index;
    private final EditQuestionDescriptor editQuestionDescriptor;

    /**
     * @param index of the question in the filtered question list to edit
     * @param editQuestionDescriptor details to edit the question with
     */
    public EditCommand(Index index, EditQuestionDescriptor editQuestionDescriptor) {
        requireNonNull(index);
        requireNonNull(editQuestionDescriptor);

        this.index = index;
        this.editQuestionDescriptor = new EditQuestionDescriptor(editQuestionDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Question> lastShownList = model.getFilteredQuestionList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_QUESTION_DISPLAYED_INDEX);
        }

        Question questionToEdit = lastShownList.get(index.getZeroBased());
        Question editedQuestion = createEditedQuestion(questionToEdit, editQuestionDescriptor);

        if (!questionToEdit.isSameQuestion(editedQuestion) && model.hasQuestion(editedQuestion)) {
            throw new CommandException(MESSAGE_DUPLICATE_QUESTION);
        }

        model.setQuestion(questionToEdit, editedQuestion);
        model.updateFilteredQuestionList(PREDICATE_SHOW_ALL_QUESTIONS);
        return new CommandResult(String.format(MESSAGE_EDIT_QUESTION_SUCCESS, editedQuestion));
    }

    /**
     * Creates and returns a {@code Question} with the details of {@code questionToEdit}
     * edited with {@code editQuestionDescriptor}.
     */
    private static Question createEditedQuestion(Question questionToEdit,
                                                 EditQuestionDescriptor editQuestionDescriptor) {
        assert questionToEdit != null;

        Name updatedName = editQuestionDescriptor.getName().orElse(questionToEdit.getName());
        Answer updatedAnswer = editQuestionDescriptor.getAnswer().orElse(questionToEdit.getAnswer());
        Category updatedCategory = editQuestionDescriptor.getCategory().orElse(questionToEdit.getCategory());
        Type updatedType = editQuestionDescriptor.getType().orElse(questionToEdit.getType());
        Set<Tag> updatedTags = editQuestionDescriptor.getTags().orElse(questionToEdit.getTags());

        return new Question(updatedName, updatedAnswer, updatedCategory, updatedType, updatedTags);
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
                && editQuestionDescriptor.equals(e.editQuestionDescriptor);
    }

    /**
     * Stores the details to edit the question with. Each non-empty field value will replace the
     * corresponding field value of the question.
     */
    public static class EditQuestionDescriptor {
        private Name name;
        private Answer answer;
        private Category category;
        private Type type;
        private Set<Tag> tags;

        public EditQuestionDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditQuestionDescriptor(EditQuestionDescriptor toCopy) {
            setName(toCopy.name);
            setAnswer(toCopy.answer);
            setCategory(toCopy.category);
            setType(toCopy.type);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, answer, category, type, tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setAnswer(Answer answer) {
            this.answer = answer;
        }

        public Optional<Answer> getAnswer() {
            return Optional.ofNullable(answer);
        }

        public void setCategory(Category category) {
            this.category = category;
        }

        public Optional<Category> getCategory() {
            return Optional.ofNullable(category);
        }

        public void setType(Type type) {
            this.type = type;
        }

        public Optional<Type> getType() {
            return Optional.ofNullable(type);
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
            if (!(other instanceof EditQuestionDescriptor)) {
                return false;
            }

            // state check
            EditQuestionDescriptor e = (EditQuestionDescriptor) other;

            return getName().equals(e.getName())
                    && getAnswer().equals(e.getAnswer())
                    && getCategory().equals(e.getCategory())
                    && getType().equals(e.getType())
                    && getTags().equals(e.getTags());
        }
    }
}
