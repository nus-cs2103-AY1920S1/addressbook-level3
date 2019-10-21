package seedu.revision.testutil;

import static seedu.revision.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.revision.logic.parser.CliSyntax.PREFIX_CORRECT;
import static seedu.revision.logic.parser.CliSyntax.PREFIX_QUESTION;
import static seedu.revision.logic.parser.CliSyntax.PREFIX_DIFFICULTY;
import static seedu.revision.logic.parser.CliSyntax.PREFIX_QUESTION_TYPE;
import static seedu.revision.logic.parser.CliSyntax.PREFIX_WRONG;

import java.util.Set;

import seedu.revision.logic.commands.AddCommand;
import seedu.revision.logic.commands.EditCommand;
import seedu.revision.model.answerable.Answer;
import seedu.revision.model.answerable.Answerable;
import seedu.revision.model.answerable.Mcq;
import seedu.revision.model.category.Category;

/**
 * A utility class for Answerable.
 */
public class AnswerableUtil {

    /**
     * Returns an add command string for adding the {@code answerable}.
     */
    public static String getAddCommand(Answerable answerable) {
        return AddCommand.COMMAND_WORD + " " + getAnswerableDetails(answerable);
    }

    /**
     * Returns the part of command string for the given {@code answerable}'s details.
     */
    public static String getAnswerableDetails(Answerable answerable) {
        StringBuilder sb = new StringBuilder();
        if (answerable instanceof Mcq) {
            sb.append(PREFIX_QUESTION_TYPE + "mcq" + " ");
            answerable.getWrongAnswerSet().stream().forEach(
                    s -> sb.append(PREFIX_WRONG + s.answer + " ")
            );
        } else {
            sb.append(PREFIX_QUESTION_TYPE + "saq" + " ");
        }
        sb.append(PREFIX_QUESTION + answerable.getQuestion().fullQuestion + " ");
        sb.append(PREFIX_DIFFICULTY + answerable.getDifficulty().value + " ");
        answerable.getCorrectAnswerSet().stream().forEach(
            s -> sb.append(PREFIX_CORRECT + s.answer + " ")
        );
        answerable.getCategories().stream().forEach(
            s -> sb.append(PREFIX_CATEGORY + s.categoryName+ " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditAnswerableDescriptor}'s details.
     */
    public static String getEditAnswerableDescriptorDetails(EditCommand.EditAnswerableDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getQuestion().ifPresent(question -> sb.append(PREFIX_QUESTION).append(question.fullQuestion).append(" "));
        descriptor.getDifficulty().ifPresent(difficulty -> sb.append(PREFIX_DIFFICULTY).append(difficulty.value).append(" "));
        if (descriptor.getCategories().isPresent()) {
            Set<Category> tags = descriptor.getCategories().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_CATEGORY + " ");
            } else {
                tags.forEach(s -> sb.append(PREFIX_CATEGORY).append(s.categoryName).append(" "));
            }
        }
        if (descriptor.getCorrectAnswerSet().isPresent()) {
            Set<Answer> correctAnswerSet = descriptor.getCorrectAnswerSet().get();
            if (correctAnswerSet.isEmpty()) {
                sb.append(PREFIX_CORRECT + " ");
            } else {
                correctAnswerSet.forEach( s -> sb.append(PREFIX_CORRECT + s.answer + " "));
            }
        }
        if (descriptor.getWrongAnswerSet().isPresent()) {
            Set<Answer> wrongAnswerSet = descriptor.getWrongAnswerSet().get();
            if (wrongAnswerSet.isEmpty()) {
                sb.append(PREFIX_WRONG + " ");
            } else {
                wrongAnswerSet.forEach( s -> sb.append(PREFIX_WRONG + s.answer + " "));
            }
        }
        return sb.toString();
    }
}
