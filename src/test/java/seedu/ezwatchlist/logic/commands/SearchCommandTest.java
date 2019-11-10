package seedu.ezwatchlist.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.ezwatchlist.logic.commands.CommandTestUtil.*;
import static seedu.ezwatchlist.testutil.TypicalShows.getDatabase;
import static seedu.ezwatchlist.testutil.TypicalShows.getTypicalWatchList;

import java.util.*;

import org.junit.jupiter.api.Test;

import seedu.ezwatchlist.commons.core.messages.SearchMessages;
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
        ArrayList<String> firstNameList = new ArrayList<>();
        ArrayList<String> secondNameList = new ArrayList<>();
        ArrayList<String> secondGenreList = new ArrayList<>();
        firstNameList.add(VALID_SHOW_NAME_ANNABELLE);
        secondNameList.add(VALID_SHOW_NAME_BOB_THE_BUILDER);
        secondGenreList.add(VALID_GENRE_ACTION);
        firstHash.put(SearchKey.KEY_NAME, firstNameList);
        firstHash.put(SearchKey.KEY_NAME, firstNameList);
        secondHash.put(SearchKey.KEY_GENRE, secondGenreList);
        SearchCommand searchFirstCommand = new SearchCommand(firstHash);
        SearchCommand searchSecondCommand = new SearchCommand(secondHash);

        // same object -> returns true
        assertTrue(searchFirstCommand.equals(searchFirstCommand));

        // same values -> returns true
        SearchCommand findFirstCommandCopy = new SearchCommand(firstHash);
        assertTrue(searchFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(searchFirstCommand.equals("first"));
        assertFalse(searchFirstCommand.equals(1));

        // null -> returns false
        assertFalse(searchFirstCommand.equals(null));

        // different show -> returns false
        assertFalse(searchFirstCommand.equals(searchSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noShowFound() {
        /*String expectedMessage = String.format(SearchMessages.MESSAGE_SHOWS_FOUND_OVERVIEW, 0);

        HashMap<SearchKey, List<String>> emptyHash = new HashMap<>();
        ArrayList<String> emptyList = new ArrayList<>();
        emptyList.add(" ");
        emptyHash.put(SearchKey.KEY_NAME, emptyList);
        SearchCommand command = new SearchCommand(emptyHash);

        expectedModel.updateFilteredShowList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredShowList());*/
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
