package seedu.savenus.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import seedu.savenus.commons.core.GuiSettings;
import seedu.savenus.commons.core.LogsCenter;
import seedu.savenus.logic.Logic;
import seedu.savenus.logic.commands.CommandResult;
import seedu.savenus.logic.commands.exceptions.CommandException;
import seedu.savenus.logic.parser.exceptions.ParseException;

/**
 * The Main Window.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private FoodListPanel foodListPanel;
    private PurchaseListPanel purchaseListPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;
    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private StackPane foodListPanelPlaceholder;

    @FXML
    private StackPane purchaseListPanelPlaceholder;

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
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        helpWindow = new HelpWindow();
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

        purchaseListPanel = new PurchaseListPanel(logic.getPurchaseHistory());
        purchaseListPanelPlaceholder.getChildren().add(purchaseListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getMenuFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        // Bind remaining budget to displayed value
        remainingBudgetPlaceholder.textProperty().bind(logic.getMenu()
                .getWallet().getRemainingBudgetProperty().asString("$%.02f"));

        // Update number of days left
        logic.getMenu().getWallet().updateDaysToExpire();

        // Bind number of days to budget expiration to displayed value
        daysToExpirePlaceholder.textProperty().bind(logic.getMenu()
                .getWallet().getDaysToExpireProperty().asString("%d days"));
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
     * @param event The event that the user clicks on the window.
     */
    public void handleWindowPress(MouseEvent event) {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    /**
     * Method that allows the window to be moved.
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

            // Update foodListPanel after every command
            foodListPanel = new FoodListPanel(logic.getFilteredFoodList());
            foodListPanelPlaceholder.getChildren().add(foodListPanel.getRoot());
            if (commandResult.isJustAdd()) {
                foodListPanel.showLastItem();
            }

            // Update purchaseListPanel after every command
            purchaseListPanel = new PurchaseListPanel(logic.getPurchaseHistory());
            purchaseListPanelPlaceholder.getChildren().add(purchaseListPanel.getRoot());

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
