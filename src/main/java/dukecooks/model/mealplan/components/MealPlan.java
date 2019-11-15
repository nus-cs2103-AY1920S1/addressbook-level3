package dukecooks.model.mealplan.components;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import dukecooks.commons.util.CollectionUtil;
import dukecooks.model.recipe.components.Recipe;
import dukecooks.model.recipe.components.RecipeName;

/**
 * Represents a MealPlan in Duke Cooks.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class MealPlan {

    // Identity fields
    private final MealPlanName name;

    // Data field
    private final List<RecipeName> day1;
    private final List<RecipeName> day2;
    private final List<RecipeName> day3;
    private final List<RecipeName> day4;
    private final List<RecipeName> day5;
    private final List<RecipeName> day6;
    private final List<RecipeName> day7;

    /**
     * Every field must be present and not null.
     */
    public MealPlan(MealPlanName name) {
        CollectionUtil.requireAllNonNull(name);
        this.name = name;
        day1 = new ArrayList<>();
        day2 = new ArrayList<>();
        day3 = new ArrayList<>();
        day4 = new ArrayList<>();
        day5 = new ArrayList<>();
        day6 = new ArrayList<>();
        day7 = new ArrayList<>();

    }

    /**
     * Every field must be present and not null.
     */
    public MealPlan(MealPlanName name, List<RecipeName> day1, List<RecipeName> day2, List<RecipeName> day3,
                    List<RecipeName> day4, List<RecipeName> day5, List<RecipeName> day6, List<RecipeName> day7) {
        CollectionUtil.requireAllNonNull(name);
        this.name = name;
        this.day1 = day1;
        this.day2 = day2;
        this.day3 = day3;
        this.day4 = day4;
        this.day5 = day5;
        this.day6 = day6;
        this.day7 = day7;
    }

    public MealPlanName getName() {
        return name;
    }

    public String getRecipes() {
        Set<RecipeName> allRecipes = new HashSet<>();
        allRecipes.addAll(day1);
        allRecipes.addAll(day2);
        allRecipes.addAll(day3);
        allRecipes.addAll(day4);
        allRecipes.addAll(day5);
        allRecipes.addAll(day6);
        allRecipes.addAll(day7);

        return allRecipes.toString().replace("[", "").replace("]", "");
    }

    /**
     * Searches through all {@code Recipe} in {@code MealPlan} for instances of {@code oldRecipe},
     * and replaces it with {@code newRecipe}.
     */
    public void replaceRecipe(Recipe oldRecipe, Recipe newRecipe) {
        requireNonNull(oldRecipe);
        requireNonNull(newRecipe);

        for (RecipeName recipeName : day1) {
            if (recipeName.equals(oldRecipe.getName())) {
                day1.remove(recipeName);
                day1.add(newRecipe.getName());
            }
        }
        for (RecipeName recipeName : day2) {
            if (recipeName.equals(oldRecipe.getName())) {
                day2.remove(recipeName);
                day2.add(newRecipe.getName());
            }
        }
        for (RecipeName recipeName : day3) {
            if (recipeName.equals(oldRecipe.getName())) {
                day3.remove(recipeName);
                day3.add(newRecipe.getName());
            }
        }
        for (RecipeName recipeName : day4) {
            if (recipeName.equals(oldRecipe.getName())) {
                day4.remove(recipeName);
                day4.add(newRecipe.getName());
            }
        }
        for (RecipeName recipeName : day5) {
            if (recipeName.equals(oldRecipe.getName())) {
                day5.remove(recipeName);
                day5.add(newRecipe.getName());
            }
        }
        for (RecipeName recipeName : day6) {
            if (recipeName.equals(oldRecipe.getName())) {
                day6.remove(recipeName);
                day6.add(newRecipe.getName());
            }
        }
        for (RecipeName recipeName : day7) {
            if (recipeName.equals(oldRecipe.getName())) {
                day7.remove(recipeName);
                day7.add(newRecipe.getName());
            }
        }
    }

    /**
     * Searches through all {@code Recipe} in {@code MealPlan} for instances of {@code recipeToDelete},
     * removes it, and returns an updated copy of MealPlan
     */
    public MealPlan[] removeRecipe(Recipe recipeToDelete) {
        List<RecipeName> day1 = new ArrayList<>(getDay1());
        List<RecipeName> day2 = new ArrayList<>(getDay2());
        List<RecipeName> day3 = new ArrayList<>(getDay3());
        List<RecipeName> day4 = new ArrayList<>(getDay4());
        List<RecipeName> day5 = new ArrayList<>(getDay5());
        List<RecipeName> day6 = new ArrayList<>(getDay6());
        List<RecipeName> day7 = new ArrayList<>(getDay7());

        for (int i = 0; i < day1.size(); i++) {
            RecipeName recipeName = day1.get(i);
            if (recipeName.equals(recipeToDelete.getName())) {
                day1.remove(i);
                i--;
            }
        }
        for (int i = 0; i < day2.size(); i++) {
            RecipeName recipeName = day2.get(i);
            if (recipeName.equals(recipeToDelete.getName())) {
                day2.remove(i);
                i--;
            }
        }
        for (int i = 0; i < day3.size(); i++) {
            RecipeName recipeName = day3.get(i);
            if (recipeName.equals(recipeToDelete.getName())) {
                day3.remove(i);
                i--;
            }
        }
        for (int i = 0; i < day4.size(); i++) {
            RecipeName recipeName = day4.get(i);
            if (recipeName.equals(recipeToDelete.getName())) {
                day4.remove(i);
                i--;
            }
        }
        for (int i = 0; i < day5.size(); i++) {
            RecipeName recipeName = day5.get(i);
            if (recipeName.equals(recipeToDelete.getName())) {
                day5.remove(i);
                i--;
            }
        }
        for (int i = 0; i < day6.size(); i++) {
            RecipeName recipeName = day6.get(i);
            if (recipeName.equals(recipeToDelete.getName())) {
                day6.remove(i);
                i--;
            }
        }
        for (int i = 0; i < day7.size(); i++) {
            RecipeName recipeName = day7.get(i);
            if (recipeName.equals(recipeToDelete.getName())) {
                day7.remove(i);
                i--;
            }
        }

        return new MealPlan[]{this, new MealPlan(name, day1, day2, day3, day4, day5, day6, day7)};
    }

    /**
     * Returns an immutable day 1 recipe names list, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public List<RecipeName> getDay1() {
        return Collections.unmodifiableList(day1);
    }

    /**
     * Returns an immutable day 2 recipe names list, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public List<RecipeName> getDay2() {
        return Collections.unmodifiableList(day2);
    }

    /**
     * Returns an immutable day 3 recipe names list, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public List<RecipeName> getDay3() {
        return Collections.unmodifiableList(day3);
    }

    /**
     * Returns an immutable day 4 recipe names list, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public List<RecipeName> getDay4() {
        return Collections.unmodifiableList(day4);
    }

    /**
     * Returns an immutable day 5 recipe names list, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public List<RecipeName> getDay5() {
        return Collections.unmodifiableList(day5);
    }

    /**
     * Returns an immutable day 6 recipe names list, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public List<RecipeName> getDay6() {
        return Collections.unmodifiableList(day6);
    }

    /**
     * Returns an immutable day 7 recipe names list, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public List<RecipeName> getDay7() {
        return Collections.unmodifiableList(day7);
    }

    /**
     * Returns true if both meal plans of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two meal plans.
     */
    public boolean isSameMealPlan(MealPlan otherMealPlan) {
        if (otherMealPlan == this) {
            return true;
        }

        return otherMealPlan != null
                && otherMealPlan.getName().equals(getName());
    }

    /**
     * Returns true if both meal plans have the same identity and data fields.
     * This defines a stronger notion of equality between two meal plans.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof MealPlan)) {
            return false;
        }

        MealPlan otherMealPlan = (MealPlan) other;
        return otherMealPlan.getName().equals(getName())
                && otherMealPlan.getDay1().equals(getDay1())
                && otherMealPlan.getDay2().equals(getDay2())
                && otherMealPlan.getDay3().equals(getDay3())
                && otherMealPlan.getDay4().equals(getDay4())
                && otherMealPlan.getDay5().equals(getDay5())
                && otherMealPlan.getDay6().equals(getDay6())
                && otherMealPlan.getDay7().equals(getDay7());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, day1, day2, day3, day4, day5, day6, day7);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName());

        builder.append(" Day 1: ");
        getDay1().forEach(builder::append);
        builder.append(" Day 2: ");
        getDay2().forEach(builder::append);
        builder.append(" Day 3: ");
        getDay3().forEach(builder::append);
        builder.append(" Day 4: ");
        getDay4().forEach(builder::append);
        builder.append(" Day 5: ");
        getDay5().forEach(builder::append);
        builder.append(" Day 6: ");
        getDay6().forEach(builder::append);
        builder.append(" Day 7: ");
        getDay7().forEach(builder::append);

        return builder.toString();
    }
}
