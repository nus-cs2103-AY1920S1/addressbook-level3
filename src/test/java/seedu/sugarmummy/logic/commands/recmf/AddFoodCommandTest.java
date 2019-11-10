package seedu.sugarmummy.logic.commands.recmf;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.sugarmummy.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.sugarmummy.logic.commands.CommandResult;
import seedu.sugarmummy.logic.commands.exceptions.CommandException;
import seedu.sugarmummy.model.ModelStub;
import seedu.sugarmummy.model.recmf.Food;
import seedu.sugarmummy.testutil.recmf.FoodBuilder;

class AddFoodCommandTest {

    private Food food;

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddFoodCommand(null));
    }

    @Test
    public void execute_foodAcceptedByModel_addSuccessful() throws Exception {
        AddFoodCommandTest.ModelStubAcceptingFoodAdded modelStub = new AddFoodCommandTest.ModelStubAcceptingFoodAdded();
        Food food = new FoodBuilder().build();

        CommandResult commandResult = new AddFoodCommand(food).execute(modelStub);

        assertEquals(String.format(AddFoodCommand.MESSAGE_SUCCESS, food), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(food), modelStub.foodsAdded);
    }

    @Test
    public void execute_duplicateFood_throwsCommandException() {
        Food validFood = new FoodBuilder().build();
        AddFoodCommand addFoodCommand = new AddFoodCommand(validFood);
        ModelStub modelStub = new AddFoodCommandTest.ModelStubWithFood(validFood);

        assertThrows(CommandException.class, AddFoodCommand.MESSAGE_DUPLICATE_FOOD, () ->
                addFoodCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Food yogurt = new FoodBuilder().withFoodName("Yogurt").build();
        Food water = new FoodBuilder().withFoodName("water").build();
        AddFoodCommand addYogurtCommand = new AddFoodCommand(yogurt);
        AddFoodCommand addWaterCommand = new AddFoodCommand(water);

        // same object -> returns true
        assertTrue(addYogurtCommand.equals(addYogurtCommand));

        // same values -> returns true
        AddFoodCommand addFoodCommandCopy = new AddFoodCommand(yogurt);
        assertTrue(addYogurtCommand.equals(addFoodCommandCopy));

        // different types -> returns false
        assertFalse(addYogurtCommand.equals(1));

        // null -> returns false
        assertFalse(addYogurtCommand.equals(null));

        // different food -> returns false
        assertFalse(addYogurtCommand.equals(addWaterCommand));
    }

    /**
     * A Model stub that contains a single food.
     */
    private class ModelStubWithFood extends ModelStub {
        private final Food food;

        ModelStubWithFood(Food food) {
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
    private class ModelStubAcceptingFoodAdded extends ModelStub {
        final ArrayList<Food> foodsAdded = new ArrayList<>();

        @Override
        public boolean hasFood(Food food) {
            requireNonNull(food);
            return foodsAdded.stream().anyMatch(food::isSameFood);
        }

        @Override
        public void addFood(Food food) {
            requireNonNull(food);
            foodsAdded.add(food);
        }

    }
}
