package seedu.guilttrip.ui;

import java.util.Arrays;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.guilttrip.commons.core.GuiSettings;
import seedu.guilttrip.commons.core.LogsCenter;
import seedu.guilttrip.logic.Logic;
import seedu.guilttrip.logic.commands.CommandResult;
import seedu.guilttrip.logic.commands.GuiltTripCommandSuggester;
import seedu.guilttrip.logic.commands.exceptions.CommandException;
import seedu.guilttrip.logic.parser.exceptions.ParseException;
import seedu.guilttrip.model.reminders.messages.Message;
import seedu.guilttrip.ui.autoexpense.AutoExpensesPanel;
import seedu.guilttrip.ui.budget.BudgetPanel;
import seedu.guilttrip.ui.condition.ConditionPanel;
import seedu.guilttrip.ui.entry.EntryListPanel;
import seedu.guilttrip.ui.expense.ExpenseListPanel;
import seedu.guilttrip.ui.income.IncomeListPanel;
import seedu.guilttrip.ui.reminder.NotificationPanel;
import seedu.guilttrip.ui.stats.StatisticsBarChart;
import seedu.guilttrip.ui.stats.StatisticsPieChartHolder;
import seedu.guilttrip.ui.stats.StatisticsWindow;
import seedu.guilttrip.ui.util.FontManager;
import seedu.guilttrip.ui.util.PanelName;
import seedu.guilttrip.ui.util.Theme;
import seedu.guilttrip.ui.wishlist.WishListPanel;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";
    private final String lightThemeUrl = getClass().getResource("/view/LightTheme.css").toExternalForm();
    private final String lightExtensionsUrl = getClass().getResource("/view/ExtensionsLight.css")
            .toExternalForm();
    private final String darkThemeUrl = getClass().getResource("/view/DarkTheme.css").toExternalForm();
    private final String darkExtensionsUrl = getClass().getResource("/view/ExtensionsDark.css").toExternalForm();

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private EntryListPanel entryListPanel;
    private ExpenseListPanel expenseListPanel;
    private IncomeListPanel incomeListPanel;
    private BudgetPanel budgetPanel;
    private WishListPanel wishListPanel;
    private AutoExpensesPanel autoExpensesPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;
    private StatisticsWindow statsListPanel;
    private StatisticsPieChartHolder statsGraphics;
    private StatisticsBarChart statsBar;
    private BudgetPanel budgetsPanel;

    private boolean isStatsWindow;
    private boolean isStatsGraphicsWindow;

    // Customisable GUI elements
    private String font;
    private Theme theme;

    @FXML
    private Scene scene;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private VBox entryList;

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

        this.isStatsGraphicsWindow = false;
        this.isStatsWindow = false;

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
        entryList.getChildren().addAll(this.expenseListPanel.getRoot(), this.incomeListPanel.getRoot());

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

        NotificationPanel notificationPanel = new NotificationPanel(logic.getFilteredNotifications());
        remindersPlaceHolder.getChildren().add(notificationPanel.getRoot());

        this.autoExpensesPanel = new AutoExpensesPanel(logic.getFilteredAutoExpenseList());
        autoExpensesPlaceHolder.getChildren().add(this.autoExpensesPanel.getRoot());
    }

    /**
     * Sets up the GUI.
     */
    private void setUpGui(GuiSettings guiSettings) {
        setWindowDefaultSize(guiSettings);
        setFont(guiSettings);
        setTheme(guiSettings);
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
    private void setFont(GuiSettings guiSettings) {
        String savedFont = guiSettings.getFont();
        this.font = savedFont;
        String style = "-fx-font-family: " + savedFont;
        window.setStyle(style);
    }

    /**
     * Sets the theme based on {@code guiSettings}.
     */
    private void setTheme(GuiSettings guiSettings) {
        Theme savedTheme = guiSettings.getTheme();
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
    private void handleTogglePanel(String panelNameString) throws CommandException {
        togglePanel(panelNameString);
        toggleEntireSidePanelIfNecessary();
    }

    /**
     * Calls the togglePlaceHolder method with the place holder of the specified panel.
     *
     * @param panelName name of the specified panel to be toggled.
     */
    private void togglePanel(String panelName) throws CommandException {
        switch (panelName) {
        case "wishlist":
            togglePlaceHolder(wishesPlaceHolder);
            break;
        case "budget":
            if (entryList.getChildren().contains(budgetPanel.getRoot())) {
                throw new CommandException("The panel you want to toggle is already shown in the main panel!");
            }
            togglePlaceHolder(budgetsPlaceHolder);
            break;
        case "generalReminder":
            togglePlaceHolder(remindersPlaceHolder);
            break;
        case "autoexpense":
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
     * Toggles the isVisible and isManaged properties of the sidePanelsPlaceHolder.
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
        } else { // any one of the side panels are managed and visible
            sidePanelsPlaceHolder.setManaged(true);
            sidePanelsPlaceHolder.setVisible(true);
        }
        logger.info("Toggled entire side panel");
    }

    /**
     * Returns a {@code String} for the updated feedback to user that includes a list of all the fonts.
     */
    private String handleListFonts(String oldFeedbackToUser) {
        FontManager fontManager = new FontManager();
        String feedbackToUserWithFontList = oldFeedbackToUser + ": "
                + Arrays.toString(fontManager.getFonts().toArray());
        logger.info("Listed all fonts");
        return feedbackToUserWithFontList;
    }

    /**
     * Changes font in the application to the specified font.
     */
    private void handleChangeFont(String font) {
        this.font = font;
        String style = "-fx-font-family: " + font;
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
        entryList.getChildren().clear();
        entryList.getChildren().add((Node) typeOfPanel.getRoot());
    }

    /**
     * Fills the entryListPanel with either the StatisticsWindow or the EntryListPanel.
     * entryListPanelPlaceholder.getChildren().add(statsListPanel.getRoot());
     */
    private void toggleStatsPanel() {
        entryList.getChildren().clear();
        if (isStatsGraphicsWindow) {
            entryList.getChildren().add(statsGraphics.getRoot());
        } else {
            entryList.getChildren().add(statsListPanel.getRoot());
        }
    }

    /**
     * Switches the current to the {@code newTheme}.
     */
    private void switchThemeTo(Theme newTheme) {
        String oldThemeUrl = null;
        String oldExtensionsUrl = null;
        String newThemeUrl = null;
        String newExtensionsUrl = null;

        switch (newTheme) {
        case LIGHT:
            this.theme = Theme.LIGHT;
            oldThemeUrl = darkThemeUrl;
            oldExtensionsUrl = darkExtensionsUrl;
            newThemeUrl = lightThemeUrl;
            newExtensionsUrl = lightExtensionsUrl;
            break;
        case DARK:
            this.theme = Theme.DARK;
            oldThemeUrl = lightThemeUrl;
            oldExtensionsUrl = lightExtensionsUrl;
            newThemeUrl = darkThemeUrl;
            newExtensionsUrl = darkExtensionsUrl;
            break;
        default:
            // Do nothing.
            break;
        }

        removeAndAddStylesheets(oldThemeUrl, newThemeUrl);
        removeAndAddStylesheets(oldExtensionsUrl, newExtensionsUrl);
    }

    /**
     * Removes the {@code oldThemeUrl} from the scene's stylesheets and adds the {@code newThemeUrl} to the scene's
     * stylesheets.
     */
    private void removeAndAddStylesheets(String oldThemeUrl, String newThemeUrl) {
        if (this.scene.getStylesheets().contains(oldThemeUrl)) {
            this.scene.getStylesheets().remove(oldThemeUrl);
            this.scene.getStylesheets().add(newThemeUrl);
        }
    }

    /**
     * Resets the main panel (i.e. entry list panel) to contain just incomes and expenses.
     */
    private void resetMainPanel() throws CommandException {
        entryList.getChildren().removeAll(entryList.getChildren());
        entryList.getChildren().addAll(expenseListPanel.getRoot(), incomeListPanel.getRoot());

        // Add the respective panels to their placeholders and turn them on
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

    }

    public EntryListPanel getEntryListPanel() {
        return entryListPanel;
    }


    /**
     * Display generalReminder popup.
     * @param message
     */
    public void displayPopUp(Message message) {
        Stage newStage = new Stage();

        Scene stageScene = new Scene(message.render(), 300, 300);
        newStage.setScene(stageScene);
        newStage.show();
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.guilttrip.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText)
            throws CommandException, ParseException, IllegalArgumentException {
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
                String panelNameString = panelName.getName();
                handleTogglePanel(panelNameString);
            }

            if (commandResult.isListFonts()) {
                String feedbackToUser = commandResult.getFeedbackToUser();
                String feedbackToUserWithFontList = handleListFonts(feedbackToUser);
                resultDisplay.setFeedbackToUser(feedbackToUserWithFontList);
            }

            if (commandResult.isChangeFont()) {
                String fontNameString = commandResult.getFontName().toString();
                handleChangeFont(fontNameString);
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
                this.entryList.getChildren().setAll(expenseListPanel.getRoot(), incomeListPanel.getRoot());
            }

            if (commandResult.isToggleBarChart()) {
                this.togglePlaceHolderForStats(true);
                this.fillEntryListPanel(this.statsBar);
            }

            if (commandResult.toShowConditionPanel()) {
                showConditionPanel();
            }

            if (!(commandResult.toShowConditionPanel())) {
                showReminderPanel();
            }

            if (commandResult.isListEntry()) {
                String entryToList = commandResult.getEntryToList();
                assert(entryToList.equals("main") || entryToList.equals("budget") || entryToList.equals("wish")
                        || entryToList.equals("autoexpense")); // allow only these possible values of entryToList

                switch (entryToList) {
                case "main":
                    resetMainPanel();
                    break;
                case "budget":
                    resetMainPanel();
                    entryList.getChildren().add(this.budgetPanel.getRoot());
                    togglePlaceHolder(budgetsPlaceHolder);
                    break;
                case "wish":
                    resetMainPanel();
                    entryList.getChildren().add(this.wishListPanel.getRoot());
                    togglePlaceHolder(wishesPlaceHolder);
                    break;
                case "autoexpense":
                    resetMainPanel();
                    entryList.getChildren().add(this.autoExpensesPanel.getRoot());
                    togglePlaceHolder(autoExpensesPlaceHolder);
                    break;
                default:
                    // Do nothing.
                }
            }

            if (commandResult.isChangeTheme()) {
                Theme themeToChangeTo = commandResult.getNewTheme();
                switchThemeTo(themeToChangeTo);
            }

            if (commandResult.toDisplayPopUp()) {
                displayPopUp(commandResult.getMessage());
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
