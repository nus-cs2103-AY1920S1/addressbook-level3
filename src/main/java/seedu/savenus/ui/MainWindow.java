package seedu.savenus.ui;

import java.util.logging.Logger;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import seedu.savenus.commons.core.GuiSettings;
import seedu.savenus.commons.core.LogsCenter;
import seedu.savenus.logic.Logic;
import seedu.savenus.logic.commands.CommandResult;
import seedu.savenus.logic.commands.CustomSortCommand;
import seedu.savenus.logic.commands.DefaultCommand;
import seedu.savenus.logic.commands.HistoryCommand;
import seedu.savenus.logic.commands.InfoCommand;
import seedu.savenus.logic.commands.ListCommand;
import seedu.savenus.logic.commands.RecommendCommand;
import seedu.savenus.logic.commands.ThemeCommand;
import seedu.savenus.logic.commands.ViewSortCommand;
import seedu.savenus.logic.commands.exceptions.CommandException;
import seedu.savenus.logic.parser.exceptions.ParseException;
import seedu.savenus.model.food.Food;

//@@author robytanama
/**
 * The Main Window.
 */
public class MainWindow extends UiPart<Stage> {

    /**
     * The path for Dark Theme.
     */
    public static final String DARK_THEME_CSS = "view/DarkTheme.css";

    /**
     * The path for Light Theme.
     */
    public static final String LIGHT_THEME_CSS = "view/LightTheme.css";

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private FoodListPanel foodListPanel;
    private PurchaseListPanel purchaseListPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;
    private InfoWindow infoWindow;
    private SavingsHistoryPanel savingsHistoryPanel;
    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private StackPane foodListPanelPlaceholder;

    @FXML
    private StackPane purchaseListPanelPlaceholder;

    @FXML
    private StackPane savingsHistoryPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private Text remainingBudgetPlaceholder;

    @FXML
    private Text savingsAccountPlaceholder;

    @FXML
    private Text daysToExpirePlaceholder;

    @FXML
    private Button helpButton;

    @FXML
    private Button quitButton;

    @FXML
    private Button themeButton;

    @FXML
    private Button recommendButton;

    @FXML
    private Button listButton;

    @FXML
    private Button defaultButton;

    @FXML
    private Button historyButton;

    @FXML
    private Button viewSortButton;

    @FXML
    private Button customSortButton;


    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        helpWindow = new HelpWindow();
        infoWindow = new InfoWindow();

        // All the buttons tooltip configuration
        Tooltip help = new Tooltip("Help");
        help.setShowDelay(Duration.seconds(0));
        helpButton.setTooltip(help);

        Tooltip quit = new Tooltip("Quit");
        quit.setShowDelay(Duration.seconds(0));
        quitButton.setTooltip(quit);

        Tooltip theme = new Tooltip("Change theme");
        theme.setShowDelay(Duration.seconds(0));
        themeButton.setTooltip(theme);

        Tooltip recommend = new Tooltip("Recommend");
        recommend.setShowDelay(Duration.seconds(0));
        recommendButton.setTooltip(recommend);

        Tooltip list = new Tooltip("List");
        list.setShowDelay(Duration.seconds(0));
        listButton.setTooltip(list);

        Tooltip defaults = new Tooltip("Default");
        defaults.setShowDelay(Duration.seconds(0));
        defaultButton.setTooltip(defaults);

        Tooltip history = new Tooltip("History");
        history.setShowDelay(Duration.seconds(0));
        historyButton.setTooltip(history);

        Tooltip viewSort = new Tooltip("View sort");
        viewSort.setShowDelay(Duration.seconds(0));
        viewSortButton.setTooltip(viewSort);

        Tooltip customSort = new Tooltip("Custom sort");
        customSort.setShowDelay(Duration.seconds(0));
        customSortButton.setTooltip(customSort);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        foodListPanel = new FoodListPanel(logic.getFilteredFoodList());
        foodListPanelPlaceholder.getChildren().add(foodListPanel.getRoot());

        purchaseListPanel = new PurchaseListPanel(logic.getPurchaseHistoryList());
        purchaseListPanelPlaceholder.getChildren().add(purchaseListPanel.getRoot());

