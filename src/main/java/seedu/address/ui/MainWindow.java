package seedu.address.ui;

import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyAddressBook;

//@@author shaoyi1997-reused
//Reused from SE-EDU Address Book Level 3 with major modifications
/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";
    private static final int MINIMUM_HEIGHT = 650;
    private static final int MINIMUM_WIDTH = 1250;

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;
    private ReadOnlyAddressBook readOnlyAddressBook;
    private double xOffset = 0;
    private double yOffset = 0;
    private boolean isDim = false;

    // Independent Ui parts residing in this Ui container
    private WorkerListPanel workerListPanel;
    private LineChartPanel lineChartPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;
    private BodyMasterDetailPane bodyMasterDetailPane;
    private NotificationButton notificationButton;
    private CommandBox commandBox;
    private FridgeGridView fridgeGridView;

    @FXML
    private Scene appScene;

    @FXML
    private MenuBar menuBar;

    @FXML
    private Button minimiseButton;

    @FXML
    private Button maximiseButton;

    @FXML
    private Button exitButton;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane workerListPanelPlaceholder;

    @FXML
    private StackPane fridgeListPlaceholder;

    @FXML
    private StackPane lineChartPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private StackPane bodyMasterListPlaceholder;

    @FXML
    private StackPane notificationButtonPlaceholder;

    @FXML
    private Menu fileButton;

    @FXML
    private Menu helpButton;

    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());
        setWindowCloseHandler();
        setMenuBarHandlers();

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
    void fillInnerParts() throws java.text.ParseException {
        workerListPanel = new WorkerListPanel(logic.getFilteredWorkerList());
        workerListPanelPlaceholder.getChildren().add(workerListPanel.getRoot());

        lineChartPanel = LineChartPanel.getLineChartPanelInstance(logic.getAddressBook().getBodyList());
        lineChartPanelPlaceholder.getChildren().add(lineChartPanel.getLineChart());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getAddressBookFilePath(), logic.getAddressBook());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        bodyMasterDetailPane = new BodyMasterDetailPane(new BodyTableView(logic.getFilteredBodyList(),
                logic.selectedBodyProperty(), logic::setSelectedBody),
                new BodyCardSelected(logic.selectedBodyProperty()));
        bodyMasterListPlaceholder.getChildren().add(bodyMasterDetailPane.getRoot());

        notificationButton = NotificationButton.getInstance(logic.getFilteredActiveNotifList());
        notificationButtonPlaceholder.getChildren().add(notificationButton.getRoot());

        fridgeGridView = new FridgeGridView(logic.getFilteredFridgeList());
        fridgeListPlaceholder.getChildren().add(fridgeGridView.getRoot());
    }

    private void setMenuBarHandlers() {
        // determines the horizontal and vertical positions of the window relative to its origin
        menuBar.setOnMousePressed((event) -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        // changes the window position according to the offset
        menuBar.setOnMouseDragged((event) -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });

        // set handler when user double clicks on the menubar
        menuBar.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                maximiseRestore();
            }
        });

        // sets the handlers for the respective buttons
        setMaximiseButtonHandler();
        setMinimiseButtonHandler();

        // allows the window to be draggable and resizable
        ResizableWindow.enableResizableWindow(primaryStage, MINIMUM_WIDTH, MINIMUM_HEIGHT,
                Double.MAX_VALUE, Double.MAX_VALUE);

        /*
        // cancels the focus on items in the menubar (esp menu)
        // necessary to allow users to navigate commandTextField using arrow keys after the mouse has gone over the menu
        // as upon mouse exit of menu, focus on menu is still retained
        Robot robot = new Robot();
        commandBox.getRoot().setOnKeyPressed(e -> {
            System.out.println("in");
            if (e.getCode() == KeyCode.LEFT || e.getCode() == KeyCode.RIGHT) {
                robot.keyPress(KeyCode.ESCAPE);
            }
        });

        //helpButton.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
        //});
        */
    }

    /**
     * Enables the maximization and restoration of the window.
     */
    private void maximiseRestore() {
        if (primaryStage.isMaximized()) {
            primaryStage.setMaximized(false);
            if (primaryStage.getScene().getWindow().getY() < 0) {
                primaryStage.getScene().getWindow().setY(0);
            }
            maximiseButton.setId("maximiseButton");
        } else {
            Rectangle windowBounds = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
            primaryStage.setMaximized(true);
            primaryStage.setHeight(windowBounds.height);
            primaryStage.setWidth(windowBounds.width);
            maximiseButton.setId("restoreButton");
        }
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
     * Sets the application to exit when the user presses the default OS close button.
     */
    private void setWindowCloseHandler() {
        primaryStage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, e -> handleExit());
    }

    /**
     * Sets the minimising handler for minimise button.
     */
    private void setMinimiseButtonHandler() {
        minimiseButton.setOnMouseClicked(click ->
            primaryStage.setIconified(true)
        );
    }

    /**
     * Sets the maximising and restoring handler for maximise button
     * Change button images respectively via css.
     */
    private void setMaximiseButtonHandler() {
        maximiseButton.setOnMouseClicked(click -> {
            maximiseRestore();
        });
    }

    public LineChartPanel getLineChartPanel() {
        return lineChartPanel;
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

            if (commandResult.isExit()) {
                handleExit();
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
//@@author
