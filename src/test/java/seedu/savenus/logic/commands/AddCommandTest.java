package seedu.savenus.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.savenus.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.savenus.commons.core.GuiSettings;
import seedu.savenus.logic.commands.exceptions.CommandException;
import seedu.savenus.model.Menu;
import seedu.savenus.model.Model;
import seedu.savenus.model.ReadOnlyMenu;
import seedu.savenus.model.ReadOnlyUserPrefs;
import seedu.savenus.model.food.Food;
import seedu.savenus.testutil.FoodBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullfood_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_foodAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingfoodAdded modelStub = new ModelStubAcceptingfoodAdded();
        Food validFood = new FoodBuilder().build();

        CommandResult commandResult = new AddCommand(validFood).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validFood), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validFood), modelStub.foodAdded);
    }

    @Test
    public void execute_duplicatefood_throwsCommandException() {
        Food validFood = new FoodBuilder().build();
        AddCommand addCommand = new AddCommand(validFood);
        ModelStub modelStub = new ModelStubWithfood(validFood);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_FOOD, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Food alice = new FoodBuilder().withName("Alice").build();
        Food bob = new FoodBuilder().withName("Bob").build();
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

        // different food -> returns false
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
        public Path getMenuFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setMenuFilePath(Path menuFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addFood(Food food) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setMenu(ReadOnlyMenu newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyMenu getMenu() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasFood(Food food) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteFood(Food target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setFood(Food target, Food editedFood) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Food> getFilteredFoodList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredFoodList(Predicate<Food> predicate) {
        }

        @Override
        public void updateRecommendationComparator(Comparator<Food> comparator) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetRecommendationComparator() {
        }
    }

    /**
     * A Model stub that contains a single food.
     */
    private class ModelStubWithfood extends ModelStub {
        private final Food food;

        ModelStubWithfood(Food food) {
            requireNonNull(food);
            this.food = food;
        }

        @Override
        public boolean hasFood(Food food) {
            requireNonNull(food);
            return this.food.isSameFood(food);
        }
    }

    /**
     * A Model stub that always accept the food being added.
     */
    private class ModelStubAcceptingfoodAdded extends ModelStub {
        final ArrayList<Food> foodAdded = new ArrayList<>();

        @Override
        public boolean hasFood(Food food) {
            requireNonNull(food);
            return foodAdded.stream().anyMatch(food::isSameFood);
        }

        @Override
        public void addFood(Food food) {
            requireNonNull(food);
            foodAdded.add(food);
        }

        @Override
        public ReadOnlyMenu getMenu() {
            return new Menu();
        }
    }

}
