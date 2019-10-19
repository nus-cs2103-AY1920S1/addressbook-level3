package seedu.address.ui;

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
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.waste.WasteReport;
import seedu.address.model.food.Name;

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
    private PersonListPanel personListPanel;
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
    private StackPane personListPanelPlaceholder;

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
        personListPanel = new PersonListPanel(logic.getFilteredGroceryItemList());
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getAddressBookFilePath());
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
     * Displays the waste list panel
     */
    @FXML
    private void displayWasteListPanel() {
        wasteListPanel = new WasteListPanel(logic.getFilteredWasteList());
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
        shoppingListPanel = new ShoppingListPanel(logic.getFilteredShoppingList());
        shoppingListPanelPlaceholder.getChildren().add(shoppingListPanel.getRoot());
        tabPane.getSelectionModel().select(shoppingListPage);
    }

    public PersonListPanel getPersonListPanel() {
        return personListPanel;
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
     * @see seedu.address.logic.Logic#execute(String)
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
            } else if (commandResult.isTemplateListCommand()) {
                displayTemplateListPanel();
            } else if (commandResult.isShoppingListCommand()) {
                displayShoppingListPanel();
            } else if (commandResult.isWasteReportCommand()) {
                displayWasteReport(logic.getWasteReport());
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
        // To be improved, work on adding the name of the template as well
        Name templateName = logic.getNameTemplateToBeShown();
        templateItemPanel = new TemplateItemPanel(logic.getFilteredTemplateToBeShown(), templateName.toString());
        templateListPanelPlaceholder.getChildren().add(templateItemPanel.getRoot());
        logger.info("Showing template panel instead of templatelist.");
    }

}
