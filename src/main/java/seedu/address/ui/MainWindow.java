package seedu.address.ui;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ALIAS_ALIAS_INPUT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALIAS_ALIAS_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERIOD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIMESTAMP;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

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
import seedu.address.logic.commands.ui.ViewPanelCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.ui.budget.BudgetListPanel;
import seedu.address.ui.budget.BudgetPanel;
import seedu.address.ui.expense.ExpenseListPanel;
import seedu.address.ui.panel.PanelName;
import seedu.address.ui.panel.PlaceholderPanel;
import seedu.address.ui.panel.SinglePanelView;
import seedu.address.ui.panel.exceptions.UnmappedPanelException;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Panel Manager which manages which panel(extending UiPart Region) is displayed.
    private SinglePanelView singlePanelView;

    // Ui parts which are always displayed
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane panelPlaceholder;

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
        ExpenseListPanel expenseListPanel;
        BudgetListPanel budgetListPanel;

        singlePanelView = new SinglePanelView();
        expenseListPanel = new ExpenseListPanel(logic.getFilteredExpenseList(), true);
        budgetListPanel = new BudgetListPanel(logic.getFilteredBudgetList());

        if (logic.getPrimaryBudget() != null) {
            singlePanelView.setPanel(BudgetPanel.PANEL_NAME, new BudgetPanel(logic.getPrimaryBudget()));
        } else {
            singlePanelView.setPanel(BudgetPanel.PANEL_NAME, new PlaceholderPanel());
        }

        singlePanelView.setPanel(PanelName.ALIASES_PANEL, new PlaceholderPanel());
        singlePanelView.setPanel(BudgetListPanel.PANEL_NAME, budgetListPanel);
        singlePanelView.setPanel(ExpenseListPanel.PANEL_NAME, expenseListPanel);

        singlePanelView.setPanel(PanelName.EVENTS_PANEL, new PlaceholderPanel());
        singlePanelView.setPanel(PanelName.STATISTICS_PANEL, new PlaceholderPanel());
        panelPlaceholder.getChildren().add(singlePanelView.getRoot());
        expenseListPanel.view();

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        enableSyntaxHighlighting(commandBox);

        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    /**
     * Enables syntax highlighting for a set of commands in a specified commandBox.
     * @param commandBox The commandBox to enable syntax highlighting in.
     */
    private void enableSyntaxHighlighting(CommandBox commandBox) {
        commandBox.importSyntaxStyleSheet(getRoot().getScene());

        // add supported commands (not all yet)
        commandBox.enableSyntaxHighlightingForCommand("add",
                List.of(PREFIX_PRICE, PREFIX_DESCRIPTION));
        commandBox.enableSyntaxHighlightingForCommand("alias",
                List.of(PREFIX_ALIAS_ALIAS_NAME, PREFIX_ALIAS_ALIAS_INPUT));
        commandBox.enableSyntaxHighlightingForCommand("budget",
                List.of(PREFIX_DESCRIPTION, PREFIX_PRICE, PREFIX_START_DATE, PREFIX_PERIOD));
        commandBox.enableSyntaxHighlightingForCommand("switchbudget",
                List.of(PREFIX_DESCRIPTION, PREFIX_PRICE, PREFIX_START_DATE, PREFIX_PERIOD));
        commandBox.enableSyntaxHighlightingForCommand("event",
                List.of(PREFIX_DESCRIPTION, PREFIX_PRICE, PREFIX_CATEGORY, PREFIX_TIMESTAMP));
        commandBox.enableSyntaxHighlightingForCommand("stats",
                List.of(PREFIX_DESCRIPTION, PREFIX_START_DATE, PREFIX_END_DATE));
        commandBox.enableSyntaxHighlightingForCommand("undo",
                Collections.emptyList());
        commandBox.enableSyntaxHighlightingForCommand("redo",
                Collections.emptyList());
        commandBox.enableSyntaxHighlighting();
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
     * Changes the currently viewed Panel in the MainWindow.
     * @param panelName The Panel Name of assigned to the Panel.
     * @throws UnmappedPanelException if there is no Panel assigned to the specified Panel Name.
     */
    private void changePanel(PanelName panelName) throws UnmappedPanelException {
        // updates the budget panel to display the primary budget.
        if (panelName.equals(BudgetPanel.PANEL_NAME)) {
            singlePanelView.setPanel(BudgetPanel.PANEL_NAME, new BudgetPanel(logic.getPrimaryBudget()));
        }
        singlePanelView.viewPanel(panelName);
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
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException,
            UnmappedPanelException {
        try {
            CommandResult commandResult = logic.execute(commandText);

            if (commandResult.isViewRequest()) {
                changePanel(commandResult.viewRequest());
            }

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
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        } catch (UnmappedPanelException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage() + "\n"
                    + String.format(ViewPanelCommand.SHOW_AVAILABLE_PANELS, singlePanelView.toString()));
            throw e;
        }
    }

    /**
     * Displays Reminders of the user's upcoming Events.
     */
    public void displayReminders() {
        resultDisplay.setFeedbackToUser(logic.displayReminders());
    }

}
