package seedu.weme.ui;

import static seedu.weme.logic.parser.util.ParserUtil.MESSAGE_INVALID_TAB;

import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.weme.commons.core.GuiSettings;
import seedu.weme.commons.core.LogsCenter;
import seedu.weme.logic.Logic;
import seedu.weme.logic.commands.CommandResult;
import seedu.weme.logic.commands.exceptions.CommandException;
import seedu.weme.logic.parser.exceptions.ParseException;
import seedu.weme.logic.prompter.exceptions.PromptException;
import seedu.weme.logic.prompter.prompt.CommandPrompt;
import seedu.weme.model.ModelContext;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // App content for different tabs
    private MemeGridPanel memeGridPanel;
    private TemplateGridPanel templateGridPanel;
    private CreatePanel createPanel;
    private StatsPanel statsPanel;
    private MemeGridPanel exportGridPanel;
    private ImportGridPanel importGridPanel;
    private PreferencesPanel preferencesPanel;
    private ViewPanel viewPanel;

    // Independent Ui parts residing in this Ui container
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane tabBarPlaceholder;

    @FXML
    private StackPane appContentPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        helpWindow = new HelpWindow();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
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
        fillPeripherals();
        fillAppContent();
        listenToContextChange();
    }

    /**
     * Fills up peripheral components.
     */
    private void fillPeripherals() {
        CommandBox commandBox = new CommandBox(this::executeCommand,
                this::promptCommand,
                logic.getFilteredMemeList(),
                logic.getContext());
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());
        try {
            resultDisplay.setFeedbackToUser(logic.prompt("").getCommandPrompt());
        } catch (PromptException e) {
            // Acts as a placeholder.
        }

        TabBar tabBar = new TabBar(logic.getContext());
        tabBarPlaceholder.getChildren().add(tabBar.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getWemeFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());
    }
    /**
     * Fills up main app content.
     */
    private void fillAppContent() {
        memeGridPanel = new MemeGridPanel(logic.getFilteredMemeList(),
                logic.getObservableLikeData(),
                logic.getObservableDislikeData());
        templateGridPanel = new TemplateGridPanel(logic.getFilteredTemplateList());
        createPanel = new CreatePanel(logic.getMemeTextList(), logic.getMemeCreationImage().orElse(null));
        statsPanel = new StatsPanel(logic.getWeme());
        exportGridPanel = new MemeGridPanel(
                logic.getStagedMemeList(), logic.getObservableLikeData(), logic.getObservableDislikeData());
        importGridPanel = new ImportGridPanel(logic.getImportMemeList());
        preferencesPanel = new PreferencesPanel(logic.getObservableUserPreferences());
        viewPanel = new ViewPanel(logic.getViewableMeme());

        setAppContent(logic.getContext().getValue());
    }

    /**
     * Attaches listener on ModelContext that changes app content accordingly.
     */
    private void listenToContextChange() {
        logic.getContext().addListener((observable, oldValue, newValue) -> setAppContent(newValue));
    }

    /**
     * Sets app content based on ModelContext.
     *
     * @param context the current {@code ModelContext}
     */
    private void setAppContent(ModelContext context) {
        appContentPlaceholder.getChildren().clear();
        switch (context) {
        case CONTEXT_MEMES:
            appContentPlaceholder.getChildren().add(memeGridPanel.getRoot());
            break;
        case CONTEXT_TEMPLATES:
            appContentPlaceholder.getChildren().add(templateGridPanel.getRoot());
            break;
        case CONTEXT_CREATE:
            appContentPlaceholder.getChildren().add(createPanel.getRoot());
            break;
        case CONTEXT_STATISTICS:
            appContentPlaceholder.getChildren().add(statsPanel.getRoot());
            break;
        case CONTEXT_EXPORT:
            appContentPlaceholder.getChildren().add(exportGridPanel.getRoot());
            break;
        case CONTEXT_IMPORT:
            appContentPlaceholder.getChildren().add(importGridPanel.getRoot());
            break;
        case CONTEXT_PREFERENCES:
            appContentPlaceholder.getChildren().add(preferencesPanel.getRoot());
            break;
        case CONTEXT_VIEW:
            appContentPlaceholder.getChildren().add(viewPanel.getRoot());
            break;
        default:
            throw new IllegalArgumentException(MESSAGE_INVALID_TAB);
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
        new Thread(() -> logic.cleanUp()).start();
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.weme.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (logic.getContext().getValue() == ModelContext.CONTEXT_CREATE) {
                createPanel.updateImage(logic.getMemeCreationImage().orElse(null));
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
     * Parse the user input and display the suggestions in ResultDisplay.
     * @param commandText text input from CommandBox
     * @return String complete command for auto-completion
     */
    private String promptCommand(String commandText) throws PromptException {
        try {
            CommandPrompt commandPrompt = logic.prompt(commandText);
            resultDisplay.setFeedbackToUser(commandPrompt.getCommandPrompt());
            return commandPrompt.getCompleteCommand();
        } catch (PromptException e) {
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
