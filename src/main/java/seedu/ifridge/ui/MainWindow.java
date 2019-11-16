package seedu.ifridge.ui;

import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.ifridge.commons.core.GuiSettings;
import seedu.ifridge.commons.core.LogsCenter;
import seedu.ifridge.logic.Logic;
import seedu.ifridge.logic.commands.CommandResult;
import seedu.ifridge.logic.commands.exceptions.CommandException;
import seedu.ifridge.logic.parser.exceptions.ParseException;
import seedu.ifridge.model.food.Name;
import seedu.ifridge.model.waste.WasteReport;

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
    private GroceryListPanel groceryListPanel;
    private TemplateListPanel templateListPanel;
    private TemplateItemPanel templateItemPanel;
    private WasteListPanel wasteListPanel;
    private ShoppingListPanel shoppingListPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab templateListPage;

    @FXML
    private Tab wasteListPage;

    @FXML
    private Tab shoppingListPage;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane groceryListPanelPlaceholder;

    @FXML
    private StackPane templateListPanelPlaceholder;

    @FXML
    private StackPane templateItemPanelPlaceholder;

    @FXML
    private StackPane wasteListPanelPlaceholder;

    @FXML
    private StackPane shoppingListPanelPlaceholder;

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
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
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
        displayGroceryListPanel();
        displayShoppingListPanel();
        displayWasteListPanel();
        displayTemplateListPanel();

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getGroceryListFilePath());
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
     * Displays the grocery list panel
     */
    @FXML
    private void displayGroceryListPanel() {
        groceryListPanel = new GroceryListPanel(logic.getFilteredGroceryItemList(),
                logic.getIFridgeSettings().getNumberOfDays());
        groceryListPanelPlaceholder.getChildren().add(groceryListPanel.getRoot());
        logger.info("Showing grocery list panel.");
    }

    /**
     * Displays the waste list panel
     */
    @FXML
    private void displayWasteListPanel() {
        wasteListPanel = new WasteListPanel(logic.getFilteredWasteList(), logic.getIFridgeSettings().getNumberOfDays());
        wasteListPanelPlaceholder.getChildren().add(wasteListPanel.getRoot());
        tabPane.getSelectionModel().select(wasteListPage);
        logger.info("Showing waste list panel.");
    }

    /**
     * Displays the template list panel
     */
    @FXML
    private void displayTemplateListPanel() {
        templateListPanel = new TemplateListPanel(logic.getFilteredTemplateList());
        templateListPanelPlaceholder.getChildren().add(templateListPanel.getRoot());
        tabPane.getSelectionModel().select(templateListPage);
    }

    /**
     * Displays the shopping list panel
     */
    @FXML
    private void displayShoppingListPanel() {
        shoppingListPanel = new ShoppingListPanel(logic.getFilteredShoppingList(), logic.getFilteredBoughtList());
        shoppingListPanelPlaceholder.getChildren().add(shoppingListPanel.getRoot());
        tabPane.getSelectionModel().select(shoppingListPage);
    }

    public GroceryListPanel getGroceryListPanel() {
        return groceryListPanel;
    }

    public TemplateListPanel getTemplateListPanel() {
        return templateListPanel;
    }

    public TemplateItemPanel getTemplateItemPanel() {
        return templateItemPanel;
    }

    public WasteListPanel getWasteListPanel() {
        return wasteListPanel;
    }

    public ShoppingListPanel getShoppingListPanel() {
        return shoppingListPanel;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.ifridge.logic.Logic#execute(String)
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

            if (commandResult.isWasteListCommand()) {
                displayWasteListPanel();
            } else if (commandResult.isTemplateListItemCommand()) {
                displayTemplateItemPanel();
                displayTemplateListPanel();
            } else if (commandResult.isTemplateListCommand()) {
                displayTemplateListPanel();
                templateItemPanelPlaceholder.getChildren().clear();
            } else if (commandResult.isShoppingListCommand()) {
                displayShoppingListPanel();
            } else if (commandResult.isWasteReportCommand()) {
                displayWasteReport(logic.getWasteReport());
            }

            if (commandResult.isReminderDefaultCommand()) {
                displayGroceryListPanel();
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }

    private void displayWasteReport(WasteReport wasteReport) {
        ReportWasteWindow wasteWindow = new ReportWasteWindow(wasteReport);
        wasteWindow.showWasteReport();

    }
    /**
     * Resets the templateListPanel to show the templateItemPanel whenever a templateItem command is executed
     */
    private void displayTemplateItemPanel() {
        Name templateName = logic.getNameTemplateToBeShown();
        templateItemPanel = new TemplateItemPanel(logic.getFilteredTemplateToBeShown(), templateName.toString());
        templateItemPanelPlaceholder.getChildren().add(templateItemPanel.getRoot());
        logger.info("Showing template panel instead of templateList.");
    }

}
