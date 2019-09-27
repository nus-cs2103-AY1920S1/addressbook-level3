package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_RATING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUESTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ANSWER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;

import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.category.Category;
import seedu.address.model.flashcard.FlashCard;

/**
 * A utility class for FlashCard.
 */
public class PersonUtil {

    /**
     * Returns an add command string for adding the {@code flashCard}.
     */
    public static String getAddCommand(FlashCard flashCard) {
        return AddCommand.COMMAND_WORD + " " + getPersonDetails(flashCard);
    }

    /**
     * Returns the part of command string for the given {@code flashCard}'s details.
     */
    public static String getPersonDetails(FlashCard flashCard) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_QUESTION + flashCard.getQuestion().fullQuestion + " ");
        sb.append(PREFIX_ANSWER + flashCard.getAnswer().fullAnswer + " ");
        sb.append(PREFIX_RATING + flashCard.getRating().value + " ");
        flashCard.getCategories().stream().forEach(
            s -> sb.append(PREFIX_CATEGORY + s.categoryName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditPersonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getQuestion().ifPresent(name -> sb.append(PREFIX_QUESTION).append(name.fullQuestion).append(" "));
        descriptor.getAnswer().ifPresent(answer -> sb.append(PREFIX_ANSWER).append(answer.fullAnswer).append(" "));
        descriptor.getRating().ifPresent(address -> sb.append(PREFIX_RATING).append(address.value).append(" "));
        if (descriptor.getCategories().isPresent()) {
            Set<Category> categories = descriptor.getCategories().get();
            if (categories.isEmpty()) {
                sb.append(PREFIX_CATEGORY);
            } else {
                categories.forEach(s -> sb.append(PREFIX_CATEGORY).append(s.categoryName).append(" "));
            }
        }
        return sb.toString();
    }
}
