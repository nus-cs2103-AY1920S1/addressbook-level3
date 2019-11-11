package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_FLASHCARD_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalFlashCards.PROP_DELAY;
import static seedu.address.testutil.TypicalFlashCards.TRANS_DELAY;
import static seedu.address.testutil.TypicalFlashCards.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.category.CategoryContainsAnyKeywordsPredicate;

public class ListCategoryCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        CategoryContainsAnyKeywordsPredicate firstPredicate =
                new CategoryContainsAnyKeywordsPredicate(Collections.singletonList("first"));
        CategoryContainsAnyKeywordsPredicate secondPredicate =
                new CategoryContainsAnyKeywordsPredicate(Collections.singletonList("second"));

        ListCategoryCommand findFirstCommand = new ListCategoryCommand(firstPredicate);
        ListCategoryCommand findSecondCommand = new ListCategoryCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        ListCategoryCommand findFirstCommandCopy = new ListCategoryCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different flashCard -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noFlashCardFound() {
        String expectedMessage = String.format(MESSAGE_FLASHCARD_LISTED_OVERVIEW, 0);
        CategoryContainsAnyKeywordsPredicate predicate = preparePredicate(" ");
        ListCategoryCommand command = new ListCategoryCommand(predicate);
        expectedModel.updateFilteredFlashCardList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredFlashCardList());
    }

    @Test
    public void execute_multipleKeywords_multipleFlashCardFound() {
        String expectedMessage = String.format(MESSAGE_FLASHCARD_LISTED_OVERVIEW, 2);
        CategoryContainsAnyKeywordsPredicate predicate = preparePredicate("C delay");
        ListCategoryCommand command = new ListCategoryCommand(predicate);
        expectedModel.updateFilteredFlashCardList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(PROP_DELAY, TRANS_DELAY), model.getFilteredFlashCardList());
    }

    /**
     * Parses {@code userInput} into a {@code CategoryContainsAnyKeywordsPredicate}.
     */
    private CategoryContainsAnyKeywordsPredicate preparePredicate(String userInput) {
        return new CategoryContainsAnyKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
