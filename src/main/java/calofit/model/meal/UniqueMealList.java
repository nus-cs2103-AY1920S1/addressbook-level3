package calofit.model.meal;

import calofit.commons.util.CollectionUtil;
import calofit.model.meal.exceptions.DuplicateMealException;
import calofit.model.meal.exceptions.MealNotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Iterator;
import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 * A meal is considered unique by comparing using {@code Meal#isSamePerson(Meal)}. As such, adding and updating of
 * persons uses Meal#isSamePerson(Meal) for equality so as to ensure that the meal being added or updated is
 * unique in terms of identity in the UniqueMealList. However, the removal of a meal uses Meal#equals(Object) so
 * as to ensure that the meal with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Meal#isSameMeal(Meal)
 */
public class UniqueMealList implements Iterable<Meal> {

    private final ObservableList<Meal> internalList = FXCollections.observableArrayList();
    private final ObservableList<Meal> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent meal as the given argument.
     */
    public boolean contains(Meal toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameMeal);
    }

    /**
     * Adds a meal to the list.
     * The meal must not already exist in the list.
     */
    public void add(Meal toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateMealException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the meal {@code target} in the list with {@code editedMeal}.
     * {@code target} must exist in the list.
     * The meal identity of {@code editedMeal} must not be the same as another existing meal in the list.
     */
    public void setMeal(Meal target, Meal editedMeal) {
        CollectionUtil.requireAllNonNull(target, editedMeal);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new MealNotFoundException();
        }

        if (!target.isSameMeal(editedMeal) && contains(editedMeal)) {
            throw new DuplicateMealException();
        }

        internalList.set(index, editedMeal);
    }

    /**
     * Removes the equivalent meal from the list.
     * The meal must exist in the list.
     */
    public void remove(Meal toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new MealNotFoundException();
        }
    }

    public void setMeals(UniqueMealList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code meals}.
     * {@code meals} must not contain duplicate meals.
     */
    public void setMeals(List<Meal> meals) {
        CollectionUtil.requireAllNonNull(meals);
        if (!mealsAreUnique(meals)) {
            throw new DuplicateMealException();
        }

        internalList.setAll(meals);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Meal> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Meal> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueMealList // instanceof handles nulls
                        && internalList.equals(((UniqueMealList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code meals} contains only unique meals.
     */
    private boolean mealsAreUnique(List<Meal> meals) {
        for (int i = 0; i < meals.size() - 1; i++) {
            for (int j = i + 1; j < meals.size(); j++) {
                if (meals.get(i).isSameMeal(meals.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
