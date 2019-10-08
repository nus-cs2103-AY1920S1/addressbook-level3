package calofit.model;

import calofit.commons.core.GuiSettings;
import calofit.model.dish.DishDatabase;
import calofit.model.dish.NameContainsKeywordsPredicate;
import calofit.testutil.DishDatabaseBuilder;
import calofit.testutil.Assert;
import calofit.testutil.TypicalDishes;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import static calofit.model.Model.PREDICATE_SHOW_ALL_DISHES;
import static org.junit.jupiter.api.Assertions.*;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new DishDatabase(), new DishDatabase(modelManager.getDishDatabase()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setDishDatabaseFilePath(Paths.get("dishdb/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setDishDatabaseFilePath(Paths.get("new/dishdb/file/path"));
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
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setDishDatabaseFilePath_nullPath_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> modelManager.setDishDatabaseFilePath(null));
    }

    @Test
    public void setDishDatabaseFilePath_validPath_setsDishDatabaseFilePath() {
        Path path = Paths.get("dishdb/file/path");
        modelManager.setDishDatabaseFilePath(path);
        assertEquals(path, modelManager.getDishDatabaseFilePath());
    }

    @Test
    public void hasDish_nullDish_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> modelManager.hasDish(null));
    }

    @Test
    public void hasDish_dishNotInDishDatabase_returnsFalse() {
        assertFalse(modelManager.hasDish(TypicalDishes.ALICE));
    }

    @Test
    public void hasDish_dishInDishDatabase_returnsTrue() {
        modelManager.addDish(TypicalDishes.ALICE);
        assertTrue(modelManager.hasDish(TypicalDishes.ALICE));
    }

    @Test
    public void getFilteredDishList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredDishList().remove(0));
    }

    @Test
    public void equals() {
        DishDatabase dishDatabase = new DishDatabaseBuilder().withDish(TypicalDishes.ALICE).withDish(TypicalDishes.BENSON).build();
        DishDatabase differentDishDatabase = new DishDatabase();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(dishDatabase, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(dishDatabase, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different dishDatabase -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentDishDatabase, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = TypicalDishes.ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredDishList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(dishDatabase, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredDishList(PREDICATE_SHOW_ALL_DISHES);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setDishDatabaseFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(dishDatabase, differentUserPrefs)));
    }
}
