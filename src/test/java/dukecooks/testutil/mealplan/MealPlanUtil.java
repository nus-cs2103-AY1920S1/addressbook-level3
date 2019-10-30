package dukecooks.testutil.mealplan;

import java.util.List;

import dukecooks.logic.commands.mealplan.AddMealPlanCommand;
import dukecooks.logic.commands.mealplan.EditMealPlanCommand;
import dukecooks.logic.parser.CliSyntax;
import dukecooks.model.mealplan.components.MealPlan;
import dukecooks.model.recipe.components.RecipeName;

/**
 * A utility class for MealPlan.
 */
public class MealPlanUtil {

    /**
     * Returns an add command string for adding the {@code mealPlan}.
     */
    public static String getAddMealPlanCommand(MealPlan mealPlan) {
        return AddMealPlanCommand.COMMAND_WORD + " " + AddMealPlanCommand.VARIANT_WORD + " "
                + getMealPlanDetails(mealPlan);
    }

    /**
     * Returns the part of command string for the given {@code mealPlan}'s details.
     */
    public static String getMealPlanDetails(MealPlan mealPlan) {
        StringBuilder sb = new StringBuilder();
        sb.append(CliSyntax.PREFIX_NAME + mealPlan.getName().fullName + " ");
        mealPlan.getDay1().stream().forEach(
            s -> sb.append(CliSyntax.PREFIX_DAY1 + s.fullName + " ")
        );
        mealPlan.getDay2().stream().forEach(
            s -> sb.append(CliSyntax.PREFIX_DAY2 + s.fullName + " ")
        );
        mealPlan.getDay3().stream().forEach(
            s -> sb.append(CliSyntax.PREFIX_DAY3 + s.fullName + " ")
        );
        mealPlan.getDay4().stream().forEach(
            s -> sb.append(CliSyntax.PREFIX_DAY4 + s.fullName + " ")
        );
        mealPlan.getDay5().stream().forEach(
            s -> sb.append(CliSyntax.PREFIX_DAY5 + s.fullName + " ")
        );
        mealPlan.getDay6().stream().forEach(
            s -> sb.append(CliSyntax.PREFIX_DAY6 + s.fullName + " ")
        );
        mealPlan.getDay7().stream().forEach(
            s -> sb.append(CliSyntax.PREFIX_DAY7 + s.fullName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditMealPlanDescriptor}'s details.
     */
    public static String getEditMealPlanDescriptorDetails(EditMealPlanCommand.EditMealPlanDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(CliSyntax.PREFIX_NAME).append(name.fullName).append(" "));

        if (descriptor.getDay1ToAdd().isPresent()) {
            List<RecipeName> recipes = descriptor.getDay1ToAdd().get();
            if (recipes.isEmpty()) {
                sb.append(CliSyntax.PREFIX_DAY1);
            } else {
                recipes.forEach(s -> sb.append(CliSyntax.PREFIX_DAY1).append(s.fullName).append(" "));
            }
        }

        if (descriptor.getDay2ToAdd().isPresent()) {
            List<RecipeName> recipes = descriptor.getDay2ToAdd().get();
            if (recipes.isEmpty()) {
                sb.append(CliSyntax.PREFIX_DAY2);
            } else {
                recipes.forEach(s -> sb.append(CliSyntax.PREFIX_DAY2).append(s.fullName).append(" "));
            }
        }

        if (descriptor.getDay3ToAdd().isPresent()) {
            List<RecipeName> recipes = descriptor.getDay3ToAdd().get();
            if (recipes.isEmpty()) {
                sb.append(CliSyntax.PREFIX_DAY3);
            } else {
                recipes.forEach(s -> sb.append(CliSyntax.PREFIX_DAY3).append(s.fullName).append(" "));
            }
        }

        if (descriptor.getDay4ToAdd().isPresent()) {
            List<RecipeName> recipes = descriptor.getDay4ToAdd().get();
            if (recipes.isEmpty()) {
                sb.append(CliSyntax.PREFIX_DAY4);
            } else {
                recipes.forEach(s -> sb.append(CliSyntax.PREFIX_DAY4).append(s.fullName).append(" "));
            }
        }

        if (descriptor.getDay5ToAdd().isPresent()) {
            List<RecipeName> recipes = descriptor.getDay5ToAdd().get();
            if (recipes.isEmpty()) {
                sb.append(CliSyntax.PREFIX_DAY5);
            } else {
                recipes.forEach(s -> sb.append(CliSyntax.PREFIX_DAY5).append(s.fullName).append(" "));
            }
        }

        if (descriptor.getDay6ToAdd().isPresent()) {
            List<RecipeName> recipes = descriptor.getDay6ToAdd().get();
            if (recipes.isEmpty()) {
                sb.append(CliSyntax.PREFIX_DAY6);
            } else {
                recipes.forEach(s -> sb.append(CliSyntax.PREFIX_DAY6).append(s.fullName).append(" "));
            }
        }

        if (descriptor.getDay7ToAdd().isPresent()) {
            List<RecipeName> recipes = descriptor.getDay7ToAdd().get();
            if (recipes.isEmpty()) {
                sb.append(CliSyntax.PREFIX_DAY7);
            } else {
                recipes.forEach(s -> sb.append(CliSyntax.PREFIX_DAY7).append(s.fullName).append(" "));
            }
        }

        return sb.toString();
    }
}
