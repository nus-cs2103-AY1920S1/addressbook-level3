package seedu.deliverymans.ui;

import java.nio.file.Paths;

import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import seedu.deliverymans.commons.core.GuiSettings;
import seedu.deliverymans.commons.core.LogsCenter;
import seedu.deliverymans.logic.Context;
import seedu.deliverymans.logic.Logic;
import seedu.deliverymans.logic.LogicManager;
import seedu.deliverymans.logic.commands.CommandResult;
import seedu.deliverymans.logic.commands.exceptions.CommandException;
import seedu.deliverymans.logic.parser.exceptions.ParseException;
import seedu.deliverymans.model.restaurant.Restaurant;

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
    private CustomerListPanel customerListPanel;
    private DeliverymanListPanel deliverymanListPanel;
    private DeliverymenStatusListPanel deliverymenStatusListPanel;
    private DeliverymenStatusStatisticsPanel deliverymenStatusStatisticsPanel;
    private RestaurantListPanel restaurantListPanel;
    private OrderListPanel orderListPanel;
    private FoodListPanel foodListPanel;
    private ResultDisplay resultDisplay;
    private StatisticsDisplay statisticsDisplay;
    private HelpWindow helpWindow;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane editingRestaurantPlaceholder;

    @FXML
    private StackPane listPanelPlaceholder;

    @FXML
    private StackPane statisticsPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private SplitPane splitPane;

    @FXML
    private VBox personList;

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
        orderListPanel = new OrderListPanel(logic.getFilteredOrderList());
        listPanelPlaceholder.getChildren().add(orderListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(Paths.get("")); // to be edited
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand, logic);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        deliverymenStatusListPanel = new DeliverymenStatusListPanel(logic.getAvailableDeliverymenList(),
                logic.getUnavailableDeliverymenList(), logic.getDeliveringDeliverymenList());
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
     * Changes context of the system depending on {@code context}.
     */
    private void changeDisplay(Context context) {
        editingRestaurantPlaceholder.setPrefHeight(0);
        editingRestaurantPlaceholder.setMinHeight(0);
        personList.setMinWidth(340);

        if (statisticsPlaceholder.getChildren().size() > 0) {
            statisticsPlaceholder.getChildren().remove(0);
        }

        switch (context) {
        case CUSTOMER:
            customerListPanel = new CustomerListPanel(logic.getFilteredCustomerList());
            listPanelPlaceholder.getChildren().add(customerListPanel.getRoot());
            break;
        case DELIVERYMEN:
            deliverymanListPanel = new DeliverymanListPanel(logic.getFilteredDeliverymenList());
            listPanelPlaceholder.getChildren().add(deliverymanListPanel.getRoot());
            break;
        case RESTAURANT:
            restaurantListPanel = new RestaurantListPanel(logic.getFilteredRestaurantList());
            listPanelPlaceholder.getChildren().add(restaurantListPanel.getRoot());
            break;
        case EDITING:
            Restaurant editing = logic.getEditingRestaurantList().get(0);
            editingRestaurantPlaceholder.setPrefHeight(145.0);
            editingRestaurantPlaceholder.setMinHeight(145.0);

            restaurantListPanel = new RestaurantListPanel(logic.getEditingRestaurantList());
            editingRestaurantPlaceholder.getChildren().add(restaurantListPanel.getRoot());

            foodListPanel = new FoodListPanel(FXCollections.observableArrayList(editing.getMenu()));
            listPanelPlaceholder.getChildren().add(foodListPanel.getRoot());

            orderListPanel = new OrderListPanel(editing.getOrders(logic));
            statisticsPlaceholder.getChildren().add(orderListPanel.getRoot());

            break;
        default:
            orderListPanel = new OrderListPanel(logic.getFilteredOrderList());
            listPanelPlaceholder.getChildren().add(orderListPanel.getRoot());
        }
    }

    /**
     * Changes the Ui to display depending the {@code Class} commandClassName.
     * */
    private void changeDisplay(Class commandClassName) {
        if (statisticsPlaceholder.getChildren().size() > 0) {
            statisticsPlaceholder.getChildren().remove(0);
        }
        personList.setMinWidth(340);
        switch(commandClassName.getSimpleName()) {
        case "CustomerHistoryCommand":
            orderListPanel = new OrderListPanel(logic.getCustomerOrders());;
            statisticsPlaceholder.getChildren().add(orderListPanel.getRoot());
            break;
        case "DeliverymanStatusSwitchCommand":
            deliverymanListPanel = new DeliverymanListPanel(logic.getFilteredDeliverymenList());
            listPanelPlaceholder.getChildren().add(deliverymanListPanel.getRoot());
            statisticsPlaceholder.getChildren().add(deliverymenStatusListPanel.getRoot());
            break;
        case "DeliverymanGetStatisticsCommand":
            personList.setMinWidth(1200);
            deliverymenStatusStatisticsPanel = new DeliverymenStatusStatisticsPanel(logic.getDeliverymenStatusStats());
            statisticsPlaceholder.getChildren().add(deliverymenStatusStatisticsPanel.getRoot());
            break;
        case "DeliverymanListStatusCommand":
            statisticsPlaceholder.getChildren().add(deliverymenStatusListPanel.getRoot());
            personList.setMinWidth(650);
            break;
        default:

        }
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.deliverymans.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());
            Class commandName = commandResult.getCommandName();
            Context nextContext = commandResult.getContext();
            boolean isNewContext = (nextContext != null);

            if (commandName != null) {
                changeDisplay(commandName);
            } else if (isNewContext) {
                LogicManager.setContext(nextContext);
                changeDisplay(LogicManager.getContext());
            } else {
                changeDisplay(LogicManager.getContext());
            }

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
        }
    }

    public static Context getContext() {
        return LogicManager.getContext();
    }
}
