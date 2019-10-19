package seedu.deliverymans.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.deliverymans.commons.core.GuiSettings;
import seedu.deliverymans.commons.core.LogsCenter;
import seedu.deliverymans.logic.commands.Command;
import seedu.deliverymans.logic.commands.CommandResult;
import seedu.deliverymans.logic.commands.exceptions.CommandException;
import seedu.deliverymans.logic.parser.exceptions.ParseException;
import seedu.deliverymans.logic.parser.universal.UniversalParser;
import seedu.deliverymans.model.Model;
import seedu.deliverymans.model.addressbook.ReadOnlyAddressBook;
import seedu.deliverymans.model.addressbook.person.Person;
import seedu.deliverymans.model.customer.Customer;
import seedu.deliverymans.model.database.ReadOnlyDeliverymenDatabase;
import seedu.deliverymans.model.database.ReadOnlyOrderBook;
import seedu.deliverymans.model.database.ReadOnlyRestaurantDatabase;
import seedu.deliverymans.model.deliveryman.Deliveryman;
import seedu.deliverymans.model.order.Order;
import seedu.deliverymans.model.restaurant.Restaurant;
import seedu.deliverymans.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final UniversalParser universalParser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        universalParser = new UniversalParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = universalParser.parseCommand(commandText);
        commandResult = command.execute(model);

        model.notifyChange(commandText);
        try {
            storage.saveAddressBook(model.getAddressBook());
            storage.saveRestaurantDatabase(model.getRestaurantDatabase());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    //=========== AddressBook =============================================================
    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return model.getAddressBook();
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public Path getAddressBookFilePath() {
        return model.getAddressBookFilePath();
    }

    //=========== Customer =============================================================
    @Override
    public ObservableList<Customer> getFilteredCustomerList() {
        return model.getFilteredCustomerList();
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
    public ReadOnlyOrderBook getOrderBook() {
        return model.getOrderBook();
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
}
