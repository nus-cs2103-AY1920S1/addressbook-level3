package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ALIAS_ALIAS_INPUT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALIAS_ALIAS_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FIRST_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERIOD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SECOND_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIMESTAMP;

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
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.ui.ViewPanelCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Timekeeper;
import seedu.address.model.budget.Budget;
import seedu.address.model.expense.Event;
import seedu.address.model.statistics.PieChartStatistics;
import seedu.address.model.statistics.Statistics;
import seedu.address.model.statistics.TabularStatistics;
import seedu.address.ui.budget.BudgetListPanel;
import seedu.address.ui.budget.BudgetPanel;
import seedu.address.ui.event.EventListPanel;
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
    private static final String MESSAGE_BUDGET_NEAR = "You are close to your budget limit.";
    private static final String MESSAGE_BUDGET_EXCEEDED = "Your budget is exceeded.";
    private static final Background BUDGET_WARNING_POPUP_BACKGROUND = new Background(
            new BackgroundFill(
                    Color.RED,
                    new CornerRadii(10),
                    Insets.EMPTY)
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
    private HelpWindow helpWindow;
    private StatsWindow statsWindow;

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
        ExpenseListPanel expenseListPanel;
        BudgetListPanel budgetListPanel;
        EventListPanel eventListPanel;

        singlePanelView = new SinglePanelView();
        expenseListPanel = new ExpenseListPanel(logic.getFilteredExpenseList(), true);
        budgetListPanel = new BudgetListPanel(logic.getFilteredBudgetList());
        eventListPanel = new EventListPanel(logic.getFilteredEventList(), true);

        if (logic.getPrimaryBudget() != null) {
            singlePanelView.setPanel(BudgetPanel.PANEL_NAME, new BudgetPanel(logic.getPrimaryBudget()));
        } else {
            singlePanelView.setPanel(BudgetPanel.PANEL_NAME, new PlaceholderPanel());
        }

        singlePanelView.setPanel(PanelName.ALIASES_PANEL, new PlaceholderPanel());
        singlePanelView.setPanel(BudgetListPanel.PANEL_NAME, budgetListPanel);
        singlePanelView.setPanel(ExpenseListPanel.PANEL_NAME, expenseListPanel);
        singlePanelView.setPanel(EventListPanel.PANEL_NAME, eventListPanel);

        singlePanelView.setPanel(PanelName.STATISTICS_PANEL, new PlaceholderPanel());
        panelPlaceholder.getChildren().add(singlePanelView.getRoot());
        expenseListPanel.view();

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getMooLahFilePath());
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
                List.of(PREFIX_DESCRIPTION, PREFIX_PRICE, PREFIX_CATEGORY));
        commandBox.enableSyntaxHighlightingForCommand("alias",
                List.of(PREFIX_ALIAS_ALIAS_NAME, PREFIX_ALIAS_ALIAS_INPUT));
        commandBox.enableSyntaxHighlightingForCommand("addbudget",
                List.of(PREFIX_DESCRIPTION, PREFIX_PRICE, PREFIX_START_DATE, PREFIX_PERIOD));
        commandBox.enableSyntaxHighlightingForCommand("switchbudget",
                List.of(PREFIX_DESCRIPTION));
        commandBox.enableSyntaxHighlightingForCommand("pastperiod",
                List.of(PREFIX_TIMESTAMP));
        commandBox.enableSyntaxHighlightingForCommand("event",
                List.of(PREFIX_DESCRIPTION, PREFIX_PRICE, PREFIX_CATEGORY, PREFIX_TIMESTAMP));
        commandBox.enableSyntaxHighlightingForCommand("statscompare",
                List.of(PREFIX_FIRST_START_DATE, PREFIX_SECOND_START_DATE, PREFIX_PERIOD));
        commandBox.enableSyntaxHighlightingForCommand("undo",
                Collections.emptyList());
        commandBox.enableSyntaxHighlightingForCommand("redo",
                Collections.emptyList());
        commandBox.enableSyntaxHighlightingForCommand("listbudgets",
                Collections.emptyList());
        commandBox.enableSyntaxHighlightingForCommand("listevents",
                Collections.emptyList());
        commandBox.enableSyntaxHighlightingForCommand("deletebudget",
                Collections.emptyList());
        commandBox.enableSyntaxHighlightingForCommand("deletefrombudget",
                Collections.emptyList());
        commandBox.enableSyntaxHighlightingForCommand("list",
                Collections.emptyList());
        commandBox.enableSyntaxHighlightingForCommand("delete",
                Collections.emptyList());
        commandBox.enableSyntaxHighlightingForCommand("edit",
                Collections.emptyList());
        commandBox.enableSyntaxHighlightingForCommand("editfrombudget",
                Collections.emptyList());
        commandBox.enableSyntaxHighlightingForCommand("editbudget",
                Collections.emptyList());
        commandBox.enableSyntaxHighlightingForCommand("view",
                Collections.emptyList());
        commandBox.enableSyntaxHighlightingForCommand("clear",
                Collections.emptyList());
        commandBox.enableSyntaxHighlightingForCommand("find",
                Collections.emptyList());
        commandBox.enableSyntaxHighlightingForCommand("exit",
                Collections.emptyList());
        commandBox.enableSyntaxHighlightingForCommand("help",
                Collections.emptyList());
        commandBox.enableSyntaxHighlightingForCommand("stats",
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

    /**
     * Opens the stats window or focuses on it if it's already opened.
     */
    @FXML
    private void displayStats(CommandResult commandResult) {
        Statistics currentStats = commandResult.getStatistics();
        if (currentStats instanceof PieChartStatistics) {
            this.statsWindow = new StatsWindow(commandResult.getNames(),
                    commandResult.getPercentages(), commandResult.getTitle());
        } else if (currentStats instanceof TabularStatistics) {
            this.statsWindow = new StatsWindow(commandResult.getTitle(), commandResult.getDifferenceTable());
        }
        requireNonNull(statsWindow);
        if (!statsWindow.isShowing()) {
            statsWindow.show();
        } else {
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
        timer.cancel();
        timer.purge();
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException,
            UnmappedPanelException {
        try {

            Budget primaryBudget = logic.getPrimaryBudget();
            boolean initialIsNear = primaryBudget.isNear();
            boolean initialIsExceeded = primaryBudget.isExceeded();

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

            if (commandResult.isStatistic()) {
                displayStats(commandResult);
            }

            boolean finalIsNear = primaryBudget.isNear();
            boolean finalIsExceeded = primaryBudget.isExceeded();

            showWarningIfAny(initialIsNear, initialIsExceeded, finalIsNear, finalIsExceeded);

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
        resultDisplay.setFeedbackToUser(timekeeper.displayReminders());
    }

    /**
     * Handles the opening of popup windows to notify the user of transpired events.
     */
    @FXML
    public void handleTranspiredEvents() {
        List<Event> transpiredEvents = timekeeper.getTranspiredEvents();
        for (Event event : transpiredEvents) {
            TranspiredEventsWindow eventWindow = new TranspiredEventsWindow(logic);
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
        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished(event -> popup.hide());
        delay.play();
    }

    /**
     * Determines if there is a need to show warnings, and shows the corresponding warnings.
     */
    public void showWarningIfAny(boolean initialIsNear, boolean initialIsExceeded,
                                 boolean finalIsNear, boolean finalIsExceeded) {
        if (!initialIsExceeded && finalIsExceeded) {
            showPopupMessage(MESSAGE_BUDGET_EXCEEDED);
            return;
        }

        if (!initialIsNear && finalIsNear) {
            showPopupMessage(MESSAGE_BUDGET_NEAR);
        }
    }
}
