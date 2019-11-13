package calofit.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import calofit.logic.commands.exceptions.CommandException;
import calofit.model.Model;
import calofit.model.dish.Dish;
import calofit.model.dish.DishDatabase;
import calofit.model.dish.Name;
import calofit.model.dish.ReadOnlyDishDatabase;
import calofit.model.meal.Meal;
import calofit.model.meal.MealLog;
import calofit.testutil.Assert;
import calofit.testutil.DishBuilder;
import calofit.testutil.TypicalDishes;

public class AddCommandTest {

    @Test
    public void constructor_nullDish_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new AddCommand((Dish) null));
    }

    @Test
    public void execute_dishAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingDishAdded modelStub = Mockito.spy(ModelStubAcceptingDishAdded.class);

        Dish validDish = new DishBuilder().build();
        CommandResult commandResult = new AddCommand(validDish).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validDish), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validDish), modelStub.dishesAdded);
    }

    @Test
    public void execute_mealAddedToMealLog_addSuccessful() throws CommandException {
        ObservableList<Dish> dishes = FXCollections.observableList(TypicalDishes.getTypicalDishes());
        MealLog mealLog = Mockito.mock(MealLog.class);
        Model model = Mockito.mock(Model.class);
        Mockito.doReturn(mealLog).when(model).getMealLog();
        Mockito.doReturn(dishes).when(model).getFilteredDishList();

        List<Integer> indexes = List.of(1, 3, 6);
        CommandResult result = new AddCommand(indexes).execute(model);
        ArgumentCaptor<LinkedList<Meal>> mealsAdded = ArgumentCaptor.forClass(LinkedList.class);
        Mockito.verify(mealLog).addListOfMeals(mealsAdded.capture());
        Set<Dish> addedDishes = mealsAdded.getValue().stream().map(Meal::getDish).collect(Collectors.toSet());
        Set<Dish> targetDishes = indexes.stream().map(i -> dishes.get(i - 1)).collect(Collectors.toSet());
        assertEquals(targetDishes, addedDishes);
    }

    //@Test
    //public void execute_

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
     * A Model stub that always accept the dish being added.
     */
    private abstract static class ModelStubAcceptingDishAdded implements Model {
        final ArrayList<Dish> dishesAdded;
        final MealLog mealLog;

        public ModelStubAcceptingDishAdded() {
            dishesAdded = new ArrayList<>();
            mealLog = new MealLog();
        }

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
        public boolean hasDishName(Name dishName) {
            requireNonNull(dishName);
            return dishesAdded.stream().anyMatch(dish -> {
                return dish.getName().toLowerCase().equals(dishName.toLowerCase());
            });
        }

        @Override
        public Dish getDishByName(Name dishName) {
            for (int i = 0; i < dishesAdded.size(); i++) {
                Dish dishInList = dishesAdded.get(i);
                if (dishInList.getName().toLowerCase().equals(dishName.toLowerCase())) {
                    return dishInList;
                }
            }
            return null;
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
