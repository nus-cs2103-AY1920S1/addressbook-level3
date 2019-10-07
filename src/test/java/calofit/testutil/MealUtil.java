package calofit.testutil;

import calofit.logic.commands.AddCommand;
import calofit.logic.commands.EditCommand;
import calofit.logic.parser.CliSyntax;
import calofit.model.meal.Meal;
import calofit.model.tag.Tag;

import java.util.Set;

/**
 * A utility class for Meal.
 */
public class MealUtil {

    /**
     * Returns an add command string for adding the {@code meal}.
     */
    public static String getAddCommand(Meal meal) {
        return AddCommand.COMMAND_WORD + " " + getMealDetails(meal);
    }

    /**
     * Returns the part of command string for the given {@code meal}'s details.
     */
    public static String getMealDetails(Meal meal) {
        StringBuilder sb = new StringBuilder();
        sb.append(CliSyntax.PREFIX_NAME + meal.getName().fullName + " ");
        meal.getTags().stream().forEach(
            s -> sb.append(CliSyntax.PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditMealDescriptor}'s details.
     */
    public static String getEditMealDescriptorDetails(EditCommand.EditMealDescriptor descriptor) {
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
