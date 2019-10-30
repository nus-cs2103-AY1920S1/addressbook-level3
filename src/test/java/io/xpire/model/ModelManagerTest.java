package io.xpire.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.xpire.commons.core.GuiSettings;
import io.xpire.model.item.ContainsKeywordsPredicate;
import io.xpire.model.item.Item;
import io.xpire.testutil.Assert;
import io.xpire.testutil.ExpiryDateTrackerBuilder;
import io.xpire.testutil.ReplenishListBuilder;
import io.xpire.testutil.TypicalItems;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        Assertions.assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new Xpire(), new Xpire(modelManager.getXpire()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setListFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setListFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        Assertions.assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setExpiryDateTrackerFilePath_nullPath_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> modelManager.setListFilePath(null));
    }

    @Test
    public void setExpiryDateTrackerFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setListFilePath(path);
        assertEquals(path, modelManager.getListFilePath());
    }

    @Test
    public void hasItem_nullItem_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> modelManager.hasItem(null));
    }

    @Test
    public void hasItem_itemNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasItem(TypicalItems.KIWI));
    }

    @Test
    public void hasItem_itemInAddressBook_returnsTrue() {
        modelManager.addItem(TypicalItems.KIWI);
        assertTrue(modelManager.hasItem(TypicalItems.KIWI));
    }

    @Test
    public void getFilteredItemList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, () -> modelManager
                .getFilteredXpireItemList().remove(0));
    }

    @Test
    public void equals() {

        Xpire xpire = new ExpiryDateTrackerBuilder()
                .withItem(TypicalItems.KIWI).withItem(TypicalItems.BANANA).build();
        ReplenishList replenishList = new ReplenishListBuilder()
                .withItem(TypicalItems.BAGEL).withItem(TypicalItems.CHOCOLATE).build();
        ReadOnlyListView<? extends Item>[] lists = new ReadOnlyListView[]{xpire, replenishList};

        Xpire differentXpire = new Xpire();
        ReplenishList differentReplenishList = new ReplenishList();
        ReadOnlyListView<? extends Item>[] differentLists = new ReadOnlyListView[]{differentXpire,
            differentReplenishList};
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(lists, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(lists, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentLists, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = TypicalItems.KIWI.getName().toString().split("\\s+");
        modelManager.updateFilteredItemList(new ContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(lists, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredItemList(Model.PREDICATE_SHOW_ALL_ITEMS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setListFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(lists, differentUserPrefs)));
    }
}
