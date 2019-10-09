package calofit.model.dish;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;

/**
 * Wraps all data at the dish database level
 * Duplicates are not allowed (by .isSameDish comparison)
 */
public class DishDatabase implements ReadOnlyDishDatabase {

    private final UniqueDishList dishes;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        dishes = new UniqueDishList();
    }

    public DishDatabase() {}

    /**
     * Creates an DishDatabase using the Persons in the {@code toBeCopied}
     */
    public DishDatabase(ReadOnlyDishDatabase toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the dish list with {@code dishes}.
     * {@code dishes} must not contain duplicate dishes.
     */
    public void setDishes(List<Dish> dishes) {
        this.dishes.setDishes(dishes);
    }

    /**
     * Resets the existing data of this {@code DishDatabase} with {@code newData}.
     */
    public void resetData(ReadOnlyDishDatabase newData) {
        requireNonNull(newData);

        setDishes(newData.getDishList());
    }

    //// dish-level operations

    /**
     * Returns true if a dish with the same identity as {@code dish} exists in the dish database.
     */
    public boolean hasDish(Dish dish) {
        requireNonNull(dish);
        return dishes.contains(dish);
    }

    /**
     * Adds a dish to the dish database.
     * The dish must not already exist in the dish database.
     */
    public void addDish(Dish p) {
        dishes.add(p);
    }

    /**
     * Replaces the given dish {@code target} in the list with {@code editedDish}.
     * {@code target} must exist in the dish database.
     * The dish identity of {@code editedDish} must not be the same as another existing dish in the dish database.
     */
    public void setDish(Dish target, Dish editedDish) {
        requireNonNull(editedDish);

        dishes.setDish(target, editedDish);
    }

    /**
     * Removes {@code key} from this {@code DishDatabase}.
     * {@code key} must exist in the dish database.
     */
    public void removeDish(Dish key) {
        dishes.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return dishes.asUnmodifiableObservableList().size() + " dishes";
        // TODO: refine later
    }

    @Override
    public ObservableList<Dish> getDishList() {
        return dishes.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DishDatabase // instanceof handles nulls
                && dishes.equals(((DishDatabase) other).dishes));
    }

    @Override
    public int hashCode() {
        return dishes.hashCode();
    }
}
