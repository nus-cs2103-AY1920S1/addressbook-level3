package seedu.flashcard.testutil;

import static seedu.flashcard.logic.parser.CliSyntax.PREFIX_QUESTION;
import static seedu.flashcard.logic.parser.CliSyntax.PREFIX_DEFINITION;
import static seedu.flashcard.logic.parser.CliSyntax.PREFIX_ANSWER;
import static seedu.flashcard.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.flashcard.logic.parser.CliSyntax.PREFIX_CHOICE;

import seedu.flashcard.logic.commands.AddCommand;
import seedu.flashcard.logic.commands.EditCommand.EditFlashcardDescriptor;
import seedu.flashcard.model.flashcard.Choice;
import seedu.flashcard.model.flashcard.Flashcard;
import seedu.flashcard.model.flashcard.McqFlashcard;
import seedu.flashcard.model.flashcard.ShortAnswerFlashcard;
import seedu.flashcard.model.tag.Tag;

import java.util.List;
import java.util.Set;

/**
 * A utility class of flashcards
 */
public class FlashcardUtil {

    public static String getAddCommand(Flashcard flashcard) {
        if (flashcard.isMcq()) {
            return AddCommand.COMMAND_WORD + " " + getFlashcardDetails((McqFlashcard) flashcard);
        } else {
            return AddCommand.COMMAND_WORD + " " + getFlashcardDetails((ShortAnswerFlashcard) flashcard);
        }
    }

    /**
     * For short answer flashcard usage.
     * Returns the part of command string for the given {@code flashcard}'s details.
     */
    public static String getFlashcardDetails(ShortAnswerFlashcard flashcard) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_QUESTION + flashcard.getQuestion().question + " ");
        sb.append(PREFIX_DEFINITION + flashcard.getDefinition().definition + " ");
        sb.append(PREFIX_ANSWER + flashcard.getAnswer().answer + " ");
        flashcard.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * For MCQ flashcard usage.
     * Returns the part of command string for the given {@code flashcard}'s details.
     */
    public static String getFlashcardDetails(McqFlashcard flashcard) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_QUESTION + flashcard.getQuestion().question + " ");
        sb.append(PREFIX_DEFINITION + flashcard.getDefinition().definition + " ");
        sb.append(PREFIX_ANSWER + flashcard.getAnswer().answer + " ");
        flashcard.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        flashcard.getChoices().stream().forEach(
            s -> sb.append(PREFIX_CHOICE + s.choice + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditFlashcardDescriptor}'s details.
     */
    public static String getEditFlashcardDescriptorDetails(EditFlashcardDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getQuestion().ifPresent(question -> sb.append(PREFIX_QUESTION).append(question.question).append(" "));
        descriptor.getDefinition().ifPresent(definition -> sb.append(PREFIX_DEFINITION).append(definition.definition).append(" "));
        descriptor.getAnswer().ifPresent(answer -> sb.append(PREFIX_ANSWER).append(answer.answer).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        if (descriptor.getTags().isPresent()) {
            List<Choice> choices = descriptor.getChoices().get();
            if (choices.isEmpty()) {
                sb.append(PREFIX_CHOICE);
            } else {
                choices.forEach(c -> sb.append(PREFIX_CHOICE).append(c.choice).append(" "));
            }
        }
        return sb.toString();
    }
}
