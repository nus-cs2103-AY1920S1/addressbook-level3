package dukecooks.model.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import dukecooks.model.mealplan.MealPlanBook;
import dukecooks.model.mealplan.ReadOnlyMealPlanBook;
import dukecooks.model.mealplan.components.MealPlan;
import dukecooks.model.mealplan.components.MealPlanName;
import dukecooks.model.recipe.components.RecipeName;

/**
 * Contains utility methods for populating {@code MealPlanBook} with sample data.
 */
public class SampleMealPlanDataUtil {
    public static MealPlan[] getSampleMealPlans() {
        List<RecipeName> TEA = Arrays.asList(new RecipeName[]{new RecipeName("Tea")});
        List<RecipeName> MILO = Arrays.asList(new RecipeName[]{new RecipeName("Milo")});
        List<RecipeName> OMELETTE = Arrays.asList(new RecipeName[]{new RecipeName("Cheese Omelette")});
        List<RecipeName> TUNA = Arrays.asList(new RecipeName[]{new RecipeName("Tuna Sandwich")});
        List<RecipeName> MAGGI = Arrays.asList(new RecipeName[]{new RecipeName("Chicken Maggi")});
        List<RecipeName> FRIED = Arrays.asList(new RecipeName[]{new RecipeName("Fried Chicken")});
        return new MealPlan[] {
            new MealPlan(new MealPlanName("Tea Plan"), TEA, TEA, TEA, TEA, TEA, TEA, TEA),
            new MealPlan(new MealPlanName("Milo Plan"), MILO, MILO, MILO, MILO, MILO, MILO, MILO),
            new MealPlan(new MealPlanName("Cheese Omelette Plan"), OMELETTE, OMELETTE, OMELETTE, OMELETTE, OMELETTE,
                    OMELETTE, OMELETTE),
            new MealPlan(new MealPlanName("Tuna Sandwich Plan"), TUNA, TUNA, TUNA, TUNA, TUNA, TUNA, TUNA),
            new MealPlan(new MealPlanName("Chicken Maggi Plan"), MAGGI, MAGGI, MAGGI, MAGGI, MAGGI, MAGGI, MAGGI),
            new MealPlan(new MealPlanName("Fried Chicken Plan"), FRIED, FRIED, FRIED, FRIED, FRIED, FRIED, FRIED),
        };
    }

    public static ReadOnlyMealPlanBook getSampleMealPlanBook() {
        MealPlanBook sampleMpb = new MealPlanBook();
        for (MealPlan sampleMealPlan : getSampleMealPlans()) {
            sampleMpb.addMealPlan(sampleMealPlan);
        }
        return sampleMpb;
    }

    /**
     * Returns a day 1 recipe name list containing the list of strings given.
     */
    public static List<RecipeName> getDay1(String... strings) {
        return Arrays.stream(strings)
                .map(RecipeName::new)
                .collect(Collectors.toList());
    }

    /**
     * Returns a day 2 recipe name list containing the list of strings given.
     */
    public static List<RecipeName> getDay2(String... strings) {
        return Arrays.stream(strings)
                .map(RecipeName::new)
                .collect(Collectors.toList());
    }

    /**
     * Returns a day 3 recipe name list containing the list of strings given.
     */
    public static List<RecipeName> getDay3(String... strings) {
        return Arrays.stream(strings)
                .map(RecipeName::new)
                .collect(Collectors.toList());
    }

    /**
     * Returns a day 4 recipe name list containing the list of strings given.
     */
    public static List<RecipeName> getDay4(String... strings) {
        return Arrays.stream(strings)
                .map(RecipeName::new)
                .collect(Collectors.toList());
    }

    /**
     * Returns a day 5 recipe name list containing the list of strings given.
     */
    public static List<RecipeName> getDay5(String... strings) {
        return Arrays.stream(strings)
                .map(RecipeName::new)
                .collect(Collectors.toList());
    }

    /**
     * Returns a day 6 recipe name list containing the list of strings given.
     */
    public static List<RecipeName> getDay6(String... strings) {
        return Arrays.stream(strings)
                .map(RecipeName::new)
                .collect(Collectors.toList());
    }

    /**
     * Returns a day 7 recipe name list containing the list of strings given.
     */
    public static List<RecipeName> getDay7(String... strings) {
        return Arrays.stream(strings)
                .map(RecipeName::new)
                .collect(Collectors.toList());
    }

}
