package calofit.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import javafx.collections.ObservableList;

import org.junit.jupiter.api.Test;

import calofit.commons.core.GuiSettings;
import calofit.logic.commands.exceptions.CommandException;
import calofit.model.CalorieBudget;
import calofit.model.Model;
import calofit.model.ReadOnlyUserPrefs;
import calofit.model.dish.Dish;
import calofit.model.dish.DishDatabase;
import calofit.model.dish.ReadOnlyDishDatabase;
import calofit.model.meal.Meal;
import calofit.model.meal.MealLog;
import calofit.model.util.Statistics;
import calofit.testutil.Assert;
import calofit.testutil.DishBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullDish_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_dishAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingDishAdded modelStub = new ModelStubAcceptingDishAdded();
        Dish validDish = new DishBuilder().build();

        CommandResult commandResult = new AddCommand(validDish).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validDish), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validDish), modelStub.dishesAdded);
    }

    @Test
    public void execute_duplicateDish_throwsCommandException() {
        Dish validDish = new DishBuilder().build();
        AddCommand addCommand = new AddCommand(validDish);
        ModelStub modelStub = new ModelStubWithDish(validDish);

        Assert.assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_MEAL, ()
            -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Dish alice = new DishBuilder().withName("Alice").build();
        Dish bob = new DishBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different dish -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getDishDatabaseFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setDishDatabaseFilePath(Path dishDatabaseFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addDish(Dish dish) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyDishDatabase getDishDatabase() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setDishDatabase(ReadOnlyDishDatabase dishDatabase) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasDish(Dish dish) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasDishName(Dish dish) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Dish getDishByName(Dish dish) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteDish(Dish target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setDish(Dish target, Dish editedDish) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Dish> getFilteredDishList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredDishList(Predicate<Dish> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Meal> getFilteredMealList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addMeal(Meal meal) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public MealLog getMealLog() {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public Statistics getStatistics() {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public CalorieBudget getCalorieBudget() {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void updateStatistics() {
            throw new AssertionError("This method should not be called");
        }
    }

    /**
     * A Model stub that contains a single dish.
     */
    private class ModelStubWithDish extends ModelStub {
        private final Dish dish;
        private final MealLog mealLog = new MealLog();

        ModelStubWithDish(Dish dish) {
            requireNonNull(dish);
            this.dish = dish;
        }

        @Override
        public boolean hasDish(Dish dish) {
            requireNonNull(dish);
            return this.dish.isSameDish(dish);
        }

        @Override
        public boolean hasDishName(Dish dish) {
            requireNonNull(dish);
            return this.dish.isSameDishName(dish);
        }

        @Override
        public Dish getDishByName(Dish dish) {
            return this.dish;
        }

        @Override
        public MealLog getMealLog() {
            return mealLog;
        }
    }

    /**
     * A Model stub that always accept the dish being added.
     */
    private class ModelStubAcceptingDishAdded extends ModelStub {
        final ArrayList<Dish> dishesAdded = new ArrayList<>();
        final MealLog mealLog = new MealLog();

        @Override
        public boolean hasDish(Dish dish) {
            requireNonNull(dish);
            return dishesAdded.stream().anyMatch(dish::isSameDish);
        }

        @Override
        public void addDish(Dish dish) {
            requireNonNull(dish);
            dishesAdded.add(dish);
        }

        @Override
        public boolean hasDishName(Dish dish) {
            requireNonNull(dish);
            return dishesAdded.stream().anyMatch(dish::isSameDishName);
        }

        @Override
        public Dish getDishByName(Dish dish) {
            for (int i = 0; i < dishesAdded.size(); i++) {
                Dish dishInList = dishesAdded.get(i);
                if (dishInList.getName().equals(dish.getName())) {
                    return dishInList;
                }
            }
            return dish;
        }

        @Override
        public MealLog getMealLog() {
            return mealLog;
        }

        @Override
        public ReadOnlyDishDatabase getDishDatabase() {
            return new DishDatabase();
        }
    }

}
