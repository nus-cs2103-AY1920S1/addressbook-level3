package seedu.ezwatchlist.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ezwatchlist.testutil.TypicalShows.getDatabase;
import static seedu.ezwatchlist.testutil.TypicalShows.getTypicalWatchList;

import java.util.*;

import org.junit.jupiter.api.Test;

import seedu.ezwatchlist.logic.parser.SearchKey;
import seedu.ezwatchlist.model.Model;
import seedu.ezwatchlist.model.ModelManager;
import seedu.ezwatchlist.model.UserPrefs;
import seedu.ezwatchlist.model.show.NameContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code SearchCommand}.
 */
public class SearchCommandTest {
    private Model model = new ModelManager(getTypicalWatchList(), getDatabase(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalWatchList(), getDatabase(), new UserPrefs());

    @Test
    public void equals() {
        HashMap<SearchKey, List<String>> firstHash = new HashMap<>();
        HashMap<SearchKey, List<String>> secondHash = new HashMap<>();
        ArrayList<String> firstList = new ArrayList<>();
        ArrayList<String> secondList = new ArrayList<>();
        firstList.add("first");
        secondList.add("second");
        firstHash.put(SearchKey.KEY_NAME, firstList);
        secondHash.put(SearchKey.KEY_NAME, secondList);
        SearchCommand searchFirstCommand = new SearchCommand(firstHash);
        SearchCommand searchSecondCommand = new SearchCommand(secondHash);

        // same object -> returns true
        assertTrue(searchFirstCommand.equals(searchFirstCommand));

        // same values -> returns true
        SearchCommand findFirstCommandCopy = new SearchCommand(firstHash);
        //assertTrue(searchFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(searchFirstCommand.equals(1));

        // null -> returns false
        assertFalse(searchFirstCommand.equals(null));

        // different show -> returns false
        assertFalse(searchFirstCommand.equals(searchSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noShowFound() {
        /*String expectedMessage = String.format(MESSAGE_SHOWS_LISTED_OVERVIEW, 0);
        NameContainsKeywordsPredicate predicate = preparePredicate(" ");
        HashMap<String, List<String>> emptyHash = new HashMap<String, List<String>>();
        ArrayList<String> emptyList = new ArrayList<>();
        emptyList.add(" ");
        emptyHash.put("name", emptyList);
        SearchCommand command = new SearchCommand(emptyHash);
        expectedModel.updateFilteredShowList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredShowList());
        */
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
