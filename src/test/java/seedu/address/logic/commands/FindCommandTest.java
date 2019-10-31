package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalIntervieweeList;
import static seedu.address.testutil.TypicalPersons.getTypicalInterviewerList;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.PersonNameHasKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalIntervieweeList(), getTypicalInterviewerList(),
            new UserPrefs(), new LinkedList<>());
    private Model expectedModel = new ModelManager(getTypicalIntervieweeList(), getTypicalInterviewerList(),
            new UserPrefs(), new LinkedList<>());

    @Test
    public void equals() {
        PersonNameHasKeywordsPredicate firstPredicate =
                new PersonNameHasKeywordsPredicate(Collections.singletonList("first"));
        PersonNameHasKeywordsPredicate secondPredicate =
                new PersonNameHasKeywordsPredicate(Collections.singletonList("second"));

        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        // TODO: Fix this train wreck (ken)
        /*
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        PersonNameHasKeywordsPredicate predicate = preparePredicate(" ");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
        */
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        /*
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        PersonNameHasKeywordsPredicate predicate = preparePredicate("Kurz Elle Kunz");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredPersonList());
        */
    }

    /**
     * Parses {@code userInput} into a {@code PersonNameHasKeywordsPredicate}.
     */
    private PersonNameHasKeywordsPredicate preparePredicate(String userInput) {
        return new PersonNameHasKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
