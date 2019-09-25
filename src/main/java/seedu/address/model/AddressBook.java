package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.food.Food;
import seedu.address.model.food.UniqueFoodList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameFood comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueFoodList foods;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        foods = new UniqueFoodList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the foods in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the food list with {@code foods}.
     * {@code foods} must not contain duplicate foods.
     */
    public void setFoods(List<Food> foods) {
        this.foods.setFoods(foods);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setFoods(newData.getFoodList());
    }

    //// food-level operations

    /**
     * Returns true if a food with the same identity as {@code food} exists in the address book.
     */
    public boolean hasFood(Food food) {
        requireNonNull(food);
        return foods.contains(food);
    }

    /**
     * Adds a food to the address book.
     * The food must not already exist in the address book.
     */
    public void addFood(Food p) {
        foods.add(p);
    }

    /**
     * Replaces the given food {@code target} in the list with {@code editedFood}.
     * {@code target} must exist in the address book.
     * The food identity of {@code editedFood} must not be the same as another existing food in the address book.
     */
    public void setFood(Food target, Food editedFood) {
        requireNonNull(editedFood);

        foods.setFood(target, editedFood);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeFood(Food key) {
        foods.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return foods.asUnmodifiableObservableList().size() + " foods";
        // TODO: refine later
    }

    @Override
    public ObservableList<Food> getFoodList() {
        return foods.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && foods.equals(((AddressBook) other).foods));
    }

    @Override
    public int hashCode() {
        return foods.hashCode();
    }
}
