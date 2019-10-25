package seedu.ifridge.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.ifridge.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.ifridge.commons.core.GuiSettings;

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
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGroceryListFilePath(null));
    }

    @Test
    public void setAddressBookFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setGroceryListFilePath(path);
        assertEquals(path, modelManager.getGroceryListFilePath());
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasGroceryItem(null));
    }

    /*@Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasGroceryItem(ALICE));
    }*/

    /*@Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        modelManager.addGroceryItem(ALICE);
        assertTrue(modelManager.hasGroceryItem(ALICE));
    }*/

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredGroceryItemList().remove(0));
    }

    /*@Test
    public void equals() {
        GroceryList groceryList = new GroceryListBuilder().withPerson(ALICE).withPerson(BENSON).build();
        GroceryList differentGroceryList = new GroceryList();
        TemplateList templateList = new TemplateListBuilder().withTemplateItem(DIET_PLAN)
                .withTemplateItem(BIRTHDAY_PARTY).build();
        TemplateList differentTemplateList = new TemplateList();
        TreeMap<WasteMonth, WasteList> wasteArchive = new WasteArchiveBuilder()
                .withWasteList(CURRENT_WASTE_LIST)
                .withWasteList(LAST_MONTH_WASTE_LIST).build();
        TreeMap<WasteMonth, WasteList> differentWasteArchive = new TreeMap<>();
        ShoppingList shoppingList = new ShoppingListBuilder().withShoppingItem(CAKE).withShoppingItem(DATES).build();
        ShoppingList differentShoppingList = new ShoppingList();
        GroceryList boughtList = new GroceryListBuilder().withPerson(ALICE).withPerson(BENSON).build();
        GroceryList differentBoughtList = new GroceryList();

        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(groceryList, userPrefs, templateList, wasteArchive, shoppingList, boughtList);
        ModelManager modelManagerCopy = new ModelManager(groceryList, userPrefs, templateList,
                wasteArchive, shoppingList, boughtList);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different groceryList -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentGroceryList, userPrefs, templateList, wasteArchive,
                shoppingList, boughtList)));

        // different templateList -> returns false
        assertFalse(modelManager.equals(new ModelManager(groceryList, userPrefs, differentTemplateList,
                differentWasteArchive, shoppingList, boughtList)));

        // different shoppingList -> returns false
        assertFalse(modelManager.equals(new ModelManager(groceryList, userPrefs, templateList,
                wasteArchive, differentShoppingList, boughtList)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredGroceryItemList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(groceryList, userPrefs, templateList, wasteArchive,
                shoppingList, boughtList)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredGroceryItemList(PREDICATE_SHOW_ALL_GROCERY_ITEMS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setGroceryListFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(groceryList, differentUserPrefs, templateList, wasteArchive,
                shoppingList, boughtList)));
    }*/
}
