package seedu.moolah.ui;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.Collections;
import java.util.Timer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.testfx.api.FxToolkit;

import guitests.guihandles.HelpWindowHandle;
import guitests.guihandles.MainWindowHandle;
import seedu.moolah.logic.Logic;
import seedu.moolah.logic.LogicManager;
import seedu.moolah.model.ModelManager;
import seedu.moolah.model.Timekeeper;
import seedu.moolah.storage.JsonMooLahStorage;
import seedu.moolah.storage.JsonUserPrefsStorage;
import seedu.moolah.storage.Storage;
import seedu.moolah.storage.StorageManager;

/**
 * Contains tests for {@code MainWindow}.
 *
 * Sourced from (with modifications):
 * https://github.com/se-edu/addressbook-level4/blob/master/src/test/java/seedu/address/ui/MainWindowCloseTest.java
 *
 */
public class MainWindowTest extends GuiUnitTest {
    @TempDir
    public Path temporaryFolder;

    private MainWindow mainWindow;
    private MainWindowHandle mainWindowHandle;

    @BeforeEach
    public void setUp() throws Exception {
        JsonMooLahStorage jsonMooLahStorage =
                new JsonMooLahStorage(temporaryFolder.resolve("moolah.json"));
        JsonUserPrefsStorage jsonUserPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        FxToolkit.setupStage(stage -> {
            Storage storage = new StorageManager(jsonMooLahStorage, jsonUserPrefsStorage);
            Logic logic = new LogicManager(new ModelManager(), storage);
            mainWindow = new MainWindow(stage, logic, new Timekeeper(logic), new Timer());
            mainWindow.fillInnerParts();
            mainWindowHandle = new MainWindowHandle(mainWindow.getPrimaryStage());
            mainWindowHandle.focus();
        });
        FxToolkit.showStage();
    }

    @Test
    public void close_menuBarExitButton_allWindowsClosed() {
        assertDoesNotThrow(() -> mainWindowHandle.getMainMenu().exitViaMenu());
        // The application will exit when all windows are closed.
        assertEquals(Collections.emptyList(), guiRobot.listWindows());
    }

    @Test
    void help() {
        mainWindowHandle.getMainMenu().openHelpWindowUsingAccelerator();
        assertTrue(HelpWindowHandle.isWindowPresent());
    }

    @Test
    void close_externalRequest_exitAppRequestEventPosted() {
        assertDoesNotThrow(() -> mainWindowHandle.getMainMenu().openHelpWindowUsingMenu());
        assertTrue(HelpWindowHandle.isWindowPresent());
        mainWindowHandle.closeMainWindowExternally();
        // The application will exit when all windows are closed.
        assertEquals(Collections.emptyList(), guiRobot.listWindows());
    }
}
