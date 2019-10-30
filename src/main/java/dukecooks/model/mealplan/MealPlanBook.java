package dukecooks.model.mealplan;

import static java.util.Objects.requireNonNull;

import java.util.List;

import dukecooks.model.mealplan.components.MealPlan;
import dukecooks.model.mealplan.components.UniqueMealPlanList;
import javafx.collections.ObservableList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameMealPlan comparison)
 */
public class MealPlanBook implements ReadOnlyMealPlanBook {

    private final UniqueMealPlanList mealPlans;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        mealPlans = new UniqueMealPlanList();
    }

    public MealPlanBook() {}

    /**
     * Creates a MealPlanBook using the Meal Plans in the {@code toBeCopied}
     */
    public MealPlanBook(ReadOnlyMealPlanBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the meal plan list with {@code mealPlans}.
     * {@code mealPlans} must not contain duplicate mealPlans.
     */
    public void setMealPlans(List<MealPlan> mealPlans) {
        this.mealPlans.setMealPlans(mealPlans);
    }

    /**
     * Resets the existing data of this {@code MealPlanBook} with {@code newData}.
     */
    public void resetData(ReadOnlyMealPlanBook newData) {
        requireNonNull(newData);

        setMealPlans(newData.getMealPlanList());
    }

    //// meal plan-level operations

    /**
     * Returns true if a mealPlan with the same identity as {@code mealPlan} exists in Duke Cooks.
     */
    public boolean hasMealPlan(MealPlan mealPlan) {
        requireNonNull(mealPlan);
        return mealPlans.contains(mealPlan);
    }

    /**
     * Adds a meal plan to Duke Cooks.
     * The meal plan must not already exist in Duke Cooks.
     */
    public void addMealPlan(MealPlan p) {
        mealPlans.add(p);
    }

    /**
     * Replaces the given meal plan {@code target} in the list with {@code editedMealPlan}.
     * {@code target} must exist in Duke Cooks.
     * The meal plan identity of {@code editedMealPlan} must not be the same
     * as another existing meal plan in Duke Cooks.
     */
    public void setMealPlan(MealPlan target, MealPlan editedMealPlan) {
        requireNonNull(editedMealPlan);

        mealPlans.setMealPlan(target, editedMealPlan);
    }

    /**
     * Removes {@code key} from this {@code MealPlanBook}.
     * {@code key} must exist in Duke Cooks.
     */
    public void removeMealPlan(MealPlan key) {
        mealPlans.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return mealPlans.asUnmodifiableObservableList().size() + " meal plans";
        // TODO: refine later
    }

    @Override
    public ObservableList<MealPlan> getMealPlanList() {
        return mealPlans.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MealPlanBook // instanceof handles nulls
                && mealPlans.equals(((MealPlanBook) other).mealPlans));
    }

    @Override
    public int hashCode() {
        return mealPlans.hashCode();
    }
}
