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
import seedu.address.gamemanager.GameManager;
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
    private GameManager gameManager;

    // Independent Ui parts residing in this Ui container
    private TimerDisplay timerDisplay;
    private ResultDisplay resultDisplay;
    private ModularDisplay modularDisplay;
    private CurrentModeFooter currentModeFooter;
    private HelpWindow helpWindow;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane resultDisplayPlaceholder;

    //One size fits all stackpane
    @FXML
    private StackPane modularDisplayPlaceholder;

    //TimerDisplay placeholder
    @FXML
    private StackPane timerDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private StackPane currentModePlaceholder;


    public MainWindow(Stage primaryStage, GameManager gameManager) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.gameManager = gameManager;
        this.modularDisplay = new ModularDisplay(gameManager);

        // Configure the UI
        setWindowDefaultSize(gameManager.getGuiSettings());

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
        modularDisplay.displayTitle(modularDisplayPlaceholder);

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        //Set up timer display
        timerDisplay = new TimerDisplay();
        timerDisplayPlaceholder.getChildren().add(timerDisplay.getRoot());
        //Set up callback function in GameManager to update TimerDisplay
        gameManager.setTimerDisplayCallBack(this::updateTimerDisplay);
        //Set up callback function in GameManager to update ResultDisplay
        gameManager.setResultDisplayCallBack(this::updateResultDisplay);
        //Set up callback function in GameManager to call MainWindow's executeCommand
        gameManager.setMainWindowExecuteCallBack(this::executeCommand);

        StatusBarFooter statusBarFooter = new StatusBarFooter(gameManager.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        //Displays the current mode. Starts in "load" mode.
        currentModeFooter = new CurrentModeFooter();
        currentModePlaceholder.getChildren().add(currentModeFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
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
        gameManager.setGuiSettings(guiSettings);
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
            CommandResult commandResult = gameManager.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());

            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            //This is temporary. Todo: Make a separate class to handle switching and ui updates.
            if (commandText.equals("home")) {
                currentModeFooter.changeMode("home");
            } else if (commandText.matches("start [1-9]")) {
                currentModeFooter.changeMode("game");
            }

            //So is this. Todo: Compile both the above and below into a new "UpdateUI" class.
            if (commandText.equals("list")) {
                modularDisplay.swapToList(modularDisplayPlaceholder);
            } else if (commandText.equals("help")) {
                //modularDisplay.swapToBanks(modularDisplayPlaceholder);
            } else {
                modularDisplay.swapToHome(modularDisplayPlaceholder);
            }

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

    /**
     * Updates the timerDisplay module of MainWindow to be called from GameTimer.
     * @param timerMessage Message to be displayed on the TimerDisplay.
     * @param timeLeft Time in milliseconds that is left in the current timer.
     */
    private void updateTimerDisplay(String timerMessage, long timeLeft) {
        if (timeLeft <= 200) {
            this.timerDisplay.setAlertTextColour();
        } else {
            this.timerDisplay.setNormalTextColour();
        }
        this.timerDisplay.setFeedbackToUser(timerMessage);
    }

    private void updateResultDisplay(String resultDisplayMessage) {
        this.resultDisplay.setFeedbackToUser(resultDisplayMessage);
    }

}
