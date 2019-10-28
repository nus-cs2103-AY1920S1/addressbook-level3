package dukecooks.model.mealplan.components;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import dukecooks.commons.util.CollectionUtil;
import dukecooks.model.recipe.components.Recipe;

/**
 * Represents a MealPlan in Duke Cooks.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class MealPlan {

    // Identity fields
    private final MealPlanName name;

    // Data field
    private final List<Recipe> day1;
    private final List<Recipe> day2;
    private final List<Recipe> day3;
    private final List<Recipe> day4;
    private final List<Recipe> day5;
    private final List<Recipe> day6;
    private final List<Recipe> day7;

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
    public MealPlan(MealPlanName name, List<Recipe> day1, List<Recipe> day2, List<Recipe> day3, List<Recipe> day4,
                    List<Recipe> day5, List<Recipe> day6, List<Recipe> day7) {
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

    /**
     * Returns an immutable day 1 recipes list, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public List<Recipe> getDay1() {
        return Collections.unmodifiableList(day1);
    }

    /**
     * Returns an immutable day 2 recipes list, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public List<Recipe> getDay2() {
        return Collections.unmodifiableList(day2);
    }

    /**
     * Returns an immutable day 3 recipes list, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public List<Recipe> getDay3() {
        return Collections.unmodifiableList(day3);
    }

    /**
     * Returns an immutable day 4 recipes list, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public List<Recipe> getDay4() {
        return Collections.unmodifiableList(day4);
    }

    /**
     * Returns an immutable day 5 recipes list, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public List<Recipe> getDay5() {
        return Collections.unmodifiableList(day5);
    }

    /**
     * Returns an immutable day 6 recipes list, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public List<Recipe> getDay6() {
        return Collections.unmodifiableList(day6);
    }

    /**
     * Returns an immutable day 7 recipes list, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public List<Recipe> getDay7() {
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
