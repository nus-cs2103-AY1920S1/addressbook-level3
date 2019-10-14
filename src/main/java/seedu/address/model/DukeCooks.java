package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.recipe.Recipe;
import seedu.address.model.recipe.UniqueRecipeList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class DukeCooks implements ReadOnlyDukeCooks {

    private final UniqueRecipeList persons;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniqueRecipeList();
    }

    public DukeCooks() {}

    /**
     * Creates a DukeCooks using the Persons in the {@code toBeCopied}
     */
    public DukeCooks(ReadOnlyDukeCooks toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the recipe list with {@code recipes}.
     * {@code recipes} must not contain duplicate recipes.
     */
    public void setPersons(List<Recipe> recipes) {
        this.persons.setRecipes(recipes);
    }

    /**
     * Resets the existing data of this {@code DukeCooks} with {@code newData}.
     */
    public void resetData(ReadOnlyDukeCooks newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
    }

    //// recipe-level operations

    /**
     * Returns true if a recipe with the same identity as {@code recipe} exists in Duke Cooks.
     */
    public boolean hasPerson(Recipe recipe) {
        requireNonNull(recipe);
        return persons.contains(recipe);
    }

    /**
     * Adds a recipe to Duke Cooks.
     * The recipe must not already exist in Duke Cooks.
     */
    public void addPerson(Recipe p) {
        persons.add(p);
    }

    /**
     * Replaces the given recipe {@code target} in the list with {@code editedRecipe}.
     * {@code target} must exist in Duke Cooks.
     * The recipe identity of {@code editedRecipe} must not be the same as another existing recipe in Duke Cooks.
     */
    public void setPerson(Recipe target, Recipe editedRecipe) {
        requireNonNull(editedRecipe);

        persons.setRecipe(target, editedRecipe);
    }

    /**
     * Removes {@code key} from this {@code DukeCooks}.
     * {@code key} must exist in Duke Cooks.
     */
    public void removePerson(Recipe key) {
        persons.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asUnmodifiableObservableList().size() + " persons";
        // TODO: refine later
    }

    @Override
    public ObservableList<Recipe> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DukeCooks // instanceof handles nulls
                && persons.equals(((DukeCooks) other).persons));
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }
}
