package seedu.address.ui.finance;

import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.finance.Logic;
import seedu.address.logic.finance.commands.CommandResult;
import seedu.address.logic.finance.commands.exceptions.CommandException;
import seedu.address.logic.finance.parser.exceptions.ParseException;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "FinanceWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private LogEntryListPanel logEntryListPanel;
    private BudgetListPanel budgetListPanel;
    private StatsGraphic statsGraphic;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private Menu closeToExceedBudgetMenu;

    @FXML
    private Menu exceedBudgetMenu;

    @FXML
    private StackPane logEntryListPanelPlaceholder;

    @FXML
    private StackPane budgetListPanelPlaceholder;

    @FXML
    private VBox budgetList;

    @FXML
    private StackPane statsGraphicPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

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
        updateBudgetBars();
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
        logEntryListPanel = new LogEntryListPanel(logic.getFilteredLogEntryList());
        logEntryListPanelPlaceholder.getChildren().add(logEntryListPanel.getRoot());

        budgetListPanel = new BudgetListPanel(logic.getFilteredBudgetDataList());
        budgetListPanelPlaceholder.getChildren().add(budgetListPanel.getRoot());

        statsGraphic = new StatsGraphic(logic.getGraphicsData());
        statsGraphicPlaceholder.getChildren().add(statsGraphic.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getFinanceLogFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        updateBudgetBars();
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
     * Switch to view of statistical summaries.
     */
    public void showStats() {
        // Update stats view
        statsGraphic = new StatsGraphic(logic.getGraphicsData());
        statsGraphicPlaceholder.getChildren().add(statsGraphic.getRoot());
        logEntryListPanelPlaceholder.setVisible(false);
        budgetList.setVisible(false);
        statsGraphicPlaceholder.setVisible(true);
    }

    /**
     * Switch to view of list of log entries.
     */
    public void showLogEntries() {
        logEntryListPanel = new LogEntryListPanel(logic.getFilteredLogEntryList());
        logEntryListPanelPlaceholder.getChildren().add(logEntryListPanel.getRoot());
        budgetList.setVisible(false);
        statsGraphicPlaceholder.setVisible(false);
        logEntryListPanelPlaceholder.setVisible(true);
    }

    /**
     * Switch to view of list of budgets.
     */
    public void showBudget() {
        budgetListPanel = new BudgetListPanel(logic.getFilteredBudgetDataList());
        budgetListPanelPlaceholder.getChildren().add(budgetListPanel.getRoot());
        logEntryListPanelPlaceholder.setVisible(false);
        statsGraphicPlaceholder.setVisible(false);
        budgetList.setVisible(true);
    }

    /**
     * Shows the list of budgets.
     */
    @FXML
    public void handleBudgetBar() {
        showBudget();
    }

    /**
     * Shows budget bars if any active budget is close to exceeding or has exceeded.
     */
    private void updateBudgetBars() {
        if (logic.hasAnyActiveBudgetExceeded()) {
            exceedBudgetMenu.setVisible(true);
        } else {
            exceedBudgetMenu.setVisible(false);
        }
        if (logic.hasAnyActiveBudgetCloseToExceed()) {
            closeToExceedBudgetMenu.setVisible(true);
        } else {
            closeToExceedBudgetMenu.setVisible(false);
        }
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

    public LogEntryListPanel getLogEntryListPanel() {
        return logEntryListPanel;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowStats()) {
                showStats();
            } else if (commandResult.isShowBudget()) {
                showBudget();
            } else if (commandResult.isShowHelp()) {
                handleHelp();
            } else if (commandResult.isExit()) {
                handleExit();
            } else {
                showLogEntries();
            }

            updateBudgetBars();

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
