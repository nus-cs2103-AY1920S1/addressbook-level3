package dukecooks.logic.commands.mealplan;

import static dukecooks.testutil.TypicalIndexes.INDEX_FIRST_MEALPLAN;
import static dukecooks.testutil.mealplan.TypicalMealPlans.getTypicalMealPlanBook;
import static dukecooks.testutil.recipe.TypicalRecipes.MILO;
import static dukecooks.testutil.recipe.TypicalRecipes.OMELETTE;
import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import dukecooks.commons.core.Messages;
import dukecooks.commons.core.index.Index;
import dukecooks.logic.commands.CommandTestUtil;
import dukecooks.logic.commands.exceptions.CommandException;
import dukecooks.logic.commands.mealplan.EditMealPlanCommand.EditMealPlanDescriptor;
import dukecooks.model.Model;
import dukecooks.model.ModelManager;
import dukecooks.model.ModelStub;
import dukecooks.model.UserPrefs;
import dukecooks.model.mealplan.MealPlanBook;
import dukecooks.model.mealplan.ReadOnlyMealPlanBook;
import dukecooks.model.mealplan.components.MealPlan;
import dukecooks.model.recipe.ReadOnlyRecipeBook;
import dukecooks.model.recipe.RecipeBook;
import dukecooks.model.recipe.components.Recipe;
import dukecooks.testutil.Assert;
import dukecooks.testutil.TypicalIndexes;
import dukecooks.testutil.mealplan.EditMealPlanDescriptorBuilder;
import dukecooks.testutil.mealplan.MealPlanBuilder;
import dukecooks.testutil.recipe.RecipeBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand)
 * and unit tests for EditMealPlanCommand.
 */
public class EditMealPlanCommandTest {

