package dukecooks.testutil.mealplan;


import java.util.Set;

import dukecooks.logic.commands.mealplan.AddMealPlanCommand;
import dukecooks.logic.commands.mealplan.EditMealPlanCommand;
import dukecooks.logic.parser.CliSyntax;
import dukecooks.model.mealplan.components.MealPlan;
import dukecooks.model.recipe.components.Ingredient;

/**
 * A utility class for MealPlan.
 */
public class MealPlanUtil {

    /**
     * Returns an add command string for adding the {@code mealPlan}.
     */
    public static String getAddMealPlanCommand(MealPlan mealPlan) {
        return AddMealPlanCommand.COMMAND_WORD + " " + AddMealPlanCommand.VARIANT_WORD + " " + getMealPlanDetails(mealPlan);
    }

    /**
     * Returns the part of command string for the given {@code mealPlan}'s details.
     */
    public static String getMealPlanDetails(MealPlan mealPlan) {
        StringBuilder sb = new StringBuilder();
        sb.append(CliSyntax.PREFIX_NAME + mealPlan.getName().fullName + " ");
        mealPlan.getIngredients().stream().forEach(
            s -> sb.append(CliSyntax.PREFIX_INGREDIENT + s.ingredientName + " ")
        );
        sb.append(CliSyntax.PREFIX_CALORIES + mealPlan.getCalories().value + " ");
        sb.append(CliSyntax.PREFIX_CARBS + mealPlan.getCarbs().value + " ");
        sb.append(CliSyntax.PREFIX_FATS + mealPlan.getFats().value + " ");
        sb.append(CliSyntax.PREFIX_PROTEIN + mealPlan.getProtein().value + " ");
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditMealPlanDescriptor}'s details.
     */
    public static String getEditMealPlanDescriptorDetails(EditMealPlanCommand.EditMealPlanDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(CliSyntax.PREFIX_NAME).append(name.fullName).append(" "));
        if (descriptor.getIngredientsToAdd().isPresent()) {
            Set<Ingredient> ingredients = descriptor.getIngredientsToAdd().get();
            if (ingredients.isEmpty()) {
                sb.append(CliSyntax.PREFIX_INGREDIENT);
            } else {
                ingredients.forEach(s -> sb.append(CliSyntax.PREFIX_INGREDIENT).append(s.ingredientName).append(" "));
            }
        }
        descriptor.getCalories().ifPresent(calories -> sb.append(CliSyntax.PREFIX_CALORIES).append(calories.value)
                .append(" "));
        descriptor.getCarbs().ifPresent(carbs -> sb.append(CliSyntax.PREFIX_CARBS).append(carbs.value).append(" "));
        descriptor.getFats().ifPresent(fats -> sb.append(CliSyntax.PREFIX_FATS).append(fats.value).append(" "));
        descriptor.getProtein().ifPresent(protein -> sb.append(CliSyntax.PREFIX_PROTEIN).append(protein.value)
                .append(" "));
        return sb.toString();
    }
}
