package dukecooks.logic.commands.mealplan;

import static dukecooks.testutil.mealplan.TypicalMealPlans.CHICKEN_MP;
import static dukecooks.testutil.mealplan.TypicalMealPlans.TEA_MP;
import static dukecooks.testutil.mealplan.TypicalMealPlans.TUNA_MP;
import static dukecooks.testutil.mealplan.TypicalMealPlans.getTypicalMealPlanBook;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import dukecooks.commons.core.Messages;
import dukecooks.logic.commands.CommandTestUtil;
import dukecooks.model.Model;
import dukecooks.model.ModelManager;
import dukecooks.model.UserPrefs;
import dukecooks.model.mealplan.components.MealPlanRecipesContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindMealPlanWithCommand}.
 */
public class FindMealPlanWithCommandTest {
    private Model model = new ModelManager(getTypicalMealPlanBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalMealPlanBook(), new UserPrefs());

    @Test
    public void equals() {
        MealPlanRecipesContainsKeywordsPredicate firstPredicate =
                new MealPlanRecipesContainsKeywordsPredicate(Collections.singletonList("first"));
        MealPlanRecipesContainsKeywordsPredicate secondPredicate =
                new MealPlanRecipesContainsKeywordsPredicate(Collections.singletonList("second"));

        FindMealPlanWithCommand findFirstCommand = new FindMealPlanWithCommand(firstPredicate);
        FindMealPlanWithCommand findSecondCommand = new FindMealPlanWithCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindMealPlanWithCommand findFirstCommandCopy = new FindMealPlanWithCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different mealPlan -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noMealPlanFound() {
        String expectedMessage = String.format(Messages.MESSAGE_MEALPLAN_LISTED_OVERVIEW, 0);
        MealPlanRecipesContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindMealPlanWithCommand command = new FindMealPlanWithCommand(predicate);
        expectedModel.updateFilteredMealPlanList(predicate);
        CommandTestUtil.assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredMealPlanList());
    }

    /**
     * Parses {@code userInput} into a {@code MealPlanRecipesContainsKeywordsPredicate}.
     */
    private MealPlanRecipesContainsKeywordsPredicate preparePredicate(String userInput) {
        return new MealPlanRecipesContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
