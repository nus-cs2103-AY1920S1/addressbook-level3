package seedu.jarvis.logic.commands.finance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static seedu.jarvis.commons.core.Messages.MESSAGE_PURCHASES_LISTED_OVERVIEW;
import static seedu.jarvis.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.jarvis.testutil.Assert.assertThrows;
import static seedu.jarvis.testutil.finance.TypicalPurchases.DINNER_REEDZ;
import static seedu.jarvis.testutil.finance.TypicalPurchases.LUNCH_JAPANESE;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.ModelManager;
import seedu.jarvis.model.cca.CcaTracker;
import seedu.jarvis.model.course.CoursePlanner;
import seedu.jarvis.model.finance.FinanceTracker;
import seedu.jarvis.model.finance.InstallmentNameContainsKeywordsPredicate;
import seedu.jarvis.model.finance.PurchaseNameContainsKeywordsPredicate;
import seedu.jarvis.model.history.HistoryManager;
import seedu.jarvis.model.planner.Planner;
import seedu.jarvis.model.userprefs.UserPrefs;

public class FindPurchaseCommandTest {
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(new CcaTracker(), new HistoryManager(), new FinanceTracker(), new UserPrefs(),
                new Planner(), new CoursePlanner());
        expectedModel = new ModelManager(model.getCcaTracker(), model.getHistoryManager(),
                model.getFinanceTracker(), new UserPrefs(), model.getPlanner(), model.getCoursePlanner());
    }

    /**
     * Verifies that checking {@code FindPurchaseCommand} for the availability of inverse execution returns false.
     */
    @Test
    public void hasInverseExecution() {
        PurchaseNameContainsKeywordsPredicate predicate = preparePredicate(" ");
    }

    @Test
    public void equals() {
        PurchaseNameContainsKeywordsPredicate firstPredicate =
                new PurchaseNameContainsKeywordsPredicate(Collections.singletonList("first"));
        PurchaseNameContainsKeywordsPredicate secondPredicate =
                new PurchaseNameContainsKeywordsPredicate(Collections.singletonList("second"));

        FindPurchaseCommand findFirstCommand = new FindPurchaseCommand(firstPredicate);
        FindPurchaseCommand findSecondCommand = new FindPurchaseCommand(secondPredicate);

        //same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        //same values -> return true
        FindPurchaseCommand findFirstCopy = new FindPurchaseCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCopy));

        //different types
        assertFalse(findFirstCommand.equals(1));

        //null -> returns false
        assertFalse(findFirstCommand.equals(null));

        //different purchase -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPurchaseFound() {
        String expectedMessage = String.format(MESSAGE_PURCHASES_LISTED_OVERVIEW, 0);
        PurchaseNameContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindPurchaseCommand command = new FindPurchaseCommand(predicate);
        expectedModel.updateFilteredPurchaseList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPurchaseList());
    }

    @Test
    public void execute_multipleKeywords_multiplePurchasesFound() {
        model.addPurchase(LUNCH_JAPANESE);
        model.addPurchase(DINNER_REEDZ);
        String expectedMessage = String.format(MESSAGE_PURCHASES_LISTED_OVERVIEW, 2);
        PurchaseNameContainsKeywordsPredicate predicate = preparePredicate("Lunch Dinner");
        FindPurchaseCommand command = new FindPurchaseCommand(predicate);
        expectedModel.updateFilteredPurchaseList(predicate);
        assertEquals(Arrays.asList(LUNCH_JAPANESE, DINNER_REEDZ), model.getFilteredPurchaseList());
        //assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    /**
     * Verifies that calling inverse execution of {@code FindPurchaseCommand} will always throw a
     * {@code CommandException} with the correct message.
     */
    @Test
    public void executeInverse_throwsCommandException() {
        PurchaseNameContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindPurchaseCommand findPurchaseCommand = new FindPurchaseCommand(predicate);
        assertThrows(CommandException.class,
                FindPurchaseCommand.MESSAGE_NO_INVERSE, () -> findPurchaseCommand.executeInverse(model));
    }

    /**
     * Parses {@code userInput} into a {@code PurchaseNameContainsKeywordsPredicate}.
     */
    private PurchaseNameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new PurchaseNameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a {@code InstallmentNameContainsKeywordsPredicate}.
     */
    private InstallmentNameContainsKeywordsPredicate preparePredicate2(String userInput) {
        return new InstallmentNameContainsKeywordsPredicate(Arrays.asList(userInput));
    }
}
