package seedu.address.ui;

import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.appmanager.AppManager;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.UiLogicHelper;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.gamecommands.GameCommandResult;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";
    private static final String MESSAGE_INIT_BANK = "Welcome to Dukemon!\n"
            + "Start by creating or selecting a bank:\n"
            + "Eg. create mybank\n"
            + "Eg. select sample";


    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;

    private AppManager appManager;

    private UiLogicHelper uiLogicHelper;

    //Secondary parser for updating the Ui.
    private UpdateUi updateUi;

    // Independent Ui parts residing in this Ui container
    private TimerDisplay timerDisplay;
    private ResultDisplay resultDisplay;
    private ModularDisplay modularDisplay;
    private HelpWindow helpWindow;
    private CommandBox commandBox;

    @FXML
    private Scene scene;

    /** CommandBox placeholder.*/
    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    /** ResultDisplay placeholder.*/
    @FXML
    private StackPane resultDisplayPlaceholder;

    /** ModularDisplay placeholder.*/
    @FXML
    private StackPane modularDisplayPlaceholder;

    /** TimerDisplay placeholder.*/
    @FXML
    private AnchorPane timerDisplayPlaceholder;

    /** StatusBar placeholder.*/
    @FXML
    private StackPane statusbarPlaceholder;


    MainWindow(Stage primaryStage, AppManager appManager) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;

        this.appManager = appManager;
        this.modularDisplay = new ModularDisplay(appManager);

        this.uiLogicHelper = appManager.getLogic();

        // Configure the UI
        setWindowDefaultSize(appManager.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow();
    }

    Stage getPrimaryStage() {
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
        //Setting modularDisplay to load mode by default.
        modularDisplay.swapToHomeDisplay(modularDisplayPlaceholder);
        modularDisplay.registerDragAndDropCallBack(this::executeCommand, this::executeCommand);
        modularDisplay.initialiseFilePath("data");

        //Set up the resultDisplay (main feedback for commands).
        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        //Give user instruction to load a bank when first starting up the app.
        resultDisplay.setFeedbackToUser(MESSAGE_INIT_BANK);

        //Set up timer display
        timerDisplay = new TimerDisplay();
        timerDisplayPlaceholder.getChildren().add(timerDisplay.getRoot());

        //Set up callback function in AppManager to update TimerDisplay
        appManager.registerTimerDisplayCallBack(this::updateTimerDisplay);

        //Set up callback function in AppManager to update HintDisplay
        appManager.registerHintDisplayCallBack(this::updateHintDisplay);

        //Set up callback function in AppManager to update QuestionDisplay
        appManager.registerQuestionDisplayCallBack(this::updateQuestionDisplay);

        //Set up callback function in AppManager to call MainWindow's executeCommand
        appManager.registerMainWindowExecuteCallBack(this::executeCommand);

        //Set up status bar footer
        StatusBarFooter statusBarFooter = new StatusBarFooter();
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        //Set up command box
        commandBox = new CommandBox(this::executeCommand, uiLogicHelper);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        //Set up UpdateUI
        updateUi = new UpdateUi(modularDisplay);
        updateUi.setTheme(appManager.getAppSettings().getDefaultTheme(), scene);
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
    private void handleHelp() {
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
        appManager.setGuiSettings(guiSettings);
        helpWindow.hide();
        primaryStage.hide();
    }

    /**
     * Opens the result stats window when the game is finished.
     */
    private void handleFinishedGame() {
        if (appManager.getGameStatistics() == null) {
            throw new IllegalStateException("gameStatistics in gameManager should not be null when game"
                    + "is finished");
        }

        modularDisplay.swapToGameResult(modularDisplayPlaceholder, appManager.getGameStatistics(),
                appManager.getActiveWordBankStatistics());
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {

            CommandResult commandResult = appManager.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());

            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            //Updates the Ui.
            updateUi.updateModularDisplay(uiLogicHelper.getMode(), modularDisplayPlaceholder);

            updateUi.setTheme(appManager.getAppSettings().getDefaultTheme(), scene);


            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            if (commandResult.isPromptingGuess()) {
                commandBox.setGuessTextAndCaret();
            } else {
                commandBox.clearCommandBox();
            }

            if (commandResult instanceof GameCommandResult) {
                GameCommandResult gameCommandResult = (GameCommandResult) commandResult;
                if (gameCommandResult.isFinishedGame()) {
                    handleFinishedGame();
                }
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }

    /**
     * Updates the timerDisplay module of MainWindow to be called from GameTimerManager.
     * @param timerMessage Message to be displayed on the TimerDisplay.
     * @param timeLeft Time in milliseconds that is left in the current timer.
     */
    private void updateTimerDisplay(String timerMessage, long timeLeft, long totalTimeGiven) {
        double percentageTimeLeft = (timeLeft * 1.0) / totalTimeGiven;

        /* when time left is <= half of totalTime given, switch to alert colour */
        if (percentageTimeLeft <= 0.5) {
            this.timerDisplay.setAlertTextColour();
        } else {
            this.timerDisplay.setNormalTextColour();
        }
        timerDisplay.setProgressBarProgress(percentageTimeLeft);
        timerDisplay.setFeedbackToUser(timerMessage);
    }

    /**
     * Updates the HintDisplay section of the UI with the {@code hintString}.
     */
    private void updateHintDisplay(String hintString) {
        modularDisplay.updateHint(hintString, modularDisplayPlaceholder);
    }

    /**
     * Updates the QuestionDisplay section of the UI in the modularDisplay with
     * {@code questionString}
     */
    private void updateQuestionDisplay(String questionString) {
        modularDisplay.updateQuestion(questionString, modularDisplayPlaceholder);
    }

}
