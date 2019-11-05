//@@author tanlk99
package tagline.ui;

import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import tagline.commons.core.GuiSettings;
import tagline.commons.core.LogsCenter;
import tagline.logic.Logic;
import tagline.logic.commands.CommandResult;
import tagline.logic.commands.exceptions.CommandException;
import tagline.logic.parser.exceptions.ParseException;
import tagline.logic.parser.exceptions.PromptRequestException;
import tagline.ui.exceptions.PromptOngoingException;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    public static final String BEGIN_PROMPTING_STRING = "Please confirm some additional details for the command. "
            + "Press the escape key to abort.";
    public static final String ABORT_PROMPTING_STRING = "Command has been aborted.";
    public static final double CHAT_PANE_MINIMUM_WIDTH_RATIO = 0.3;
    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    private ChatPane chatPane;
    private ResultPane resultPane;
    private HelpWindow helpWindow;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private HBox centerPane;

    @FXML
    private StackPane chatPanePlaceholder;

    @FXML
    private StackPane resultPanePlaceholder;

    private PromptHandler promptHandler;

    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();
        setAbortPromptListener();
        setResizeListener();

        helpWindow = new HelpWindow();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Sets the listener to dynamically resize the chat pane to take up a ratio of at least
     * {@code CHAT_PANE_MINIMUM_WIDTH_RATIO} of the total width.
     */
    private void setResizeListener() {
        centerPane.widthProperty().addListener((observable, oldVal, newVal) -> {
            double chatPaneWidth = (double) newVal * CHAT_PANE_MINIMUM_WIDTH_RATIO;
            if (chatPaneWidth > chatPanePlaceholder.getMinWidth()) {
                chatPanePlaceholder.setPrefWidth(chatPaneWidth);
                chatPanePlaceholder.setMaxWidth(chatPaneWidth);
            }
        });
    }

    /**
     * Sets the listener to abort prompts by pressing the Esc key.
     */
    private void setAbortPromptListener() {
        getRoot().getScene().setOnKeyPressed(new EventHandler<>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ESCAPE && promptHandler != null
                        && !promptHandler.isAborted()) {
                    logger.info("ESCAPE PRESSED");
                    chatPane.setFeedbackToUser(ABORT_PROMPTING_STRING);
                    promptHandler.setAborted();
                }
            }
        });
    }

    //@@author
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
         * consume function-key events. Because CommandBox contains a TextField, thus
         * some accelerators (e.g F1) will not work when the focus is in them because
         * the key event is consumed by the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    //@@author tanlk99
    /**
     * Initializes the chat pane.
     */
    private void initChatPane() {
        chatPane = new ChatPane();
        chatPane.fillInnerParts(this::handleUserInput);
        chatPanePlaceholder.getChildren().add(chatPane.getRoot());
    }

    /**
     * Initializes the result pane and all its views.
     */
    private void initResultPane() {
        resultPane = new ResultPane();
        resultPane.fillInnerParts(logic.getFilteredContactList(), logic.getFilteredNoteList(),
                logic.getFilteredGroupList(), logic.getFilteredTagList());
        resultPanePlaceholder.getChildren().add(resultPane.getRoot());
    }

    /**
     * Initializes the status bar.
     */
    private void initStatusBar() {
        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getNoteBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        initStatusBar();
        initChatPane();
        initResultPane();
    }

    //@@author
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

    //@@author tanlk99
    /**
     * Handles some user input and returns the result.
     *
     * @param commandText Raw input entered by the user
     * @return A CommandResult representing the result of a successful command.
     * @throws CommandException if a command was executed unsuccessfully
     * @throws ParseException if the user entered an invalid command
     * @throws PromptOngoingException if prompting is ongoing and more responses are expected
     */
    private CommandResult handleUserInput(String commandText)
            throws CommandException, ParseException, PromptOngoingException {
        chatPane.setCommandFromUser(commandText);

        //Clean up aborted or complete prompt handler
        if (promptHandler != null && (promptHandler.isAborted() || promptHandler.isComplete())) {
            promptHandler = null;
        }

        try {
            //Prompting in progress
            if (promptHandler != null) {
                return handlePromptResponse(commandText);
            } else {
                return handleCommandString(commandText);
            }
        } catch (PromptRequestException e) {
            logger.info("Invalid command, requesting prompt: " + commandText);
            chatPane.setFeedbackToUser(BEGIN_PROMPTING_STRING);

            if (promptHandler != null) {
                promptHandler = new PromptHandler(promptHandler.getPendingCommand(), e.getPrompts());
            } else {
                promptHandler = new PromptHandler(commandText, e.getPrompts());
            }

            chatPane.setFeedbackToUser(promptHandler.getNextPrompt());
            throw new PromptOngoingException();
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            chatPane.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }

    /**
     * Handles a command string from the user.
     *
     * @see Logic#execute(String) for details on command execution.
     */
    private CommandResult handleCommandString(String commandText) throws ParseException, CommandException {
        CommandResult commandResult = logic.execute(commandText);
        displayCommandResult(commandResult);
        return commandResult;
    }

    /**
     * Handles a response to a prompt.
     *
     * <p>If prompting is complete, this method executes the command with the filled prompts and returns the command
     * result. Otherwise, this method throws a {@code PromptOngoingException}, indicating that more responses from the
     * user are expected.</p>
     *
     * @throws PromptOngoingException if there are more prompts to answer
     */
    private CommandResult handlePromptResponse(String commandText)
            throws PromptOngoingException, ParseException, CommandException {
        promptHandler.fillNextPrompt(commandText);

        if (!promptHandler.isComplete()) {
            chatPane.setFeedbackToUser(promptHandler.getNextPrompt());
            throw new PromptOngoingException();
        }

        CommandResult commandResult = logic.execute(promptHandler.getPendingCommand(),
                promptHandler.getFilledPromptList());
        displayCommandResult(commandResult);
        return commandResult;
    }

    /**
     * Handles the GUI feedback for a {@code CommandResult}.
     */
    private void displayCommandResult(CommandResult commandResult) {
        logger.info("Result: " + commandResult.getFeedbackToUser());
        chatPane.setFeedbackToUser(commandResult.getFeedbackToUser());
        resultPane.setCurrentViewType(commandResult.getViewType());

        if (commandResult.isShowHelp()) {
            handleHelp();
        }

        if (commandResult.isExit()) {
            handleExit();
        }
    }
}
