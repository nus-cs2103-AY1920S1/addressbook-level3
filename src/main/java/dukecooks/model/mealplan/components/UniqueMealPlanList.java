package dukecooks.model.mealplan.components;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.List;

import dukecooks.commons.util.CollectionUtil;
import dukecooks.model.mealplan.exceptions.DuplicateMealPlanException;
import dukecooks.model.mealplan.exceptions.MealPlanNotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A list of meal plans that enforces uniqueness between its elements and does not allow nulls.
 * A meal plan is considered unique by comparing using {@code MealPlan#isSamemeal plan(MealPlan)}. As such, adding and
 * updating of meal plans uses MealPlan#isSameMealPlan(MealPlan) for equality so as to ensure that the meal plan being
 * added or updated is unique in terms of identity in the UniqueMealPlanList. However, the removal of a meal plan uses
 * MealPlan#equals(Object) so as to ensure that the meal plan with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see MealPlan#isSameMealPlan(MealPlan)
 */
public class UniqueMealPlanList implements Iterable<MealPlan> {

    private final ObservableList<MealPlan> internalList = FXCollections.observableArrayList();
    private final ObservableList<MealPlan> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent meal plan as the given argument.
     */
    public boolean contains(MealPlan toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameMealPlan);
    }

    /**
     * Adds a meal plan to the list.
     * The meal plan must not already exist in the list.
     */
    public void add(MealPlan toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateMealPlanException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the meal plan {@code target} in the list with {@code editedMealPlan}.
     * {@code target} must exist in the list.
     * The meal plan identity of {@code editedMealPlan} must not be the same as another existing meal plan in the list.
     */
    public void setMealPlan(MealPlan target, MealPlan editedMealPlan) {
        CollectionUtil.requireAllNonNull(target, editedMealPlan);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new MealPlanNotFoundException();
        }

        if (!target.isSameMealPlan(editedMealPlan) && contains(editedMealPlan)) {
            throw new DuplicateMealPlanException();
        }

        internalList.set(index, editedMealPlan);
    }

    /**
     * Removes the equivalent meal plan from the list.
     * The meal plan must exist in the list.
     */
    public void remove(MealPlan toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new MealPlanNotFoundException();
        }
    }

    public void setMealPlans(UniqueMealPlanList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code mealPlans}.
     * {@code mealPlans} must not contain duplicate mealPlans.
     */
    public void setMealPlans(List<MealPlan> mealPlans) {
        CollectionUtil.requireAllNonNull(mealPlans);
        if (!mealPlansAreUnique(mealPlans)) {
            throw new DuplicateMealPlanException();
        }

        internalList.setAll(mealPlans);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<MealPlan> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<MealPlan> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueMealPlanList // instanceof handles nulls
                        && internalList.equals(((UniqueMealPlanList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code mealPlans} contains only unique mealPlans.
     */
    private boolean mealPlansAreUnique(List<MealPlan> mealPlans) {
        for (int i = 0; i < mealPlans.size() - 1; i++) {
            for (int j = i + 1; j < mealPlans.size(); j++) {
                if (mealPlans.get(i).isSameMealPlan(mealPlans.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
