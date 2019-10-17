package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CORRECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUESTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DIFFICULTY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUESTION_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WRONG;

import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.model.answerable.Answer;
import seedu.address.model.answerable.Answerable;
import seedu.address.model.category.Category;

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
        //TODO: Implement Answerable
        sb.append(PREFIX_QUESTION_TYPE + "mcq" + " ");
        sb.append(PREFIX_QUESTION + answerable.getQuestion().fullQuestion + " ");
        sb.append(PREFIX_DIFFICULTY + answerable.getDifficulty().value + " ");
        answerable.getCorrectAnswerSet().stream().forEach(
            s -> sb.append(PREFIX_CORRECT + s.answer + " ")
        );
        answerable.getWrongAnswerSet().stream().forEach(
                s -> sb.append(PREFIX_WRONG + s.answer + " ")
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
