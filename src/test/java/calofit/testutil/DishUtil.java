package calofit.testutil;

import calofit.logic.commands.AddCommand;
import calofit.logic.commands.EditCommand;
import calofit.logic.parser.CliSyntax;
import calofit.model.dish.Dish;
import calofit.model.tag.Tag;
import java.util.Set;

/**
 * A utility class for Dish.
 */
public class DishUtil {

    /**
     * Returns an add command string for adding the {@code dish}.
     */
    public static String getAddCommand(Dish dish) {
        return AddCommand.COMMAND_WORD + " " + getDishDetails(dish);
    }

    /**
     * Returns the part of command string for the given {@code dish}'s details.
     */
    public static String getDishDetails(Dish dish) {
        StringBuilder sb = new StringBuilder();
        sb.append(CliSyntax.PREFIX_NAME + dish.getName().fullName + " ");
        dish.getTags().stream().forEach(
            s -> sb.append(CliSyntax.PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditDishDescriptor}'s details.
     */
    public static String getEditDishDescriptorDetails(EditCommand.EditDishDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(CliSyntax.PREFIX_NAME).append(name.fullName).append(" "));
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