        savingsHistoryPanel = new SavingsHistoryPanel(logic.getSavingsHistory().getSavingsHistory());
        savingsHistoryPanelPlaceholder.getChildren().add(savingsHistoryPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getMenuFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        // Bind remaining budget to displayed value
        StringBinding remainingBudgetBinding = Bindings.createStringBinding(() ->
                        String.format("Budget: $%s", logic.getWallet().getRemainingBudgetAmount().toString()),
                logic.getWallet().getRemainingBudgetProperty());
        remainingBudgetPlaceholder.textProperty().bind(remainingBudgetBinding);

        // Update number of days left
        logic.getWallet().updateDaysToExpire();

        // Bind number of days to budget expiration to displayed value
        StringBinding daysToExpireBinding = Bindings.createStringBinding(() -> logic.getWallet()
                        .getDaysToExpireProperty().get() == 0
                        ? ""
                        : String.format("%d days left", logic.getWallet().getNumberOfDaysToExpire()),
                logic.getWallet().getDaysToExpireProperty());
        daysToExpirePlaceholder.textProperty().bind(daysToExpireBinding);

        // Bind savings to the savings display to show the value.
        StringBinding savingsAccountBinding = Bindings.createStringBinding(() -> String.format(
                "Savings: $%s",
                logic.getSavingsAccount().getCurrentSavings().get().getAmount().toString()));
        savingsAccountPlaceholder.textProperty().bind(savingsAccountBinding);
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

    /**
     * Opens the info window or focuses on it if it's already opened.
     */
    @FXML
    public void handleInfo(String info) {
        if (!infoWindow.isShowing()) {
            infoWindow.show(info);
        } else {
            infoWindow.focus();
        }
    }

    void show() {
        primaryStage.show();
        primaryStage.centerOnScreen();
    }

    /**
     * Changes the theme of the app to Dark Theme.
     */
    @FXML
    public void changeThemeToDark() {
        primaryStage.getScene().getStylesheets().clear();
        primaryStage.getScene().setUserAgentStylesheet(null);
        primaryStage.getScene().getStylesheets()
                .add(DARK_THEME_CSS);

        // For help window
        helpWindow.setToDarkTheme();

        // For info window
        infoWindow.setToDarkTheme();
    }

    /**
     * Changes the theme of the app to Light Theme.
     */
    @FXML
    public void changeThemeToLight() {
        // For the primary stage
        primaryStage.getScene().getStylesheets().clear();
        primaryStage.getScene().setUserAgentStylesheet(null);
        primaryStage.getScene().getStylesheets()
                .add(LIGHT_THEME_CSS);

        // For help window
        helpWindow.setToLightTheme();

        // For info window
        infoWindow.setToLightTheme();
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
     * Runs {@code theme} command.
     */
    @FXML
    private void handleTheme() {
        if (primaryStage.getScene().getStylesheets().get(0).equals(LIGHT_THEME_CSS)) {
            changeThemeToDark();
        } else {
            changeThemeToLight();
        }
    }

    /**
     * Runs {@code recommend} command.
     */
    @FXML
    private void handleRecommend() throws CommandException, ParseException {
        CommandResult commandResult = logic.execute(RecommendCommand.COMMAND_WORD);
        foodListPanel.updateFoodList(logic.getFilteredFoodList());
        logger.info("Result: " + commandResult.getFeedbackToUser());
        resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());
    }

    /**
     * Runs {@code list} command.
     */
    @FXML
    private void handleList() throws CommandException, ParseException {
        CommandResult commandResult = logic.execute(ListCommand.COMMAND_WORD);
        foodListPanel.updateFoodList(logic.getFilteredFoodList());
        logger.info("Result: " + commandResult.getFeedbackToUser());
        resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());
    }

