package seedu.address.ui;

import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
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

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";
    private static SingleSelectionModel<Tab> selectionModel;

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private ListPanel listPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;
    private FetchEventWindow fetchEventWindow;
    private FetchEmployeeWindow fetchEmployeeWindow;
    private DateWindow dateWindow;
    private ScheduleBox scheduleBox;

    @FXML
    private StackPane schedulePlaceholder;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane listPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private TabPane tabPanePlaceholder;

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
     *
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
    void fillInnerParts() {
        listPanel = new ListPanel(logic.getFilteredEmployeeList(), logic.getFilteredEventList(), this);
        listPanelPlaceholder.getChildren().add(listPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        scheduleBox = new ScheduleBox(logic.getFilteredScheduledEventList(), logic, this);
        schedulePlaceholder.getChildren().add(scheduleBox.getRoot());

        selectionModel = tabPanePlaceholder.getSelectionModel();
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
     * Returns the current tab number.
     * Assumes return value of 0 for JUnit testing when selectionModel is null.
     */
    public static int getCurrentTabIndex() {

        return selectionModel == null ? 0 : selectionModel.getSelectedIndex();
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

    /**
     * Opens the fetch window or focuses on it if it's already opened.
     */
    @FXML
    public void handleEventFetch(Integer index) {
        if (fetchEventWindow != null) {
            fetchEventWindow.hide();
        }
        fetchEventWindow = new FetchEventWindow(logic, index);
        fetchEventWindow.getRoot().getScene().getStylesheets().add("view/FetchWindowTheme.css");
        fetchEventWindow.getRoot().getScene().getStylesheets().add("view/Extensions.css");
        if (!fetchEventWindow.isShowing()) {
            fetchEventWindow.show();
        } else {
            fetchEventWindow.focus();
        }
    }

    /**
     * Opens the FetchEmployeeWindow or focuses on it if it's already opened.
     */
    @FXML
    public void handleEmployeeFetch(Integer index) {
        if (fetchEmployeeWindow != null) {
            fetchEmployeeWindow.hide();
        }
        fetchEmployeeWindow = new FetchEmployeeWindow(logic, index);
        fetchEmployeeWindow.getRoot().getScene().getStylesheets().add("view/FetchWindowTheme.css");
        if (!fetchEmployeeWindow.isShowing()) {
            fetchEmployeeWindow.show();
        } else {
            fetchEmployeeWindow.focus();
        }
    }

    /**
     * Opens the date window or focuses on it if it's already opened.
     */
    @FXML
    public void generateDate() {
        if (dateWindow != null) {
            dateWindow.hide();
        }
        dateWindow = new DateWindow(logic);
        dateWindow.getRoot().getScene().getStylesheets().add("view/FetchWindowTheme.css");
        if (!dateWindow.isShowing()) {
            dateWindow.show();
        } else {
            dateWindow.focus();
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
        if (fetchEventWindow != null) {
            fetchEventWindow.hide();
        }
        helpWindow.hide();
        primaryStage.hide();
    }


    public seedu.address.ui.ListPanel getListPanel() {
        return listPanel;
    }


    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    public CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }
            if (commandResult.getFetch() != null) {
                if (commandResult.getType().equals("Employee_Fetch")) {
                    handleEmployeeFetch(commandResult.getFetch());
                }
                if (commandResult.getType().equals("Event_Fetch")) {
                    handleEventFetch(commandResult.getFetch());
                }
            }

            if (commandResult.getType().equals("Generate")) {
                generateDate();
            }

            if (commandResult.getType().equals("Schedule_Update")) {
                selectionModel.select(1);
                scheduleBox.setLabelText(commandResult.getUiChange());
            }

            if (commandResult.getType().equals("Schedule_Between")) {
                selectionModel.select(1);
                scheduleBox.setLabelText("");
            }

            if (commandResult.getType().contains("Schedule")) {
                selectionModel.select(1);
            }

            if (commandResult.getType().contains("Employee")) {
                selectionModel.select(0);
            }

            if (commandResult.getType().equals("Main_Tab")) {
                selectionModel.select(0);
            }

            if (fetchEventWindow != null && !commandResult.getType().equals("List")) {
                fetchEventWindow.updateCards();
            }


            return commandResult;

        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