    private Model model = new ModelManager(getTypicalMealPlanBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        MealPlan editedMealPlan = new MealPlanBuilder().build();
        model.addRecipe(MILO);
        model.addRecipe(new RecipeBuilder().build());

        String expectedMessage = String.format(EditMealPlanCommand.MESSAGE_EDIT_MEALPLAN_SUCCESS, editedMealPlan);

        Model expectedModel = new ModelManager(new MealPlanBook(model.getMealPlanBook()), new UserPrefs());
        expectedModel.addRecipe(MILO);
        expectedModel.addRecipe(new RecipeBuilder().build());
        expectedModel.setMealPlan(model.getFilteredMealPlanList().get(0), editedMealPlan);

        EditMealPlanCommand.EditMealPlanDescriptor descriptor =
                new EditMealPlanDescriptorBuilder(model.getFilteredMealPlanList().get(0), editedMealPlan).build();
        EditMealPlanCommand editMealPlanCommand = new EditMealPlanCommand(INDEX_FIRST_MEALPLAN,
                descriptor);

        CommandTestUtil.assertCommandSuccess(editMealPlanCommand, model, expectedMessage, expectedModel);
        model.deleteRecipe(MILO);
        model.deleteRecipe(new RecipeBuilder().build());
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditMealPlanCommand editMealPlanCommand = new EditMealPlanCommand(INDEX_FIRST_MEALPLAN,
                new EditMealPlanCommand.EditMealPlanDescriptor());
        MealPlan editedMealPlan = model.getFilteredMealPlanList().get(INDEX_FIRST_MEALPLAN
                .getZeroBased());

        String expectedMessage = String.format(EditMealPlanCommand.MESSAGE_EDIT_MEALPLAN_SUCCESS, editedMealPlan);

        Model expectedModel = new ModelManager(new MealPlanBook(model.getMealPlanBook()), new UserPrefs());

        CommandTestUtil.assertCommandSuccess(editMealPlanCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        CommandTestUtil.showMealPlanAtIndex(model, INDEX_FIRST_MEALPLAN);

        MealPlan mealPlanInFilteredList = model.getFilteredMealPlanList().get(INDEX_FIRST_MEALPLAN
                .getZeroBased());
        MealPlan editedMealPlan = new MealPlanBuilder(mealPlanInFilteredList).withName(CommandTestUtil
                .VALID_NAME_BURGER)
                .build();
        EditMealPlanCommand editMealPlanCommand = new EditMealPlanCommand(INDEX_FIRST_MEALPLAN,
                new EditMealPlanDescriptorBuilder().withMealPlanName(CommandTestUtil.VALID_NAME_BURGER).build());

        String expectedMessage = String.format(EditMealPlanCommand.MESSAGE_EDIT_MEALPLAN_SUCCESS, editedMealPlan);

        Model expectedModel = new ModelManager(new MealPlanBook(model.getMealPlanBook()), new UserPrefs());
        expectedModel.setMealPlan(model.getFilteredMealPlanList().get(0), editedMealPlan);

        CommandTestUtil.assertCommandSuccess(editMealPlanCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateMealPlanUnfilteredList_failure() {
        model.addRecipe(MILO);
        MealPlan firstMealPlan = model.getFilteredMealPlanList().get(INDEX_FIRST_MEALPLAN
                .getZeroBased());
        EditMealPlanCommand.EditMealPlanDescriptor descriptor = new EditMealPlanDescriptorBuilder(firstMealPlan)
                .build();
        EditMealPlanCommand editMealPlanCommand = new EditMealPlanCommand(TypicalIndexes.INDEX_SECOND_MEALPLAN,
                descriptor);

        CommandTestUtil.assertMealPlanCommandFailure(editMealPlanCommand, model,
                EditMealPlanCommand.MESSAGE_DUPLICATE_MEALPLAN);
        model.deleteRecipe(MILO);
    }

    @Test
    public void execute_duplicateMealPlanFilteredList_failure() {
        model.addRecipe(OMELETTE);
        CommandTestUtil.showMealPlanAtIndex(model, INDEX_FIRST_MEALPLAN);

        // edit mealPlan in filtered list into a duplicate in MealPlanBook
        MealPlan mealPlanInList = model.getMealPlanBook().getMealPlanList().get(TypicalIndexes.INDEX_SECOND_MEALPLAN
                .getZeroBased());
        EditMealPlanCommand editMealPlanCommand = new EditMealPlanCommand(INDEX_FIRST_MEALPLAN,
                new EditMealPlanDescriptorBuilder(mealPlanInList).build());

        CommandTestUtil.assertMealPlanCommandFailure(editMealPlanCommand, model,
                EditMealPlanCommand.MESSAGE_DUPLICATE_MEALPLAN);
        model.deleteRecipe(OMELETTE);
    }

    @Test
    public void execute_invalidMealPlanIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredMealPlanList().size() + 1);
        EditMealPlanCommand.EditMealPlanDescriptor descriptor = new EditMealPlanDescriptorBuilder()
                .withMealPlanName(CommandTestUtil.VALID_NAME_BURGER).build();
        EditMealPlanCommand editMealPlanCommand = new EditMealPlanCommand(outOfBoundIndex, descriptor);

        CommandTestUtil.assertMealPlanCommandFailure(editMealPlanCommand, model,
                Messages.MESSAGE_INVALID_MEALPLAN_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of MealPlanBook
     */
    @Test
    public void execute_invalidMealPlanIndexFilteredList_failure() {
        CommandTestUtil.showMealPlanAtIndex(model, INDEX_FIRST_MEALPLAN);
        Index outOfBoundIndex = TypicalIndexes.INDEX_SECOND_MEALPLAN;
        // ensures that outOfBoundIndex is still in bounds of MealPlanBook list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getMealPlanBook().getMealPlanList().size());

        EditMealPlanCommand editMealPlanCommand = new EditMealPlanCommand(outOfBoundIndex,
                new EditMealPlanDescriptorBuilder().withMealPlanName(CommandTestUtil.VALID_NAME_BURGER).build());

        CommandTestUtil.assertMealPlanCommandFailure(editMealPlanCommand, model,
                Messages.MESSAGE_INVALID_MEALPLAN_DISPLAYED_INDEX);
    }

    @Test
    public void execute_nonExistentRecipe_throwsCommandException() {
        EditMealPlanDescriptor invalidRecipeMealPlan = new EditMealPlanDescriptorBuilder(new MealPlanBuilder()
                .withDay1("Non Existent Recipe").build()).build();
        EditMealPlanCommand editMealPlanCommand = new EditMealPlanCommand(INDEX_FIRST_MEALPLAN, invalidRecipeMealPlan);
        ModelStub modelStub = new ModelStubAcceptingMealPlanAdded();
        modelStub.addRecipe(new RecipeBuilder().build());
        modelStub.addMealPlan(new MealPlanBuilder().build());

        Assert.assertThrows(CommandException.class,
                String.format(Messages.MESSAGE_RECIPE_DOES_NOT_EXIST, invalidRecipeMealPlan.getDay1ToAdd().get().get(0)
                        .fullName), () -> editMealPlanCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        final EditMealPlanCommand standardCommand = new EditMealPlanCommand(INDEX_FIRST_MEALPLAN,
                CommandTestUtil.DESC_FISH_MP);

        // same values -> returns true
        EditMealPlanCommand.EditMealPlanDescriptor copyDescriptor = new EditMealPlanCommand
                .EditMealPlanDescriptor(CommandTestUtil.DESC_FISH_MP);
        EditMealPlanCommand commandWithSameValues = new EditMealPlanCommand(INDEX_FIRST_MEALPLAN,
                copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearMealPlanCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditMealPlanCommand(TypicalIndexes.INDEX_SECOND_MEALPLAN,
                CommandTestUtil.DESC_FISH_MP)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditMealPlanCommand(INDEX_FIRST_MEALPLAN,
                CommandTestUtil.DESC_BURGER_MP)));
    }

    /**
     * A Model stub that always accept the meal plan being edited.
     */
    private class ModelStubAcceptingMealPlanAdded extends ModelStub {
        final ArrayList<MealPlan> mealPlansAdded = new ArrayList<>();
        final ArrayList<Recipe> recipesAdded = new ArrayList<>();

        @Override
        public boolean hasMealPlan(MealPlan mealPlan) {
            requireNonNull(mealPlan);
            return mealPlansAdded.stream().anyMatch(mealPlan::isSameMealPlan);
        }

        @Override
        public void addMealPlan(MealPlan mealPlan) {
            requireNonNull(mealPlan);
            mealPlansAdded.add(mealPlan);
        }

        @Override
        public ReadOnlyMealPlanBook getMealPlanBook() {
            return new MealPlanBook();
        }

        @Override
        public boolean hasRecipe(Recipe recipe) {
            requireNonNull(recipe);
            return recipesAdded.stream().anyMatch(recipe::isSameRecipe);
        }

        @Override
        public void addRecipe(Recipe recipe) {
            requireNonNull(recipe);
            recipesAdded.add(recipe);
        }

        @Override
        public ReadOnlyRecipeBook getRecipeBook() {
            RecipeBook recipeBook = new RecipeBook();
            recipeBook.setRecipes(recipesAdded);
            return recipeBook;
        }

        @Override
        public ObservableList<MealPlan> getFilteredMealPlanList() {
            return FXCollections.observableArrayList(mealPlansAdded);
        }
    }
}
