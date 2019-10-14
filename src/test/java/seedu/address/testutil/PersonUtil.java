package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.recipe.Recipe;
import seedu.address.model.tag.Tag;

/**
 * A utility class for Recipe.
 */
public class PersonUtil {

    /**
     * Returns an add command string for adding the {@code recipe}.
     */
    public static String getAddCommand(Recipe recipe) {
        return AddCommand.COMMAND_WORD + " " + getPersonDetails(recipe);
    }

    /**
     * Returns the part of command string for the given {@code recipe}'s details.
     */
    public static String getPersonDetails(Recipe recipe) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + recipe.getName().fullName + " ");
        recipe.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditPersonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
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
