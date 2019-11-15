package dukecooks.logic.commands.diary;

import static dukecooks.testutil.diary.TypicalDiaries.ENERGY_FOODS;
import static dukecooks.testutil.diary.TypicalDiaries.FUN_FOODS;
import static dukecooks.testutil.diary.TypicalDiaries.getTypicalDiaryRecords;
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
import dukecooks.model.diary.components.DiaryNameContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindDiaryCommand}.
 */
public class FindDiaryCommandTest {
    private Model model = new ModelManager(getTypicalDiaryRecords(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalDiaryRecords(), new UserPrefs());

    @Test
    public void equals() {
        DiaryNameContainsKeywordsPredicate firstPredicate =
                new DiaryNameContainsKeywordsPredicate(Collections.singletonList("first"));
        DiaryNameContainsKeywordsPredicate secondPredicate =
                new DiaryNameContainsKeywordsPredicate(Collections.singletonList("second"));

        FindDiaryCommand findFirstCommand = new FindDiaryCommand(firstPredicate);
        FindDiaryCommand findSecondCommand = new FindDiaryCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindDiaryCommand findFirstCommandCopy = new FindDiaryCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different recipe -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noDiariesFound() {
        String expectedMessage = String.format(Messages.MESSAGE_DIARY_LISTED_OVERVIEW, 0);
        DiaryNameContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindDiaryCommand command = new FindDiaryCommand(predicate);
        expectedModel.updateFilteredDiaryList(predicate);
        CommandTestUtil.assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredDiaryList());
    }

    @Test
    public void execute_multipleKeywords_multipleDiariesFound() {
        String expectedMessage = String.format(Messages.MESSAGE_DIARY_LISTED_OVERVIEW, 2);
        DiaryNameContainsKeywordsPredicate predicate = preparePredicate("Foods");
        FindDiaryCommand command = new FindDiaryCommand(predicate);
        expectedModel.updateFilteredDiaryList(predicate);
        CommandTestUtil.assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ENERGY_FOODS, FUN_FOODS), model.getFilteredDiaryList());
    }

    /**
     * Parses {@code userInput} into a {@code DiaryNameContainsKeywordsPredicate}.
     */
    private DiaryNameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new DiaryNameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
