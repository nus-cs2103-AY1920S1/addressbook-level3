package seedu.address.ui;

import static seedu.address.commons.core.GuiSettings.DARK_THEME_CSS_PATH;
import static seedu.address.commons.core.GuiSettings.LIBERRY_THEME_CSS_PATH;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
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
    private static final String SERVE_MODE = "SERVE MODE";
    private static final String NORMAL_MODE = "NORMAL MODE";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;
    private GuiSettings guiSettings;

    // Independent Ui parts residing in this Ui container
    private BookListPanel bookListPanel;
    private BorrowerPanel borrowerPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;

    @FXML
    private AnchorPane commandBoxPlaceholder;

    @FXML
    private Pane resultDisplayPlaceholder;

    @FXML
    private StackPane bookListPanelPlaceholder;

    @FXML
    private StackPane borrowerPanelPlaceholder;

    @FXML
    private Label mode;

    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;
        guiSettings = logic.getGuiSettings();

        // Configure the UI
        setWindowDefaultSize(guiSettings);
        setTheme(guiSettings);

        helpWindow = new HelpWindow();
        if (logic.isServeMode()) {
            mode.setText(SERVE_MODE);
        } else {
            mode.setText(NORMAL_MODE);
        }
    }

    private void setTheme(GuiSettings guiSettings) {
        if (guiSettings.isDarkTheme()) {
            this.getRoot().getScene().getStylesheets().clear();
            this.getRoot().getScene().getStylesheets().add(DARK_THEME_CSS_PATH);
        } else {
            this.getRoot().getScene().getStylesheets().clear();
            this.getRoot().getScene().getStylesheets().add(LIBERRY_THEME_CSS_PATH);
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void updateModeUI() {
        if (logic.isServeMode()) {
            if (guiSettings.isDarkTheme()) {
                mode.setStyle("-fx-background-color: " + GuiSettings.COLOR_DARK_THEME_MODE_LABEL + ";");
            } else {
                mode.setStyle("-fx-background-color: " + GuiSettings.COLOR_LIBERRY_THEME_MODE_LABEL + ";");
            }
        } else {
            mode.setStyle("-fx-background-color: " + GuiSettings.COLOR_TRANSPARENT + ";");
        }
        primaryStage.show();
    }

    public void handleToggleUi() {
        guiSettings.toggleTheme();
        setTheme(guiSettings);
        bookListPanel = new BookListPanel(logic.getFilteredBookList(), guiSettings.isDarkTheme());
        bookListPanelPlaceholder.getChildren().add(bookListPanel.getRoot());
        updateModeUI();
        if (logic.isServeMode()) {
            updateBorrowerPanel();
        }
        primaryStage.show();
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        bookListPanel = new BookListPanel(logic.getFilteredBookList(), guiSettings.isDarkTheme());
        bookListPanelPlaceholder.getChildren().add(bookListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        borrowerPanel = new BorrowerPanel(guiSettings.isDarkTheme());
        borrowerPanelPlaceholder.getChildren().add(borrowerPanel.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        if (guiSettings.isDefault()) {
            primaryStage.setHeight(getDefaultHeight());
            primaryStage.setWidth(getDefaultWidth());
        } else {
            primaryStage.setHeight(guiSettings.getWindowHeight());
            primaryStage.setWidth(guiSettings.getWindowWidth());
        }
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    private double getDefaultWidth() {
        return Screen.getPrimary().getVisualBounds().getWidth();
    }

    private double getDefaultHeight() {
        return Screen.getPrimary().getVisualBounds().getHeight();
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
        GuiSettings newGuiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY(), this.guiSettings.isDarkTheme());
        logic.setGuiSettings(newGuiSettings);
        helpWindow.hide();
        primaryStage.hide();
    }

    /**
     * Updates the borrower panel and sets the label to Serve Mode.
     */
    @FXML
    private void handleServe() {
        mode.setText(SERVE_MODE);
        updateModeUI();
    }

    /**
     * Updates the borrower panel.
     */
    @FXML
    private void updateBorrowerPanel() {
        assert logic.isServeMode() : "Not in serve mode";
        borrowerPanel.setBorrower(logic.getServingBorrower(), logic.getServingBorrowerBookList(), guiSettings.isDarkTheme());
    }

    /**
     * Resets the borrower panel and sets the label to Normal Mode.
     */
    @FXML
    private void handleDone() {
        mode.setText(NORMAL_MODE);
        updateModeUI();
        borrowerPanel.reset();
    }

    /**
     * Observer method invoked when changes happen.
     */
    public void update() {
        updateBorrowerPanel();
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
            } else if (commandResult.isExit()) {
                handleExit();
            } else if (commandResult.isServe()) {
                handleServe();
            } else if (commandResult.isDone()) {
                handleDone();
            } else if (commandResult.isToggleUi()) {
                handleToggleUi();
            }

            if (logic.isServeMode() && !commandResult.isDone()) {
                updateBorrowerPanel();
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
