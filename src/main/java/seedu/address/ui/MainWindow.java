package seedu.address.ui;

import java.util.List;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
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
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;
import seedu.address.model.projection.Projection;
import seedu.address.model.transaction.BankAccountOperation;
import seedu.address.model.transaction.Budget;
import seedu.address.model.transaction.LedgerOperation;
import seedu.address.ui.tab.Tab;

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
    private MainTabPanel mainTabPanel;
    private TransactionListPanel transactionListPanel;
    private BudgetListPanel budgetListPanel;
    private LedgerListPanel ledgerListPanel;
    private PersonListPanel peopleListPanel;
    private ProjectionListPanel projectionListPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;
    private StatusBarFooter statusBarFooter;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane mainTabPanelPlaceholder;

    @FXML
    private MenuItem transactionMenuItem;

    @FXML
    private MenuItem budgetMenuItem;

    @FXML
    private StackPane transactionListPanelPlaceholder;

    @FXML
    private StackPane budgetListPanelPlaceholder;

    @FXML
    private StackPane ledgerListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusBarPlaceholder;

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
        ObservableList<BankAccountOperation> transactionList = logic.getFilteredTransactionList();
        transactionListPanel = new TransactionListPanel(transactionList);

        ObservableList<Budget> budgetList = logic.getBudgetList();
        budgetListPanel = new BudgetListPanel(budgetList);

        ObservableList<LedgerOperation> ledgerOperationsList = logic.getLedgerOperationsList();
        ledgerListPanel = new LedgerListPanel(ledgerOperationsList);

        ObservableList<Person> people = logic.getPeopleInLedger();
        peopleListPanel = new PersonListPanel(people);

        ObservableList<Projection> projectionsList = logic.getProjectionList();
        projectionListPanel = new ProjectionListPanel(projectionsList);

        mainTabPanel = new MainTabPanel(transactionListPanel, budgetListPanel, ledgerListPanel, projectionListPanel,
            peopleListPanel);
        mainTabPanelPlaceholder.getChildren().add(mainTabPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        statusBarFooter = new StatusBarFooter(logic.getUserStateFilePath(), transactionList);
        statusBarPlaceholder.getChildren().add(statusBarFooter.getRoot());

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
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        primaryStage.hide();
    }

    /**
     * Handles the switching of tab depending on {@code tab}.
     */
    @FXML
    private void handleSwitchTab(Tab tab) {

        switch (tab) {
        case TRANSACTION:
            showTransactionTab();
            break;
        case BUDGET:
            showBudgetTab();
            break;
        case LEDGER:
            showLedgerTab();
            break;
        case PROJECTION:
            showProjectionTab();
            break;
        default:
            break;
        }
    }

    private void showLedgerBalance() {
        statusBarFooter.setBalance(logic.getUserState().getLedger().getBalance());
    }

    public TransactionListPanel getTransactionListPanel() {
        return transactionListPanel;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Command result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            // update bank balance display
            List<BankAccountOperation> transactionList = logic.getTransactionList();
            statusBarFooter.setBalance(transactionList);

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            if (commandResult.isSwitchTab()) {
                handleSwitchTab(commandResult.getTab());
            }

            return commandResult;

        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }

    /**
     * Switch to transaction tab.
     */
    private void showTransactionTab() {
        mainTabPanel.switchToTransactionTab();
    }

    /**
     * Switch to budget tab.
     */
    private void showBudgetTab() {
        mainTabPanel.switchToBudgetTab();
    }

    /**
     * Switch to ledger tab.
     */
    private void showLedgerTab() {
        showLedgerBalance();
        mainTabPanel.switchToLedgerTab();
    }

    private void showProjectionTab() {
        mainTabPanel.switchToProjectionTab();
    }

}
