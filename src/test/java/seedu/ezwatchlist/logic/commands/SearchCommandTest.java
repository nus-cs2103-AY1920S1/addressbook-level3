package seedu.ezwatchlist.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ezwatchlist.commons.core.Messages.MESSAGE_SHOWS_LISTED_OVERVIEW;
import static seedu.ezwatchlist.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.ezwatchlist.testutil.TypicalShows.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.ezwatchlist.model.Model;
import seedu.ezwatchlist.model.ModelManager;
import seedu.ezwatchlist.model.UserPrefs;
import seedu.ezwatchlist.model.show.NameContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class SearchCommandTest {
    private Model model = new ModelManager(getTypicalWatchList(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalWatchList(), new UserPrefs());

    @Test
    public void equals() {
        /*NameContainsKeywordsPredicate firstPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        NameContainsKeywordsPredicate secondPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("second"));*/

        SearchCommand searchFirstCommand = new SearchCommand(Optional.of("first"));
        SearchCommand searchSecondCommand = new SearchCommand(Optional.of("second"));

        // same object -> returns true
        assertTrue(searchFirstCommand.equals(searchFirstCommand));

        // same values -> returns true
        SearchCommand findFirstCommandCopy = new SearchCommand(Optional.of("first"));
        assertTrue(searchFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(searchFirstCommand.equals(1));

        // null -> returns false
        assertFalse(searchFirstCommand.equals(null));

        // different show -> returns false
        assertFalse(searchFirstCommand.equals(searchSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noShowFound() {
        String expectedMessage = String.format(MESSAGE_SHOWS_LISTED_OVERVIEW, 0);
        NameContainsKeywordsPredicate predicate = preparePredicate(" ");
        SearchCommand command = new SearchCommand(Optional.of(" "));
        expectedModel.updateFilteredShowList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredShowList());
    }
/*
    @Test

    public void execute_multipleKeywords_multipleShowsFound() {
        String expectedMessage = String.format(MESSAGE_SHOWS_LISTED_OVERVIEW, 3);
        NameContainsKeywordsPredicate predicate = preparePredicate("Avengers: Saving God");
        SearchCommand command = new SearchCommand(Optional.of("Avengers: Saving God"));
        expectedModel.updateFilteredShowList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(AVENGERSENDGAME, SAVINGPRIVATERYAN, GODFATHER2), model.getFilteredShowList());
    }
*/
    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
