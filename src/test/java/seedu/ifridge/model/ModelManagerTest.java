package seedu.ifridge.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ifridge.model.Model.PREDICATE_SHOW_ALL_GROCERY_ITEMS;
import static seedu.ifridge.testutil.Assert.assertThrows;
import static seedu.ifridge.testutil.TypicalBoughtList.APPLE;
import static seedu.ifridge.testutil.TypicalBoughtList.EGG;
import static seedu.ifridge.testutil.TypicalGroceryItems.BANANA;
import static seedu.ifridge.testutil.TypicalGroceryItems.SPAGHETTI;
import static seedu.ifridge.testutil.TypicalShoppingList.CAKE;
import static seedu.ifridge.testutil.TypicalShoppingList.DATES;
import static seedu.ifridge.testutil.TypicalTemplateList.BIRTHDAY_PARTY;
import static seedu.ifridge.testutil.TypicalTemplateList.DIET_PLAN;
import static seedu.ifridge.testutil.TypicalUnitDictionary.getTypicalUnitDictionary;
import static seedu.ifridge.testutil.TypicalWasteArchive.WASTE_LIST_CURRENT_MONTH;
import static seedu.ifridge.testutil.TypicalWasteArchive.WASTE_LIST_LAST_MONTH;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeMap;

import org.junit.jupiter.api.Test;

import seedu.ifridge.commons.core.GuiSettings;
import seedu.ifridge.model.food.NameContainsKeywordsPredicate;
import seedu.ifridge.model.waste.WasteMonth;
import seedu.ifridge.testutil.GroceryListBuilder;
import seedu.ifridge.testutil.ShoppingListBuilder;
import seedu.ifridge.testutil.TemplateListBuilder;
import seedu.ifridge.testutil.WasteArchiveBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new GroceryList(), new GroceryList(modelManager.getGroceryList()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setGroceryListFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setGroceryListFilePath(Paths.get("new/address/book/file/path"));
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
    public void setGroceryListFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGroceryListFilePath(null));
    }

    @Test
    public void setGroceryListFilePath_validPath_setsGroceryListFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setGroceryListFilePath(path);
        assertEquals(path, modelManager.getGroceryListFilePath());
    }

    @Test
    public void hasGroceryItem_nullGroceryItem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasGroceryItem(null));
    }

    /*@Test
    public void hasGroceryItem_personNotInGroceryList_returnsFalse() {
        assertFalse(modelManager.hasGroceryItem(ALICE));
    }*/

    /*@Test
    public void hasGroceryItem_personInGroceryList_returnsTrue() {
        modelManager.addGroceryItem(ALICE);
        assertTrue(modelManager.hasGroceryItem(ALICE));
    }*/

    @Test
    public void getFilteredGroceryItemList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredGroceryItemList().remove(0));
    }

    @Test
    public void equals() {
        GroceryList groceryList = new GroceryListBuilder().withGroceryItem(BANANA).withGroceryItem(SPAGHETTI).build();
        GroceryList differentGroceryList = new GroceryList();
        TemplateList templateList = new TemplateListBuilder().withTemplateItem(DIET_PLAN)
                .withTemplateItem(BIRTHDAY_PARTY).build();
        TemplateList differentTemplateList = new TemplateList();
        TreeMap<WasteMonth, WasteList> wasteArchive = new WasteArchiveBuilder()
                .withWasteList(WASTE_LIST_CURRENT_MONTH)
                .withWasteList(WASTE_LIST_LAST_MONTH).build();
        TreeMap<WasteMonth, WasteList> differentWasteArchive = new TreeMap<>();
        ShoppingList shoppingList = new ShoppingListBuilder().withShoppingItem(CAKE).withShoppingItem(DATES).build();
        ShoppingList differentShoppingList = new ShoppingList();
        GroceryList boughtList = new GroceryListBuilder().withGroceryItem(APPLE).withGroceryItem(EGG).build();
        GroceryList differentBoughtList = new GroceryList();
        UnitDictionary unitDictionary = getTypicalUnitDictionary();
        UnitDictionary differentUnitDictionary = new UnitDictionary(new HashMap<>());

        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(groceryList, userPrefs, templateList, wasteArchive, shoppingList,
                boughtList, unitDictionary);
        ModelManager modelManagerCopy = new ModelManager(groceryList, userPrefs, templateList,
                wasteArchive, shoppingList, boughtList, unitDictionary);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different groceryList -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentGroceryList, userPrefs, templateList, wasteArchive,
                shoppingList, boughtList, unitDictionary)));

        // different templateList -> returns false
        assertFalse(modelManager.equals(new ModelManager(groceryList, userPrefs, differentTemplateList,
                differentWasteArchive, shoppingList, boughtList, unitDictionary)));

        // different shoppingList -> returns false
        assertFalse(modelManager.equals(new ModelManager(groceryList, userPrefs, templateList,
                wasteArchive, differentShoppingList, boughtList, unitDictionary)));

        // different boughtList -> returns false
        assertFalse(modelManager.equals(new ModelManager(groceryList, userPrefs, templateList, wasteArchive,
                shoppingList, differentBoughtList, unitDictionary)));

        // different unitDictionary -> returns false
        assertFalse(modelManager.equals(new ModelManager(groceryList, userPrefs, templateList, wasteArchive,
                shoppingList, boughtList, differentUnitDictionary)));
        // different filteredList -> returns false
        String[] keywords = BANANA.getName().fullName.split("\\s+");
        modelManager.updateFilteredGroceryItemList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(groceryList, userPrefs, templateList, wasteArchive,
                shoppingList, boughtList, unitDictionary)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredGroceryItemList(PREDICATE_SHOW_ALL_GROCERY_ITEMS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setGroceryListFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(groceryList, differentUserPrefs, templateList, wasteArchive,
                shoppingList, boughtList, unitDictionary)));
    }
}
