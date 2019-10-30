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

    public static final MealPlan MILO_MP = new MealPlanBuilder().withName("Milo Plan")
            .withDay1("Milo").withDay2("Milo").withDay3("Milo").withDay4("Milo")
            .withDay5("Milo").withDay6("Milo").withDay7("Milo").build();
    public static final MealPlan OMELETTE_MP = new MealPlanBuilder().withName("Cheese Omelette Plan")
            .withDay1("Cheese Omelette").withDay2("Cheese Omelette").withDay3("Cheese Omelette")
            .withDay4("Cheese Omelette").withDay5("Cheese Omelette").withDay6("Cheese Omelette")
            .withDay7("Cheese Omelette").build();
    public static final MealPlan TUNA_MP = new MealPlanBuilder().withName("Tuna Sandwich Plan")
            .withDay1("Tuna Sandwich").withDay2("Tuna Sandwich").withDay3("Tuna Sandwich").withDay4("Tuna Sandwich")
            .withDay5("Tuna Sandwich").withDay6("Tuna Sandwich").withDay7("Tuna Sandwich").build();
    public static final MealPlan MAGGI_MP = new MealPlanBuilder().withName("Chicken Maggi Plan")
            .withDay1("Chicken Maggi").withDay2("Chicken Maggi").withDay3("Chicken Maggi").withDay4("Chicken Maggi")
            .withDay5("Chicken Maggi").withDay6("Chicken Maggi").withDay7("Chicken Maggi").build();
    public static final MealPlan CHICKEN_MP = new MealPlanBuilder().withName("Fried Chicken Plan")
            .withDay1("Fried Chicken").withDay2("Fried Chicken").withDay3("Fried Chicken").withDay4("Fried Chicken")
            .withDay5("Fried Chicken").withDay6("Fried Chicken").withDay7("Fried Chicken").build();
    public static final MealPlan TEA_MP = new MealPlanBuilder().withName("Tea Plan")
            .withDay1("Tea").withDay2("Tea").withDay3("Tea").withDay4("Tea")
            .withDay5("Tea").withDay6("Tea").withDay7("Tea").build();
    public static final MealPlan FRIES_MP = new MealPlanBuilder().withName("French Fries Plan")
            .withDay1("French Fries").withDay2("French Fries").withDay3("French Fries").withDay4("French Fries")
            .withDay5("French Fries").withDay6("French Fries").withDay7("French Fries").build();

    // Manually added
    public static final MealPlan TAMAGO_MP = new MealPlanBuilder().withName("Tamago Maki Plan")
            .withDay1("Tamago Maki").withDay2("Tamago Maki").withDay3("Tamago Maki").withDay4("Tamago Maki")
            .withDay5("Tamago Maki").withDay6("Tamago Maki").withDay7("Tamago Maki").build();
    public static final MealPlan KAPPA_MP = new MealPlanBuilder().withName("Kappa Maki Plan")
            .withDay1("Kappa Maki").withDay2("Kappa Maki").withDay3("Kappa Maki").withDay4("Kappa Maki")
            .withDay5("Kappa Maki").withDay6("Kappa Maki").withDay7("Kappa Maki").build();

    // Manually added - MealPlan's details found in {@code CommandTestUtil}
    public static final MealPlan FISH_MP = new MealPlanBuilder().withName(CommandTestUtil.VALID_NAME_FISH_MP)
            .withDay1(CommandTestUtil.VALID_NAME_FISH).withDay2(CommandTestUtil.VALID_NAME_FISH)
            .withDay3(CommandTestUtil.VALID_NAME_FISH).withDay4(CommandTestUtil.VALID_NAME_FISH)
            .withDay5(CommandTestUtil.VALID_NAME_FISH).withDay6(CommandTestUtil.VALID_NAME_FISH)
            .withDay7(CommandTestUtil.VALID_NAME_FISH).build();
    public static final MealPlan BURGER_MP = new MealPlanBuilder().withName(CommandTestUtil.VALID_NAME_BURGER_MP)
            .withDay1(CommandTestUtil.VALID_NAME_BURGER).withDay2(CommandTestUtil.VALID_NAME_BURGER)
            .withDay3(CommandTestUtil.VALID_NAME_BURGER).withDay4(CommandTestUtil.VALID_NAME_BURGER)
            .withDay5(CommandTestUtil.VALID_NAME_BURGER).withDay6(CommandTestUtil.VALID_NAME_BURGER)
            .withDay7(CommandTestUtil.VALID_NAME_BURGER).build();

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
        return new ArrayList<>(Arrays.asList(MILO_MP, OMELETTE_MP, TUNA_MP, MAGGI_MP, CHICKEN_MP, TEA_MP, FRIES_MP));
    }
}
