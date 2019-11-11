package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_MEANING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WORD;

import java.util.Set;

import seedu.address.logic.commands.cardcommands.AddCommand;
import seedu.address.logic.commands.cardcommands.EditCommand;
import seedu.address.model.card.Card;
import seedu.address.model.tag.Tag;

/**
 * A utility class for Card.
 */
public class CardUtil {

    /**
     * Returns an add command string for adding the {@code card}.
     */
    public static String getAddCommand(Card card) {
        return AddCommand.COMMAND_WORD + " " + getCardDetails(card);
    }

    /**
     * Returns the part of command string for the given {@code card}'s details.
     */
    private static String getCardDetails(Card card) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_WORD + card.getWord().getValue() + " ");
        sb.append(PREFIX_MEANING + card.getMeaning().getValue() + " ");
        card.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.getTagName() + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditCardDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditCommand.EditCardDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getWord().ifPresent(name -> sb.append(PREFIX_WORD).append(name.getValue()).append(" "));
        descriptor.getMeaning().ifPresent(phone -> sb.append(PREFIX_MEANING).append(phone.getValue()).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.getTagName()).append(" "));
            }
        }
        return sb.toString();
    }
}
