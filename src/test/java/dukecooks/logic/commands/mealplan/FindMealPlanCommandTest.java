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
import dukecooks.model.mealplan.components.MealPlanNameContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindMealPlanCommand}.
 */
public class FindMealPlanCommandTest {
    private Model model = new ModelManager(getTypicalMealPlanBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalMealPlanBook(), new UserPrefs());

    @Test
    public void equals() {
        MealPlanNameContainsKeywordsPredicate firstPredicate =
                new MealPlanNameContainsKeywordsPredicate(Collections.singletonList("first"));
        MealPlanNameContainsKeywordsPredicate secondPredicate =
                new MealPlanNameContainsKeywordsPredicate(Collections.singletonList("second"));

        FindMealPlanCommand findFirstCommand = new FindMealPlanCommand(firstPredicate);
        FindMealPlanCommand findSecondCommand = new FindMealPlanCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindMealPlanCommand findFirstCommandCopy = new FindMealPlanCommand(firstPredicate);
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
        MealPlanNameContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindMealPlanCommand command = new FindMealPlanCommand(predicate);
        expectedModel.updateFilteredMealPlanList(predicate);
        CommandTestUtil.assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredMealPlanList());
    }

    @Test
    public void execute_multipleKeywords_multipleMealPlansFound() {
        String expectedMessage = String.format(Messages.MESSAGE_MEALPLAN_LISTED_OVERVIEW, 3);
        MealPlanNameContainsKeywordsPredicate predicate = preparePredicate("Sandwich Fried Tea");
        FindMealPlanCommand command = new FindMealPlanCommand(predicate);
        expectedModel.updateFilteredMealPlanList(predicate);
        CommandTestUtil.assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(TUNA_MP, CHICKEN_MP, TEA_MP), model.getFilteredMealPlanList());
    }

    /**
     * Parses {@code userInput} into a {@code MealPlanNameContainsKeywordsPredicate}.
     */
    private MealPlanNameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new MealPlanNameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
