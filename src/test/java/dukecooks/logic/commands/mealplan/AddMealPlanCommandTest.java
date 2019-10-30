package dukecooks.logic.commands.mealplan;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import dukecooks.logic.commands.CommandResult;
import dukecooks.logic.commands.exceptions.CommandException;
import dukecooks.model.ModelStub;
import dukecooks.model.mealplan.MealPlanBook;
import dukecooks.model.mealplan.ReadOnlyMealPlanBook;
import dukecooks.model.mealplan.components.MealPlan;
import dukecooks.model.recipe.components.Recipe;
import dukecooks.testutil.Assert;
import dukecooks.testutil.mealplan.MealPlanBuilder;

public class AddMealPlanCommandTest {

    @Test
    public void constructor_nullMealPlan_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new AddMealPlanCommand(null));
    }

    @Test
    public void execute_duplicateMealPlan_throwsCommandException() {
        MealPlan validMealPlan = new MealPlanBuilder().build();
        AddMealPlanCommand addMealPlanCommand = new AddMealPlanCommand(validMealPlan);
        ModelStub modelStub = new ModelStubWithMealPlan(validMealPlan);

        Assert.assertThrows(CommandException.class, AddMealPlanCommand.MESSAGE_DUPLICATE_MEALPLAN, () -> addMealPlanCommand
                .execute(modelStub));
    }

    @Test
    public void equals() {
        MealPlan alice = new MealPlanBuilder().withName("Alice").build();
        MealPlan bob = new MealPlanBuilder().withName("Bob").build();
        AddMealPlanCommand addAliceCommand = new AddMealPlanCommand(alice);
        AddMealPlanCommand addBobCommand = new AddMealPlanCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddMealPlanCommand addAliceCommandCopy = new AddMealPlanCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different mealPlan -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * A Model stub that contains a single mealPlan.
     */
    private class ModelStubWithMealPlan extends ModelStub {
        private final MealPlan mealPlan;

        ModelStubWithMealPlan(MealPlan mealPlan) {
            requireNonNull(mealPlan);
            this.mealPlan = mealPlan;
        }

        @Override
        public boolean hasMealPlan(MealPlan mealPlan) {
            requireNonNull(mealPlan);
            return this.mealPlan.isSameMealPlan(mealPlan);
        }
    }

    /**
     * A Model stub that always accept the mealPlan being added.
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
    }

}
