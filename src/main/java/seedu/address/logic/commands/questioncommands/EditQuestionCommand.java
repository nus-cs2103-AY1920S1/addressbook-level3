package seedu.address.logic.commands.questioncommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ANSWER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DIFFICULTY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUESTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_QUESTIONS;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.question.Answer;
import seedu.address.model.question.Difficulty;
import seedu.address.model.question.Question;
import seedu.address.model.question.QuestionBody;
import seedu.address.model.question.Subject;

/**
 * Edits the details of an existing question.
 */
public class EditQuestionCommand extends Command {
    public static final String COMMAND_WORD = "editq";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the question identified "
            + "by the index number used in the displayed question list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_QUESTION + "QUESTION] "
            + "[" + PREFIX_ANSWER + "ANSWER] "
            + "[" + PREFIX_SUBJECT + "SUBJECT] "
            + "[" + PREFIX_DIFFICULTY + "DIFFICULTY] "
            + "Example: " + COMMAND_WORD + " 1 + 1 = "
            + PREFIX_ANSWER + "2"
            + PREFIX_SUBJECT + "Math";

    public static final String MESSAGE_EDIT_QUESTION_SUCCESS = "Edited Question: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_QUESTION = "This question already exists in NUStudy.";

    private final Index index;
    private final EditQuestionDescriptor editQuestionDescriptor;

    /**
     * @param index of the question in the filtered question list to edit
     * @param editQuestionDescriptor details to edit the question with
     */
    public EditQuestionCommand(Index index, EditQuestionDescriptor editQuestionDescriptor) {
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

        QuestionBody updatedQuestionBody = editQuestionDescriptor.getQuestionBody()
                .orElse(questionToEdit.getQuestionBody());
        Answer updatedAnswer = editQuestionDescriptor.getAnswer().orElse(questionToEdit.getAnswer());
        Subject updatedSubject = editQuestionDescriptor.getSubject().orElse(questionToEdit.getSubject());
        Difficulty updatedDifficulty = editQuestionDescriptor.getDifficulty().orElse(questionToEdit.getDifficulty());

        return new Question(updatedQuestionBody, updatedAnswer, updatedSubject, updatedDifficulty);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditQuestionCommand)) {
            return false;
        }

        // state check
        EditQuestionCommand e = (EditQuestionCommand) other;
        return index.equals(e.index)
                && editQuestionDescriptor.equals(e.editQuestionDescriptor);
    }

    /**
     * Stores the details to edit the question with. Each non-empty field value will replace the
     * corresponding field value of the question.
     */
    public static class EditQuestionDescriptor {
        private QuestionBody questionBody;
        private Answer answer;
        private Subject subject;
        private Difficulty address;

        public EditQuestionDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditQuestionDescriptor(EditQuestionDescriptor toCopy) {
            setQuestionBody(toCopy.questionBody);
            setAnswer(toCopy.answer);
            setSubject(toCopy.subject);
            setDifficulty(toCopy.address);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(questionBody, answer, subject, address);
        }

        public void setQuestionBody(QuestionBody questionBody) {
            this.questionBody = questionBody;
        }

        public Optional<QuestionBody> getQuestionBody() {
            return Optional.ofNullable(questionBody);
        }

        public void setAnswer(Answer answer) {
            this.answer = answer;
        }

        public Optional<Answer> getAnswer() {
            return Optional.ofNullable(answer);
        }

        public void setSubject(Subject subject) {
            this.subject = subject;
        }

        public Optional<Subject> getSubject() {
            return Optional.ofNullable(subject);
        }

        public void setDifficulty(Difficulty address) {
            this.address = address;
        }

        public Optional<Difficulty> getDifficulty() {
            return Optional.ofNullable(address);
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

            return getQuestionBody().equals(e.getQuestionBody())
                    && getAnswer().equals(e.getAnswer())
                    && getSubject().equals(e.getSubject())
                    && getDifficulty().equals(e.getDifficulty());
        }
    }
}
