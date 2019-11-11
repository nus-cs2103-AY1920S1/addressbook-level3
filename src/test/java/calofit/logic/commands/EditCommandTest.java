package calofit.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import calofit.commons.core.Messages;
import calofit.commons.core.index.Index;
import calofit.model.Model;
import calofit.model.ModelManager;
import calofit.model.UserPrefs;
import calofit.model.dish.Dish;
import calofit.model.dish.DishDatabase;
import calofit.model.meal.Meal;
import calofit.model.meal.MealLog;
import calofit.model.tag.Tag;
import calofit.model.util.Timestamp;
import calofit.testutil.DishBuilder;
import calofit.testutil.EditDishDescriptorBuilder;
import calofit.testutil.TypicalDishes;
import calofit.testutil.TypicalIndexes;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(TypicalDishes.getTypicalMealLog(),
            TypicalDishes.getTypicalDishDatabase(), new UserPrefs(), TypicalDishes.getTypicalBudget());

    @Test
    public void execute_allFieldSpecified_success() {
        Dish editedDish = new DishBuilder().build();
        Meal editedMeal = new Meal(editedDish, new Timestamp(LocalDateTime.now()));
        EditCommand.EditDishDescriptor descriptor = new EditDishDescriptorBuilder(editedDish).build();
        EditCommand editCommand = new EditCommand(TypicalIndexes.INDEX_FIRST_MEAL, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_MEAL_SUCCESS,
                model.getMealLog().getTodayMeals().get(0), editedMeal);

        Model expectedModel = new ModelManager(new MealLog(model.getMealLog()),
                new DishDatabase(model.getDishDatabase()), new UserPrefs());

        expectedModel.getMealLog().setMeal(model.getMealLog().getTodayMeals().get(0), editedMeal);

        CommandTestUtil.assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecified_success() {
        Index indexOfLastMeal = Index.fromOneBased(model.getMealLog().getTodayMeals().size());
        Meal lastMeal = model.getMealLog().getTodayMeals().get(indexOfLastMeal.getZeroBased());
        Dish lastDish = lastMeal.getDish();
        List<Tag> lastDishTags = new ArrayList<>(lastDish.getTags());
        Set<Tag> lastDishTagsSet = new HashSet<>();
        for (int i = 0; i < lastDishTags.size(); i++) {
            lastDishTagsSet.add(lastDishTags.get(i));
        }

        Timestamp lastTime = lastMeal.getTimestamp();

        DishBuilder lastDishInList = new DishBuilder(lastDish);
        lastDishTagsSet.add(new Tag(CommandTestUtil.VALID_TAG_SALTY));
        Dish editedDish = lastDishInList.withName(CommandTestUtil.VALID_NAME_MACARONI)
                .withTagsSet(lastDishTagsSet).build();
        Meal editedMeal = new Meal(editedDish, lastTime);

        EditCommand.EditDishDescriptor descriptor = new EditDishDescriptorBuilder()
                .withName(CommandTestUtil.VALID_NAME_MACARONI)
                .withTags(CommandTestUtil.VALID_TAG_SALTY).build();

        EditCommand editCommand = new EditCommand(indexOfLastMeal, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_MEAL_SUCCESS, lastMeal, editedMeal);

        Model expectedModel = new ModelManager(new MealLog(model.getMealLog()),
                new DishDatabase(model.getDishDatabase()), new UserPrefs());

        expectedModel.getMealLog().setMeal(lastMeal, editedMeal);
        CommandTestUtil.assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecified_success() {
        EditCommand editCommand = new EditCommand(TypicalIndexes.INDEX_FIRST_MEAL,
                new EditCommand.EditDishDescriptor());
        Meal editedMeal = model.getMealLog().getTodayMeals().get(TypicalIndexes.INDEX_FIRST_MEAL.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_MEAL_SUCCESS, editedMeal, editedMeal);

        Model expectedModel = new ModelManager(new MealLog(model.getMealLog()),
                new DishDatabase(model.getDishDatabase()), new UserPrefs());

        CommandTestUtil.assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_removeTagSpecified_success() {
        Index indexOfLastMeal = Index.fromOneBased(model.getMealLog().getTodayMeals().size());
        Meal lastMeal = model.getMealLog().getTodayMeals().get(indexOfLastMeal.getZeroBased());
        Dish lastDish = lastMeal.getDish();
        List<Tag> tagList = new ArrayList<>(lastDish.getTags());
        Tag tagToBeRemoved = tagList.get(0);
        Set<Tag> lastDishTagsSet = new HashSet<>();
        for (int i = 0; i < tagList.size(); i++) {
            lastDishTagsSet.add(tagList.get(i));
        }

        Timestamp lastTime = lastMeal.getTimestamp();

        DishBuilder lastDishInList = new DishBuilder(lastDish);

        lastDishTagsSet.remove(tagToBeRemoved);
        Dish editedDish = lastDishInList.withTagsSet(lastDishTagsSet).build();
        Meal editedMeal = new Meal(editedDish, lastTime);

        EditCommand.EditDishDescriptor descriptor = new EditDishDescriptorBuilder()
                .withTagsToBeRemoved(tagToBeRemoved).build();

        EditCommand editCommand = new EditCommand(indexOfLastMeal,
                descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_MEAL_SUCCESS, lastMeal, editedMeal);

        Model expectedModel = new ModelManager(new MealLog(model.getMealLog()),
                new DishDatabase(model.getDishDatabase()), new UserPrefs());

        CommandTestUtil.assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    //@Test
    //public void execute_filteredList_success() {
    //    CommandTestUtil.showDishAtIndex(model, TypicalIndexes.INDEX_FIRST_MEAL);
    //
    //    Dish dishInFilteredList = model.getFilteredDishList().get(TypicalIndexes.INDEX_FIRST_MEAL.getZeroBased());
    //    Dish editedDish = new DishBuilder(dishInFilteredList).withName(CommandTestUtil.VALID_NAME_MACARONI).build();
    //    EditCommand editCommand = new EditCommand(TypicalIndexes.INDEX_FIRST_MEAL,
    //            new EditDishDescriptorBuilder().withName(CommandTestUtil.VALID_NAME_MACARONI).build());
    //
    //    String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_MEAL_SUCCESS, editedDish);
    //
    //    Model expectedModel = new ModelManager(new DishDatabase(model.getDishDatabase()), new UserPrefs());
    //    expectedModel.setDish(model.getFilteredDishList().get(0), editedDish);
    //
    //    CommandTestUtil.assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    //}

    //@Test
    //public void execute_duplicateDishUnfilteredList_failure() {
    //    Dish firstDish = model.getFilteredDishList().get(TypicalIndexes.INDEX_FIRST_MEAL.getZeroBased());
    //    EditCommand.EditDishDescriptor descriptor = new EditDishDescriptorBuilder(firstDish).build();
    //    EditCommand editCommand = new EditCommand(TypicalIndexes.INDEX_SECOND_MEAL, descriptor);
    //
    //    CommandTestUtil.assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_MEAL);
    //}

    //@Test
    //public void execute_duplicateDishFilteredList_failure() {
    //    CommandTestUtil.showDishAtIndex(model, TypicalIndexes.INDEX_FIRST_MEAL);
    //
    //    // edit dish in filtered list into a duplicate in dish database
    //    Dish dishInList = model.getDishDatabase().getDishList().get(TypicalIndexes.INDEX_SECOND_MEAL.getZeroBased());
    //    EditCommand editCommand = new EditCommand(TypicalIndexes.INDEX_FIRST_MEAL,
    //            new EditDishDescriptorBuilder(dishInList).build());
    //
    //    CommandTestUtil.assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_MEAL);
    //}

    @Test
    public void execute_invalidDishIndex_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredDishList().size() + 1);
        EditCommand.EditDishDescriptor descriptor = new EditDishDescriptorBuilder()
                .withName(CommandTestUtil.VALID_NAME_MACARONI).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        CommandTestUtil.assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_MEAL_DISPLAYED_INDEX);
    }


    @Test
    public void equals() {
        final EditCommand standardCommand =
                new EditCommand(TypicalIndexes.INDEX_FIRST_MEAL, CommandTestUtil.DESC_DUCK_RICE);

        // same values -> returns true
        EditCommand.EditDishDescriptor copyDescriptor =
                new EditCommand.EditDishDescriptor(CommandTestUtil.DESC_DUCK_RICE);
        EditCommand commandWithSameValues = new EditCommand(TypicalIndexes.INDEX_FIRST_MEAL, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(TypicalIndexes.INDEX_SECOND_MEAL,
                CommandTestUtil.DESC_DUCK_RICE)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(
                new EditCommand(TypicalIndexes.INDEX_FIRST_MEAL, CommandTestUtil.DESC_MACARONI)));
    }

}
