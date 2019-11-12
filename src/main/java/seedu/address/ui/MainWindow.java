package seedu.address.ui;

import static seedu.address.commons.core.Messages.MESSAGE_WELCOME_STUDYBUDDYPRO;

import java.util.logging.Logger;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.FunctionMode;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.commandresults.CheatSheetCommandResult;
import seedu.address.logic.commands.commandresults.FlashcardCommandResult;
import seedu.address.logic.commands.commandresults.GlobalCommandResult;
import seedu.address.logic.commands.commandresults.NoteCommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.UnknownCommandResultTypeException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindowCopy.fxml";
    private static final String TIME_TRIAL_END_FEEDBACK = "The time trial has ended!";

    private static boolean isTimeTrialOngoing;

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;
    private ActivityWindow activityWindow;

    @FXML
    private StackPane activityWindowPlaceholder;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane personListPanelPlaceholder;

    @FXML
    private StackPane noteCardListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private ImageView notesHighlightCircle;

    @FXML
    private ImageView fcHighlightCircle;

    @FXML
    private ImageView csHighlightCircle;

    @FXML
    private ImageView currentHighlightedCircle;

    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow();
        activityWindow = new ActivityWindow();
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
    //To adjust this method to show relative path when switching between modes
    void fillInnerParts() {
        activityWindow = new ActivityWindow();
        activityWindowPlaceholder.getChildren().add(activityWindow.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());
        resultDisplay.setFeedbackToUser(MESSAGE_WELCOME_STUDYBUDDYPRO);

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getStudyBuddyProFilePath());
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


    /**
     * This method is called upon the end of a timetrial
     */
    public void setFeedbackTimeTrialEnd(String feedbackMessage) {
        resultDisplay.setFeedbackToUser(feedbackMessage);
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
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException,
            UnknownCommandResultTypeException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());

            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isGlobalCommandResult()) {
                executeGlobalCommandHelper((GlobalCommandResult) commandResult);
            } else if (commandResult.isFlashcardCommandResult()) {
                executeFlashcardCommandHelper((FlashcardCommandResult) commandResult);
            } else if (commandResult.isCheatSheetCommandResult()) {
                executeCheatSheetCommandHelper((CheatSheetCommandResult) commandResult);
            } else if (commandResult.isNoteCommandResult()) {
                executeNoteCommandHelper((NoteCommandResult) commandResult);
            } else {
                throw new UnknownCommandResultTypeException("Invalid CommandResult type!");
            }

            return commandResult;
        } catch (CommandException | ParseException | UnknownCommandResultTypeException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }

    /**
     *
     * @param globalCommandResult
     */
    private void executeGlobalCommandHelper(GlobalCommandResult globalCommandResult) {
        if (globalCommandResult.isShowHelp()) {
            handleHelp();
        }

        if (globalCommandResult.isExit()) {
            handleExit();
        }

        if (globalCommandResult.isToggle()) {
            toggleModeTo(globalCommandResult.getTargetMode().get());
        }
    }

    /**
     *
     * @param flashcardCommandResult
     */
    private void executeFlashcardCommandHelper(FlashcardCommandResult flashcardCommandResult) {
        if (isTimeTrialOngoing && !flashcardCommandResult.isShowAns()) {
            activityWindow.terminateTimeTrial();
        }

        if (flashcardCommandResult.isShowAns()) {
            activityWindow.showAnswer();
        } else if (flashcardCommandResult.isTimeTrial()) {
            isTimeTrialOngoing = true;
            activityWindow.startTimeTrial(flashcardCommandResult.getDeck());
        } else if (flashcardCommandResult.getFlashcard().isPresent()) {
            activityWindow.displayFlashcard(flashcardCommandResult.getFlashcard().get());
        }
    }

    /**
     *
     * @param cheatSheetCommandResult
     */
    private void executeCheatSheetCommandHelper(CheatSheetCommandResult cheatSheetCommandResult) {
        if (cheatSheetCommandResult.getCheatSheet().isPresent()) {
            activityWindow.displayCheatSheet(cheatSheetCommandResult.getCheatSheet().get());
        } else if (cheatSheetCommandResult.isSwitchTags()) {
            activityWindow.switchCheatSheetContent(cheatSheetCommandResult.getTagIndex().get());
        } else {
            activityWindow.displayEmptyCheatSheet();
        }
    }

    /**
     *
     * @param noteCommandResult
     */
    private void executeNoteCommandHelper(NoteCommandResult noteCommandResult) {
        if (noteCommandResult.getNote().isPresent()) {
            activityWindow.displayNote(noteCommandResult.getNote().get());
        }
    }

    /**
     * Switches the list of available commands based on the function that the user wants to use.
     * @param targetMode Function mode that user wants to switch to
     */
    private void toggleModeTo (FunctionMode targetMode) {
        deselectCircle(currentHighlightedCircle);
        switch (targetMode) {
        case FLASHCARD:
            currentHighlightedCircle = fcHighlightCircle;
            break;
        case CHEATSHEET:
            currentHighlightedCircle = csHighlightCircle;
            break;
        case NOTE:
            currentHighlightedCircle = notesHighlightCircle;
            break;
        default:
        }

        activityWindow.switchWindowTo(targetMode);
        highlightCircle(currentHighlightedCircle);
    }

    private void deselectCircle(ImageView targetCircle) {
        FadeTransition ft = new FadeTransition(Duration.millis(400), targetCircle);
        ft.setToValue(0);
        ft.play();
    }

    private void highlightCircle(ImageView targetCircle) {
        FadeTransition ft = new FadeTransition(Duration.millis(400), targetCircle);
        ft.setToValue(1);
        ft.play();
    }

    public static void setIsTimeTrialOngoing(boolean isTimeTrialOngoing) {
        MainWindow.isTimeTrialOngoing = isTimeTrialOngoing;
    }
}
