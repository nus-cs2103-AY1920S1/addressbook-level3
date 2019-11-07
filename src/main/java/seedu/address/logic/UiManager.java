package seedu.address.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.listeners.CommandInputListener;
import seedu.address.model.CalendarDate;
import seedu.address.model.ModelData;
import seedu.address.model.events.EventDateComparator;
import seedu.address.model.events.EventSource;
import seedu.address.model.listeners.ModelDataListener;
import seedu.address.model.tasks.TaskDateComparator;
import seedu.address.model.tasks.TaskSource;
import seedu.address.ui.ColorTheme;
import seedu.address.ui.MainWindow;
import seedu.address.ui.Ui;
import seedu.address.ui.UserOutput;
import seedu.address.ui.listeners.UserOutputListener;

/**
 * The manager of the UI component.
 * Responsible for creating and destroying the graphical ui.
 */
public class UiManager implements Ui, UserOutputListener, ModelDataListener {

    public static final int WINDOW_WIDTH = 1280;
    public static final int WINDOW_HEIGHT = 720;

    public static final String ALERT_DIALOG_PANE_FIELD_ID = "alertDialogPane";

    private static final Logger logger = LogsCenter.getLogger(UiManager.class);
    private static final String ICON_APPLICATION = "/images/horo_icon_32.png";

    private MainWindow mainWindow;
    private List<CommandInputListener> uiListeners;

    public UiManager() {
        this.uiListeners = new ArrayList<>();
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting UI...");

        //Set the application icon.
        primaryStage.getIcons().add(getImage(ICON_APPLICATION));

        try {
            mainWindow = new MainWindow(primaryStage, commandInput -> {
                this.uiListeners.forEach(listener -> listener.onCommandInput(commandInput));
            });
            mainWindow.show(); //This should be called before creating other UI parts
            mainWindow.fillInnerParts();

        } catch (Throwable e) {
            logger.severe(StringUtil.getDetails(e));
            showFatalErrorDialogAndShutdown("Fatal error during initializing", e);
        }
    }

    public void addCommandInputListener(CommandInputListener listener) {
        this.uiListeners.add(listener);
    }

    private Image getImage(String imagePath) {
        return new Image(MainApp.class.getResourceAsStream(imagePath));
    }

    private void showAlertDialogAndWait(Alert.AlertType type, String title, String headerText, String contentText) {
        showAlertDialogAndWait(mainWindow.getPrimaryStage(), type, title, headerText, contentText);
    }

    /**
     * Shows an alert dialog on {@code owner} with the given parameters.
     * This method only returns after the user has closed the alert dialog.
     */
    private static void showAlertDialogAndWait(Stage owner, AlertType type, String title, String headerText,
                                               String contentText) {
        final Alert alert = new Alert(type);
        alert.getDialogPane().getStylesheets().add("view/DarkTheme.css");
        alert.initOwner(owner);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.getDialogPane().setId(ALERT_DIALOG_PANE_FIELD_ID);
        alert.showAndWait();
    }

    /**
     * Shows an error alert dialog with {@code title} and error message, {@code e},
     * and exits the application after the user has closed the alert dialog.
     */
    private void showFatalErrorDialogAndShutdown(String title, Throwable e) {
        logger.severe(title + " " + e.getMessage() + StringUtil.getDetails(e));
        showAlertDialogAndWait(Alert.AlertType.ERROR, title, e.getMessage(), e.toString());
        Platform.exit();
        System.exit(1);
    }

    public void viewDay(CalendarDate calendarDate) {
        mainWindow.viewDay(calendarDate);
    }

    public void viewWeek(CalendarDate calendarDate) {
        mainWindow.viewWeek(calendarDate);
    }

    public void viewMonth(CalendarDate calendarDate) {
        mainWindow.viewMonth(calendarDate);
    }

    public void viewList() {
        mainWindow.viewList();
    }

    public void viewLog() {
        mainWindow.viewLog();
    }

    /**
     * Changes the view of the UI to the CalendarPanel to the given date.
     *
     * @param calendarDate The given date.
     */
    public void viewCalendar(CalendarDate calendarDate) {
        if (calendarDate == null) {
            mainWindow.viewCalendar();
        } else {
            mainWindow.changeCalendarScreenDate(calendarDate);
        }
    }

    /**
     * Returns a copy of the event list sorted by start date.
     *
     * @param events The given event list.
     * @return A copy of the event list sorted by date.
     */
    private List<EventSource> sortDateEventList(List<EventSource> events) {
        List<EventSource> sortedDateEventList = new ArrayList<>(events);
        sortedDateEventList.sort(new EventDateComparator());
        return sortedDateEventList;
    }

    /**
     * Returns a HashMap based on the indexing of the original event list.
     *
     * @param events The unsorted event list.
     * @return Returns a HashMap based on the indexing of the original event list
     */
    private HashMap<EventSource, Integer> addEventIndex(List<EventSource> events) {
        int backIndex = 0;
        HashMap<EventSource, Integer> eventHash = new HashMap<>();
        for (EventSource event : events) {
            eventHash.put(event, backIndex);
            backIndex++;
        }
        return eventHash;
    }

    /**
     * Returns a copy of the task list sorted by due date.
     *
     * @param tasks The given task list.
     * @return A copy of the task list sorted by due date.
     */
    private List<TaskSource> sortDateTaskList(List<TaskSource> tasks) {
        List<TaskSource> sortedDateTaskList = new ArrayList<>(tasks);
        sortedDateTaskList.sort(new TaskDateComparator());
        return sortedDateTaskList;
    }

    /**
     * Returns a HashMap based on the indexing of the original event list.
     *
     * @param tasks The unsorted task list.
     * @return Returns a HashMap based on the indexing of the original task list
     */
    private HashMap<TaskSource, Integer> addTaskIndex(List<TaskSource> tasks) {
        int backIndex = 0;
        HashMap<TaskSource, Integer> taskHash = new HashMap<>();
        for (TaskSource task : tasks) {
            taskHash.put(task, backIndex);
            backIndex++;
        }
        return taskHash;
    }

    @Override
    public void onModelDataChange(ModelData modelData) {
        List<EventSource> sortedDateEventList = sortDateEventList(modelData.getEvents());
        List<TaskSource> sortedDateTaskList = sortDateTaskList(modelData.getTasks());
        HashMap<EventSource, Integer> eventHash = addEventIndex(modelData.getEvents());
        HashMap<TaskSource, Integer> taskHash = addTaskIndex(modelData.getTasks());
        this.mainWindow.onModelListChange(
                sortedDateEventList,
                sortedDateTaskList,
                eventHash,
                taskHash);
    }

    @Override
    public void onUserOutput(UserOutput output, ColorTheme result) {
        this.mainWindow.onUserOutput(output, result);
    }
}
