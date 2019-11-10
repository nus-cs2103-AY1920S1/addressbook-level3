package seedu.address.ui;

import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private PersonListPanel personListPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane personListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private StackPane featureBoxPlaceholder;

    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());
        setAccelerators();
        helpWindow = new HelpWindow();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts(Model model) {
        personListPanel = new PersonListPanel(logic.getFilteredPersonList());
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        CalendarPanel calendarPanel = new CalendarPanel(model);
        featureBoxPlaceholder.getChildren().add(calendarPanel.getRoot());
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

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        primaryStage.hide();
    }

    public PersonListPanel getPersonListPanel() {
        return personListPanel;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());
            updateMainWindow(commandResult);
            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }

    /**
     * Updates the view of {@code MainWindow}
     * @param commandResult Command result from executing user command
     */
    private void updateMainWindow(CommandResult commandResult) {
        if (!(commandResult.getFeature() == null)) {
            updateFeatureBox(commandResult);
        }
        if (!(commandResult.getPerson() == null)) {
            displayInformationDisplay();
        }
        if (commandResult.isClear()) {
            displayCalendar(commandResult.getModel());
        }
        if (commandResult.isShowHelp()) {
            handleHelp();
        }
        if (commandResult.isExit()) {
            handleExit();
        }
    }

    /**
     * Updates the view of the feature box.
     * @param commandResult Command result from executing user command
     */
    private void updateFeatureBox(CommandResult commandResult) {
        switch (commandResult.getFeature().getName()) {
        case "calendar":
            if (!(commandResult.getDate() == null)) {
                displayCalendarWithDate(commandResult);
            } else {
                displayCalendar(commandResult.getModel());
            }
            break;
        case "attendance":
            showAttendancePanel(commandResult.getModel());
            break;
        case "performance":
            showPerformancePanel(commandResult.getModel());
            break;
        case "records":
            showRecordsPanel(commandResult.getModel(), commandResult.getEventName());
            break;
        default:
            break;
        }
    }

    /**
     * Displays {@code CalendarPanel} in the feature box.
     * @param model Represents state of data in Athletick
     */
    private void displayCalendar(Model model) {
        CalendarPanel calendarPanel = new CalendarPanel(model);
        featureBoxPlaceholder.getChildren().clear();
        featureBoxPlaceholder.getChildren().add(calendarPanel.getRoot());
    }

    /**
     * Displays {@code AttendancePanel} in the feature box.
     * @param model Represents state of data in Athletick
     */
    private void showAttendancePanel(Model model) {
        AttendancePanel attendance = new AttendancePanel(model);
        featureBoxPlaceholder.getChildren().clear();
        featureBoxPlaceholder.getChildren().add(attendance.getRoot());
    }

    /**
     * Displays {@code PerformancePanel} in the feature box.
     * @param model Represents state of data in Athletick
     */
    private void showPerformancePanel(Model model) {
        PerformancePanel performance = new PerformancePanel(model);
        featureBoxPlaceholder.getChildren().clear();
        featureBoxPlaceholder.getChildren().add(performance.getRoot());
    }

    /**
     * Displays {@code RecordsPanel} for particular event in the feature box.
     * @param model Represents state of data in Athletick
     * @param eventName Name of event.
     */
    private void showRecordsPanel(Model model, String eventName) {
        RecordsPanel recordsPanel =
                new RecordsPanel(model, eventName);
        featureBoxPlaceholder.getChildren().clear();
        featureBoxPlaceholder.getChildren().add(recordsPanel.getRoot());
    }

    /**
     * Displays {@code InformationDisplay} when user selects a person.
     */
    private void displayInformationDisplay() {
        InformationDisplay informationDisplay = new InformationDisplay(logic.getPerson(),
                logic.getPersonAttendance(),
                logic.getAthleteEvents());
        featureBoxPlaceholder.getChildren().clear();
        featureBoxPlaceholder.getChildren().add(informationDisplay.getRoot());
    }

    /**
     * Displays {@code CalendarPanel} or {@code CalendarDetailPanel} depending on type of date
     * provided in {@code commandResult}.
     * @param commandResult Command result from executing user command
     */
    private void displayCalendarWithDate(CommandResult commandResult) {
        Model model = commandResult.getModel();
        switch (commandResult.getDate().getType()) {
        case 1:
            CalendarDetailPanel calendarDetailPanel =
                    new CalendarDetailPanel(commandResult.getDate(), model);
            featureBoxPlaceholder.getChildren().clear();
            featureBoxPlaceholder.getChildren().add(calendarDetailPanel.getRoot());
            break;
        case 2:
            CalendarPanel calendarPanel = new CalendarPanel(commandResult.getDate(), model);
            featureBoxPlaceholder.getChildren().clear();
            featureBoxPlaceholder.getChildren().add(calendarPanel.getRoot());
            break;
        default:
            break;
        }
    }
}
