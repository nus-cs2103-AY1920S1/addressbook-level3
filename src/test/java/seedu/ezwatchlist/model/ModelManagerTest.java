package seedu.ezwatchlist.model;

import org.junit.jupiter.api.Test;
import seedu.ezwatchlist.commons.core.GuiSettings;
import seedu.ezwatchlist.model.WatchList;
import seedu.ezwatchlist.model.ModelManager;
import seedu.ezwatchlist.model.UserPrefs;
import seedu.ezwatchlist.model.show.NameContainsKeywordsPredicate;
import seedu.ezwatchlist.testutil.WatchListBuilder;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.ezwatchlist.model.Model.PREDICATE_SHOW_ALL_SHOWS;
import static seedu.ezwatchlist.testutil.Assert.assertThrows;
import static seedu.ezwatchlist.testutil.TypicalShows.AVENGERSENDGAME;
import static seedu.ezwatchlist.testutil.TypicalShows.FIGHTCLUB;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new WatchList(), new WatchList(modelManager.getWatchList()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setWatchListFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setWatchListFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setWatchListFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setWatchListFilePath(null));
    }

    @Test
    public void setWatchListFilePath_validPath_setsWatchListFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setWatchListFilePath(path);
        assertEquals(path, modelManager.getWatchListFilePath());
    }

    @Test
    public void hasShow_nullShow_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasShow(null));
    }

    @Test
    public void hasShow_showNotInWatchList_returnsFalse() {
        assertFalse(modelManager.hasShow(AVENGERSENDGAME));
    }

    @Test
    public void hasShow_showInWatchList_returnsTrue() {
        modelManager.addShow(AVENGERSENDGAME);
        assertTrue(modelManager.hasShow(AVENGERSENDGAME));
    }

    @Test
    public void getFilteredShowList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredShowList().remove(0));
    }

    @Test
    public void equals() {
        WatchList watchList = new WatchListBuilder().withShow(AVENGERSENDGAME).withShow(FIGHTCLUB).build();
        WatchList differentWatchList = new WatchList();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(watchList, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(watchList, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different watchlist -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentWatchList, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = AVENGERSENDGAME.getName().showName.split("\\s+");
        modelManager.updateFilteredShowList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(watchList, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredShowList(PREDICATE_SHOW_ALL_SHOWS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setWatchListFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(watchList, differentUserPrefs)));
    }
}
