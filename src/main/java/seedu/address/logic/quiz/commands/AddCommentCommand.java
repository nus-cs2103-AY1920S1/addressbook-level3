package seedu.address.logic.quiz.commands;

import static java.util.Objects.requireNonNull;

import static seedu.address.logic.quiz.parser.CliSyntax.PREFIX_COMMENT;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.quiz.commands.exceptions.CommandException;
import seedu.address.model.quiz.Model;

import seedu.address.model.quiz.person.Answer;
import seedu.address.model.quiz.person.Category;
import seedu.address.model.quiz.person.Comment;
import seedu.address.model.quiz.person.Name;
import seedu.address.model.quiz.person.Question;
import seedu.address.model.quiz.person.Type;
import seedu.address.model.quiz.tag.Tag;

/**
 * Add comment or explanation of an existing question in modulo quiz.
 */
public class AddCommentCommand extends Command {

    public static final String COMMAND_WORD = "comment";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Add comment/explanation of the question identified "
            + "by the index number used in the displayed question list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: " + COMMAND_WORD
            + " INDEX (must be a positive integer) "
            + PREFIX_COMMENT + "COMMENT" + "\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_COMMENT
            + "The explanation is on lecture notes chapter 9 page 24.";

    public static final String MESSAGE_COMMENT_QUESTION_SUCCESS = "Updated comment for question %1$s";

    private final Index index;
    private final String questionComment;

    /**
     * Creates an AddCommentCommand to add the specified {@code Question}
     *
     * @param index of the question in the filtered question list that needs to be edited
     * @param questionComment to add a comment to the specified index question.
     */
    public AddCommentCommand(Index index, String questionComment) {
        requireNonNull(index);
        requireNonNull(questionComment);

        this.index = index;
        this.questionComment = questionComment.trim();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Comment comment = new Comment(questionComment);
        List<Question> lastShownList = model.getFilteredQuestionList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_QUESTION_DISPLAYED_INDEX);
        }

        Question questionToEdit = lastShownList.get(index.getZeroBased());
        Question editedQuestion = createEditedQuestion(questionToEdit, comment);

        model.setQuestion(questionToEdit, editedQuestion);
        model.commitQuizBook();

        String commentMessages = index.getOneBased() + ": " + questionComment + ".\nEnter detail "
                + index.getOneBased() + " to see the updates.";

        return new CommandResult(String.format(MESSAGE_COMMENT_QUESTION_SUCCESS, commentMessages),
                "detail");
    }

    /**
     * Creates and returns a {@code Question} with the details of {@code questionToEdit}
     * edited with {@code editQuestionDescriptor}.
     */
    private static Question createEditedQuestion(Question questionToEdit,
                                                 Comment comment) {
        assert questionToEdit != null;

        Name updatedName = questionToEdit.getName();
        Comment commentedQuestion = comment;
        Answer updatedAnswer = questionToEdit.getAnswer();
        Category updatedCategory = questionToEdit.getCategory();
        Type updatedType = questionToEdit.getType();
        Set<Tag> updatedTags = questionToEdit.getTags();

        return new Question(updatedName, commentedQuestion, updatedAnswer, updatedCategory, updatedType, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddCommentCommand)) {
            return false;
        }

        // state check
        AddCommentCommand e = (AddCommentCommand) other;
        return index.equals(e.index)
                && questionComment.equals(e.questionComment);
    }
}
