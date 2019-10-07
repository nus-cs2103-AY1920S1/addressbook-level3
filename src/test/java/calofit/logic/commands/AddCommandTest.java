package calofit.logic.commands;

import calofit.commons.core.GuiSettings;
import calofit.logic.commands.exceptions.CommandException;
import calofit.model.AddressBook;
import calofit.model.Model;
import calofit.model.ReadOnlyAddressBook;
import calofit.model.ReadOnlyUserPrefs;
import calofit.model.meal.Meal;
import calofit.testutil.Assert;
import calofit.testutil.MealBuilder;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.*;

public class AddCommandTest {

    @Test
    public void constructor_nullMeal_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_mealAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingMealAdded modelStub = new ModelStubAcceptingMealAdded();
        Meal validMeal = new MealBuilder().build();

        CommandResult commandResult = new AddCommand(validMeal).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validMeal), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validMeal), modelStub.mealsAdded);
    }

    @Test
    public void execute_duplicateMeal_throwsCommandException() {
        Meal validMeal = new MealBuilder().build();
        AddCommand addCommand = new AddCommand(validMeal);
        ModelStub modelStub = new ModelStubWithMeal(validMeal);

        Assert.assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_MEAL, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Meal alice = new MealBuilder().withName("Alice").build();
        Meal bob = new MealBuilder().withName("Bob").build();
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

        // different meal -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
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
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addMeal(Meal meal) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasMeal(Meal meal) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteMeal(Meal target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setMeal(Meal target, Meal editedMeal) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Meal> getFilteredMealList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredMealList(Predicate<Meal> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single meal.
     */
    private class ModelStubWithMeal extends ModelStub {
        private final Meal meal;

        ModelStubWithMeal(Meal meal) {
            requireNonNull(meal);
            this.meal = meal;
        }

        @Override
        public boolean hasMeal(Meal meal) {
            requireNonNull(meal);
            return this.meal.isSameMeal(meal);
        }
    }

    /**
     * A Model stub that always accept the meal being added.
     */
    private class ModelStubAcceptingMealAdded extends ModelStub {
        final ArrayList<Meal> mealsAdded = new ArrayList<>();

        @Override
        public boolean hasMeal(Meal meal) {
            requireNonNull(meal);
            return mealsAdded.stream().anyMatch(meal::isSameMeal);
        }

        @Override
        public void addMeal(Meal meal) {
            requireNonNull(meal);
            mealsAdded.add(meal);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
