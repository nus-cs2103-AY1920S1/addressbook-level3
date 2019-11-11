package seedu.moolah.ui;

import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.logging.Logger;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import seedu.moolah.commons.core.GuiSettings;
import seedu.moolah.commons.core.LogsCenter;
import seedu.moolah.logic.Logic;
import seedu.moolah.logic.commands.CommandGroup;
import seedu.moolah.logic.commands.CommandResult;
import seedu.moolah.logic.commands.GenericCommandWord;
import seedu.moolah.logic.commands.RedoCommand;
import seedu.moolah.logic.commands.UndoCommand;
import seedu.moolah.logic.commands.alias.AddAliasCommand;
import seedu.moolah.logic.commands.alias.DeleteAliasCommand;
import seedu.moolah.logic.commands.alias.ListAliasesCommand;
import seedu.moolah.logic.commands.budget.AddBudgetCommand;
import seedu.moolah.logic.commands.budget.ClearBudgetsCommand;
import seedu.moolah.logic.commands.budget.DeleteBudgetByIndexCommand;
import seedu.moolah.logic.commands.budget.DeleteBudgetByNameCommand;
import seedu.moolah.logic.commands.budget.DeleteExpenseFromBudgetCommand;
import seedu.moolah.logic.commands.budget.EditBudgetCommand;
import seedu.moolah.logic.commands.budget.EditExpenseFromBudgetCommand;
import seedu.moolah.logic.commands.budget.ListBudgetsCommand;
import seedu.moolah.logic.commands.budget.SwitchBudgetCommand;
import seedu.moolah.logic.commands.budget.SwitchPeriodCommand;
import seedu.moolah.logic.commands.event.AddEventCommand;
import seedu.moolah.logic.commands.event.DeleteEventCommand;
import seedu.moolah.logic.commands.event.EditEventCommand;
import seedu.moolah.logic.commands.event.ListEventsCommand;
import seedu.moolah.logic.commands.exceptions.CommandException;
import seedu.moolah.logic.commands.expense.AddExpenseCommand;
import seedu.moolah.logic.commands.expense.AddMenuExpenseCommand;
import seedu.moolah.logic.commands.expense.DeleteExpenseCommand;
import seedu.moolah.logic.commands.expense.EditExpenseCommand;
import seedu.moolah.logic.commands.expense.FindExpenseCommand;
import seedu.moolah.logic.commands.expense.ListExpensesCommand;
import seedu.moolah.logic.commands.general.ClearCommand;
import seedu.moolah.logic.commands.general.ExitCommand;
import seedu.moolah.logic.commands.general.HelpCommand;
import seedu.moolah.logic.commands.statistics.StatsCommand;
import seedu.moolah.logic.commands.statistics.StatsCompareCommand;
import seedu.moolah.logic.commands.statistics.StatsTrendCommand;
import seedu.moolah.logic.commands.ui.ViewPanelCommand;
import seedu.moolah.logic.parser.alias.AddAliasCommandParser;
import seedu.moolah.logic.parser.budget.AddBudgetCommandParser;
import seedu.moolah.logic.parser.budget.DeleteBudgetByNameCommandParser;
import seedu.moolah.logic.parser.budget.DeleteExpenseFromBudgetCommandParser;
import seedu.moolah.logic.parser.budget.EditBudgetCommandParser;
import seedu.moolah.logic.parser.budget.EditExpenseFromBudgetCommandParser;
import seedu.moolah.logic.parser.budget.SwitchBudgetCommandParser;
import seedu.moolah.logic.parser.budget.SwitchPeriodCommandParser;
import seedu.moolah.logic.parser.event.AddEventCommandParser;
import seedu.moolah.logic.parser.event.EditEventCommandParser;
import seedu.moolah.logic.parser.exceptions.ParseException;
import seedu.moolah.logic.parser.expense.AddExpenseCommandParser;
import seedu.moolah.logic.parser.expense.AddMenuExpenseCommandParser;
import seedu.moolah.logic.parser.expense.EditExpenseCommandParser;
import seedu.moolah.logic.parser.statistics.StatsCommandParser;
import seedu.moolah.logic.parser.statistics.StatsCompareCommandParser;
import seedu.moolah.logic.parser.statistics.StatsTrendCommandParser;
import seedu.moolah.model.Timekeeper;
import seedu.moolah.model.expense.Event;
import seedu.moolah.model.statistics.Statistics;
import seedu.moolah.ui.alias.AliasListPanel;
import seedu.moolah.ui.budget.BudgetListPanel;
import seedu.moolah.ui.budget.BudgetPanel;
import seedu.moolah.ui.event.EventListPanel;
import seedu.moolah.ui.expense.ExpenseListPanel;
import seedu.moolah.ui.panel.PanelName;
import seedu.moolah.ui.panel.PlaceholderPanel;
import seedu.moolah.ui.panel.SinglePanelView;
import seedu.moolah.ui.panel.exceptions.UnmappedPanelException;
import seedu.moolah.ui.statistics.StatisticsRegionFactory;
import seedu.moolah.ui.statistics.StatsPanel;
import seedu.moolah.ui.textfield.CommandBox;


