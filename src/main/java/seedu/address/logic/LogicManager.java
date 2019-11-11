package seedu.address.logic;

import java.io.IOException;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.autocomplete.AutoCompleteResult;
import seedu.address.logic.autocomplete.AutoCompleteResultGenerator;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.UndoableCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.SellerManagerParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.CalendarDate;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyDataBook;
import seedu.address.model.customer.Customer;
import seedu.address.model.order.Order;
import seedu.address.model.phone.Phone;
import seedu.address.model.schedule.Schedule;
import seedu.address.statistic.Statistic;
import seedu.address.statistic.StatsPayload;
import seedu.address.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final SellerManagerParser sellerManagerParser;
    private final Statistic statistic;
    private final CommandHistory commandHistory;
    private final UndoRedoStack undoRedoStack;
    private final AutoCompleteResultGenerator autoCompleteResultGenerator;

    public LogicManager(Model model, Storage storage, Statistic statistic) {
        this.model = model;
        this.storage = storage;
        this.statistic = statistic;
        autoCompleteResultGenerator = new AutoCompleteResultGenerator(model);
        sellerManagerParser = new SellerManagerParser();
        commandHistory = new CommandHistory();
        undoRedoStack = new UndoRedoStack();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;


        try {
            Command command = sellerManagerParser.parseCommand(commandText);

            logger.info("----------------[COMMAND][" + command + "]");

            commandResult = command.execute(model, commandHistory, undoRedoStack);
            storage.saveCustomerBook(model.getCustomerBook());
            storage.savePhoneBook(model.getPhoneBook());
            storage.saveScheduleBook(model.getScheduleBook());
            storage.saveOrderBook(model.getOrderBook());
            storage.saveArchivedOrderBook(model.getArchivedOrderBook());
            commandHistory.add(commandText);
            undoRedoStack.push(command);

            if (command instanceof UndoableCommand) {
                UndoableCommand undoableCommand = ((UndoableCommand) command);
                undoableCommand.saveSuccessMessage(commandResult.getFeedbackToUser());
            }
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyDataBook<Order> getOrderBook() {
        return model.getOrderBook();
    }

    @Override
    public ReadOnlyDataBook<Order> getArchivedOrderBook() {
        return model.getArchivedOrderBook();
    }

    @Override
    public ReadOnlyDataBook<Phone> getPhoneBook() {
        return model.getPhoneBook();
    }

    @Override
    public ObservableList<Customer> getFilteredCustomerList() {
        return model.getFilteredCustomerList(); }

    @Override
    public ObservableList<Phone> getFilteredPhoneList() {
        return model.getFilteredPhoneList();
    }

    @Override
    public ObservableList<Order> getFilteredOrderList() {
        return model.getFilteredOrderList();
    }

    @Override
    public ObservableList<Order> getFilteredArchivedOrderList() {
        return model.getFilteredArchivedOrderList();
    }


    @Override
    public ObservableList<Schedule> getFilteredScheduleList() {
        return model.getFilteredScheduleList(); }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }

    @Override
    public String calculateTotalProfit(StatsPayload statsPayload) {
        return this.statistic.calculateTotalProfitOnCompleted(this.getArchivedOrderBook(), statsPayload);
    }

    @Override
    public String calculateTotalRevenue(StatsPayload statsPayload) {
        return this.statistic.calculateTotalRevenueOnCompleted(this.getArchivedOrderBook(), statsPayload);
    }

    @Override
    public String calculateTotalCost(StatsPayload statsPayload) {
        return this.statistic.calculateTotalCostOnCompleted(this.getArchivedOrderBook(), statsPayload);
    }

    @Override
    public CalendarDate getCalendarDate() {
        return model.getCalendarDate();
    }

    @Override
    public XYChart.Series<String, Number> calculateTotalProfitGraph(StatsPayload statsPayload) {
        return this.statistic.calculateTotalProfitOnCompletedGraph(this.getArchivedOrderBook(), statsPayload);
    }

    @Override
    public XYChart.Series<String, Number> calculateTotalRevenueGraph(StatsPayload statsPayload) {
        return this.statistic.calculateTotalRevenueOnCompletedGraph(this.getArchivedOrderBook(), statsPayload);
    }

    @Override
    public XYChart.Series<String, Number> calculateTotalCostGraph(StatsPayload statsPayload) {
        return this.statistic.calculateTotalCostOnCompletedGraph(this.getArchivedOrderBook(), statsPayload);
    }

    @Override
    public AutoCompleteResult getAutoCompleteResult(String input) {
        return autoCompleteResultGenerator.process(input);
    }

}
