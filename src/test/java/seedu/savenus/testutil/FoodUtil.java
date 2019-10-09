package seedu.savenus.testutil;

import static seedu.savenus.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.savenus.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.savenus.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.savenus.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.savenus.logic.commands.AddCommand;
import seedu.savenus.logic.commands.EditCommand.EditFoodDescriptor;
import seedu.savenus.model.food.Food;
import seedu.savenus.model.tag.Tag;

/**
 * A utility class for Food.
 */
public class FoodUtil {

    /**
     * Returns an add command string for adding the {@code food}.
     */
    public static String getAddCommand(Food food) {
        return AddCommand.COMMAND_WORD + " " + getfoodDetails(food);
    }

    /**
     * Returns the part of command string for the given {@code food}'s details.
     */
    public static String getfoodDetails(Food food) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + food.getName().fullName + " ");
        sb.append(PREFIX_PRICE + food.getPrice().value + " ");
        sb.append(PREFIX_DESCRIPTION + food.getDescription().value + " ");
        food.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditFoodDescriptor}'s details.
     */
    public static String getEditFoodDescriptorDetails(EditFoodDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPrice().ifPresent(price -> sb.append(PREFIX_PRICE).append(price.value).append(" "));
        descriptor.getDescription().ifPresent(description -> sb.append(PREFIX_DESCRIPTION)
                .append(description.value).append(" "));
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
