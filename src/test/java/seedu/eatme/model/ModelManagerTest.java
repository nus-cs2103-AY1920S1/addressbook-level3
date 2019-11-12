package seedu.eatme.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.eatme.model.Model.PREDICATE_SHOW_ALL_EATERIES;
import static seedu.eatme.testutil.Assert.assertThrows;
import static seedu.eatme.testutil.TypicalEateries.POPEYES;
import static seedu.eatme.testutil.TypicalEateries.TEXAS;
import static seedu.eatme.testutil.TypicalFeeds.getTypicalFeedList;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.eatme.commons.core.GuiSettings;
import seedu.eatme.model.eatery.NameContainsKeywordsPredicate;
import seedu.eatme.testutil.EateryListBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new EateryList(), new EateryList(modelManager.getEateryList()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setEateryListFilePath(Paths.get("eatery/list/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setEateryListFilePath(Paths.get("new/eatery/list/file/path"));
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
    public void setEateryListFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setEateryListFilePath(null));
    }

    @Test
    public void setEateryListFilePath_validPath_setsEateryListFilePath() {
        Path path = Paths.get("eatery/list/file/path");
        modelManager.setEateryListFilePath(path);
        assertEquals(path, modelManager.getEateryListFilePath());
    }

    @Test
    public void hasEatery_nullEatery_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasEatery(null));
    }

    @Test
    public void hasEatery_eateryNotInEateryList_returnsFalse() {
        assertFalse(modelManager.hasEatery(POPEYES));
    }

    @Test
    public void hasEatery_eateryInEateryList_returnsTrue() {
        modelManager.addEatery(POPEYES);
        assertTrue(modelManager.hasEatery(POPEYES));
    }

    @Test
    public void getFilteredEateryList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredEateryList().remove(0));
    }

    @Test
    public void equals() {
        EateryList eateryList = new EateryListBuilder().withEatery(POPEYES).withEatery(TEXAS).build();
        EateryList differentEateryList = new EateryList();
        FeedList feedList = getTypicalFeedList();
        FeedList differentFeedList = new FeedList();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(eateryList, feedList, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(eateryList, feedList, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different eateryList -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentEateryList, feedList, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = POPEYES.getName().fullName.split("\\s+");
        modelManager.updateFilteredEateryList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(eateryList, feedList, userPrefs)));

        // different feedList -> returns false
        assertFalse(modelManager.equals(new ModelManager(eateryList, differentFeedList, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredEateryList(PREDICATE_SHOW_ALL_EATERIES);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setEateryListFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(eateryList, feedList, differentUserPrefs)));
    }
}
