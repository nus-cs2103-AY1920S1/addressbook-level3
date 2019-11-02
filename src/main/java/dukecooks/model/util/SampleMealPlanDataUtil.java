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
        List<RecipeName> tea = Arrays.asList(new RecipeName[]{new RecipeName("Tea")});
        List<RecipeName> milo = Arrays.asList(new RecipeName[]{new RecipeName("Milo")});
        List<RecipeName> omelette = Arrays.asList(new RecipeName[]{new RecipeName("Cheese Omelette")});
        List<RecipeName> tuna = Arrays.asList(new RecipeName[]{new RecipeName("Tuna Sandwich")});
        List<RecipeName> maggi = Arrays.asList(new RecipeName[]{new RecipeName("Chicken Maggi")});
        List<RecipeName> fried = Arrays.asList(new RecipeName[]{new RecipeName("Fried Chicken")});
        return new MealPlan[] {
            new MealPlan(new MealPlanName("Tea Plan"), tea, tea, tea, tea, tea, tea, tea),
            new MealPlan(new MealPlanName("Milo Plan"), milo, milo, milo, milo, milo, milo, milo),
            new MealPlan(new MealPlanName("Cheese Omelette Plan"), omelette, omelette, omelette, omelette, omelette,
                    omelette, omelette),
            new MealPlan(new MealPlanName("Tuna Sandwich Plan"), tuna, tuna, tuna, tuna, tuna, tuna, tuna),
            new MealPlan(new MealPlanName("Chicken Maggi Plan"), maggi, maggi, maggi, maggi, maggi, maggi, maggi),
            new MealPlan(new MealPlanName("Fried Chicken Plan"), fried, fried, fried, fried, fried, fried, fried),
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
