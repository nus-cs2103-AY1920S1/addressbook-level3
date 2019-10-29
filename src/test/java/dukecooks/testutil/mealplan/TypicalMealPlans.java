package dukecooks.testutil.mealplan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dukecooks.logic.commands.CommandTestUtil;
import dukecooks.model.mealplan.MealPlanBook;
import dukecooks.model.mealplan.components.MealPlan;

/**
 * A utility class containing a list of {@code MealPlan} objects to be used in tests.
 */
public class TypicalMealPlans {

    public static final MealPlan MILO = new MealPlanBuilder().withName("Milo")
            .withIngredients("Milo Powder")
            .withCalories("180").withCarbs("0").withFats("0").withProtein("0").build();
    public static final MealPlan OMELETTE = new MealPlanBuilder().withName("Cheese Omelette")
            .withIngredients("Eggs", "Cheese")
            .withCalories("358").withCarbs("1").withFats("28").withProtein("21").build();
    public static final MealPlan TUNA = new MealPlanBuilder().withName("Tuna Sandwich")
            .withIngredients("Tuna", "Bread")
            .withCalories("290").withCarbs("29").withFats("10").withProtein("24").build();
    public static final MealPlan MAGGI = new MealPlanBuilder().withName("Chicken Maggi")
            .withIngredients("Chicken Maggi")
            .withCalories("402").withCarbs("59").withFats("15").withProtein("10").build();
    public static final MealPlan CHICKEN = new MealPlanBuilder().withName("Fried Chicken")
            .withIngredients("Chicken Wings", "Flour", "Oil")
            .withCalories("410").withCarbs("3").withFats("29").withProtein("34").build();
    public static final MealPlan TEA = new MealPlanBuilder().withName("Tea")
            .withIngredients("Tea Leaves")
            .withCalories("0").withCarbs("0").withFats("0").withProtein("0").build();
    public static final MealPlan FRIES = new MealPlanBuilder().withName("French Fries")
            .withIngredients("Potatoes", "Salt", "Oil")
            .withCalories("312").withCarbs("41").withFats("15").withProtein("3").build();

    // Manually added
    public static final MealPlan TAMAGO = new MealPlanBuilder().withName("Tamago Maki")
            .withIngredients("Eggs", "Rice", "Nori")
            .withCalories("300").withCarbs("22").withFats("15").withProtein("20").build();
    public static final MealPlan KAPPA = new MealPlanBuilder().withName("Kappa Maki")
            .withIngredients("Cucumber", "Rice", "Nori")
            .withCalories("130").withCarbs("35").withFats("0").withProtein("0").build();

    // Manually added - MealPlan's details found in {@code CommandTestUtil}
    public static final MealPlan FISH = new MealPlanBuilder().withName(CommandTestUtil.VALID_NAME_FISH)
            .withIngredients(CommandTestUtil.VALID_INGREDIENT_FISH)
            .withCalories(CommandTestUtil.VALID_CALORIES_FISH).withCarbs(CommandTestUtil.VALID_CARBS_FISH)
            .withFats(CommandTestUtil.VALID_FATS_FISH).withProtein(CommandTestUtil.VALID_PROTEIN_FISH)
            .build();;
    public static final MealPlan BURGER = new MealPlanBuilder().withName(CommandTestUtil.VALID_NAME_BURGER)
            .withIngredients(CommandTestUtil.VALID_INGREDIENT_BURGER)
            .withCalories(CommandTestUtil.VALID_CALORIES_BURGER).withCarbs(CommandTestUtil.VALID_CARBS_BURGER)
            .withFats(CommandTestUtil.VALID_FATS_BURGER).withProtein(CommandTestUtil.VALID_PROTEIN_BURGER)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Maki"; // A keyword that matches MAKI

    private TypicalMealPlans() {} // prevents instantiation

    /**
     * Returns an {@code MealPlanBook} with all the typical mealPlans.
     */
    public static MealPlanBook getTypicalMealPlanBook() {
        MealPlanBook dc = new MealPlanBook();
        for (MealPlan mealPlan : getTypicalMealPlans()) {
            dc.addMealPlan(mealPlan);
        }
        return dc;
    }

    public static List<MealPlan> getTypicalMealPlans() {
        return new ArrayList<>(Arrays.asList(MILO, OMELETTE, TUNA, MAGGI, CHICKEN, TEA, FRIES));
    }
}
