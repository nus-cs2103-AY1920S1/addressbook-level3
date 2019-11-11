package seedu.deliverymans.logic;

import static seedu.deliverymans.logic.parser.CliSyntax.PREFIX_CUSTOMER;
import static seedu.deliverymans.logic.parser.CliSyntax.PREFIX_FOOD;
import static seedu.deliverymans.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.deliverymans.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.deliverymans.logic.parser.CliSyntax.PREFIX_RESTAURANT;

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
import seedu.deliverymans.logic.parser.ArgumentMultimap;
import seedu.deliverymans.logic.parser.ArgumentTokenizer;
import seedu.deliverymans.logic.parser.ParserUtil;
import seedu.deliverymans.logic.parser.Prefix;
import seedu.deliverymans.logic.parser.exceptions.ParseException;
import seedu.deliverymans.logic.parser.universal.UniversalParser;
import seedu.deliverymans.model.Model;
import seedu.deliverymans.model.Name;
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

    private LinkedList<String> currentList = new LinkedList<>();
    private Restaurant currRestaurant;

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
        Command command = universalParser.parseCommand(commandText, currentContext);
        commandResult = command.execute(model);

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
        int firstSpace = input.indexOf(" ");
        if (firstSpace == -1) { // still entering commandWord
            return trieManager.getAutoCompleteResults(input, currentContext);
        }
        String commandWord = input.substring(0, firstSpace);

        // input is not a valid commandWord/ commandWord has no valid prefix to autocomplete
        if (!trieManager.hasPrefixes(commandWord)) {
            return new LinkedList<>();
        }
        if (input.endsWith("/")) { // entering a new prefix for the valid commandWord
            currentList = getRelevantList(input);
        }
        // still entering the input for the current prefix
        return currentList;
    }

    private LinkedList<String> getRelevantList(String input) {
        int lastSpace = input.lastIndexOf(" ");
        String prefix = input.substring(lastSpace + 1);
        switch (prefix) {
        case "n/":
            return getFilteredOrderList().stream().map(x -> x.getOrderName().fullName)
                    .sorted(String::compareToIgnoreCase)
                    .collect(Collectors.toCollection(LinkedList::new));
        case "c/":
            if (hasDuplicatePrefix(input, prefix)) {
                return new LinkedList<>();
            }
            return getFilteredCustomerList().stream().map(x -> x.getUserName().fullName)
                    .sorted(String::compareToIgnoreCase)
                    .collect(Collectors.toCollection(LinkedList::new));
        case "r/":
            if (hasDuplicatePrefix(input, prefix)) {
                return new LinkedList<>();
            }
            return getFilteredRestaurantList().stream().map(x -> x.getName().fullName)
                    .sorted(String::compareToIgnoreCase)
                    .collect(Collectors.toCollection(LinkedList::new));
        case "f/":
            currRestaurant = getInputRestaurant(input);
            if (currRestaurant == null) {
                return new LinkedList<>();
            }
            return currRestaurant.getMenu().stream().map(x -> x.getName().fullName)
                    .sorted(String::compareToIgnoreCase)
                    .collect(Collectors.toCollection(LinkedList::new));
        default:
            return new LinkedList<>();
        }
    }

    private boolean hasDuplicatePrefix(String input, String prefix) {
        return ParserUtil.hasRepeatedPrefix(input, new Prefix(prefix));
    }

    private Restaurant getInputRestaurant(String input) {
        Name restaurantName;
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(input, PREFIX_RESTAURANT,
                PREFIX_FOOD, PREFIX_QUANTITY, PREFIX_CUSTOMER, PREFIX_INDEX);

        // if prefix value is not present
        if (argMultimap.getValue(PREFIX_RESTAURANT).isEmpty()) {
            return null;
        }

        try {
            restaurantName = ParserUtil.parseName(argMultimap.getValue(PREFIX_RESTAURANT).get());
        } catch (ParseException pe) { // invalid Name format
            System.out.println(argMultimap.getValue(PREFIX_RESTAURANT).get());
            return null;
        }

        for (Restaurant restaurant : getFilteredRestaurantList()) {
            if (restaurant.getName().equals(restaurantName)) {
                return restaurant;
            }
        }
        return null;
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
    public ObservableList<Order> getCustomerOrders() {
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
    public static void setContext(Context context) {
        currentContext = context;
    }

    public static Context getContext() {
        return currentContext;
    }
}
