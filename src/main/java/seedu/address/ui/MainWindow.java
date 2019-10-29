package seedu.address.ui;

import java.util.List;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.chart.XYChart;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.UiChange;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.statistic.StatsPayload;
import seedu.address.ui.exception.EnumNotPresentException;
import seedu.address.ui.panels.ArchivedOrderListPanel;
import seedu.address.ui.panels.CalendarPanel;
import seedu.address.ui.panels.CustomerListPanel;
import seedu.address.ui.panels.OrderListPanel;
import seedu.address.ui.panels.PhoneListPanel;


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

    //private PersonListPanel personListPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;
    private TabPanel tabPanel;
    private StatisticsWindow statsWindow;
    private DefaultStatisticsWindow defaultStatsWindow;

    //real panels
    private CustomerListPanel customerListPanel;
    private PhoneListPanel phoneListPanel;
    private OrderListPanel orderListPanel;
    private CalendarPanel calendarPanel;
    private ArchivedOrderListPanel archiveOrderListPanel;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    /*@FXML
    private StackPane personListPanelPlaceholder;*/

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane tabPanelPlaceholder;

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

        customerListPanel = new CustomerListPanel(logic.getFilteredCustomerList());
        phoneListPanel = new PhoneListPanel(logic.getFilteredPhoneList());
        orderListPanel = new OrderListPanel(logic.getFilteredOrderList());
        calendarPanel = new CalendarPanel(logic.getFilteredScheduleList(), logic.getFilteredOrderList(),
                logic.getCalendarDate());
        archiveOrderListPanel = new ArchivedOrderListPanel(logic.getFilteredArchivedOrderList());
        tabPanel = new TabPanel(customerListPanel, phoneListPanel, orderListPanel,
                calendarPanel, archiveOrderListPanel);
        tabPanelPlaceholder.getChildren().add(tabPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand, logic);
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
     * handle StatisticsWindow and create a new one based on user input
     */
    @FXML
    private void handleStats(StatsPayload statsPayload) {
        if (statsPayload.isDefaultQuery()) {
            switch (statsPayload.getStatisticType()) {
            case PROFIT:
                String totalProfitResult = this.logic.calculateTotalProfit(statsPayload);
                this.defaultStatsWindow = new DefaultStatisticsWindow(totalProfitResult, "Total Profit");
                this.defaultStatsWindow.show();
                break;
            case REVENUE:
                String totalRevenueResult = this.logic.calculateTotalRevenue(statsPayload);
                this.defaultStatsWindow = new DefaultStatisticsWindow(totalRevenueResult, "Total Revenue");
                this.defaultStatsWindow.show();
                break;
            case COST:
                String totalCostResult = this.logic.calculateTotalCost(statsPayload);
                this.defaultStatsWindow = new DefaultStatisticsWindow(totalCostResult, "Total Cost");
                this.defaultStatsWindow.show();
                break;
            default:
                throw new EnumNotPresentException("Enum not present in stat command");
            }
        } else {
            //calculate stats with input to logic manager
            switch (statsPayload.getStatisticType()) {
            case PROFIT:
                XYChart.Series<String, Number> profitResult = this.logic.calculateTotalProfitGraph(statsPayload);
                this.statsWindow = new StatisticsWindow("Total Profit", profitResult);
                this.statsWindow.show();
                break;
            case REVENUE:
                XYChart.Series<String, Number> revenueResult = this.logic.calculateTotalRevenueGraph(statsPayload);
                this.statsWindow = new StatisticsWindow("Total Revenue", revenueResult);
                this.statsWindow.show();
                break;
            case COST:
                XYChart.Series<String, Number> costResult = this.logic.calculateTotalCostGraph(statsPayload);
                this.statsWindow = new StatisticsWindow("Total Cost", costResult);
                this.statsWindow.show();
                break;
            default:
                throw new EnumNotPresentException("Enum not present in stat command");
            }
        }
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
            //retrieve the type that the command works on here;
            performUiChanges(commandResult);
            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }

    /**
     * checks which Uichange the command acts on and switches it
     */
    private void performUiChanges(CommandResult input) {
        List<UiChange> listOfUiChange = input.getUiChange();
        for (UiChange type : listOfUiChange) {
            switch (type) {
            case ARCHIVED_ORDER:
                this.showArchivedOrderPanel();
                break;
            case CUSTOMER:
                this.showCustomerPanel();
                break;
            case PHONE:
                this.showPhonePanel();
                break;
            case ORDER:
                this.showOrderPanel();
                break;
            case SCHEDULE:
                this.showSchedulePanel();
                break;
            case HELP:
                this.handleHelp();
                break;
            case STATS:
                this.handleStats(input.getPayloadObject());
                break;
            case EXIT:
                this.handleExit();
                break;
            default:
                //do nothing
                throw new EnumNotPresentException("Enum not present in command");
            }
        }
    }

    /**
     * switch selected tab to customer tab
     */
    private void showCustomerPanel() {
        tabPanel.switchTabCustomer();
    }

    /**
     * switch selected tab to phone tab
     */
    private void showPhonePanel() {
        tabPanel.switchTabPhone();
    }

    /**
     * switch selected tab to order tab
     */
    private void showOrderPanel() {
        tabPanel.switchTabOrder();
    }

    /**
     * switch selected tab to order tab
     */
    private void showSchedulePanel() {
        tabPanel.switchTabSchedule();
    }

    /**
     * switch selected tab to archived order tab
     */
    private void showArchivedOrderPanel() {
        tabPanel.switchTabArchivedOrder();
    }


}
