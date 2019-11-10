package seedu.ichifund.ui;

import java.util.logging.Logger;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.ichifund.commons.core.GuiSettings;
import seedu.ichifund.commons.core.LogsCenter;
import seedu.ichifund.logic.Logic;
import seedu.ichifund.logic.commands.CommandResult;
import seedu.ichifund.logic.commands.exceptions.CommandException;
import seedu.ichifund.logic.parser.exceptions.ParseException;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;
    private ObservableValue<Integer> currentFeatureParserIndex;

    // Independent Ui parts residing in this Ui container
    private TransactionListPanel transactionListPanel;
    private RepeaterListPanel repeaterListPanel;
    private BudgetListPanel budgetListPanel;
    private DataListPanel dataListPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private MenuItem showTransactionMenuItem;

    @FXML
    private MenuItem showRepeaterMenuItem;

    @FXML
    private MenuItem showBudgetMenuItem;

    @FXML
    private MenuItem showLoanMenuItem;

    @FXML
    private MenuItem showAnalyticsMenuItem;

    @FXML
    private TabPane mainTabPane;

    @FXML
    private StackPane transactionListPanelPlaceholder;

    @FXML
    private StackPane repeaterListPanelPlaceholder;

    @FXML
    private StackPane budgetListPanelPlaceholder;

    @FXML
    private StackPane dataListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;
        this.currentFeatureParserIndex = logic.getCurrentFeatureParserIndex();
        setupParserSwitching();

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow();
    }

    private void setupParserSwitching() {
        // Add listener to check when Logic changes currentFeatureParserIndex.
        this.currentFeatureParserIndex.addListener(new FeatureParserIndexListener(mainTabPane));

        // When tab switching is done by a mouse click, this we change currentFeatureParserIndex through Logic.
        mainTabPane.setOnMouseClicked(event -> {
            int selectedIndex = mainTabPane.getSelectionModel().getSelectedIndex();
            logic.setFeatureParser(selectedIndex);
        });
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
        setAccelerator(showTransactionMenuItem, KeyCombination.valueOf("Ctrl+1"));
        setAccelerator(showRepeaterMenuItem, KeyCombination.valueOf("Ctrl+2"));
        setAccelerator(showBudgetMenuItem, KeyCombination.valueOf("Ctrl+3"));
        setAccelerator(showLoanMenuItem, KeyCombination.valueOf("Ctrl+4"));
        setAccelerator(showAnalyticsMenuItem, KeyCombination.valueOf("Ctrl+5"));
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
        transactionListPanel = new TransactionListPanel(logic.getFilteredTransactionList(),
                logic.getTransactionContextProperty());
        transactionListPanelPlaceholder.getChildren().add(transactionListPanel.getRoot());

        repeaterListPanel = new RepeaterListPanel(logic.getFilteredRepeaterList());
        repeaterListPanelPlaceholder.getChildren().add(repeaterListPanel.getRoot());

        budgetListPanel = new BudgetListPanel(logic.getFilteredBudgetList());
        budgetListPanelPlaceholder.getChildren().add(budgetListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        dataListPanel = new DataListPanel(logic.getDataList());
        dataListPanelPlaceholder.getChildren().add(dataListPanel.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getFundBookFilePath());
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

    void show() {
        primaryStage.show();
    }

    /**
     * Switch the tab to show transactions when shortcut key is sued.
     */
    @FXML
    public void handleShowTransaction() {
        // This triggers an invalidation event in FeatureParserIndexListener, resulting in a tab switch.
        logic.setFeatureParser(0);
    }

    /**
     * Switch the tab to show repeater when shortcut key is used.
     */
    @FXML
    public void handleShowRepeater() {
        // This triggers an invalidation event in FeatureParserIndexListener, resulting in a tab switch.
        logic.setFeatureParser(1);
    }

    /**
     * Switch the tab to show budget when shortcut key is used.
     */
    @FXML
    public void handleShowBudget() {
        // This triggers an invalidation event in FeatureParserIndexListener, resulting in a tab switch.
        logic.setFeatureParser(2);
    }

    /**
     * Switch the tab to show loan when shortcut key is used.
     */
    @FXML
    public void handleShowLoan() {
        // This triggers an invalidation event in FeatureParserIndexListener, resulting in a tab switch.
        logic.setFeatureParser(3);
    }

    /**
     * Switch the tab to show analytics.
     */
    @FXML
    public void handleShowAnalytics() {
        // This triggers an invalidation event in FeatureParserIndexListener, resulting in a tab switch.
        logic.setFeatureParser(4);
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

    public TransactionListPanel getTransactionListPanel() {
        return transactionListPanel;
    }

    public RepeaterListPanel getRepeaterListPanel() {
        return repeaterListPanel;
    }

    public BudgetListPanel getBudgetListPanel() {
        return budgetListPanel;
    }

    public DataListPanel getDataListPanel() {
        return dataListPanel;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.ichifund.logic.Logic#execute(String)
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
            logger.info("Error message displayed: " + e.getMessage());
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }

    /**
     * Listener for invalidation of currentFeatureParserIndex.
     */
    private class FeatureParserIndexListener implements InvalidationListener {
        private TabPane tabPane;

        public FeatureParserIndexListener(TabPane tabpane) {
            this.tabPane = tabpane;
        }

        @Override
        public void invalidated(Observable observable) {
            // Change tabs in TabPane
            logger.info("----------Switching to tab: " + currentFeatureParserIndex + "----------");
            this.tabPane.getSelectionModel().select(currentFeatureParserIndex.getValue());
        }
    }
}
