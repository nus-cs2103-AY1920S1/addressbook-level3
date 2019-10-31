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
        return new MealPlan[] {
            new MealPlan(new MealPlanName("Tea Plan"),
                    Arrays.asList(new RecipeName[]{new RecipeName("Tea")}),
                    Arrays.asList(new RecipeName[]{new RecipeName("Tea")}),
                    Arrays.asList(new RecipeName[]{new RecipeName("Tea")}),
                    Arrays.asList(new RecipeName[]{new RecipeName("Tea")}),
                    Arrays.asList(new RecipeName[]{new RecipeName("Tea")}),
                    Arrays.asList(new RecipeName[]{new RecipeName("Tea")}),
                    Arrays.asList(new RecipeName[]{new RecipeName("Tea")})),
            new MealPlan(new MealPlanName("Milo Plan"),
                    Arrays.asList(new RecipeName[]{new RecipeName("Milo")}),
                    Arrays.asList(new RecipeName[]{new RecipeName("Milo")}),
                    Arrays.asList(new RecipeName[]{new RecipeName("Milo")}),
                    Arrays.asList(new RecipeName[]{new RecipeName("Milo")}),
                    Arrays.asList(new RecipeName[]{new RecipeName("Milo")}),
                    Arrays.asList(new RecipeName[]{new RecipeName("Milo")}),
                    Arrays.asList(new RecipeName[]{new RecipeName("Milo")})),
            new MealPlan(new MealPlanName("Cheese Omelette Plan"),
                    Arrays.asList(new RecipeName[]{new RecipeName("Cheese Omelette")}),
                    Arrays.asList(new RecipeName[]{new RecipeName("Cheese Omelette")}),
                    Arrays.asList(new RecipeName[]{new RecipeName("Cheese Omelette")}),
                    Arrays.asList(new RecipeName[]{new RecipeName("Cheese Omelette")}),
                    Arrays.asList(new RecipeName[]{new RecipeName("Cheese Omelette")}),
                    Arrays.asList(new RecipeName[]{new RecipeName("Cheese Omelette")}),
                    Arrays.asList(new RecipeName[]{new RecipeName("Cheese Omelette")})),
            new MealPlan(new MealPlanName("Tuna Sandwich Plan"),
                    Arrays.asList(new RecipeName[]{new RecipeName("Tuna Sandwich")}),
                    Arrays.asList(new RecipeName[]{new RecipeName("Tuna Sandwich")}),
                    Arrays.asList(new RecipeName[]{new RecipeName("Tuna Sandwich")}),
                    Arrays.asList(new RecipeName[]{new RecipeName("Tuna Sandwich")}),
                    Arrays.asList(new RecipeName[]{new RecipeName("Tuna Sandwich")}),
                    Arrays.asList(new RecipeName[]{new RecipeName("Tuna Sandwich")}),
                    Arrays.asList(new RecipeName[]{new RecipeName("Tuna Sandwich")})),
            new MealPlan(new MealPlanName("Chicken Maggi Plan"),
                    Arrays.asList(new RecipeName[]{new RecipeName("Chicken Maggi")}),
                    Arrays.asList(new RecipeName[]{new RecipeName("Chicken Maggi")}),
                    Arrays.asList(new RecipeName[]{new RecipeName("Chicken Maggi")}),
                    Arrays.asList(new RecipeName[]{new RecipeName("Chicken Maggi")}),
                    Arrays.asList(new RecipeName[]{new RecipeName("Chicken Maggi")}),
                    Arrays.asList(new RecipeName[]{new RecipeName("Chicken Maggi")}),
                    Arrays.asList(new RecipeName[]{new RecipeName("Chicken Maggi")})),
            new MealPlan(new MealPlanName("Fried Chicken Plan"),
                    Arrays.asList(new RecipeName[]{new RecipeName("Fried Chicken")}),
                    Arrays.asList(new RecipeName[]{new RecipeName("Fried Chicken")}),
                    Arrays.asList(new RecipeName[]{new RecipeName("Fried Chicken")}),
                    Arrays.asList(new RecipeName[]{new RecipeName("Fried Chicken")}),
                    Arrays.asList(new RecipeName[]{new RecipeName("Fried Chicken")}),
                    Arrays.asList(new RecipeName[]{new RecipeName("Fried Chicken")}),
                    Arrays.asList(new RecipeName[]{new RecipeName("Fried Chicken")})),
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
