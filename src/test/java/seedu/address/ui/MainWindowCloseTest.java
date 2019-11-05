package seedu.address.ui;

import java.nio.file.Path;

import org.junit.jupiter.api.io.TempDir;

import guitests.guihandles.StageHandle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


/**
 * Contains tests for closing of the {@code MainWindow}.
 */
public class MainWindowCloseTest extends GuiUnitTest {
    @TempDir
    public Path temporaryFolder;

    private MainWindow mainWindow;
    private EmptyMainWindowHandle mainWindowHandle;
    private Stage stage;

    /*@BeforeEach
    public void setUp() throws Exception {
        JsonAddressBookStorage jsonAddressBookStorage =
                new JsonAddressBookStorage(temporaryFolder.resolve("addressBook.json"));
        JsonUserPrefsStorage jsonUserPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        StorageManager storageManager = new StorageManager(jsonAddressBookStorage,
        jsonUserPrefsStorage, new StatisticManager());
        FxToolkit.setupStage(stage -> {
            this.stage = stage;
            mainWindow = new MainWindow(stage, new LogicManager(new ModelManager(),
            storageManager, new StatisticManager()));
            mainWindowHandle = new EmptyMainWindowHandle(stage);
            mainWindowHandle.focus();
        });
        FxToolkit.showStage();
    }*/



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
    }
}