/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";
    private static final String MESSAGE_BUDGET_HALF = "You have spent half of your budget.";
    private static final String MESSAGE_BUDGET_NEAR = "You are close to your budget limit.";
    private static final String MESSAGE_BUDGET_EXCEEDED = "You have exceeded your budget! "
            + "Time to rein in your spending.";
    private static final Background BUDGET_WARNING_POPUP_BACKGROUND = new Background(
            new BackgroundFill(
                    Paint.valueOf("cf6679"),
                    new CornerRadii(10),
                    new Insets(-15))
    );

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;
    private Timekeeper timekeeper;
    private Timer timer;

    // Panel Manager which manages which panel(extending UiPart Region) is displayed.
    private SinglePanelView singlePanelView;

    // Ui parts which are always displayed
    private ResultDisplay resultDisplay;
    private CommandBox commandBox;

    // Popup windows
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

    public MainWindow(Stage primaryStage, Logic logic, Timekeeper timekeeper, Timer timer) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;
        this.timekeeper = timekeeper;
        this.timer = timer;

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
        // fill inner UiParts
        commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getMooLahFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        singlePanelView = new SinglePanelView();
        panelPlaceholder.getChildren().add(singlePanelView.getRoot());

        // fill single panel view
        singlePanelView.setPanel(BudgetPanel.PANEL_NAME, new BudgetPanel(logic.getPrimaryBudget()));

        singlePanelView.setPanel(AliasListPanel.PANEL_NAME, new AliasListPanel(logic.getAliasMappings()));
        singlePanelView.setPanel(ExpenseListPanel.PANEL_NAME,
                new ExpenseListPanel(logic.getFilteredExpenseList(), true));
        singlePanelView.setPanel(BudgetListPanel.PANEL_NAME,
                new BudgetListPanel(logic.getFilteredBudgetList()));
        singlePanelView.setPanel(EventListPanel.PANEL_NAME,
                new EventListPanel(logic.getFilteredEventList(), true));
        singlePanelView.setPanel(StatsPanel.PANEL_NAME, new PlaceholderPanel());

        // startup panel = expense list panel
        try {
            changePanel(ExpenseListPanel.PANEL_NAME);
        } catch (UnmappedPanelException e) {
            // should not be thrown
        }

        // enable syntax highlighting
        enableSyntaxHighlighting();

    }

    /**
     * Enables syntax highlighting for a built in commands.
     */

    private void enableSyntaxHighlighting() {
        // expense commands
        commandBox.enableSuggestionAndSyntaxHighlightingFor(
                AddExpenseCommand.COMMAND_WORD,
                AddExpenseCommandParser.REQUIRED_PREFIXES,
                AddExpenseCommandParser.OPTIONAL_PREFIXES);
        commandBox.enableSuggestionAndSyntaxHighlightingFor(
                AddMenuExpenseCommand.COMMAND_WORD,
                AddMenuExpenseCommandParser.REQUIRED_PREFIXES,
                AddMenuExpenseCommandParser.OPTIONAL_PREFIXES
        );
        commandBox.enableSuggestionAndSyntaxHighlightingFor(
                DeleteExpenseCommand.COMMAND_WORD,
                Collections.emptyList(),
                Collections.emptyList());
        commandBox.enableSuggestionAndSyntaxHighlightingFor(
                DeleteExpenseFromBudgetCommand.COMMAND_WORD,
                Collections.emptyList(),
                Collections.emptyList());
        commandBox.enableSuggestionAndSyntaxHighlightingFor(
                EditExpenseCommand.COMMAND_WORD,
                EditExpenseCommandParser.REQUIRED_PREFIXES,
                EditExpenseCommandParser.OPTIONAL_PREFIXES);
        commandBox.enableSuggestionAndSyntaxHighlightingFor(
                EditExpenseFromBudgetCommand.COMMAND_WORD,
                EditExpenseCommandParser.REQUIRED_PREFIXES,
                EditExpenseCommandParser.OPTIONAL_PREFIXES);
        commandBox.enableSuggestionAndSyntaxHighlightingFor(
                FindExpenseCommand.COMMAND_WORD,
                Collections.emptyList(),
                Collections.emptyList());
        commandBox.enableSuggestionAndSyntaxHighlightingFor(
                ListExpensesCommand.COMMAND_WORD,
                Collections.emptyList(),
                Collections.emptyList());

        // event commands
        commandBox.enableSuggestionAndSyntaxHighlightingFor(
                AddEventCommand.COMMAND_WORD,
                AddEventCommandParser.REQUIRED_PREFIXES,
                AddEventCommandParser.OPTIONAL_PREFIXES);
        commandBox.enableSuggestionAndSyntaxHighlightingFor(
                EditEventCommand.COMMAND_WORD,
                EditEventCommandParser.REQUIRED_PREFIXES,
                EditEventCommandParser.OPTIONAL_PREFIXES);
        commandBox.enableSuggestionAndSyntaxHighlightingFor(
                DeleteEventCommand.COMMAND_WORD,
                Collections.emptyList(),
                Collections.emptyList());
        commandBox.enableSuggestionAndSyntaxHighlightingFor(
                ListEventsCommand.COMMAND_WORD,
                Collections.emptyList(),
                Collections.emptyList());

        // budget commands
        commandBox.enableSuggestionAndSyntaxHighlightingFor(
                AddBudgetCommand.COMMAND_WORD,
                AddBudgetCommandParser.REQUIRED_PREFIXES,
                AddBudgetCommandParser.OPTIONAL_PREFIXES);
        commandBox.enableSuggestionAndSyntaxHighlightingFor(
                SwitchBudgetCommand.COMMAND_WORD,
                SwitchBudgetCommandParser.REQUIRED_PREFIXES,
                SwitchBudgetCommandParser.OPTIONAL_PREFIXES);
        commandBox.enableSuggestionAndSyntaxHighlightingFor(
                ListBudgetsCommand.COMMAND_WORD,
                Collections.emptyList(),
                Collections.emptyList());
        commandBox.enableSuggestionAndSyntaxHighlightingFor(
                DeleteBudgetByIndexCommand.COMMAND_WORD,
                Collections.emptyList(),
                Collections.emptyList());
        commandBox.enableSuggestionAndSyntaxHighlightingFor(
                DeleteBudgetByNameCommand.COMMAND_WORD,
                DeleteBudgetByNameCommandParser.REQUIRED_PREFIXES,
                DeleteBudgetByNameCommandParser.OPTIONAL_PREFIXES);
        commandBox.enableSuggestionAndSyntaxHighlightingFor(
                EditBudgetCommand.COMMAND_WORD,
                EditBudgetCommandParser.REQUIRED_PREFIXES,
                EditBudgetCommandParser.OPTIONAL_PREFIXES);
        commandBox.enableSuggestionAndSyntaxHighlightingFor(
                SwitchPeriodCommand.COMMAND_WORD,
                SwitchPeriodCommandParser.REQUIRED_PREFIXES,
                SwitchPeriodCommandParser.OPTIONAL_PREFIXES);
        commandBox.enableSuggestionAndSyntaxHighlightingFor(
                ClearBudgetsCommand.COMMAND_WORD,
                Collections.emptyList(),
                Collections.emptyList());

        // alias commands
        commandBox.enableSuggestionAndSyntaxHighlightingFor(
                AddAliasCommand.COMMAND_WORD,
                AddAliasCommandParser.REQUIRED_PREFIXES,
                AddAliasCommandParser.OPTIONAL_PREFIXES);
        commandBox.enableSuggestionAndSyntaxHighlightingFor(
                DeleteAliasCommand.COMMAND_WORD,
                Collections.emptyList(),
                Collections.emptyList());
        commandBox.enableSuggestionAndSyntaxHighlightingFor(
                ListAliasesCommand.COMMAND_WORD,
                Collections.emptyList(),
                Collections.emptyList());

        // stats commands
        commandBox.enableSuggestionAndSyntaxHighlightingFor(
                StatsCommand.COMMAND_WORD,
                StatsCommandParser.REQUIRED_PREFIXES,
                StatsCommandParser.OPTIONAL_PREFIXES);
        commandBox.enableSuggestionAndSyntaxHighlightingFor(
                StatsCompareCommand.COMMAND_WORD,
                StatsCompareCommandParser.REQUIRED_PREFIXES,
                StatsCompareCommandParser.OPTIONAL_PREFIXES);
        commandBox.enableSuggestionAndSyntaxHighlightingFor(
                StatsTrendCommand.COMMAND_WORD,
                StatsTrendCommandParser.REQUIRED_PREFIXES,
                StatsTrendCommandParser.OPTIONAL_PREFIXES);

        // general commands
        commandBox.enableSuggestionAndSyntaxHighlightingFor(
                UndoCommand.COMMAND_WORD,
                Collections.emptyList(),
                Collections.emptyList());
        commandBox.enableSuggestionAndSyntaxHighlightingFor(
                RedoCommand.COMMAND_WORD,
                Collections.emptyList(),
                Collections.emptyList());
        commandBox.enableSuggestionAndSyntaxHighlightingFor(
                HelpCommand.COMMAND_WORD,
                Collections.emptyList(),
                Collections.emptyList());
        commandBox.enableSuggestionAndSyntaxHighlightingFor(
                ExitCommand.COMMAND_WORD,
                Collections.emptyList(),
                Collections.emptyList());
        commandBox.enableSuggestionAndSyntaxHighlightingFor(
                ViewPanelCommand.COMMAND_WORD,
                Collections.emptyList(),
                Collections.emptyList());
        commandBox.enableSuggestionAndSyntaxHighlightingFor(
                ClearCommand.COMMAND_WORD,
                Collections.emptyList(),
                Collections.emptyList());
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
    void changePanel(PanelName panelName) throws UnmappedPanelException {
        configureGenericCommands(panelName);

        if (panelName.equals(AliasListPanel.PANEL_NAME)) {
            singlePanelView.setPanel(AliasListPanel.PANEL_NAME, new AliasListPanel(logic.getAliasMappings()));
        } else if (panelName.equals(BudgetPanel.PANEL_NAME)) {
            singlePanelView.setPanel(BudgetPanel.PANEL_NAME, new BudgetPanel(logic.getPrimaryBudget()));
        } else if (panelName.equals(BudgetListPanel.PANEL_NAME)) {
            singlePanelView.setPanel(BudgetListPanel.PANEL_NAME, new BudgetListPanel(logic.getFilteredBudgetList()));
        } else if (panelName.equals(ExpenseListPanel.PANEL_NAME)) {
            singlePanelView.setPanel(ExpenseListPanel.PANEL_NAME,
                    new ExpenseListPanel(logic.getFilteredExpenseList(), true));
        } else if (panelName.equals(EventListPanel.PANEL_NAME)) {
            singlePanelView.setPanel(EventListPanel.PANEL_NAME,
                    new EventListPanel(logic.getFilteredEventList(), true));
        } else if (panelName.equals(StatsPanel.PANEL_NAME)) {
            try {
                populateStatisticsPanel();
            } catch (NullPointerException e) {
                singlePanelView.setPanel(StatsPanel.PANEL_NAME, new PlaceholderPanel());
            }
        }
        singlePanelView.viewPanel(panelName);
    }

    /**
     * Changes the currently viewed Panel in the MainWindow to the Statistics Panel
     */
    private void populateStatisticsPanel() {
        Statistics statistics = logic.getStatistics();
        StatisticsRegionFactory factory = statistics.createFactory();
        Region data = factory.createRegion();
        String localTitle = factory.getTitle();
        singlePanelView.setPanel(StatsPanel.PANEL_NAME, new StatsPanel(data, localTitle));
    }


    /**
     * Configures the custom text field to highlight for syntax for generic commands depending on the current panel.
     */
    private void configureGenericCommands(PanelName panelName) {
        commandBox.disableSuggestionsAndSyntaxHighlightingFor(GenericCommandWord.ADD);
        commandBox.disableSuggestionsAndSyntaxHighlightingFor(GenericCommandWord.DELETE);
        commandBox.disableSuggestionsAndSyntaxHighlightingFor(GenericCommandWord.LIST);
        commandBox.disableSuggestionsAndSyntaxHighlightingFor(GenericCommandWord.EDIT);

        // primary budget command
        if (panelName.equals(BudgetPanel.PANEL_NAME)) {
            commandBox.enableSuggestionAndSyntaxHighlightingFor(
                    GenericCommandWord.ADD,
                    AddExpenseCommandParser.REQUIRED_PREFIXES,
                    AddExpenseCommandParser.OPTIONAL_PREFIXES);
            commandBox.enableSuggestionAndSyntaxHighlightingFor(
                    GenericCommandWord.DELETE,
                    EditExpenseFromBudgetCommandParser.REQUIRED_PREFIXES,
                    EditExpenseFromBudgetCommandParser.OPTIONAL_PREFIXES);
            commandBox.enableSuggestionAndSyntaxHighlightingFor(
                    GenericCommandWord.EDIT,
                    DeleteExpenseFromBudgetCommandParser.REQUIRED_PREFIXES,
                    DeleteExpenseFromBudgetCommandParser.OPTIONAL_PREFIXES);

        //expense list command
        } else if (panelName.equals(ExpenseListPanel.PANEL_NAME)) {
            commandBox.enableSuggestionAndSyntaxHighlightingFor(
                    GenericCommandWord.ADD,
                    AddExpenseCommandParser.REQUIRED_PREFIXES,
                    AddExpenseCommandParser.OPTIONAL_PREFIXES);
            commandBox.enableSuggestionAndSyntaxHighlightingFor(
                    GenericCommandWord.LIST,
                    Collections.emptyList(),
                    Collections.emptyList());
            commandBox.enableSuggestionAndSyntaxHighlightingFor(
                    GenericCommandWord.DELETE,
                    Collections.emptyList(),
                    Collections.emptyList());

        //event list command
        } else if (panelName.equals(EventListPanel.PANEL_NAME)) {
            commandBox.enableSuggestionAndSyntaxHighlightingFor(
                    GenericCommandWord.ADD,
                    AddEventCommandParser.REQUIRED_PREFIXES,
                    AddEventCommandParser.OPTIONAL_PREFIXES);
            commandBox.enableSuggestionAndSyntaxHighlightingFor(
                    GenericCommandWord.LIST,
                    Collections.emptyList(),
                    Collections.emptyList());
            commandBox.enableSuggestionAndSyntaxHighlightingFor(
                    GenericCommandWord.DELETE,
                    Collections.emptyList(),
                    Collections.emptyList());
            commandBox.enableSuggestionAndSyntaxHighlightingFor(
                    GenericCommandWord.EDIT,
                    EditEventCommandParser.REQUIRED_PREFIXES,
                    EditEventCommandParser.OPTIONAL_PREFIXES);

        // budget list command
        } else if (panelName.equals(BudgetListPanel.PANEL_NAME)) {
            commandBox.enableSuggestionAndSyntaxHighlightingFor(
                    GenericCommandWord.ADD,
                    AddBudgetCommandParser.REQUIRED_PREFIXES,
                    AddBudgetCommandParser.OPTIONAL_PREFIXES);
            commandBox.enableSuggestionAndSyntaxHighlightingFor(
                    GenericCommandWord.LIST,
                    Collections.emptyList(),
                    Collections.emptyList());

        } else if (panelName.equals(AliasListPanel.PANEL_NAME)) {
            commandBox.enableSuggestionAndSyntaxHighlightingFor(
                    GenericCommandWord.ADD,
                    AddAliasCommandParser.REQUIRED_PREFIXES,
                    AddAliasCommandParser.OPTIONAL_PREFIXES);
            commandBox.enableSuggestionAndSyntaxHighlightingFor(
                    GenericCommandWord.DELETE,
                    Collections.emptyList(),
                    Collections.emptyList());
            commandBox.enableSuggestionAndSyntaxHighlightingFor(
                    GenericCommandWord.LIST,
                    Collections.emptyList(),
                    Collections.emptyList());

        } else if (panelName.equals(StatsPanel.PANEL_NAME)) {
            // does not use generic commands
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
        timer.cancel();
        timer.purge();
    }

    /**
     * Executes the command and returns the result. If the command is a generic command, append the command group based
     * on the current panel.
     *
     * @see Logic#execute(String, String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException,
            UnmappedPanelException {

        try {
            boolean[] initialPrimaryBudgetStatus = logic.recordInitialPrimaryBudgetStatus();

            String commandGroup = decideCommandGroup();
            CommandResult commandResult = logic.execute(commandText, commandGroup);

            changePanel(commandResult.viewRequest());

            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            boolean[] finalPrimaryBudgetStatus = logic.recordFinalPrimaryBudgetStatus();

            showWarningIfAny(initialPrimaryBudgetStatus, finalPrimaryBudgetStatus);

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
     * Decides what the command group should be based on the current panel name.
     */
    private String decideCommandGroup() {
        if (BudgetPanel.PANEL_NAME.equals(singlePanelView.getCurrentPanelName())) {
            return CommandGroup.PRIMARY_BUDGET;
        } else if (ExpenseListPanel.PANEL_NAME.equals(singlePanelView.getCurrentPanelName())) {
            return CommandGroup.EXPENSE;
        } else if (EventListPanel.PANEL_NAME.equals(singlePanelView.getCurrentPanelName())) {
            return CommandGroup.EVENT;
        } else if (AliasListPanel.PANEL_NAME.equals(singlePanelView.getCurrentPanelName())) {
            return CommandGroup.ALIAS;
        } else if (StatsPanel.PANEL_NAME.equals(singlePanelView.getCurrentPanelName())) {
            return CommandGroup.STATISTIC;
        }
        return CommandGroup.GENERAL;
    }

    /**
     * Displays Reminders of the user's upcoming Events.
     */
    public void displayReminders() {
        resultDisplay.setFeedbackToUser(timekeeper.displayReminders());
    }

    /**
     * Handles the opening of popup windows to notify the user of transpired events.
     */
    @FXML
    public void handleTranspiredEvents() {
        List<Event> transpiredEvents = timekeeper.getTranspiredEvents();
        for (Event event : transpiredEvents) {
            logic.deleteTranspiredEvent(event);
            TranspiredEventsWindow eventWindow = new TranspiredEventsWindow(logic, this);
            eventWindow.show(event);
        }
    }

    /**
     * Creates a popup for warnings related to budget.
     */
    public Popup createPopup(String message) {
        Popup popup = new Popup();
        popup.setAutoFix(true);
        popup.setAutoHide(true);
        popup.setHideOnEscape(true);
        Label label = new Label(message);
        label.setBackground(BUDGET_WARNING_POPUP_BACKGROUND);
        label.setFont(new Font(17));
        label.setTextFill(Color.WHITE);
        popup.getContent().add(label);
        return popup;
    }

    /**
     * Handles the showing and disappearing of popup warnings.
     */
    public void showPopupMessage(String message) {
        Popup popup = createPopup(message);
        popup.setOnShown(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent e) {
                popup.setX(primaryStage.getX() + primaryStage.getWidth() / 2 - popup.getWidth() / 2);
                popup.setY(primaryStage.getY() + primaryStage.getHeight() / 2 - popup.getHeight() / 2);
            }
        });
        popup.show(primaryStage);
        PauseTransition delay = new PauseTransition(Duration.seconds(3));
        delay.setOnFinished(event -> popup.hide());
        delay.play();
    }

    /**
     * Determines if there is a need to show warnings, and shows the corresponding warnings.
     */
    public void showWarningIfAny(boolean[] initialStatus, boolean[] finalStatus) {

        int isExceededId = 0;
        int isNearId = 1;
        int isHalfId = 2;

        if (!initialStatus[isExceededId] && finalStatus[isExceededId]) {
            showPopupMessage(MESSAGE_BUDGET_EXCEEDED);
            return;
        }

        if (!initialStatus[isNearId] && finalStatus[isNearId]) {
            showPopupMessage(MESSAGE_BUDGET_NEAR);
            return;
        }

        if (!initialStatus[isHalfId] && finalStatus[isHalfId]) {
            showPopupMessage(MESSAGE_BUDGET_HALF);
        }
    }
}
