package seedu.address.main.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.address.logic.AddressBookLogic;
import seedu.address.address.model.AddressBookModel;
import seedu.address.address.model.AddressBookModelManager;
import seedu.address.address.storage.JsonAddressBookStorage;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.LogicManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.UserPrefsModel;
import seedu.address.model.UserPrefsModelManager;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.StorageManager;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.PersonBuilder;

public class LogicManagerTest {
    @TempDir
    public Path temporaryFolder;

    private StorageManager storage;
    private Model model;
    private LogicManager logicManager;

    @BeforeEach
    public void setUp() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setGuiSettings(new GuiSettings(90, 45, 30, 15));
        userPrefs.setAddressBookFilePath(Paths.get("addressbook.json"));
        seedu.address.address.model.AddressBook addressBook = new AddressBookBuilder().withPerson(new PersonBuilder().build()).build();
        AddressBookModel addressBookModel = new AddressBookModelManager(addressBook, userPrefs);
        UserPrefsModel userPrefsModel = new UserPrefsModelManager(userPrefs);

        model = new ModelManager(userPrefsModel, addressBookModel);
        JsonAddressBookStorage addressBookStorage =
                new JsonAddressBookStorage(temporaryFolder.resolve("addressbook.json"));
        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        storage = new StorageManager(addressBookStorage, userPrefsStorage);
        logicManager = new LogicManager(model, storage);
    }

    @Test
    public void getStorage_throwsNullPointerException() {
        assertEquals(null, new LogicManager(model, null).getStorage());
    }

    @Test
    public void getAddressBookLogic_equals() {
        //no changes to address book logic
        AddressBookLogic addressBookLogic = logicManager.getAddressBookLogic();
        assertEquals(Paths.get("addressbook.json"), logicManager.getAddressBookLogic().getAddressBookFilePath());

        assertEquals(1, logicManager.getAddressBookLogic().getFilteredPersonList().size());
        assertEquals(new AddressBookBuilder().withPerson(new PersonBuilder().build()).build(),
                logicManager.getAddressBookLogic().getAddressBook());
    }

    @Test
    public void getStorage_equals() {
        assertEquals(storage, logicManager.getStorage());
    }

    @Test
    public void getGuiSettings_equals() {
        assertEquals(new GuiSettings(90, 45, 30, 15),
                logicManager.getGuiSettings());
    }

    @Test
    public void setGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                logicManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_equal() {
        logicManager.setGuiSettings(new GuiSettings(100, 200, 300, 400));
        assertEquals(new GuiSettings(100, 200, 300, 400),
                logicManager.getGuiSettings());
    }
}
