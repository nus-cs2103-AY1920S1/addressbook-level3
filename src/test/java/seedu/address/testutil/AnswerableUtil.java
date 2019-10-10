package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUESTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DIFFICULTY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.model.answerable.Answerable;
import seedu.address.model.tag.Tag;

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
        sb.append(PREFIX_QUESTION + answerable.getQuestion().fullQuestion + " ");
        sb.append(PREFIX_DIFFICULTY + answerable.getDifficulty().value + " ");
        sb.append(PREFIX_CATEGORY + answerable.getCategory().value + " ");
        answerable.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
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
        descriptor.getCategory().ifPresent(address -> sb.append(PREFIX_CATEGORY).append(address.value).append(" "));
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
