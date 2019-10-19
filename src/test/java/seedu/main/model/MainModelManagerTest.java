package seedu.main.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;

public class MainModelManagerTest {

    private MainModelManager mainModelManager = new MainModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), mainModelManager.getUserPrefs());
        assertEquals(new GuiSettings(), mainModelManager.getGuiSettings());
    }

    @Test
    public void getUserPrefs_success() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(Paths.get("address/book/file/path"));
        assertEquals(userPrefs, new MainModelManager(userPrefs).getUserPrefs());
    }

    @Test
    public void getGuiSettings_success() {
        GuiSettings guiSettings = new GuiSettings(100, 200, 150, 50);
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setGuiSettings(guiSettings);
        assertEquals(guiSettings, new MainModelManager(userPrefs).getGuiSettings());
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> mainModelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        mainModelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, mainModelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setAddressBookFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, mainModelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> mainModelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        mainModelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, mainModelManager.getGuiSettings());
    }

}
