package dukecooks.logic.commands.recipe;

import static dukecooks.logic.parser.CliSyntax.PREFIX_CALORIES;
import static dukecooks.logic.parser.CliSyntax.PREFIX_CARBS;
import static dukecooks.logic.parser.CliSyntax.PREFIX_FATS;
import static dukecooks.logic.parser.CliSyntax.PREFIX_INGREDIENT;
import static dukecooks.logic.parser.CliSyntax.PREFIX_NAME;
import static dukecooks.logic.parser.CliSyntax.PREFIX_PROTEIN;
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import dukecooks.commons.core.Event;
import dukecooks.commons.core.Messages;
import dukecooks.commons.core.index.Index;
import dukecooks.commons.util.CollectionUtil;
import dukecooks.logic.commands.CommandResult;
import dukecooks.logic.commands.EditCommand;
import dukecooks.logic.commands.exceptions.CommandException;
import dukecooks.model.Model;
import dukecooks.model.mealplan.components.MealPlan;
import dukecooks.model.mealplan.components.MealPlanRecipesContainsKeywordsPredicate;
import dukecooks.model.recipe.components.Calories;
import dukecooks.model.recipe.components.Carbs;
import dukecooks.model.recipe.components.Fats;
import dukecooks.model.recipe.components.Ingredient;
import dukecooks.model.recipe.components.Protein;
import dukecooks.model.recipe.components.Recipe;
import dukecooks.model.recipe.components.RecipeName;

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

    private static Event event;

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
        model.updateFilteredRecipeList(Model.PREDICATE_SHOW_ALL_RECIPES);

        List<String> recipeNameKeyword = new ArrayList<>();
        recipeNameKeyword.add(recipeToEdit.getName().fullName);
        Predicate<MealPlan> recipeNamePredicate = new MealPlanRecipesContainsKeywordsPredicate(recipeNameKeyword);
        //model.updateFilteredMealPlanList(recipeNamePredicate);
        //TODO: This implementation is broken, should find a way to fix it to allow faster dynamic updates of recipes
        for (MealPlan mealPlan : model.getFilteredMealPlanList()) {
            mealPlan.replaceRecipe(recipeToEdit, editedRecipe);
        }

        event = Event.getInstance();
        event.set("recipe", "all");

        return new CommandResult(String.format(MESSAGE_EDIT_RECIPE_SUCCESS, editedRecipe));
    }

    /**
     * Creates and returns a {@code Recipe} with the details of {@code recipeToEdit}
     * edited with {@code editRecipeDescriptor}.
     */
    private static Recipe createEditedRecipe(Recipe recipeToEdit, EditRecipeDescriptor editRecipeDescriptor) {
        assert recipeToEdit != null;

        RecipeName updatedName = editRecipeDescriptor.getName().orElse(recipeToEdit.getName());
        Calories updatedCalories = editRecipeDescriptor.getCalories().orElse(recipeToEdit.getCalories());
        Carbs updatedCarbs = editRecipeDescriptor.getCarbs().orElse(recipeToEdit.getCarbs());
        Fats updatedFats = editRecipeDescriptor.getFats().orElse(recipeToEdit.getFats());
        Protein updatedProtein = editRecipeDescriptor.getProtein().orElse(recipeToEdit.getProtein());
        Set<Ingredient> updatedIngredients;
        if (editRecipeDescriptor.getIngredientsToAdd().isPresent()
                || editRecipeDescriptor.getIngredientsToRemove().isPresent()) {

            updatedIngredients = new HashSet<>(recipeToEdit.getIngredients());

            if (editRecipeDescriptor.getIngredientsToAdd().isPresent()) {
                updatedIngredients.addAll(editRecipeDescriptor.getIngredientsToAdd().get());
            }

            if (editRecipeDescriptor.getIngredientsToRemove().isPresent()) {
                updatedIngredients.removeAll(editRecipeDescriptor.getIngredientsToRemove().get());
            }

        } else {
            updatedIngredients = recipeToEdit.getIngredients();
        }



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
        private RecipeName name;
        private Calories calories;
        private Carbs carbs;
        private Fats fats;
        private Protein protein;
        private Set<Ingredient> ingredientsToAdd;
        private Set<Ingredient> ingredientsToRemove;

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
            setAddIngredients(toCopy.ingredientsToAdd);
            setRemoveIngredients(toCopy.ingredientsToRemove);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, ingredientsToAdd, ingredientsToRemove,
                    calories, carbs, fats, protein);
        }

        public void setName(RecipeName name) {
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

        public Optional<RecipeName> getName() {
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
         * Set {@code ingredients} to this object's {@code ingredientsToAdd}.
         * A defensive copy of {@code ingredientsToAdd} is used internally.
         */
        public void setAddIngredients(Set<Ingredient> ingredients) {
            this.ingredientsToAdd = (ingredients != null) ? new HashSet<>(ingredients) : null;
        }

        /**
         * Set {@code ingredients} to this object's {@code ingredientsToRemove}.
         * A defensive copy of {@code ingredientsToRemove} is used internally.
         */
        public void setRemoveIngredients(Set<Ingredient> ingredients) {
            this.ingredientsToRemove = (ingredients != null) ? new HashSet<>(ingredients) : null;
        }

        /**
         * Adds {@code ingredients} to this object's {@code ingredientsToAdd}.
         * A defensive copy of {@code ingredients} is used internally.
         */
        public void addIngredients(Set<Ingredient> ingredients) {
            this.ingredientsToAdd = (ingredients != null) ? new HashSet<>(ingredients) : null;
        }

        /**
         * Removes {@code ingredients} to this object's {@code ingredients}.
         * A defensive copy of {@code ingredients} is used internally.
         */
        public void removeIngredients(Set<Ingredient> ingredients) {
            this.ingredientsToRemove = (ingredients != null) ? new HashSet<>(ingredients) : null;
        }

        /**
         * Returns an unmodifiable ingredient set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code ingredients} is null.
         */
        public Optional<Set<Ingredient>> getIngredientsToAdd() {
            return (ingredientsToAdd != null)
                    ? Optional.of(Collections.unmodifiableSet(ingredientsToAdd))
                    : Optional.empty();
        }

        /**
         * Returns an unmodifiable ingredient set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code ingredients} is null.
         */
        public Optional<Set<Ingredient>> getIngredientsToRemove() {
            return (ingredientsToRemove != null)
                    ? Optional.of(Collections.unmodifiableSet(ingredientsToRemove))
                    : Optional.empty();
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
                    && getIngredientsToAdd().equals(e.getIngredientsToAdd());
        }
    }
}
