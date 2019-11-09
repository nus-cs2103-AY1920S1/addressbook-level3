package io.xpire.model;

import static io.xpire.model.ListType.XPIRE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.xpire.commons.core.GuiSettings;
import io.xpire.model.item.ContainsKeywordsPredicate;
import io.xpire.testutil.Assert;
import io.xpire.testutil.ExpiryDateTrackerBuilder;
import io.xpire.testutil.ReplenishListBuilder;
import io.xpire.testutil.TypicalItems;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), this.modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), this.modelManager.getGuiSettings());
        assertEquals(new Xpire(), new Xpire(this.modelManager.getXpire()));
        assertEquals(new ReplenishList(), new ReplenishList(this.modelManager.getReplenishList()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> this.modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setListFilePath(Paths.get("xpire/io/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        this.modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, this.modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setListFilePath(Paths.get("new/xpire/io/file/path"));
        assertEquals(oldUserPrefs, this.modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> this.modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        this.modelManager.setGuiSettings(guiSettings);
        Assertions.assertEquals(guiSettings, this.modelManager.getGuiSettings());
    }

    @Test
    public void setExpiryDateTrackerFilePath_nullPath_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> this.modelManager.setListFilePath(null));
    }

    @Test
    public void setExpiryDateTrackerFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("xpire/io/file/path");
        this.modelManager.setListFilePath(path);
        assertEquals(path, this.modelManager.getListFilePath());
    }

    @Test
    public void hasItem_nullItem_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> this.modelManager.hasItem(XPIRE, null));
    }

    @Test
    public void hasItem_itemNotInAddressBook_returnsFalse() {
        assertFalse(this.modelManager.hasItem(XPIRE, TypicalItems.KIWI));
    }

    @Test
    public void hasItem_itemInAddressBook_returnsTrue() {
        this.modelManager.addItem(XPIRE, TypicalItems.KIWI);
        assertTrue(this.modelManager.hasItem(XPIRE, TypicalItems.KIWI));
    }

    @Test
    public void getFilteredItemList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, () -> this.modelManager
                .getCurrentList().remove(0));
    }

    @Test
    public void equals() {

        Xpire xpire = new ExpiryDateTrackerBuilder()
                .withItem(TypicalItems.KIWI).withItem(TypicalItems.BANANA).build();
        ReplenishList replenishList = new ReplenishListBuilder()
                .withItem(TypicalItems.BAGEL).withItem(TypicalItems.CHOCOLATE).build();

        ReadOnlyListView[] lists = new ReadOnlyListView[]{xpire, replenishList};

        Xpire differentXpire = new Xpire();
        ReplenishList differentReplenishList = new ReplenishList();

        ReadOnlyListView[] differentLists = new ReadOnlyListView[]{differentXpire,
            differentReplenishList};
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        this.modelManager = new ModelManager(lists, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(lists, userPrefs);
        assertEquals(this.modelManager.getReplenishList(), modelManagerCopy.getReplenishList());
        assertEquals(this.modelManager, modelManagerCopy);

        // same object -> returns true
        assertEquals(this.modelManager, this.modelManager);

        // null -> returns false
        assertNotEquals(null, this.modelManager);

        // different types -> returns false
        assertNotEquals(5, this.modelManager);

        // different xpire -> returns false
        assertNotEquals(this.modelManager, new ModelManager(differentLists, userPrefs));

        // different filteredList -> returns false
        String[] keywords = TypicalItems.KIWI.getName().toString().split("\\s+");
        this.modelManager.filterCurrentList(XPIRE, new ContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertNotEquals(this.modelManager, new ModelManager(lists, userPrefs));

        // resets modelManager to initial state for upcoming tests
        this.modelManager.setCurrentList(XPIRE);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setListFilePath(Paths.get("differentFilePath"));
        assertNotEquals(this.modelManager, new ModelManager(lists, differentUserPrefs));
    }
}
