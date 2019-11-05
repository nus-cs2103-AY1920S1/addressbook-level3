package seedu.deliverymans.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.deliverymans.commons.core.GuiSettings;
import seedu.deliverymans.commons.core.LogsCenter;
import seedu.deliverymans.logic.commands.Command;
import seedu.deliverymans.logic.commands.CommandResult;
import seedu.deliverymans.logic.commands.exceptions.CommandException;
import seedu.deliverymans.logic.parser.exceptions.ParseException;
import seedu.deliverymans.logic.parser.universal.Context;
import seedu.deliverymans.logic.parser.universal.UniversalParser;
import seedu.deliverymans.model.Model;
import seedu.deliverymans.model.customer.Customer;
import seedu.deliverymans.model.database.ReadOnlyCustomerDatabase;
import seedu.deliverymans.model.database.ReadOnlyDeliverymenDatabase;
import seedu.deliverymans.model.database.ReadOnlyOrderDatabase;
import seedu.deliverymans.model.database.ReadOnlyRestaurantDatabase;
import seedu.deliverymans.model.deliveryman.Deliveryman;
import seedu.deliverymans.model.deliveryman.deliverymanstatistics.StatisticsRecordCard;
import seedu.deliverymans.model.order.Order;
import seedu.deliverymans.model.restaurant.Restaurant;
import seedu.deliverymans.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";

    private static Context currentContext = Context.GLOBAL;

    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final TrieManager trieManager;
    private final UniversalParser universalParser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        this.trieManager = new TrieManager();
        universalParser = new UniversalParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = universalParser.parseCommand(commandText, this.currentContext);
        commandResult = command.execute(model, this);

        model.notifyChange(commandText);
        try {
            storage.saveCustomerDatabase(model.getCustomerDatabase());
            storage.saveRestaurantDatabase(model.getRestaurantDatabase());
            storage.saveDeliverymenDatabase(model.getDeliverymenDatabase());
            storage.saveOrderDatabase(model.getOrderDatabase());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    //=========== Autocomplete =========================================================
    @Override
    public LinkedList<String> getAutoCompleteResults(String input) {
        if (input.endsWith("/")) {
            int index = input.lastIndexOf(" ");
            if (index != -1) {
                String prefix = input.substring(index + 1);
                return getRelevantList(prefix);
            }
            return new LinkedList<>();
        }
        return trieManager.getAutoCompleteResults(input, currentContext);
    }

    private LinkedList<String> getRelevantList(String input) {
        switch(input) {
        case "c/":
            return getFilteredCustomerList().stream().map(x -> x.getName().fullName)
                    .collect(Collectors.toCollection(LinkedList::new));
        case "r/":
            return getFilteredRestaurantList().stream().map(x -> x.getName().fullName)
                    .collect(Collectors.toCollection(LinkedList::new));
        case "f/":
            return new LinkedList<>();
        default:
            return new LinkedList<>();
        }
    }

    //=========== Customer =============================================================

    @Override
    public ReadOnlyCustomerDatabase getCustomerDatabase() {
        return model.getCustomerDatabase();
    }

    @Override
    public ObservableList<Customer> getFilteredCustomerList() {
        return model.getFilteredCustomerList();
    }

    @Override
    public Customer getCustomerOrders() {
        return model.getCustomerOrders();
    }

    @Override
    public Path getCustomerDatabaseFilePath() {
        return model.getCustomerDatabaseFilePath();
    }

    //=========== Deliveryman =============================================================
    @Override
    public ReadOnlyDeliverymenDatabase getDeliverymenDatabase() {
        return model.getDeliverymenDatabase();
    }

    @Override
    public ObservableList<Deliveryman> getFilteredDeliverymenList() {
        return model.getFilteredDeliverymenList();
    }

    @Override
    public Path getDeliverymenDatabaseFilePath() {
        return model.getDeliverymenDatabaseFilePath();
    }

    @Override
    public ObservableList<Deliveryman> getAvailableDeliverymenList() {
        return model.getAvailableMenList();
    }

    @Override
    public ObservableList<Deliveryman> getUnavailableDeliverymenList() {
        return model.getUnavailableMenList();
    }

    @Override
    public ObservableList<Deliveryman> getDeliveringDeliverymenList() {
        return model.getDeliveringMenList();
    }

    @Override
    public StatisticsRecordCard getDeliverymenStatusStats() {
        return model.getDeliverymenStatusStats();
    }

    //=========== Restaurant =============================================================
    @Override
    public ReadOnlyRestaurantDatabase getRestaurantDatabase() {
        return model.getRestaurantDatabase();
    }

    @Override
    public ObservableList<Restaurant> getFilteredRestaurantList() {
        return model.getFilteredRestaurantList();
    }

    @Override
    public ObservableList<Restaurant> getEditingRestaurantList() {
        return model.getEditingRestaurantList();
    }

    @Override
    public Path getRestaurantDatabaseFilePath() {
        return model.getRestaurantDatabaseFilePath();
    }

    //=========== Order =============================================================
    @Override
    public ReadOnlyOrderDatabase getOrderBook() {
        return model.getOrderDatabase();
    }

    @Override
    public ObservableList<Order> getFilteredOrderList() {
        return model.getFilteredOrderList();
    }

    @Override
    public Path getOrderBookFilePath() {
        return model.getOrderBookFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }

    //=============Context======================
    @Override
    public void setContext(Context context) {
        this.currentContext = context;
    }

    public static Context getContext() {
        return currentContext;
    }
}
