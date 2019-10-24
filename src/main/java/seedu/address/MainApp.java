package seedu.address;

import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Version;
import seedu.address.logic.CommandManager;
import seedu.address.logic.UiManager;
import seedu.address.logic.commands.AddEventCommand;
import seedu.address.logic.commands.DeleteEventCommand;
import seedu.address.logic.commands.EditEventCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.notification.NotificationChecker;
import seedu.address.logic.notification.NotificationCheckingThread;
import seedu.address.model.ModelManager;
import seedu.address.model.undo.UndoRedoManager;
import seedu.address.ui.systemtray.PopupListener;
import seedu.address.ui.systemtray.SystemTrayCommunicator;

/**
 * Runs the application.
 */
public class MainApp extends Application {

    private static final Version VERSION = new Version(0, 6, 0, true);
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    private static final String COMMAND_ADD_EVENT = "add_event";
    private static final String COMMAND_DELETE_EVENT = "delete_event";
    private static final String COMMAND_EDIT_EVENT = "edit_event";
    private static final String COMMAND_UNDO = "undo";
    private static final String COMMAND_REDO = "redo";

    private UiManager uiManager;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing AddressBook ]===========================");
        super.init();

        CommandManager commandManager = new CommandManager();
        ModelManager modelManager = new ModelManager();
        uiManager = new UiManager();
        UndoRedoManager undoRedoManager = new UndoRedoManager();

        // Register commands to CommandManager.
        commandManager.addCommand(COMMAND_ADD_EVENT, () -> AddEventCommand.newBuilder(modelManager));
        commandManager.addCommand(COMMAND_DELETE_EVENT, () -> DeleteEventCommand.newBuilder(modelManager));
        commandManager.addCommand(COMMAND_EDIT_EVENT, () -> EditEventCommand.newBuilder(modelManager));
        commandManager.addCommand(COMMAND_UNDO, () -> UndoCommand.newBuilder(undoRedoManager));
        commandManager.addCommand(COMMAND_REDO, () -> RedoCommand.newBuilder(undoRedoManager));

        // Add Listeners
        commandManager.addUserOutputListener(uiManager);

        modelManager.addEventListListener(uiManager);
        modelManager.addEventListListener(undoRedoManager);

        uiManager.addCommandInputListener(commandManager);

        undoRedoManager.addUndoRedoListener(modelManager);

        NotificationCheckingThread notificationCheckingThread =
            new NotificationCheckingThread(new NotificationChecker(modelManager));
        notificationCheckingThread.addPopupListener(new PopupListener(new SystemTrayCommunicator()));
        notificationCheckingThread.setDaemon(true);
        notificationCheckingThread.start();
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting AddressBook " + MainApp.VERSION);
        uiManager.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping Address Book ] =============================");
        System.exit(0);
    }
}
