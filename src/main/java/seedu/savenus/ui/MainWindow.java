package seedu.savenus.ui;

import java.util.logging.Logger;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import seedu.savenus.commons.core.GuiSettings;
import seedu.savenus.commons.core.LogsCenter;
import seedu.savenus.logic.Logic;
import seedu.savenus.logic.commands.CommandResult;
import seedu.savenus.logic.commands.InfoCommand;
import seedu.savenus.logic.commands.exceptions.CommandException;
import seedu.savenus.logic.parser.exceptions.ParseException;
import seedu.savenus.model.food.Food;

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
    private Text daysToExpirePlaceholder;


    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        helpWindow = new HelpWindow();
        infoWindow = new InfoWindow();
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
                        String.format("$%s", logic.getWallet().getRemainingBudgetAmount().toString()),
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
     * Method that allows the mouse to click on the window to be moved.
     *
     * @param event The event that the user clicks on the window.
     */
    public void handleWindowPress(MouseEvent event) {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    /**
     * Method that allows the window to be moved.
     *
     * @param event The event that the user drags the window.
     */
    public void handleWindowDrag(MouseEvent event) {
        primaryStage.setX(event.getScreenX() - xOffset);
        primaryStage.setY(event.getScreenY() - yOffset);
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
                    || commandResult.getFeedbackToUser().equals(InfoCommand.COLLAPSE_INFO)
                    || commandResult.getFeedbackToUser().equals(InfoCommand.CUSTOM_SORT_INFO)
                    || commandResult.getFeedbackToUser().equals(InfoCommand.DEFAULT_INFO)
                    || commandResult.getFeedbackToUser().equals(InfoCommand.DELETE_INFO)
                    || commandResult.getFeedbackToUser().equals(InfoCommand.DISLIKE_INFO)
                    || commandResult.getFeedbackToUser().equals(InfoCommand.EDIT_INFO)
                    || commandResult.getFeedbackToUser().equals(InfoCommand.EXIT_INFO)
                    || commandResult.getFeedbackToUser().equals(InfoCommand.EXPAND_INFO)
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
                    || commandResult.getFeedbackToUser().equals(InfoCommand.TOP_UP_INFO)) {
                if (infoWindow.isShowing()) {
                    infoWindow.closeWindow();
                }
                handleInfo(commandResult.getFeedbackToUser());
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

            // Update savingsHistoryPanel after every command.
            savingsHistoryPanel.updateSavingsHistory(logic.getSavingsHistory().getSavingsHistory());

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
