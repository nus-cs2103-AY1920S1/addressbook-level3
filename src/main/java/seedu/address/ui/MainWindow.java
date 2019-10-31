package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Logger;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import javafx.util.Duration;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.CalendarDate;
import seedu.address.model.events.EventSource;
import seedu.address.model.listeners.EventListListener;
import seedu.address.model.listeners.TaskListListener;
import seedu.address.model.tasks.TaskSource;
import seedu.address.ui.listeners.UserOutputListener;
import seedu.address.ui.panel.calendar.CalendarPanel;
import seedu.address.ui.panel.list.ListPanel;
import seedu.address.ui.panel.log.LogPanel;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> implements UserOutputListener, EventListListener, TaskListListener {
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

        setWindowDefaultSize(new GuiSettings());
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void show() {
        primaryStage.show();
    }

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
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    /**
     * Changes the View Panel to show the Calendar Panel.
     */
    public void viewCalendar() {
        calendarPanel.getRoot().setVisible(true);
        listPanel.getRoot().setVisible(false);
        logPanel.getRoot().setVisible(false);
        viewTitle.setText("Calendar");
    }


    /**
     * Changes the View Panel to show the Calendar Panel of a certain date in the timeline.
     *
     * @param calendarDate The given date.
     */
    public void viewDay(CalendarDate calendarDate) {
        calendarPanel.changeToDayView(calendarDate);
        viewCalendar();
    }


    /**
     * Changes the View Panel to show the Calendar Panel of a certain week in the timeline.
     *
     * @param calendarDate The given date of the week.
     */
    public void viewWeek(CalendarDate calendarDate) {
        calendarPanel.changeToWeekView(calendarDate);
        viewCalendar();
    }

    /**
     * Changes the View Panel to show the Calendar Panel of a certain month.
     *
     * @param calendarDate The Calendar's date.
     */
    public void viewMonth(CalendarDate calendarDate) {
        calendarPanel.changeToMonthView(calendarDate);
        viewCalendar();
    }

    /**
     * Changes the View Panel to show the List Panel.
     */
    public void viewList() {
        calendarPanel.getRoot().setVisible(false);
        listPanel.getRoot().setVisible(true);
        logPanel.getRoot().setVisible(false);
        viewTitle.setText("List");
    }

    /**
     * Changes the View Panel to show the Log Panel.
     */
    public void viewLog() {
        calendarPanel.getRoot().setVisible(false);
        listPanel.getRoot().setVisible(false);
        logPanel.getRoot().setVisible(true);
        viewTitle.setText("Log");
    }

    /**
     * Creates a pop-up of the output using the same LogBox in the LogPanel and animates it.
     */
    private void createOutputLogBox(String feedbackToUser, String color) {
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

    /**
     * Changes of the calendar screen of the calendar
     */
    public void changeCalendarScreenDate(CalendarDate calendarDate) {
        calendarPanel.changeCalendarScreenDate(calendarDate);
        viewCalendar();
    }

    /**
     * Adds Listeners that re-sizes the calendar panel when the width has been changed.
     */
    private void addResizingListeners() {
        primaryStage.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue,
                                Number oldSceneWidth,
                                Number newSceneWidth) {
                calendarPanel.resizeTimelineView();
            }
        });
    }

    /**
     * Prints the welcome message.
     */
    private void welcomeMessage() {
        onUserOutput(new UserOutput(WELCOME_MESSAGE), ColorTheme.WELCOME);
    }

    @Override
    public void onEventListChange(List<EventSource> events) {
        this.listPanel.onEventListChange(events);
        this.calendarPanel.onEventListChange(events);
    }

    @Override
    public void onTaskListChange(List<TaskSource> tasks) {
        // TODO: Tasks
    }

    @Override
    public void onUserOutput(UserOutput output, ColorTheme results) {
        String color = getColor(results);
        this.logPanel.createLogBox(output.toString(), color);
        createOutputLogBox(output.toString(), color);
    }
}
