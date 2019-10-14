package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_RECIPES;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.recipe.Ingredient;
import seedu.address.model.recipe.*;

/**
 * Edits the details of an existing recipe in Duke Cooks.
 */
public class EditRecipeCommand extends EditCommand {

    public static final String VARIANT_WORD = "recipe";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the recipe identified "
            + "by the index number used in the displayed recipe list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_INGREDIENT + "INGREDIENT]... "
            + "[" + PREFIX_CALORIES + "CALORIES] "
            + "[" + PREFIX_CARBS + "CARBS] "
            + "[" + PREFIX_FATS + "FATS] "
            + "[" + PREFIX_PROTEIN + "PROTEIN] \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PROTEIN + "123";

    public static final String MESSAGE_EDIT_RECIPE_SUCCESS = "Edited Recipe: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_RECIPE = "This recipe already exists in the Duke Cooks.";

    private final Index index;
    private final EditRecipeDescriptor editRecipeDescriptor;

    /**
     * @param index of the recipe in the filtered recipe list to edit
     * @param editRecipeDescriptor details to edit the recipe with
     */
    public EditRecipeCommand(Index index, EditRecipeDescriptor editRecipeDescriptor) {
        requireNonNull(index);
        requireNonNull(editRecipeDescriptor);

        this.index = index;
        this.editRecipeDescriptor = new EditRecipeDescriptor(editRecipeDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Recipe> lastShownList = model.getFilteredRecipeList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);
        }

        Recipe recipeToEdit = lastShownList.get(index.getZeroBased());
        Recipe editedRecipe = createEditedRecipe(recipeToEdit, editRecipeDescriptor);

        if (!recipeToEdit.isSameRecipe(editedRecipe) && model.hasRecipe(editedRecipe)) {
            throw new CommandException(MESSAGE_DUPLICATE_RECIPE);
        }

        model.setRecipe(recipeToEdit, editedRecipe);
        model.updateFilteredRecipeList(PREDICATE_SHOW_ALL_RECIPES);
        return new CommandResult(String.format(MESSAGE_EDIT_RECIPE_SUCCESS, editedRecipe));
    }

    /**
     * Creates and returns a {@code Recipe} with the details of {@code recipeToEdit}
     * edited with {@code editRecipeDescriptor}.
     */
    private static Recipe createEditedRecipe(Recipe recipeToEdit, EditRecipeDescriptor editRecipeDescriptor) {
        assert recipeToEdit != null;

        Name updatedName = editRecipeDescriptor.getName().orElse(recipeToEdit.getName());
        Calories updatedCalories = editRecipeDescriptor.getCalories().orElse(recipeToEdit.getCalories());
        Carbs updatedCarbs = editRecipeDescriptor.getCarbs().orElse(recipeToEdit.getCarbs());
        Fats updatedFats = editRecipeDescriptor.getFats().orElse(recipeToEdit.getFats());
        Protein updatedProtein = editRecipeDescriptor.getProtein().orElse(recipeToEdit.getProtein());
        Set<Ingredient> updatedIngredients = editRecipeDescriptor.getIngredients().orElse(recipeToEdit.getIngredients());

        return new Recipe(updatedName, updatedIngredients, updatedCalories, updatedCarbs, updatedFats, updatedProtein);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditRecipeCommand)) {
            return false;
        }

        // state check
        EditRecipeCommand e = (EditRecipeCommand) other;
        return index.equals(e.index)
                && editRecipeDescriptor.equals(e.editRecipeDescriptor);
    }

    /**
     * Stores the details to edit the recipe with. Each non-empty field value will replace the
     * corresponding field value of the recipe.
     */
    public static class EditRecipeDescriptor {
        private Name name;
        private Calories calories;
        private Carbs carbs;
        private Fats fats;
        private Protein protein;
        private Set<Ingredient> ingredients;

        public EditRecipeDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code ingredients} is used internally.
         */
        public EditRecipeDescriptor(EditRecipeDescriptor toCopy) {
            setName(toCopy.name);
            setCalories(toCopy.calories);
            setCarbs(toCopy.carbs);
            setFats(toCopy.fats);
            setProtein(toCopy.protein);
            setIngredients(toCopy.ingredients);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, ingredients);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public void setCalories(Calories calories) {
            this.calories = calories;
        }

        public void setCarbs(Carbs carbs) {
            this.carbs = carbs;
        }

        public void setFats(Fats fats) {
            this.fats = fats;
        }

        public void setProtein(Protein protein) {
            this.protein = protein;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public Optional<Calories> getCalories() {
            return Optional.ofNullable(calories);
        }

        public Optional<Carbs> getCarbs() {
            return Optional.ofNullable(carbs);
        }

        public Optional<Fats> getFats() {
            return Optional.ofNullable(fats);
        }

        public Optional<Protein> getProtein() {
            return Optional.ofNullable(protein);
        }


        /**
         * Sets {@code ingredients} to this object's {@code ingredients}.
         * A defensive copy of {@code ingredients} is used internally.
         */
        public void setIngredients(Set<Ingredient> ingredients) {
            this.ingredients = (ingredients != null) ? new HashSet<>(ingredients) : null;
        }

        /**
         * Returns an unmodifiable ingredient set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code ingredients} is null.
         */
        public Optional<Set<Ingredient>> getIngredients() {
            return (ingredients != null) ? Optional.of(Collections.unmodifiableSet(ingredients)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditRecipeDescriptor)) {
                return false;
            }

            // state check
            EditRecipeDescriptor e = (EditRecipeDescriptor) other;

            return getName().equals(e.getName())
                    && getIngredients().equals(e.getIngredients());
        }
    }
}
