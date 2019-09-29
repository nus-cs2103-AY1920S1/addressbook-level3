package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalBooks.BOOK_1;
import static seedu.address.testutil.TypicalBooks.BOOK_2;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.book.TitleContainsKeywordPredicate;
import seedu.address.testutil.CatalogBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new Catalog(), new Catalog(modelManager.getCatalog()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setCatalogFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setCatalogFilePath(Paths.get("new/address/book/file/path"));
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
    public void setCatalogFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setCatalogFilePath(null));
    }

    @Test
    public void setCatalogFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setCatalogFilePath(path);
        assertEquals(path, modelManager.getCatalogFilePath());
    }

    @Test
    public void hasBook_nullBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasBook(null));
    }

    @Test
    public void hasBook_bookNotInCatalog_returnsFalse() {
        assertFalse(modelManager.hasBook(BOOK_1));
    }

    @Test
    public void hasBook_bookInCatalog_returnsTrue() {
        modelManager.addBook(BOOK_1);
        assertTrue(modelManager.hasBook(BOOK_1));
    }

    @Test
    public void getFilteredBookList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredBookList().remove(0));
    }

    @Test
    public void equals() {
        Catalog catalog = new CatalogBuilder().withPerson(BOOK_1).withPerson(BOOK_2).build();
        Catalog differentCatalog = new Catalog();
        UserPrefs userPrefs = new UserPrefs();
        LoanRecords loanRecords = new LoanRecords();
        BorrowerRecords borrowerRecords = new BorrowerRecords();

        // same values -> returns true
        modelManager = new ModelManager(catalog, loanRecords, borrowerRecords, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(catalog, loanRecords, borrowerRecords, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentCatalog, loanRecords, borrowerRecords, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = BOOK_1.getTitle().value.split("\\s+");
        modelManager.updateFilteredBookList(new TitleContainsKeywordPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(differentCatalog, loanRecords, borrowerRecords, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredBookList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setCatalogFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(catalog, loanRecords, borrowerRecords, differentUserPrefs)));
    }
}
