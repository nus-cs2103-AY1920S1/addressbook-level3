package seedu.address.testutil;

import static seedu.address.logic.quiz.parser.CliSyntax.PREFIX_ANSWER;
import static seedu.address.logic.quiz.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.quiz.parser.CliSyntax.PREFIX_QUESTION;
import static seedu.address.logic.quiz.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.quiz.parser.CliSyntax.PREFIX_TYPE;

import java.util.Set;

import seedu.address.logic.quiz.commands.AddCommand;
import seedu.address.logic.quiz.commands.EditCommand.EditQuestionDescriptor;
import seedu.address.model.quiz.person.Question;
import seedu.address.model.quiz.tag.Tag;

/**
 * A utility class for Question.
 */
public class QuestionUtil {

    /**
     * Returns an add command string for adding the {@code question}.
     */
    public static String getAddCommand(Question question) {
        return AddCommand.COMMAND_WORD + " " + getPersonDetails(question);
    }

    /**
     * Returns the part of command string for the given {@code question}'s details.
     */
    public static String getPersonDetails(Question question) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_QUESTION + question.getName().fullName + " ");
        sb.append(PREFIX_ANSWER + question.getAnswer().value + " ");
        sb.append(PREFIX_CATEGORY + question.getCategory().value + " ");
        sb.append(PREFIX_TYPE + question.getType().value + " ");
        question.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditQuestionDescriptor}'s details.
     */
    public static String getEditQuestionDescriptorDetails(EditQuestionDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_QUESTION).append(name.fullName).append(" "));
        descriptor.getAnswer().ifPresent(answer -> sb.append(PREFIX_ANSWER).append(answer.value).append(" "));
        descriptor.getCategory().ifPresent(category -> sb.append(PREFIX_CATEGORY).append(category.value).append(" "));
        descriptor.getType().ifPresent(type -> sb.append(PREFIX_TYPE).append(type.value).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
