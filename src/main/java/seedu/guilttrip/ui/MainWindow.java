package seedu.guilttrip.ui;

import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import seedu.guilttrip.commons.core.GuiSettings;
import seedu.guilttrip.commons.core.LogsCenter;
import seedu.guilttrip.logic.Logic;
import seedu.guilttrip.logic.commands.CommandResult;
import seedu.guilttrip.logic.commands.GuiltTripCommandSuggester;
import seedu.guilttrip.logic.commands.exceptions.CommandException;
import seedu.guilttrip.logic.parser.exceptions.ParseException;
import seedu.guilttrip.model.reminders.Reminder;
import seedu.guilttrip.ui.autoexpense.AutoExpensesPanel;
import seedu.guilttrip.ui.budget.BudgetPanel;
import seedu.guilttrip.ui.condition.ConditionPanel;
import seedu.guilttrip.ui.expense.ExpenseListPanel;
import seedu.guilttrip.ui.income.IncomeListPanel;
import seedu.guilttrip.ui.reminder.NotificationPanel;
import seedu.guilttrip.ui.reminder.ReminderPanel;
import seedu.guilttrip.ui.stats.StatisticsBarChart;
import seedu.guilttrip.ui.stats.StatisticsPieChartPanel;
import seedu.guilttrip.ui.stats.StatisticsWindow;
import seedu.guilttrip.ui.util.FontName;
import seedu.guilttrip.ui.util.PanelName;
import seedu.guilttrip.ui.util.Theme;
import seedu.guilttrip.ui.wishlist.WishListPanel;

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
    private ExpenseListPanel expenseListPanel;
    private IncomeListPanel incomeListPanel;
    private ReminderPanel reminderPanel;
    private BudgetPanel budgetPanel;
    private NotificationPanel notificationPanel;
    private WishListPanel wishListPanel;
    private AutoExpensesPanel autoExpensesPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;
    private PopupWindow popupWindow;
    private StatisticsWindow statsListPanel;
    private StatisticsPieChartPanel statsGraphics;
    private StatisticsBarChart statsBar;
    private BudgetPanel budgetsPanel;

    // Customisable GUI elements
    private FontName font;
    private Theme theme;

    @FXML
    private Scene scene;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private VBox mainPanel;

    @FXML
    private StackPane expenseListPanelPlaceholder;

    @FXML
    private StackPane incomeListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private HBox window;

    @FXML
    private VBox sidePanelsPlaceHolder;

    @FXML
    private VBox wishesPlaceHolder;

    @FXML
    private VBox budgetsPlaceHolder;

    @FXML
    private VBox remindersPlaceHolder;

    @FXML
    private VBox autoExpensesPlaceHolder;

    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setUpGui(logic.getGuiSettings());

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
        statsListPanel = new StatisticsWindow(logic.getListOfStatsForExpense(), logic.getListOfStatsForIncome(),
                logic.getTotalExpenseForPeriod(), logic.getTotalIncomeForPeriod());
        statsBar = new StatisticsBarChart(logic.getListOfStatsForBarChart());

        this.expenseListPanel = new ExpenseListPanel(logic.getFilteredExpenseList());
        this.incomeListPanel = new IncomeListPanel(logic.getFilteredIncomeList());
        mainPanel.getChildren().addAll(this.expenseListPanel.getRoot(), this.incomeListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getGuiltTripFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand, this::suggestCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        this.wishListPanel = new WishListPanel(logic.getFilteredWishList());
        wishesPlaceHolder.getChildren().add(this.wishListPanel.getRoot());

        this.budgetPanel = new BudgetPanel(logic.getFilteredBudgetList());
        budgetsPlaceHolder.getChildren().add(this.budgetPanel.getRoot());

        this.reminderPanel = new ReminderPanel(logic.getFilteredReminders());
        this.notificationPanel = new NotificationPanel(logic.getFilteredNotifications());
        remindersPlaceHolder.getChildren().add(notificationPanel.getRoot());

        this.autoExpensesPanel = new AutoExpensesPanel(logic.getFilteredAutoExpenseList());
        autoExpensesPlaceHolder.getChildren().add(this.autoExpensesPanel.getRoot());
    }

    /**
     * Sets up the GUI.
     */
    private void setUpGui(GuiSettings guiSettings) {
        setWindowDefaultSize(guiSettings);
        setFont(guiSettings.getFont());
        setTheme(guiSettings.getTheme());
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
     * Sets the font based on {@code guiSettings}.
     */
    private void setFont(FontName savedFont) {
        this.font = savedFont;
        String style = "-fx-font-family: " + FontName.toLowerCaseString(savedFont);
        window.setStyle(style);
    }

    /**
     * Sets the theme based on {@code guiSettings}.
     */
    private void setTheme(Theme savedTheme) {
        this.theme = savedTheme;
        switchThemeTo(savedTheme);
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
                (int) primaryStage.getX(), (int) primaryStage.getY(), font, theme);
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        primaryStage.hide();
    }

    /**
     * Toggles the isVisible and isManaged property for the specified panel.
     * Checks if the entire side panel needs to be toggled as well.
     */
    private void handleTogglePanel(PanelName panelName) throws CommandException {
        togglePanel(panelName);
        toggleEntireSidePanelIfNecessary();
    }

    /**
     * Calls the togglePlaceHolder method with the place holder of the specified panel.
     *
     * @param panelName name of the specified panel to be toggled.
     */
    private void togglePanel(PanelName panelName) throws CommandException {
        switch (panelName) {
        case WISH:
            if (mainPanel.getChildren().contains(wishListPanel.getRoot())) {
                throw new CommandException("The panel you want to toggle is already shown in the main panel!");
            }
            togglePlaceHolder(wishesPlaceHolder);
            break;
        case BUDGET:
            if (mainPanel.getChildren().contains(budgetPanel.getRoot())) {
                throw new CommandException("The panel you want to toggle is already shown in the main panel!");
            }
            togglePlaceHolder(budgetsPlaceHolder);
            break;

        case REMINDER:
            if (mainPanel.getChildren().contains(notificationPanel.getRoot())) {
                throw new CommandException("The panel you want to toggle is already shown in the main panel!");
            }
            togglePlaceHolder(remindersPlaceHolder);
            break;
        case AUTOEXPENSE:
            togglePlaceHolder(autoExpensesPlaceHolder);
            break;
        default:
            break;
        }
        logger.info("Toggled " + panelName + " side panel");
    }

    /**
     * Replaces the GeneralReminder Panel with the Conditions Panel.
     */
    private void showConditionPanel() {
        remindersPlaceHolder.getChildren().clear();
        remindersPlaceHolder.getChildren().add(new Label("Conditions"));
        ConditionPanel conditionPanel = new ConditionPanel(logic.getFilteredConditions());
        remindersPlaceHolder.getChildren().add(conditionPanel.getRoot());
    }

    /**
     * Replaces the Conditions Panel with the GeneralReminder Panel.
     */
    private void showReminderPanel() {
        remindersPlaceHolder.getChildren().clear();
        remindersPlaceHolder.getChildren().add(new Label("Reminders"));
        NotificationPanel notificationPanel = new NotificationPanel(logic.getFilteredNotifications());
        remindersPlaceHolder.getChildren().add(notificationPanel.getRoot());
    }

    /**
     * Replaces the old selected reminder with a new one.
     */
    public void updateReminderSelected(Reminder reminder) {
        reminderPanel.updateReminderSelected(reminder);
    }

    /**
     * Toggles the isVisible and isManaged properties of the specified place holder.
     * @param placeHolder specified place holder to be toggled.
     */
    private void togglePlaceHolder(VBox placeHolder) {
        boolean isManaged = placeHolder.isManaged();
        placeHolder.setManaged(!isManaged);
        boolean isVisible = placeHolder.isVisible();
        placeHolder.setVisible(!isVisible);
    }

    /**
     * Toggles the isVisible and isManaged properties of the sidePanelsPlaceHolder in preparation for switching panels.
     */
    private void togglePlaceHolderForStats(boolean isStatsWindow) {
        if (isStatsWindow) {
            sidePanelsPlaceHolder.setManaged(false);
            sidePanelsPlaceHolder.setVisible(false);
        } else {
            sidePanelsPlaceHolder.setManaged(true);
            sidePanelsPlaceHolder.setVisible(true);
            toggleAllTrue();
        }
    }

    /**
     * Sets both the isVisible and isManaged properties the side panel place holder to false if none of the side panels
     * are visible and managed.
     * Otherwise, both of those properties are set to true.
     */
    private void toggleEntireSidePanelIfNecessary() {
        if (!wishesPlaceHolder.isManaged() && !budgetsPlaceHolder.isManaged() && !remindersPlaceHolder.isManaged()
                && !autoExpensesPlaceHolder.isManaged()) {
            sidePanelsPlaceHolder.setManaged(false);
            sidePanelsPlaceHolder.setVisible(false);
            logger.info("Toggled entire side panel off");
        } else { // any one of the side panels are managed and visible
            sidePanelsPlaceHolder.setManaged(true);
            sidePanelsPlaceHolder.setVisible(true);
            logger.info("Toggled entire side panel on");
        }
    }

    /**
     * Changes font in the application to the specified font.
     */
    private void handleChangeFont(FontName font) {
        this.font = font;
        String style = "-fx-font-family: " + FontName.toLowerCaseString(font);
        window.setStyle(style);
    }

    /**
     * Sets both the isVisible and isManaged properties of all the PlaceHolders in the sidepanelPlaceHolder to True.
     * This occurs if the window is switched back to Entry Window.
     */
    private void toggleAllTrue() {
        budgetsPlaceHolder.setVisible(true);
        budgetsPlaceHolder.setManaged(true);
        remindersPlaceHolder.setVisible(true);
        remindersPlaceHolder.setManaged(true);
        wishesPlaceHolder.setVisible(true);
        wishesPlaceHolder.setManaged(true);
    }

    /**
     * Fills the entryListPanel with the type of Panel passed in.
     */
    private void fillEntryListPanel(UiPart typeOfPanel) {
        mainPanel.getChildren().clear();
        mainPanel.getChildren().add((Node) typeOfPanel.getRoot());
    }

    /**
     * Switches the current to the {@code newTheme}.
     */
    private void switchThemeTo(Theme newTheme) {
        this.theme = newTheme;

        String newThemeUrl = theme.getThemeUrl(newTheme);
        String newExtensionsUrl = theme.getThemeExtensionUrl(newTheme);

        // Replace stylesheets with new theme's stylesheets
        this.scene.getStylesheets().removeAll(this.scene.getStylesheets());
        this.scene.getStylesheets().addAll(newThemeUrl, newExtensionsUrl);
    }

    /**
     * Resets the main panel (i.e. entry list panel) to contain just incomes and expenses.
     */
    private void resetMainPanel() {
        mainPanel.getChildren().removeAll(mainPanel.getChildren());
        mainPanel.getChildren().addAll(expenseListPanel.getRoot(), incomeListPanel.getRoot());

        // Add the respective panels back to their placeholders and turn them on
        if (!budgetsPlaceHolder.getChildren().contains(budgetPanel.getRoot())) {
            budgetsPlaceHolder.getChildren().add(budgetPanel.getRoot());
            togglePlaceHolder(budgetsPlaceHolder);
        }

        if (!wishesPlaceHolder.getChildren().contains(wishListPanel.getRoot())) {
            wishesPlaceHolder.getChildren().add(wishListPanel.getRoot());
            togglePlaceHolder(wishesPlaceHolder);
        }

        if (!autoExpensesPlaceHolder.getChildren().contains(autoExpensesPanel.getRoot())) {
            autoExpensesPlaceHolder.getChildren().add(autoExpensesPanel.getRoot());
            togglePlaceHolder(autoExpensesPlaceHolder);
        }

        if (!remindersPlaceHolder.getChildren().contains(notificationPanel.getRoot())) {
            remindersPlaceHolder.getChildren().add(reminderPanel.getRoot());
            togglePlaceHolder(remindersPlaceHolder);
        }

    }

    /**
     * Shows the reminder popup alert window.
     */
    private void showReminderPopup() {
        final Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.getDialogPane().getStylesheets().add(this.theme.getThemeUrl());
        alert.initStyle(StageStyle.DECORATED);
        alert.getDialogPane().setGraphic(new ImageView(new Image("images/guiltTrip()_32.png")));
        alert.initOwner(this.primaryStage);
        alert.setTitle("reminder");
        alert.setHeaderText("headerText");
        alert.setContentText("contentText");
        alert.getDialogPane().setId("alertDialogPane");
        alert.showAndWait();
    }


    /**
     * Executes the command and returns the result.
     *
     * @see seedu.guilttrip.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException,
            IllegalArgumentException {
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

            if (commandResult.isTogglePanel()) {
                PanelName panelName = commandResult.getPanelName();
                handleTogglePanel(panelName);
            }

            if (commandResult.isChangeFont()) {
                handleChangeFont(commandResult.getFontName());
            }

            if (commandResult.isToggleStats()) {
                this.togglePlaceHolderForStats(true);
                this.fillEntryListPanel(this.statsListPanel);
                this.statsListPanel.fillStatsTable();
            }

            if (commandResult.isTogglePieChart()) {
                this.togglePlaceHolderForStats(true);
                this.fillEntryListPanel(this.statsListPanel);
                this.statsListPanel.fillStatsPie();
            }

            if (commandResult.isToggleEntryPanel()) {
                this.togglePlaceHolderForStats(false);
                this.toggleAllTrue();
                this.mainPanel.getChildren().setAll(expenseListPanel.getRoot(), incomeListPanel.getRoot());
            }

            if (commandResult.isToggleBarChart()) {
                this.togglePlaceHolderForStats(true);
                this.fillEntryListPanel(this.statsBar);
            }

            if (commandResult.toShowConditionPanel()) {
                showConditionPanel();
            }

            /*if (!(commandResult.toShowConditionPanel())) {
                showReminderPanel();
            }*/

            if (commandResult.isList()) {
                String toList = commandResult.getToList();
                // allow only these possible values of toList
                assert (toList.equals("main") || toList.equals("budget") || toList.equals("wish")
                        || toList.equals("reminder"));

                switch (toList) {
                case "main":
                    resetMainPanel();
                    break;
                case "budget":
                    resetMainPanel();
                    mainPanel.getChildren().removeAll(mainPanel.getChildren());
                    mainPanel.getChildren().add(this.budgetPanel.getRoot());
                    if (budgetsPlaceHolder.isVisible() && budgetsPlaceHolder.isManaged()) {
                        togglePlaceHolder(budgetsPlaceHolder);
                    }
                    break;
                case "wish":
                    resetMainPanel();
                    mainPanel.getChildren().removeAll(mainPanel.getChildren());
                    mainPanel.getChildren().add(this.wishListPanel.getRoot());
                    if (wishesPlaceHolder.isVisible() && wishesPlaceHolder.isManaged()) {
                        togglePlaceHolder(wishesPlaceHolder);
                    }
                    break;
                case "reminder":
                    resetMainPanel();
                    if (remindersPlaceHolder.isVisible() && remindersPlaceHolder.isManaged()) {
                        togglePlaceHolder(remindersPlaceHolder);
                    }
                    mainPanel.getChildren().clear();
                    mainPanel.getChildren().add(this.reminderPanel.getRoot());
                    break;
                default:
                    // Do nothing.
                }
            }

            if (commandResult.isChangeTheme()) {
                Theme themeToChangeTo = commandResult.getNewTheme();
                switchThemeTo(themeToChangeTo);
            }


            return commandResult;

        } catch (CommandException | ParseException | IllegalArgumentException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }

    /**
     * Parse the user input and display the suggestions in ResultDisplay.
     *
     * @param userInput text input from CommandBox
     */
    private void suggestCommand(String userInput) {
        resultDisplay.setFeedbackToUser(GuiltTripCommandSuggester.getSuggestionString(userInput));
    }
}
