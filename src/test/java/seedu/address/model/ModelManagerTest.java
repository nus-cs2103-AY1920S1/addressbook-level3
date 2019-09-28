package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_MEMES;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalMemes.ALICE;
import static seedu.address.testutil.TypicalMemes.BENSON;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.meme.NameContainsKeywordsPredicate;
import seedu.address.testutil.AddressBookBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new MemeBook(), new MemeBook(modelManager.getMemeBook()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setMemeBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setMemeBookFilePath(Paths.get("new/address/book/file/path"));
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
        assertThrows(NullPointerException.class, () -> modelManager.setMemeBookFilePath(null));
    }

    @Test
    public void setAddressBookFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setMemeBookFilePath(path);
        assertEquals(path, modelManager.getMemeBookFilePath());
    }

    @Test
    public void hasMeme_nullMeme_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasMeme(null));
    }

    @Test
    public void hasMeme_memeNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasMeme(ALICE));
    }

    @Test
    public void hasMeme_memeInAddressBook_returnsTrue() {
        modelManager.addMeme(ALICE);
        assertTrue(modelManager.hasMeme(ALICE));
    }

    @Test
    public void getFilteredMemeList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredMemeList().remove(0));
    }

    @Test
    public void equals() {
        MemeBook memeBook = new AddressBookBuilder().withMeme(ALICE).withMeme(BENSON).build();
        MemeBook differentMemeBook = new MemeBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(memeBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(memeBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different memeBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentMemeBook, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredMemeList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(memeBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredMemeList(PREDICATE_SHOW_ALL_MEMES);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setMemeBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(memeBook, differentUserPrefs)));
    }
}
