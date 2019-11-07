package seedu.address;

import java.nio.file.Paths;
import java.time.ZoneId;
import java.util.Locale;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.CommandManager;
import seedu.address.logic.NotificationManager;
import seedu.address.logic.StorageManager;
import seedu.address.logic.UiManager;
import seedu.address.logic.UndoRedoManager;
import seedu.address.logic.commands.AddEventCommand;
import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.logic.commands.CalendarViewCommand;
import seedu.address.logic.commands.DayViewCommand;
import seedu.address.logic.commands.DeleteEventCommand;
import seedu.address.logic.commands.DeleteTaskCommand;
import seedu.address.logic.commands.EditEventCommand;
import seedu.address.logic.commands.EditTaskCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.ExportIcsCommand;
import seedu.address.logic.commands.ImportIcsCommand;
import seedu.address.logic.commands.ListViewCommand;
import seedu.address.logic.commands.LogViewCommand;
import seedu.address.logic.commands.MonthViewCommand;
import seedu.address.logic.commands.NotificationOffCommand;
import seedu.address.logic.commands.NotificationOnCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.WeekViewCommand;
import seedu.address.model.ModelManager;

/**
 * Runs the application.
 */
public class MainApp extends Application {

    public static final Locale LOCALE = Locale.ENGLISH;
    public static final ZoneId TIME_ZONE = ZoneId.systemDefault();

    private static final String VERSION = "1.4";
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    private static final String COMMAND_ADD_EVENT = "add_event";
    private static final String COMMAND_DELETE_EVENT = "delete_event";
    private static final String COMMAND_EDIT_EVENT = "edit_event";
    private static final String COMMAND_ADD_TASK = "add_task";
    private static final String COMMAND_DELETE_TASK = "delete_task";
    private static final String COMMAND_EDIT_TASK = "edit_task";
    private static final String COMMAND_UNDO = "undo";
    private static final String COMMAND_REDO = "redo";
    private static final String COMMAND_IMPORT_ICS = "import";
    private static final String COMMAND_EXPORT_ICS = "export";
    private static final String COMMAND_NOTIFICATION_OFF = "notif_off";
    private static final String COMMAND_NOTIFICATION_ON = "notif_on";
    private static final String COMMAND_DAY_VIEW = "day";
    private static final String COMMAND_WEEK_VIEW = "week";
    private static final String COMMAND_MONTH_VIEW = "month";
    private static final String COMMAND_CALENDAR_VIEW = "calendar";
    private static final String COMMAND_LIST_VIEW = "list";
    private static final String COMMAND_LOG_VIEW = "log";
    private static final String COMMAND_EXIT = "exit";

    private CommandManager commandManager;
    private ModelManager modelManager;
    private NotificationManager notificationManager;
    private UiManager uiManager;
    private StorageManager storageManager;
    private UndoRedoManager undoRedoManager;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing Horo ]===========================");
        super.init();

        commandManager = new CommandManager();
        modelManager = new ModelManager();
        notificationManager = new NotificationManager(modelManager);
        storageManager = new StorageManager(modelManager);
        storageManager.setEventsFile(Paths.get("data", "events.json"));
        storageManager.setTasksFile(Paths.get("data", "tasks.json"));
        uiManager = new UiManager();
        undoRedoManager = new UndoRedoManager(modelManager);

        registerCommands();
        addListeners();
    }


    /**
     * Registers each of the commands to the CommandManager.
     */
    private void registerCommands() {
        commandManager.addCommand(COMMAND_ADD_EVENT, () -> AddEventCommand.newBuilder(modelManager));
        commandManager.addCommand(COMMAND_DELETE_EVENT, () -> DeleteEventCommand.newBuilder(modelManager));
        commandManager.addCommand(COMMAND_EDIT_EVENT, () -> EditEventCommand.newBuilder(modelManager));
        commandManager.addCommand(COMMAND_ADD_TASK, () -> AddTaskCommand.newBuilder(modelManager));
        commandManager.addCommand(COMMAND_DELETE_TASK, () -> DeleteTaskCommand.newBuilder(modelManager));
        commandManager.addCommand(COMMAND_EDIT_TASK, () -> EditTaskCommand.newBuilder(modelManager));
        commandManager.addCommand(COMMAND_UNDO, () -> UndoCommand.newBuilder(undoRedoManager));
        commandManager.addCommand(COMMAND_REDO, () -> RedoCommand.newBuilder(undoRedoManager));
        commandManager.addCommand(COMMAND_IMPORT_ICS, () -> ImportIcsCommand.newBuilder(modelManager));
        commandManager.addCommand(COMMAND_EXPORT_ICS, () -> ExportIcsCommand.newBuilder(modelManager));
        commandManager.addCommand(COMMAND_NOTIFICATION_OFF, () ->
            NotificationOffCommand.newBuilder(notificationManager));
        commandManager.addCommand(COMMAND_NOTIFICATION_ON, () ->
            NotificationOnCommand.newBuilder(notificationManager));
        commandManager.addCommand(COMMAND_DAY_VIEW, () -> DayViewCommand.newBuilder(uiManager));
        commandManager.addCommand(COMMAND_WEEK_VIEW, () -> WeekViewCommand.newBuilder(uiManager));
        commandManager.addCommand(COMMAND_MONTH_VIEW, () -> MonthViewCommand.newBuilder(uiManager));
        commandManager.addCommand(COMMAND_CALENDAR_VIEW, () -> CalendarViewCommand.newBuilder(uiManager));
        commandManager.addCommand(COMMAND_LIST_VIEW, () -> ListViewCommand.newBuilder(uiManager));
        commandManager.addCommand(COMMAND_LOG_VIEW, () -> LogViewCommand.newBuilder(uiManager));
        commandManager.addCommand(COMMAND_EXIT, ExitCommand::newBuilder);
    }

    /**
     * Registers each listener to the appropriate manager.
     */
    private void addListeners() {
        commandManager.addUserOutputListener(uiManager);

        modelManager.addModelDataListener(uiManager);

        modelManager.addModelDataListener(storageManager);
        modelManager.addModelDataListener(undoRedoManager);

        uiManager.addCommandInputListener(commandManager);
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting Horo v" + VERSION);

        // Start UiManager
        uiManager.start(primaryStage);

        // Load Model from storage
        storageManager.load();

        // Start UndoRedoManager
        undoRedoManager.start();
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping Horo ] =============================");
        System.exit(0);
    }
}
