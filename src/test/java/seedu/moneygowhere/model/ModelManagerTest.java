package seedu.moneygowhere.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.moneygowhere.model.Model.PREDICATE_SHOW_ALL_SPENDINGS;
import static seedu.moneygowhere.testutil.Assert.assertThrows;
import static seedu.moneygowhere.testutil.TypicalSpendings.APPLE;
import static seedu.moneygowhere.testutil.TypicalSpendings.BANANA;
import static seedu.moneygowhere.testutil.TypicalSpendings.BILL_REMINDER;
import static seedu.moneygowhere.testutil.TypicalSpendings.CATFOOD;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.moneygowhere.commons.core.GuiSettings;
import seedu.moneygowhere.model.spending.NameContainsKeywordsPredicate;
import seedu.moneygowhere.model.spending.Spending;
import seedu.moneygowhere.testutil.SpendingBookBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new SpendingBook(), new SpendingBook(modelManager.getSpendingBook()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setSpendingBookFilePath(Paths.get("spending/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setSpendingBookFilePath(Paths.get("new/spending/book/file/path"));
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
        assertThrows(NullPointerException.class, () -> modelManager.setSpendingBookFilePath(null));
    }

    @Test
    public void setAddressBookFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("spending/book/file/path");
        modelManager.setSpendingBookFilePath(path);
        assertEquals(path, modelManager.getSpendingBookFilePath());
    }

    @Test
    public void hasSpending_nullSpending_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasSpending(null));
    }

    @Test
    public void hasSpending_spendingNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasSpending(APPLE));
    }

    @Test
    public void hasSpending_spendingInAddressBook_returnsTrue() {
        modelManager.addSpending(APPLE);
        assertTrue(modelManager.hasSpending(APPLE));
    }

    @Test
    public void hasMultipleSpending_spendingInAddressBook_returnsTrue() {
        List<Spending> spendings = new ArrayList<>(Arrays.asList(APPLE, BANANA, CATFOOD));
        modelManager.addSpending(spendings);
        assertTrue(modelManager.hasSpending(APPLE));
        assertTrue(modelManager.hasSpending(BANANA));
        assertTrue(modelManager.hasSpending(CATFOOD));
    }

    @Test
    public void hasReminder_nullReminder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasReminder(null));
    }

    @Test
    public void hasReminder_reminderNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasReminder(BILL_REMINDER));
    }

    @Test
    public void hasReminder_reminderInAddressBook_returnsTrue() {
        modelManager.addReminder(BILL_REMINDER);
        assertTrue(modelManager.hasReminder(BILL_REMINDER));
    }

    @Test
    public void getFilteredSpendingList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredSpendingList().remove(0));
    }

    @Test
    public void equals() {
        SpendingBook spendingBook = new SpendingBookBuilder().withSpending(APPLE).withSpending(BANANA).build();
        SpendingBook differentSpendingBook = new SpendingBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(spendingBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(spendingBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different spendingBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentSpendingBook, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = APPLE.getName().fullName.split("\\s+");
        modelManager.updateFilteredSpendingList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(spendingBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredSpendingList(PREDICATE_SHOW_ALL_SPENDINGS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setSpendingBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(spendingBook, differentUserPrefs)));
    }
}
