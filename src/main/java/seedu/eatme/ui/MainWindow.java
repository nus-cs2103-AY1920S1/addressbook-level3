package seedu.eatme.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.eatme.commons.core.GuiSettings;
import seedu.eatme.commons.core.LogsCenter;
import seedu.eatme.logic.Logic;
import seedu.eatme.logic.commands.CommandResult;
import seedu.eatme.logic.commands.exceptions.CommandException;
import seedu.eatme.logic.parser.exceptions.ParseException;

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
    private EateryListPanel eateryListPanel;
    private EateryListPanel todoListPanel;

    private ResultDisplay resultDisplay;
    private StatsWindow statsWindow;
    private FeedPostListPanel feedPostListPanel;
    private HelpWindow helpWindow;

    private CommandBox commandBox;
    private ReviewListPanel reviewListPanel;

    @FXML
    private VBox commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane eateryListPanelPlaceholder;

    @FXML
    private VBox resultDisplayPlaceholder;

    @FXML
    private StackPane feedPostListPanelPlaceholder;

    @FXML
    private VBox statusbarPlaceholder;

    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        helpWindow = new HelpWindow();
        statsWindow = new StatsWindow();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {

        eateryListPanel = new EateryListPanel(logic.getFilteredEateryList(), true);
        eateryListPanelPlaceholder.getChildren().add(eateryListPanel.getRoot());

        feedPostListPanel = new FeedPostListPanel(logic.getFeedList(), logic);
        feedPostListPanelPlaceholder.getChildren().add(feedPostListPanel.getRoot());

        reviewListPanel = new ReviewListPanel(logic.getActiveReviews());

        resultDisplay = new ResultDisplay(reviewListPanel);
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getEateryListFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillDataParts() {
        eateryListPanel = new EateryListPanel(logic.getFilteredEateryList(), true);
        todoListPanel = new EateryListPanel(logic.getFilteredTodoList(), false);

        if (logic.isMainMode()) {
            eateryListPanelPlaceholder.getChildren().addAll(eateryListPanel.getRoot());
        } else {
            eateryListPanelPlaceholder.getChildren().addAll(todoListPanel.getRoot());
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

    /**
     * Displays the statistics window.
     */
    private void showStats() {
        if (!statsWindow.isShowing()) {
            statsWindow.initStats(logic.getStatistics());
            statsWindow.show();
        } else {
            statsWindow.initStats(logic.getStatistics());
            statsWindow.focus();
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
     * Display pending command generated from to-do in commandbox.
     */
    private void handleSaveTodo(String pendingCommand) {
        String[] resTokens = pendingCommand.split(":");
        commandBox = new CommandBox(this::executeCommand, resTokens[1]);
        commandBoxPlaceholder.getChildren().clear();
        commandBoxPlaceholder.getChildren().addAll(commandBox.getRoot());
    }

    public EateryListPanel getEateryListPanel() {
        return eateryListPanel;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.eatme.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult);

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            if (commandResult.wantToSave()) {
                handleSaveTodo(commandResult.getFeedbackToUser());
            }

            if (commandResult.isShowStats()) {
                showStats();
            }

            fillDataParts();
            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e);
            throw e;
        }
    }
}
