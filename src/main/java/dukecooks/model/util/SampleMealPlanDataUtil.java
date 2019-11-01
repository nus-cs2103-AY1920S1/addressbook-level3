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
        List<RecipeName> Tea = Arrays.asList(new RecipeName[]{new RecipeName("Tea")});
        List<RecipeName> Milo = Arrays.asList(new RecipeName[]{new RecipeName("Milo")});
        List<RecipeName> Omelette = Arrays.asList(new RecipeName[]{new RecipeName("Cheese Omelette")});
        List<RecipeName> Tuna = Arrays.asList(new RecipeName[]{new RecipeName("Tuna Sandwich")});
        List<RecipeName> Maggi = Arrays.asList(new RecipeName[]{new RecipeName("Chicken Maggi")});
        List<RecipeName> Fried = Arrays.asList(new RecipeName[]{new RecipeName("Fried Chicken")});
        return new MealPlan[] {
            new MealPlan(new MealPlanName("Tea Plan"), Tea, Tea, Tea, Tea, Tea, Tea, Tea),
            new MealPlan(new MealPlanName("Milo Plan"), Milo, Milo, Milo, Milo, Milo, Milo, Milo),
            new MealPlan(new MealPlanName("Cheese Omelette Plan"), Omelette, Omelette, Omelette, Omelette, Omelette,
                    Omelette, Omelette),
            new MealPlan(new MealPlanName("Tuna Sandwich Plan"), Tuna, Tuna, Tuna, Tuna, Tuna, Tuna, Tuna),
            new MealPlan(new MealPlanName("Chicken Maggi Plan"), Maggi, Maggi, Maggi, Maggi, Maggi, Maggi, Maggi),
            new MealPlan(new MealPlanName("Fried Chicken Plan"), Fried, Fried, Fried, Fried, Fried, Fried, Fried),
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
