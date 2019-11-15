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
        List<RecipeName> salad = Arrays.asList(new RecipeName[]{new RecipeName("Chicken Salad")});
        List<RecipeName> lasagna = Arrays.asList(new RecipeName[]{new RecipeName("Beef Lasagna")});
        List<RecipeName> carbonara = Arrays.asList(new RecipeName[]{new RecipeName("Carbonara")});
        List<RecipeName> friedRice = Arrays.asList(new RecipeName[]{new RecipeName("Egg Fried Rice")});
        List<RecipeName> porridge = Arrays.asList(new RecipeName[]{new RecipeName("Century Egg Porridge")});
        List<RecipeName> bam = Arrays.asList(new RecipeName[]{new RecipeName("Bangers and Mash")});
        List<RecipeName> aglio = Arrays.asList(new RecipeName[]{new RecipeName("Prawn Aglio Olio")});
        List<RecipeName> ramen = Arrays.asList(new RecipeName[]{new RecipeName("Tonkotsu Ramen")});
        List<RecipeName> basil = Arrays.asList(new RecipeName[]{new RecipeName("Basil Pork Rice")});

        return new MealPlan[] {
            new MealPlan(new MealPlanName("Carbohydrates Bulking"), basil, friedRice, porridge, friedRice, basil,
                    porridge,
                    friedRice),
            new MealPlan(new MealPlanName("It is a Meat Week"), bam, fried, lasagna, basil, bam, fried, basil),
            new MealPlan(new MealPlanName("Trying to be Healthy"), omelette, salad, porridge, aglio, carbonara,
                    tuna, milo),
            new MealPlan(new MealPlanName("I Love Pasta so much"), carbonara, aglio, lasagna, carbonara, lasagna,
                    aglio,
                    carbonara),
            new MealPlan(new MealPlanName("I am Broke waiting for pay"), maggi, tuna, tea, milo, omelette, maggi,
                    maggi),
            new MealPlan(new MealPlanName("Paycheck has come time to eat"), ramen, bam, aglio, lasagna, fried, salad,
                    bam),
            new MealPlan(new MealPlanName("Keto is an excuse for KFC"), fried, fried, fried, fried, fried, fried,
                    fried),
            new MealPlan(new MealPlanName("All of my Favourite Meals"), ramen, basil, lasagna, carbonara, porridge,
                    salad, fried)
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
