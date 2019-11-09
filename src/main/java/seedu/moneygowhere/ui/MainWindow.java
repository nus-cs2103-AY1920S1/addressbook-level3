package seedu.moneygowhere.ui;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import seedu.moneygowhere.commons.core.GuiSettings;
import seedu.moneygowhere.commons.core.LogsCenter;
import seedu.moneygowhere.logic.Logic;
import seedu.moneygowhere.logic.commands.CommandResult;
import seedu.moneygowhere.logic.commands.HelpCommand;
import seedu.moneygowhere.logic.commands.exceptions.CommandException;
import seedu.moneygowhere.logic.parser.exceptions.ParseException;
import seedu.moneygowhere.model.currency.Currency;

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
    private SpendingListPanel spendingListPanel;
    private ReminderListPanel reminderListPanel;
    private ResultDisplay resultDisplay;

    private GraphPanel graphPanel;
    private StatsPanel statsPanel;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane spendingListPanelPlaceholder;

    @FXML
    private StackPane reminderListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private TabPane tabPanePlaceholder;

    @FXML
    private VBox budgetPanelPlaceholder;

    @FXML
    private BudgetPanel budgetPanel;

    private Tab graphTab;
    private Tab statsTab;

    private String startDate = null;
    private String lastDate = null;

    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();
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
        spendingListPanel = new SpendingListPanel(logic.getFilteredSpendingList(),
                logic.getSpendingBook());
        spendingListPanelPlaceholder.getChildren().add(spendingListPanel.getRoot());

        reminderListPanel = new ReminderListPanel(logic.getSortedReminderList());
        reminderListPanelPlaceholder.getChildren().add(reminderListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getSpendingBookFilePath(), logic.getSpendingBook());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand, this::getPrevCommand, this::getNextCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        Currency currencyInUse = logic.getSpendingBook().getCurrencyInUse();

        BudgetPanel bp = new BudgetPanel(logic.getSpendingBook().getBudget(), currencyInUse);
        budgetPanel = bp;
        budgetPanelPlaceholder.getChildren().add(bp.getRoot());

        graphTab = new Tab("Graph");
        getStartAndEndDates(logic.getGraphData());
        getGraphPanel(currencyInUse);
        graphTab.setContent(graphPanel.getRoot());

        statsTab = new Tab("Statistics");
        getStatsPanel(currencyInUse);
        statsTab.setContent(statsPanel.getRoot());

        tabPanePlaceholder.getTabs().addAll(graphTab, statsTab);
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

    @FXML
    public void handleHelp() {
        resultDisplay.setFeedbackToUser(HelpCommand.SHOWING_HELP_MESSAGE);
    }

    void show() {
        primaryStage.show();
        primaryStage.setMaximized(true);
        primaryStage.setResizable(true);
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        primaryStage.hide();
    }

    public SpendingListPanel getSpendingListPanel() {
        return spendingListPanel;
    }

    public ReminderListPanel getReminderListPanel() {
        return reminderListPanel;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.moneygowhere.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            // store to command storage even though invalid
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            Currency currencyInUse = logic.getSpendingBook().getCurrencyInUse();

            budgetPanel.update(logic.getSpendingBook().getBudget(), currencyInUse);

            if (commandResult.isExit()) {
                handleExit();
            }

            getStartAndEndDates(logic.getGraphData());
            getGraphPanel(currencyInUse);
            getStatsPanel(currencyInUse);

            if (commandResult.isShowGraph()) {
                tabPanePlaceholder.getSelectionModel().select(graphTab);
            }

            if (commandResult.isShowStats()) {
                tabPanePlaceholder.getSelectionModel().select(statsTab);
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }

    private void getStatsPanel(Currency currencyInUse) {
        LinkedHashMap<String, Double> statsData = logic.getStatsData();
        if (startDate == null && lastDate == null) {
            statsPanel = new StatsPanel(statsData, "Statistics\n", currencyInUse);
            statsTab.setContent(statsPanel.getRoot());
        } else {
            statsPanel = new StatsPanel(statsData,
                String.format("Statistics for spending between %s and %s\n", startDate, lastDate), currencyInUse);
            statsTab.setContent(statsPanel.getRoot());
        }
    }

    private void getGraphPanel(Currency currencyInUse) {
        LinkedHashMap<String, Double> graphData = logic.getGraphData();
        if (startDate == null && lastDate == null) {
            graphPanel = new GraphPanel(graphData, "Graph\n", currencyInUse);
            graphTab.setContent(graphPanel.getRoot());
        } else {
            graphPanel = new GraphPanel(graphData,
                String.format("Graph for spending between %s and %s\n", startDate, lastDate), currencyInUse);
            graphTab.setContent(graphPanel.getRoot());
        }
    }

    private void getStartAndEndDates(LinkedHashMap<String, Double> graphData) {
        if (graphData.size() >= 2) {
            Iterator<Map.Entry<String, Double>> iterator = graphData.entrySet().iterator();
            startDate = iterator.next().getKey().toString();
            while (iterator.hasNext()) {
                lastDate = iterator.next().getKey().toString();
            }
        } else if (graphData.size() == 1) {
            startDate = graphData.entrySet().iterator().next().getKey().toString();
            lastDate = startDate;
        } else {
            startDate = null;
            lastDate = null;
        }
    }

    /**
     * Retrieves the previously stored command.
     *
     * @see seedu.moneygowhere.logic.Logic#getPrevCommand()
     */
    private String getPrevCommand() {
        return logic.getPrevCommand();
    }

    /**
     * Retrieves the next stored command.
     *
     * @see seedu.moneygowhere.logic.Logic#getNextCommand()
     */
    private String getNextCommand() {
        return logic.getNextCommand();
    }
}
