package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalGroceryItems.ALICE;
import static seedu.address.testutil.TypicalGroceryItems.BENSON;
import static seedu.address.testutil.TypicalShoppingList.CAKE;
import static seedu.address.testutil.TypicalShoppingList.DATES;
import static seedu.address.testutil.TypicalTemplateList.BIRTHDAY_PARTY;
import static seedu.address.testutil.TypicalTemplateList.DIET_PLAN;
import static seedu.address.testutil.TypicalWasteArchive.CURRENT_WASTE_LIST;
import static seedu.address.testutil.TypicalWasteArchive.LAST_MONTH_WASTE_LIST;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.TreeMap;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.food.NameContainsKeywordsPredicate;
import seedu.address.model.waste.WasteMonth;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.ShoppingListBuilder;
import seedu.address.testutil.TemplateListBuilder;
import seedu.address.testutil.WasteArchiveBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new AddressBook(), new AddressBook(modelManager.getGroceryList()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setAddressBookFilePath(Paths.get("new/address/book/file/path"));
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
        assertThrows(NullPointerException.class, () -> modelManager.setAddressBookFilePath(null));
    }

    @Test
    public void setAddressBookFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setAddressBookFilePath(path);
        assertEquals(path, modelManager.getAddressBookFilePath());
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasGroceryItem(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasGroceryItem(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        modelManager.addGroceryItem(ALICE);
        assertTrue(modelManager.hasGroceryItem(ALICE));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredGroceryItemList().remove(0));
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        AddressBook differentAddressBook = new AddressBook();
        TemplateList templateList = new TemplateListBuilder().withTemplateItem(DIET_PLAN)
                .withTemplateItem(BIRTHDAY_PARTY).build();
        TemplateList differentTemplateList = new TemplateList();
        TreeMap<WasteMonth, WasteList> wasteArchive = new WasteArchiveBuilder()
                .withWasteList(CURRENT_WASTE_LIST)
                .withWasteList(LAST_MONTH_WASTE_LIST).build();
        TreeMap<WasteMonth, WasteList> differentWasteArchive = new TreeMap<>();
        ShoppingList shoppingList = new ShoppingListBuilder().withShoppingItem(CAKE).withShoppingItem(DATES).build();
        ShoppingList differentShoppingList = new ShoppingList();

        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(addressBook, userPrefs, templateList, wasteArchive, shoppingList);
        ModelManager modelManagerCopy = new ModelManager(addressBook, userPrefs, templateList,
                wasteArchive, shoppingList);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, userPrefs, templateList, wasteArchive,
                differentShoppingList)));

        // different templateList -> returns false
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs, differentTemplateList,
                differentWasteArchive, differentShoppingList)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredGroceryItemList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs, templateList, wasteArchive,
                shoppingList)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredGroceryItemList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(addressBook, differentUserPrefs, templateList, wasteArchive,
                shoppingList)));
    }
}
