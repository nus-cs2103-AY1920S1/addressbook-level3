package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.testfx.api.FxToolkit;

import guitests.guihandles.HelpWindowHandle;
import guitests.guihandles.StageHandle;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import seedu.address.logic.LogicManager;
import seedu.address.model.ModelManager;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.StorageManager;

//@@author shaoyi1997-reused
//Reused from SE-EDU Address Book Level 4 with modifications and add on
/**
 * Contains tests for closing of the {@code MainWindow}.
 */
public class MainWindowCloseTest extends GuiUnitTest {
    @TempDir
    public Path temporaryFolder;

    private MainWindow mainWindow;
    private EmptyMainWindowHandle mainWindowHandle;
    private Stage stage;

    @BeforeEach
    public void setUp() throws Exception {
        JsonAddressBookStorage jsonAddressBookStorage =
                new JsonAddressBookStorage(temporaryFolder.resolve("addressBook.json"));
        JsonUserPrefsStorage jsonUserPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        StorageManager storageManager = new StorageManager(jsonAddressBookStorage, jsonUserPrefsStorage);
        FxToolkit.setupStage(stage -> {
            this.stage = stage;
            mainWindow = new MainWindow(stage, new LogicManager(new ModelManager(), storageManager));
            mainWindowHandle = new EmptyMainWindowHandle(stage);
            mainWindowHandle.focus();
        });
        FxToolkit.showStage();
    }

    @Test
    public void close_menuBarExitButton_allWindowsClosed() throws InterruptedException {
        mainWindowHandle.clickOnMenuExitButton();
        Thread.sleep(3000);
        // The application will exit when all windows are closed.
        assertEquals(Collections.emptyList(), guiRobot.listWindows());
    }

    @Test
    public void close_externalRequest_exitAppRequestEventPosted() throws InterruptedException {
        mainWindowHandle.clickOnMenuHelpButton();
        Thread.sleep(3000);
        assertTrue(HelpWindowHandle.isWindowPresent());
        mainWindowHandle.closeMainWindowExternally();
        // The application will exit when all windows are closed.
        assertEquals(Collections.emptyList(), guiRobot.listWindows());
    }

    @Test
    public void minimise_minimiseButton_windowMinimised() throws InterruptedException {
        mainWindowHandle.clickOnMinimiseButton();
        assertTrue(guiRobot.getStage("Address App").isIconified());
        Platform.runLater(() -> guiRobot.getStage("Address App").setIconified(false));
    }

    // tests pass locally but fail in travis
    /*
    @Test
    public void maximise_maximiseRestoreButton_windowMaximised() {
        mainWindowHandle.clickOnMaximiseButton();
        assertTrue(guiRobot.getStage("Address App").isMaximized());
    }

    @Test
    public void restore_maximiseRestoreButton_windowRestored() {
        mainWindowHandle.clickOnMaximiseButton();
        mainWindowHandle.clickOnRestoreButton();
        assertFalse(guiRobot.getStage("Address App").isMaximized());
    }
    */

    /**
     * A handle for an empty {@code MainWindow}. The components in {@code MainWindow} are not initialized.
     */
    private class EmptyMainWindowHandle extends StageHandle {

        private EmptyMainWindowHandle(Stage stage) {
            super(stage);
        }

        /**
         * Closes the {@code MainWindow} by clicking on the menu bar's exit button.
         */
        private void clickOnMenuExitButton() {
            guiRobot.clickOn("File");
            guiRobot.clickOn("Exit");
        }

        /**
         * Closes the {@code MainWindow} through an external request {@code MainWindow} (e.g pressing the 'X' button on
         * the {@code MainWindow} or closing the app through the taskbar).
         */
        private void closeMainWindowExternally() {
            guiRobot.interact(() -> stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST)));
        }

        /**
         * Opens the {@code HelpWindow} by clicking on the menu bar's help button.
         */
        private void clickOnMenuHelpButton() {
            guiRobot.clickOn("Help");
            guiRobot.clickOn("F1");
        }

        /**
         * Minimises the window by clicking on the minimise button.
         */
        private void clickOnMinimiseButton() {
            guiRobot.clickOn("#minimiseButton");
        }

        /**
         * Maximises or restores the window by clicking on the maximise button.
         */
        private void clickOnMaximiseButton() {
            guiRobot.clickOn("#maximiseButton");
        }

        /**
         * Restores the window by clicking on the restore button.
         */
        private void clickOnRestoreButton() {
            guiRobot.clickOn("#restoreButton");
        }
    }
}
//@@author
