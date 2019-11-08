package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.model.Context;
import seedu.address.model.InternalState;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.activity.TitleContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.testutil.TypicalActivities;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(
            getTypicalAddressBook(),
            new UserPrefs(),
            new InternalState(),
            TypicalActivities.getTypicalActivityBook());
    private Model expectedModel = new ModelManager(
            getTypicalAddressBook(),
            new UserPrefs(),
            new InternalState(),
            TypicalActivities.getTypicalActivityBook());

    @Test
    public void equals() {
        String firstSearchTerm = "Breakfast Lunch";
        String secondSearchTerm = "Lunch Dinner";
        String[] firstKeywords = firstSearchTerm.split("\\s+");
        String[] secondKeywords = secondSearchTerm.split("\\s+");
        FindCommand findFirstCommand = new FindCommand(firstKeywords, firstSearchTerm);
        FindCommand findSecondCommand = new FindCommand(secondKeywords, secondSearchTerm);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstKeywords, firstSearchTerm);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_viewPersonContext_throwError() {
        String expectedMessage = FindCommand.WARNING_INVALID_CONTEXT;
        String searchTerm = "Test 123";
        String[] keywords = splitSearchTerm(searchTerm);
        FindCommand command = new FindCommand(keywords, searchTerm);
        expectedModel.setContext(new Context(TypicalActivities.BREAKFAST));
        model.setContext(new Context(TypicalActivities.BREAKFAST));
        assertCommandFailure(command, model, expectedMessage);
        // There should be no change
        assertEquals(model.getFilteredPersonList(), expectedModel.getFilteredPersonList());
    }

    @Test
    public void execute_viewActivityContext_throwError() {
        String expectedMessage = FindCommand.WARNING_INVALID_CONTEXT;
        String searchTerm = "Test 123";
        String[] keywords = splitSearchTerm(searchTerm);
        FindCommand command = new FindCommand(keywords, searchTerm);
        expectedModel.setContext(new Context(CARL));
        model.setContext(new Context(CARL));
        assertCommandFailure(command, model, expectedMessage);
        // There should be no change
        assertEquals(model.getFilteredPersonList(), expectedModel.getFilteredPersonList());
    }

    // Tests for list contacts context
    @Test
    public void executeContactFind_noMatchingKeyword_noContactFound() {
        String searchTerm = "test testing";
        String[] keywords = splitSearchTerm(searchTerm);
        NameContainsKeywordsPredicate predicate = preparePredicate(searchTerm);
        String expectedMessage = String.format(Messages.MESSAGE_FOUND_BY_KEYWORD,
                0, "contact", searchTerm);
        expectedModel.updateFilteredPersonList(predicate);
        expectedModel.setContext(Context.newListContactContext());
        model.setContext(Context.newListContactContext());

        FindCommand command = new FindCommand(keywords, searchTerm);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void executeContactFind_multipleKeywords_multipleContactsFound() {
        String searchTerm = "benson pauline";
        String[] keywords = splitSearchTerm(searchTerm);
        NameContainsKeywordsPredicate predicate = preparePredicate(searchTerm);
        String expectedMessage = String.format(Messages.MESSAGE_FOUND_BY_KEYWORD,
                2, "contacts", searchTerm);
        expectedModel.updateFilteredPersonList(predicate);
        expectedModel.setContext(Context.newListContactContext());
        model.setContext(Context.newListContactContext());

        FindCommand command = new FindCommand(keywords, searchTerm);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(expectedModel.getFilteredPersonList(), model.getFilteredPersonList());
    }

    // Tests for list activities context
    @Test
    public void executeActivityFind_noMatchingKeyword_noActivityFound() {
        String searchTerm = "test testing";
        String[] keywords = splitSearchTerm(searchTerm);
        TitleContainsKeywordsPredicate predicate = prepareTitlePredicate(searchTerm);
        String expectedMessage = String.format(Messages.MESSAGE_FOUND_BY_KEYWORD,
                0, "activity", searchTerm);
        expectedModel.updateFilteredActivityList(predicate);
        expectedModel.setContext(Context.newListActivityContext());
        model.setContext(Context.newListActivityContext());

        FindCommand command = new FindCommand(keywords, searchTerm);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredActivityList());
    }

    @Test
    public void executeActivityFind_multipleKeywords_multipleActivitiesFound() {
        String searchTerm = "breakfast lunch";
        String[] keywords = splitSearchTerm(searchTerm);
        TitleContainsKeywordsPredicate predicate = prepareTitlePredicate(searchTerm);
        String expectedMessage = String.format(Messages.MESSAGE_FOUND_BY_KEYWORD,
                2, "activities", searchTerm);
        expectedModel.updateFilteredActivityList(predicate);
        expectedModel.setContext(Context.newListActivityContext());
        model.setContext(Context.newListActivityContext());

        FindCommand command = new FindCommand(keywords, searchTerm);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(expectedModel.getFilteredActivityList(), model.getFilteredActivityList());
    }

    /**
     * Helper method used to parse the search term into keywords.
     */
    private String[] splitSearchTerm(String searchTerm) {
        return searchTerm.split("\\s+");
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a {@code TitleContainsKeywordsPredicate}.
     */
    private TitleContainsKeywordsPredicate prepareTitlePredicate(String userInput) {
        return new TitleContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
