package seedu.address.ui;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.UiManager.WINDOW_HEIGHT;
import static seedu.address.logic.UiManager.WINDOW_WIDTH;

import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Logger;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import javafx.util.Duration;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.CalendarDate;
import seedu.address.model.events.EventSource;
import seedu.address.model.tasks.TaskSource;
import seedu.address.ui.panel.calendar.CalendarPanel;
import seedu.address.ui.panel.list.ListPanel;
import seedu.address.ui.panel.log.LogPanel;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    public static final Integer TIMING = 20;
    private static final String FXML = "MainWindow.fxml";
    private static final String WELCOME_MESSAGE = "Welcome to Horo";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Consumer<String> onCommandInput;

    // Independent Ui parts residing in this Ui container
    private ListPanel listPanel;
    private CalendarPanel calendarPanel;
    private LogPanel logPanel;
    private CommandBox commandBox;

    @FXML
    private StackPane popUpPanel;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private StackPane viewPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private GridPane gridManager;

    @FXML
    private Label viewTitle;

    public MainWindow(Stage primaryStage, Consumer<String> onCommandInput) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.onCommandInput = onCommandInput;

        primaryStage.setWidth(WINDOW_WIDTH);
        primaryStage.setHeight(WINDOW_HEIGHT);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void show() {
        primaryStage.show();
    }

    //@@author Kyzure
    /**
     * Fills up all the placeholders of this window.
     */
    public void fillInnerParts() {
        calendarPanel = new CalendarPanel();
        listPanel = new ListPanel();
        logPanel = new LogPanel();
        commandBox = new CommandBox(this.onCommandInput);

        viewPlaceholder.getChildren().add(calendarPanel.getRoot());
        viewPlaceholder.getChildren().add(listPanel.getRoot());
        viewPlaceholder.getChildren().add(logPanel.getRoot());
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        calendarPanel.getRoot().setVisible(true);
        listPanel.getRoot().setVisible(false);
        logPanel.getRoot().setVisible(false);

        viewTitle.setText("Calendar");
        editInnerParts();
    }

    //@@author Kyzure
    /**
     * Edits the size of the nodes based on the user's screen size.
     */
    private void editInnerParts() {
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        double screenHeight = primaryScreenBounds.getHeight();
        double screenWidth = primaryScreenBounds.getWidth();

        viewPlaceholder.setPrefWidth(screenWidth);
        viewPlaceholder.setPrefHeight(screenHeight);

        // Set the stage width and height
        primaryStage.setMaxWidth(screenWidth);
        primaryStage.setMaxHeight(screenHeight);

        addResizingListeners();
        welcomeMessage();
        delayResize();
    }

    //@@author Kyzure
    /**
     * Changes the View Panel to show the Calendar Panel.
     */
    public void viewCalendar() {
        calendarPanel.getRoot().setVisible(true);
        listPanel.getRoot().setVisible(false);
        logPanel.getRoot().setVisible(false);
        viewTitle.setText("Calendar");
    }

    //@@author Kyzure
    /**
     * Changes the View Panel to show the Calendar Panel of a certain date in the timeline.
     *
     * @param calendarDate The given date.
     */
    public void viewDay(CalendarDate calendarDate) {
        calendarPanel.changeToDayView(calendarDate);
        viewCalendar();
    }

    //@@author Kyzure
    /**
     * Changes the View Panel to show the Calendar Panel of a certain week in the timeline.
     *
     * @param calendarDate The given date of the week.
     */
    public void viewWeek(CalendarDate calendarDate) {
        calendarPanel.changeToWeekView(calendarDate);
        viewCalendar();
    }

    //@@author Kyzure
    /**
     * Changes the View Panel to show the Calendar Panel of a certain month.
     *
     * @param calendarDate The Calendar's date.
     */
    public void viewMonth(CalendarDate calendarDate) {
        calendarPanel.changeToMonthView(calendarDate);
        viewCalendar();
    }

    //@@author Kyzure
    /**
     * Changes the View Panel to show the List Panel.
     */
    public void viewList() {
        calendarPanel.getRoot().setVisible(false);
        listPanel.getRoot().setVisible(true);
        logPanel.getRoot().setVisible(false);
        viewTitle.setText("List");
    }

    //@@author Kyzure
    /**
     * Changes the View Panel to show the Log Panel.
     */
    public void viewLog() {
        calendarPanel.getRoot().setVisible(false);
        listPanel.getRoot().setVisible(false);
        logPanel.getRoot().setVisible(true);
        viewTitle.setText("Log");
    }

    //@@author Kyzure
    /**
     * Creates a pop-up of the output using the same LogBox in the LogPanel and animates it.
     */
    private void createPopUpBox(String feedbackToUser, String color) {
        requireNonNull(feedbackToUser);
        popUpPanel.getChildren().clear();
        PopUpBox popUpBox = new PopUpBox(feedbackToUser, color);
        Region popUpBoxRoot = popUpBox.getRoot();
        Timeline popUpAnimation = new Timeline(
                new KeyFrame(Duration.seconds(0), event -> {
                    popUpPanel.getChildren().add(popUpBoxRoot);
                    popUpBoxRoot.setTranslateY(popUpBoxRoot.getTranslateY() - 40);
                    TranslateTransition translateTransition =
                            new TranslateTransition(Duration.seconds(1), popUpBoxRoot);
                    double initialPos = popUpBoxRoot.getTranslateY();
                    translateTransition.setFromY(initialPos);
                    translateTransition.setToY(initialPos + 40);
                    translateTransition.setCycleCount(1);
                    translateTransition.setAutoReverse(true);
                    translateTransition.setDuration(new Duration(250));
                    translateTransition.play();
                }),
                new KeyFrame(Duration.seconds(3), event -> {
                    TranslateTransition translateTransition =
                            new TranslateTransition(Duration.seconds(1), popUpBoxRoot);
                    double initialPos = popUpBoxRoot.getTranslateY();
                    translateTransition.setFromY(initialPos);
                    translateTransition.setToY(initialPos - 40);
                    translateTransition.setCycleCount(1);
                    translateTransition.setAutoReverse(true);
                    translateTransition.setDuration(new Duration(250));
                    translateTransition.play();
                }),
                new KeyFrame(Duration.seconds(4), event -> {
                    popUpPanel.getChildren().remove(popUpBoxRoot);
                })
        );
        popUpAnimation.setCycleCount(1);
        popUpAnimation.play();
    }

    //@@author Kyzure
    /**
     * Creates a color scheme from the a list of the constant color values in the CSS file.
     * @param color The color of the object.
     * @return The color in a String value that is used in the CSS file.
     */
    private String getColor(ColorTheme color) {
        switch(color) {
        case SUCCESS:
            return "-logBoxColor";
        case FAILURE:
            return "-errorColor";
        case WELCOME:
            return "-welcomeColor";
        default:
            // Not suppose to happen;
            return "-logBoxColor";
        }
    }

    //@@author Kyzure
    /**
     * Changes of the calendar screen of the calendar
     */
    public void changeCalendarScreenDate(CalendarDate calendarDate) {
        calendarPanel.changeCalendarScreenDate(calendarDate);
        viewCalendar();
    }

    //@@author Kyzure
    /**
     * Adds Listeners that re-sizes the calendar panel when the width has been changed.
     */
    private void addResizingListeners() {
        primaryStage.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue,
                                Number oldSceneWidth,
                                Number newSceneWidth) {
                delayResize();
            }
        });
    }

    //@@author Kyzure
    /**
     * Re-sizes the CalendarPanel after a certain delay.
     *
     * @see CalendarPanel
     */
    private void delayResize() {
        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Thread.sleep(TIMING);
                } catch (InterruptedException e) {
                    throw new Exception(e.getMessage());
                }
                return null;
            }
        };
        sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                calendarPanel.resizeCalendarPanel();
            }
        });
        new Thread(sleeper).start();
    }

    /**
     * Prints the welcome message.
     */
    private void welcomeMessage() {
        onUserOutput(new UserOutput(WELCOME_MESSAGE), ColorTheme.WELCOME);
    }


    /**
     * Changes the UI based on the new events and tasks list.
     *
     * @param events The given event list.
     * @param tasks The given task list.
     */
    public void onModelListChange(List<EventSource> events,
                                  List<TaskSource> tasks,
                                  HashMap<EventSource, Integer> eventHash,
                                  HashMap<TaskSource, Integer> taskHash) {
        this.calendarPanel.onModelListChange(events, tasks, eventHash, taskHash);
        this.listPanel.onEventListChange(events, eventHash);
        this.listPanel.onTaskListChange(tasks, taskHash);
    }

    /**
     * Creates a log box as well as popup box to display to the user the output.
     *
     * @param output The given output to be displayed to the user.
     * @param results The color theme which decides whether the command is a success or failure.
     */
    public void onUserOutput(UserOutput output, ColorTheme results) {
        String color = getColor(results);
        this.logPanel.createLogBox(output.toString(), color);
        createPopUpBox(output.toString(), color);
    }
}
