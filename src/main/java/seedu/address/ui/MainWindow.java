package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import java.time.Instant;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Logger;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import javafx.util.Duration;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.events.EventSource;
import seedu.address.model.listeners.EventListListener;
import seedu.address.ui.listeners.UserOutputListener;
import seedu.address.ui.panel.calendar.CalendarPanel;
import seedu.address.ui.panel.list.ListPanel;
import seedu.address.ui.panel.log.LogPanel;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> implements UserOutputListener, EventListListener {
    public static final int WIDTH_PADDING = 20;
    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Consumer<String> onCommandInput;
    private UiParser uiParser;

    // Independent Ui parts residing in this Ui container
    private ListPanel listPanel;
    private CalendarPanel calendarPanel;
    private LogPanel logPanel;
    private CommandBox commandBox;
    private HelpWindow helpWindow;

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
        this.uiParser = new UiParser();

        setWindowDefaultSize(new GuiSettings());

        helpWindow = new HelpWindow();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Fills up all the placeholders of this window.
     */
    public void fillInnerParts() {
        calendarPanel = new CalendarPanel(uiParser);
        listPanel = new ListPanel(uiParser);
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
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    public void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        // logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        primaryStage.hide();
    }

    /**
     * Temporary method to view the calendar
     */
    public void viewCalendar() {
        calendarPanel.getRoot().setVisible(true);
        listPanel.getRoot().setVisible(false);
        logPanel.getRoot().setVisible(false);
        viewTitle.setText("Calendar");
    }

    /**
     * Temporary method to view the event list
     */
    public void viewList() {
        calendarPanel.getRoot().setVisible(false);
        listPanel.getRoot().setVisible(true);
        logPanel.getRoot().setVisible(false);
        viewTitle.setText("List");
    }

    /**
     * Temporary method to view the event list
     */
    public void viewLog() {
        calendarPanel.getRoot().setVisible(false);
        listPanel.getRoot().setVisible(false);
        logPanel.getRoot().setVisible(true);
        viewTitle.setText("Log");
    }

    /**
     * Creates a pop-up of the output using the same LogBox in the LogPanel
     */
    private void createOutputLogBox(String feedbackToUser, String color) {
        requireNonNull(feedbackToUser);
        PopUpBox popUpBox = new PopUpBox(feedbackToUser, color);
        popUpPanel.getChildren().clear();
        popUpPanel.getChildren().add(popUpBox.getRoot());

        Timeline beat = new Timeline(
                new KeyFrame(Duration.seconds(2.5), event -> popUpPanel.getChildren().clear())
        );
        beat.setCycleCount(Timeline.INDEFINITE);
        beat.play();

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
        default:
            // Not suppose to happen;
            return "-logBoxColor";
        }
    }

    /**
     * Changes of the timeline of the calendar
     */
    public void changeTimelineDate(Instant dateTime) {
        calendarPanel.changeTimelineDate(dateTime);
    }

    /**
     * Changes of the calendar screen of the calendar
     */
    public void changeCalendarScreenDate(Instant dateTime) {
        calendarPanel.changeCalendarScreenDate(dateTime);
    }

    @Override
    public void onEventListChange(List<EventSource> events) {
        this.listPanel.onEventListChange(events);
        this.calendarPanel.onEventListChange(events);
    }

    @Override
    public void onUserOutput(UserOutput output, ColorTheme results) {
        String color = getColor(results);
        this.logPanel.createLogBox(output.toString(), color);
        createOutputLogBox(output.toString(), color);
    }
}