    /**
     * Runs {@code default} command.
     */
    @FXML
    private void handleDefault() throws CommandException, ParseException {
        CommandResult commandResult = logic.execute(DefaultCommand.COMMAND_WORD);
        foodListPanel.updateFoodList(logic.getFilteredFoodList());
        logger.info("Result: " + commandResult.getFeedbackToUser());
        resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());
    }

    /**
     * Runs {@code history} command.
     */
    @FXML
    private void handleHistory() throws CommandException, ParseException {
        CommandResult commandResult = logic.execute(HistoryCommand.COMMAND_WORD);
        foodListPanel.updateFoodList(logic.getFilteredFoodList());
        logger.info("Result: " + commandResult.getFeedbackToUser());
        resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());
    }

    /**
     * Runs {@code viewsort} command.
     */
    @FXML
    private void handleViewSort() throws CommandException, ParseException {
        CommandResult commandResult = logic.execute(ViewSortCommand.COMMAND_WORD);
        foodListPanel.updateFoodList(logic.getFilteredFoodList());
        logger.info("Result: " + commandResult.getFeedbackToUser());
        resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());
    }

    /**
     * Runs {@code customsort} command.
     */
    @FXML
    private void handleCustomSort() throws CommandException, ParseException {
        CommandResult commandResult = logic.execute(CustomSortCommand.COMMAND_WORD);
        foodListPanel.updateFoodList(logic.getFilteredFoodList());
        logger.info("Result: " + commandResult.getFeedbackToUser());
        resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());
    }

    public FoodListPanel getFoodListPanel() {
        return foodListPanel;
    }

    public PurchaseListPanel getPurchaseListPanel() {
        return purchaseListPanel;
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

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            if (commandResult.getFeedbackToUser().equals(InfoCommand.ADD_INFO)
                    || commandResult.getFeedbackToUser().equals(InfoCommand.AUTO_SORT_INFO)
                    || commandResult.getFeedbackToUser().equals(InfoCommand.BUDGET_INFO)
                    || commandResult.getFeedbackToUser().equals(InfoCommand.BUY_INFO)
                    || commandResult.getFeedbackToUser().equals(InfoCommand.CLEAR_INFO)
                    || commandResult.getFeedbackToUser().equals(InfoCommand.CUSTOM_SORT_INFO)
                    || commandResult.getFeedbackToUser().equals(InfoCommand.DEFAULT_INFO)
                    || commandResult.getFeedbackToUser().equals(InfoCommand.DELETE_INFO)
                    || commandResult.getFeedbackToUser().equals(InfoCommand.DISLIKE_INFO)
                    || commandResult.getFeedbackToUser().equals(InfoCommand.EDIT_INFO)
                    || commandResult.getFeedbackToUser().equals(InfoCommand.EXIT_INFO)
                    || commandResult.getFeedbackToUser().equals(InfoCommand.FILTER_INFO)
                    || commandResult.getFeedbackToUser().equals(InfoCommand.FIND_INFO)
                    || commandResult.getFeedbackToUser().equals(InfoCommand.HELP_INFO)
                    || commandResult.getFeedbackToUser().equals(InfoCommand.HISTORY_INFO)
                    || commandResult.getFeedbackToUser().equals(InfoCommand.INFO_INFO)
                    || commandResult.getFeedbackToUser().equals(InfoCommand.LIKE_INFO)
                    || commandResult.getFeedbackToUser().equals(InfoCommand.LIST_INFO)
                    || commandResult.getFeedbackToUser().equals(InfoCommand.MAKE_SORT_INFO)
                    || commandResult.getFeedbackToUser().equals(InfoCommand.RECOMMEND_INFO)
                    || commandResult.getFeedbackToUser().equals(InfoCommand.REMOVEDISLIKE_INFO)
                    || commandResult.getFeedbackToUser().equals(InfoCommand.REMOVELIKE_INFO)
                    || commandResult.getFeedbackToUser().equals(InfoCommand.SAVE_INFO)
                    || commandResult.getFeedbackToUser().equals(InfoCommand.SORT_INFO)
                    || commandResult.getFeedbackToUser().equals(InfoCommand.THEME_INFO)
                    || commandResult.getFeedbackToUser().equals(InfoCommand.TOP_UP_INFO)
                    || commandResult.getFeedbackToUser().equals(InfoCommand.WITHDRAW_INFO)
                    || commandResult.getFeedbackToUser().equals(InfoCommand.SHOW_INFO)) {
                if (infoWindow.isShowing()) {
                    infoWindow.closeWindow();
                }
                handleInfo(commandResult.getFeedbackToUser());
            }

            // Handles the theme command change to dark theme
            if (commandResult.getFeedbackToUser().equals(ThemeCommand.MESSAGE_SUCCESS_DARK)) {
                changeThemeToDark();
            }

            // Handles the theme command change to light theme
            if (commandResult.getFeedbackToUser().equals(ThemeCommand.MESSAGE_SUCCESS_LIGHT)) {
                changeThemeToLight();
            }

            // Update foodListPanel after every command
            foodListPanel.updateFoodList(logic.getFilteredFoodList());

            if (logic.getAutoSortFlag()) {
                ObservableList<Food> foodList = logic.getFoods();
                SortedList<Food> sortedList = foodList.sorted(logic.getCustomSorter().getComparator());
                logic.setFoods(sortedList);
            }

            if (commandResult.isJustAdd() && !logic.getAutoSortFlag()) {
                foodListPanel.showLastItem();
            }

            // Update purchaseListPanel after every command.
            purchaseListPanel.updatePurchaseList(logic.getPurchaseHistoryList());

            // Update savingsHistoryPanel after every command, depending on what the user wants to see.
            if (commandResult.isShowSavingsOnly()) {
                savingsHistoryPanel.updateSavingsHistory(logic.getSavingsHistory().getSavingsOnly());
            } else if (commandResult.isShowWithdrawOnly()) {
                savingsHistoryPanel.updateSavingsHistory(logic.getSavingsHistory().getWithdrawalsOnly());
            } else {
                savingsHistoryPanel.updateSavingsHistory(logic.getSavingsHistory().getSavingsHistory());
            }

            // Update current savings each time a command is executed.
            StringBinding savingsAccountBinding = Bindings.createStringBinding(() -> String.format(
                    "Savings: $%s",
                    logic.getSavingsAccount().getCurrentSavings().get().getAmount().toString()));
            savingsAccountPlaceholder.textProperty().bind(savingsAccountBinding);

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
