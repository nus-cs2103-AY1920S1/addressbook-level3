package seedu.address.ui;

import static seedu.address.commons.core.Messages.MESSAGE_DATA_START_NEW;
import static seedu.address.logic.commands.GoCommand.HISTORY_TAB;
import static seedu.address.logic.commands.GoCommand.HOME_TAB;

import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
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

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;
    private DriverWindow driverWindow;
    private CustomerWindow customerWindow;
    private NotificationWindow notificationWindow;
    private AssignedTaskListPanel assignedTaskListPanel;
    private UnassignedTaskListPanel unassignedTaskListPanel;
    private CustomerListPanel customerListPanel;
    private DriverListPanel driverListPanel;
    private CompletedTaskListPanel completedTaskListPanel;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private StackPane assignedTaskListPanelPlaceholder;

    @FXML
    private StackPane unassignedTaskListPanelPlaceholder;

    @FXML
    private StackPane customerListPanelPlaceholder;

    @FXML
    private StackPane driverListPanelPlaceholder;

    @FXML
    private StackPane completedTaskListPanelPlaceholder;

    @FXML
    private TabPane tabPane;

    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow();
        driverWindow = new DriverWindow();
        customerWindow = new CustomerWindow();
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
    void fillInnerParts() {
        assignedTaskListPanel = new AssignedTaskListPanel(logic.getFilteredAssignedTaskList());
        assignedTaskListPanelPlaceholder.getChildren().add(assignedTaskListPanel.getRoot());

        unassignedTaskListPanel = new UnassignedTaskListPanel(logic.getFilteredUnassignedTaskList());
        unassignedTaskListPanelPlaceholder.getChildren().add(unassignedTaskListPanel.getRoot());

        completedTaskListPanel = new CompletedTaskListPanel(logic.getFilteredCompletedTaskList());
        completedTaskListPanelPlaceholder.getChildren().add(completedTaskListPanel.getRoot());

        customerListPanel = new CustomerListPanel(logic.getFilteredCustomerList());
        customerListPanelPlaceholder.getChildren().add(customerListPanel.getRoot());

        driverListPanel = new DriverListPanel(logic.getFilteredDriverList());
        driverListPanelPlaceholder.getChildren().add(driverListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        if (logic.isStartAfresh()) {
            resultDisplay.setFeedbackToUser(MESSAGE_DATA_START_NEW);
        }

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());
        //maximize the window size, and disable resize button.
        primaryStage.setMaximized(true);
        primaryStage.setResizable(false);
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    private void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    /**
     * Opens the Driver window or focuses on it if it's already opened.
     */
    @FXML
    private void handleShowDriver(int driverId) {
        driverWindow.fillFields(logic.getDriver(driverId));
        if (!driverWindow.isShowing()) {
            driverWindow.show();
        } else {
            driverWindow.focus();
        }
    }

    /**
     * Opens the customer window or focuses on it if it's already opened.
     */
    @FXML
    private void handleShowCustomer(int customerId) {
        customerWindow.fillFields(logic.getCustomer(customerId));
        if (!customerWindow.isShowing()) {
            customerWindow.show();
        } else {
            customerWindow.focus();
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
        driverWindow.hide();
        customerWindow.hide();
        primaryStage.hide();
    }

    /**
     * Navigates to the tab.
     */
    private void handleSwitchTab(String param) {
        SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();

        if (param.equalsIgnoreCase(HOME_TAB)) {
            selectionModel.select(0);
        } else if (param.equalsIgnoreCase(HISTORY_TAB)) {
            selectionModel.select(1);
        }
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

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isShowCustomer()) {
                handleShowCustomer(commandResult.getId());
            }

            if (commandResult.isShowDriver()) {
                handleShowDriver(commandResult.getId());
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            if (commandResult.isSwitchTab()) {
                handleSwitchTab(commandResult.getTabType());
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
