package dukecooks.testutil.mealplan;

import java.util.ArrayList;
import java.util.List;

import dukecooks.model.mealplan.components.MealPlan;
import dukecooks.model.mealplan.components.MealPlanName;
import dukecooks.model.recipe.components.RecipeName;
import dukecooks.model.util.SampleMealPlanDataUtil;

/**
 * A utility class to help with building MealPlan objects.
 */
public class MealPlanBuilder {

    public static final String DEFAULT_NAME = "Omelette Plan";
    public static final String DEFAULT_RECIPENAME = "Cheese Omelette";

    private MealPlanName name;
    private List<RecipeName> day1;
    private List<RecipeName> day2;
    private List<RecipeName> day3;
    private List<RecipeName> day4;
    private List<RecipeName> day5;
    private List<RecipeName> day6;
    private List<RecipeName> day7;

    public MealPlanBuilder() {
        name = new MealPlanName(DEFAULT_NAME);
        day1 = new ArrayList<>();
        day1.add(new RecipeName(DEFAULT_RECIPENAME));
        day2 = new ArrayList<>();
        day2.add(new RecipeName(DEFAULT_RECIPENAME));
        day3 = new ArrayList<>();
        day3.add(new RecipeName(DEFAULT_RECIPENAME));
        day4 = new ArrayList<>();
        day4.add(new RecipeName(DEFAULT_RECIPENAME));
        day5 = new ArrayList<>();
        day5.add(new RecipeName(DEFAULT_RECIPENAME));
        day6 = new ArrayList<>();
        day6.add(new RecipeName(DEFAULT_RECIPENAME));
        day7 = new ArrayList<>();
        day7.add(new RecipeName(DEFAULT_RECIPENAME));
    }

    /**
     * Initializes the MealPlanBuilder with the data of {@code mealPlanToCopy}.
     */
    public MealPlanBuilder(MealPlan mealPlanToCopy) {
        name = mealPlanToCopy.getName();
        day1 = new ArrayList<>(mealPlanToCopy.getDay1());
        day2 = new ArrayList<>(mealPlanToCopy.getDay2());
        day3 = new ArrayList<>(mealPlanToCopy.getDay3());
        day4 = new ArrayList<>(mealPlanToCopy.getDay4());
        day5 = new ArrayList<>(mealPlanToCopy.getDay5());
        day6 = new ArrayList<>(mealPlanToCopy.getDay6());
        day7 = new ArrayList<>(mealPlanToCopy.getDay7());
    }

    /**
     * Sets the {@code MealPlanName} of the {@code MealPlan} that we are building.
     */
    public MealPlanBuilder withName(String name) {
        this.name = new MealPlanName(name);
        return this;
    }

    /**
     * Parses the {@code recipes} into a {@code List<RecipeName>} and set it
     * to the {@code MealPlan} Day 1 recipes list that we are building.
     */
    public MealPlanBuilder withDay1(String ... recipes) {
        this.day1 = SampleMealPlanDataUtil.getDay1(recipes);
        return this;
    }

    /**
     * Parses the {@code recipes} into a {@code List<RecipeName>} and set it
     * to the {@code MealPlan} Day 2 recipes list that we are building.
     */
    public MealPlanBuilder withDay2(String ... recipes) {
        this.day2 = SampleMealPlanDataUtil.getDay2(recipes);
        return this;
    }

    /**
     * Parses the {@code recipes} into a {@code List<RecipeName>} and set it
     * to the {@code MealPlan} Day 3 recipes list that we are building.
     */
    public MealPlanBuilder withDay3(String ... recipes) {
        this.day3 = SampleMealPlanDataUtil.getDay3(recipes);
        return this;
    }

    /**
     * Parses the {@code recipes} into a {@code List<RecipeName>} and set it
     * to the {@code MealPlan} Day 4 recipes list that we are building.
     */
    public MealPlanBuilder withDay4(String ... recipes) {
        this.day4 = SampleMealPlanDataUtil.getDay4(recipes);
        return this;
    }

    /**
     * Parses the {@code recipes} into a {@code List<RecipeName>} and set it
     * to the {@code MealPlan} Day 5 recipes list that we are building.
     */
    public MealPlanBuilder withDay5(String ... recipes) {
        this.day5 = SampleMealPlanDataUtil.getDay5(recipes);
        return this;
    }

    /**
     * Parses the {@code recipes} into a {@code List<RecipeName>} and set it
     * to the {@code MealPlan} Day 6 recipes list that we are building.
     */
    public MealPlanBuilder withDay6(String ... recipes) {
        this.day6 = SampleMealPlanDataUtil.getDay6(recipes);
        return this;
    }

    /**
     * Parses the {@code recipes} into a {@code List<RecipeName>} and set it
     * to the {@code MealPlan} Day 7 recipes list that we are building.
     */
    public MealPlanBuilder withDay7(String ... recipes) {
        this.day7 = SampleMealPlanDataUtil.getDay7(recipes);
        return this;
    }

    public MealPlan build() {
        return new MealPlan(name, day1, day2, day3, day4, day5, day6, day7);
    }

}
