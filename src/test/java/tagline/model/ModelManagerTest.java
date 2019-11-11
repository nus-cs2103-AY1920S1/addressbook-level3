package tagline.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tagline.model.contact.ContactModel.PREDICATE_SHOW_ALL_CONTACTS;
import static tagline.testutil.Assert.assertThrows;
import static tagline.testutil.contact.TypicalContacts.ALICE;
import static tagline.testutil.contact.TypicalContacts.BENSON;
import static tagline.testutil.group.TypicalGroups.ASGARDIAN;
import static tagline.testutil.group.TypicalGroups.MYSTIC_ARTS;
import static tagline.testutil.note.TypicalNotes.EARTH;
import static tagline.testutil.note.TypicalNotes.TOKYO;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import tagline.commons.core.GuiSettings;
import tagline.model.contact.AddressBook;
import tagline.model.contact.NameContainsKeywordsPredicate;
import tagline.model.group.GroupBook;
import tagline.model.note.NoteBook;
import tagline.model.tag.TagBook;
import tagline.testutil.contact.AddressBookBuilder;
import tagline.testutil.group.GroupBookBuilder;
import tagline.testutil.note.NoteBookBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new NoteBook(), modelManager.getNoteBook());
        assertEquals(new AddressBook(), new AddressBook(modelManager.getAddressBook()));
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
    public void setNoteBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setNoteBookFilePath(null));
    }

    @Test
    public void setNoteBookFilePath_validPath_setsNoteBookFilePath() {
        Path path = Paths.get("note/book/file/path");
        modelManager.setNoteBookFilePath(path);
        assertEquals(path, modelManager.getNoteBookFilePath());
    }

    @Test
    public void setGroupBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGroupBookFilePath(null));
    }

    @Test
    public void setGroupBookFilePath_validPath_setsGroupBookFilePath() {
        Path path = Paths.get("group/book/file/path");
        modelManager.setGroupBookFilePath(path);
        assertEquals(path, modelManager.getGroupBookFilePath());
    }

    @Test
    public void hasContact_nullContact_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasContact(null));
    }

    @Test
    public void hasContact_contactNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasContact(ALICE));
    }

    @Test
    public void hasContact_contactInAddressBook_returnsTrue() {
        modelManager.addContact(ALICE);
        assertTrue(modelManager.hasContact(ALICE));
    }

    @Test
    public void getFilteredContactList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredContactList().remove(0));
    }

    @Test
    public void hasNote_noteNotInNoteBook_returnsFalse() {
        assertFalse(modelManager.hasNote(TOKYO));
    }

    @Test
    public void hasNote_noteInNoteBook_returnsTrue() {
        modelManager.addNote(TOKYO);
        assertTrue(modelManager.hasNote(TOKYO));
    }

    @Test
    public void getFilteredNoteList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredNoteList().remove(0));
    }

    @Test
    public void hasGroup_groupNotInGroupBook_returnsFalse() {
        assertFalse(modelManager.hasGroup(MYSTIC_ARTS));
    }

    @Test
    public void hasGroup_groupInGroupBook_returnsTrue() {
        modelManager.addGroup(MYSTIC_ARTS);
        assertTrue(modelManager.hasGroup(MYSTIC_ARTS));
    }

    @Test
    public void getFilteredGroupList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredGroupList().remove(0));
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withContact(ALICE).withContact(BENSON).build();
        AddressBook differentAddressBook = new AddressBook();
        NoteBook noteBook = new NoteBookBuilder().withNote(TOKYO).withNote(EARTH).build();
        NoteBook differentNoteBook = new NoteBook();
        GroupBook groupBook = new GroupBookBuilder().withGroup(ASGARDIAN).withGroup(MYSTIC_ARTS).build();
        GroupBook differentGroupBook = new GroupBook();
        TagBook tagBook = new TagBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(addressBook, noteBook, groupBook, tagBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, noteBook, groupBook, tagBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(
            new ModelManager(differentAddressBook, noteBook, groupBook, tagBook, userPrefs)));

        // different noteBook -> returns false
        assertFalse(modelManager.equals(
            new ModelManager(addressBook, differentNoteBook, groupBook, tagBook, userPrefs)));

        // different groupBook -> returns false
        assertFalse(modelManager.equals(
                new ModelManager(addressBook, noteBook, differentGroupBook, tagBook, userPrefs)));

        // different filteredContactList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredContactList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, noteBook, groupBook, tagBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredContactList(PREDICATE_SHOW_ALL_CONTACTS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(addressBook, noteBook, groupBook, tagBook,
                differentUserPrefs)));
    }
}
