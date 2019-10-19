package seedu.main.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.core.GuiSettings;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;
import seedu.main.model.MainModel;
import seedu.main.model.MainModelManager;
import seedu.main.model.UserPrefs;

public class MainLogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    @TempDir
    public Path temporaryFolder;

    private MainModel mainModel;
    private Storage storage;
    private MainLogic mainLogic;

    @BeforeEach
    public void setUp() {
        JsonAddressBookStorage addressBookStorage =
                new JsonAddressBookStorage(temporaryFolder.resolve("addressBook.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        storage = new StorageManager(addressBookStorage, userPrefsStorage);
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setGuiSettings(new GuiSettings(10, 40, 30, 20));
        mainModel = new MainModelManager(userPrefs);
        mainLogic = new MainLogicManager(mainModel, storage);
    }

    @Test
    public void getGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new MainLogicManager(null, storage).getGuiSettings());
    }

    @Test
    public void getGuiSettings_equal() {
        assertEquals(
                new GuiSettings(10, 40, 30, 20), mainLogic.getGuiSettings());
    }

    @Test
    public void setGuiSettings_equal() {
        mainLogic.setGuiSettings(new GuiSettings(100, 200, 300, 400));
        assertEquals(new GuiSettings(100, 200, 300, 400),
                mainLogic.getGuiSettings());
    }
}
