package com.dukeacademy.testutil;

import java.util.Set;

import com.dukeacademy.logic.commands.AddCommand;
import com.dukeacademy.logic.commands.EditCommand;
import com.dukeacademy.logic.parser.CliSyntax;
import com.dukeacademy.model.question.Question;
import com.dukeacademy.model.tag.Tag;

/**
 * A utility class for Question.
 */
public class QuestionUtil {

    /**
     * Returns an add command string for adding the {@code question}.
     */
    public static String getAddCommand(Question question) {
        return AddCommand.COMMAND_WORD + " " + getQuestionDetails(question);
    }

    /**
     * Returns the part of command string for the given {@code question}'s details.
     */
    public static String getQuestionDetails(Question question) {
        StringBuilder sb = new StringBuilder();
        sb.append(CliSyntax.PREFIX_TITLE + question.getTitle().fullTitle + " ");
        sb.append(CliSyntax.PREFIX_TOPIC + question.getTopic().value + " ");
        sb.append(CliSyntax.PREFIX_STATUS + question.getStatus().value + " ");
        sb.append(CliSyntax.PREFIX_DIFFICULTY + question.getDifficulty().value + " ");
        question.getTags().stream().forEach(
            s -> sb.append(CliSyntax.PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditQuestionDescriptor}'s details.
     */
    public static String getEditQuestionDescriptorDetails(EditCommand.EditQuestionDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getTitle().ifPresent(title -> sb.append(CliSyntax.PREFIX_TITLE).append(title.fullTitle).append(" "));
        descriptor.getTopic().ifPresent(topic -> sb.append(CliSyntax.PREFIX_TOPIC).append(topic.value).append(" "));
        descriptor.getStatus().ifPresent(status -> sb.append(CliSyntax.PREFIX_STATUS).append(status.value).append(" "));
        descriptor.getDifficulty().ifPresent(
            difficulty -> sb.append(CliSyntax.PREFIX_DIFFICULTY)
                            .append(difficulty.value)
                            .append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(CliSyntax.PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(CliSyntax.PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
