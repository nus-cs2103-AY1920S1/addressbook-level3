package seedu.address.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.SellerManagerParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.CalendarDate;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyDataBook;
import seedu.address.model.customer.Customer;
import seedu.address.model.order.Order;
import seedu.address.model.person.Person;
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
    private final CommandHistory commandHistory = CommandHistory.getCommandHistory();
    private final UndoRedoStack undoRedoStack = UndoRedoStack.getUndoRedoStack();
    private final GraphGenerator graphGenerator;

    public LogicManager(Model model, Storage storage, Statistic statistic) {
        this.model = model;
        this.storage = storage;
        this.statistic = statistic;
        graphGenerator = new GraphGenerator(model);
        sellerManagerParser = new SellerManagerParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;


        try {
            Command command = sellerManagerParser.parseCommand(commandText);
            commandResult = command.execute(model, commandHistory, undoRedoStack);
            storage.saveCustomerBook(model.getCustomerBook());
            storage.savePhoneBook(model.getPhoneBook());
            storage.saveScheduleBook(model.getScheduleBook());
            storage.saveOrderBook(model.getOrderBook());
            storage.saveArchivedOrderBook(model.getArchivedOrderBook());
            commandHistory.add(commandText);
            undoRedoStack.push(command);
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return model.getAddressBook();
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
    public ObservableList<Person> getFilteredPersonList() {
        return model.getFilteredPersonList();
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
    public Path getAddressBookFilePath() {
        return model.getAddressBookFilePath();
    }

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
    public AutoCompleteResult getAutocompleteValues(String input) {
        return graphGenerator.process(input);
    }

}
