package seedu.deliverymans.logic;

import java.nio.file.Path;
import java.util.LinkedList;

import javafx.collections.ObservableList;
import seedu.deliverymans.commons.core.GuiSettings;
import seedu.deliverymans.logic.commands.CommandResult;
import seedu.deliverymans.logic.commands.exceptions.CommandException;
import seedu.deliverymans.logic.parser.exceptions.ParseException;
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

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     *
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException   If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    //=========== Autocomplete =========================================================
    LinkedList<String> getAutoCompleteResults(String input);

    //=========== Customer =============================================================

    /**
     * Returns the CustomerDatabase.
     *
     * @see seedu.deliverymans.model.Model#getCustomerDatabase()
     */
    ReadOnlyCustomerDatabase getCustomerDatabase();

    /**
     * Returns an unmodifiable view of the filtered list of customers
     */
    ObservableList<Customer> getFilteredCustomerList();

    ObservableList<Order> getCustomerOrders();

    /**
     * Returns the user prefs' customer database file path.
     */
    Path getCustomerDatabaseFilePath();

    //=========== Deliveryman =============================================================

    /**
     * Returns the DeliverymenDatabase.
     *
     * @see seedu.deliverymans.model.Model#getDeliverymenDatabase()
     */
    ReadOnlyDeliverymenDatabase getDeliverymenDatabase();

    /**
     * Returns an unmodifiable view of the filtered list of deliverymen
     */
    ObservableList<Deliveryman> getFilteredDeliverymenList();

    /**
     * Returns the user prefs' deliverymen database file path.
     */
    Path getDeliverymenDatabaseFilePath();

    ObservableList<Deliveryman> getAvailableDeliverymenList();

    ObservableList<Deliveryman> getUnavailableDeliverymenList();

    ObservableList<Deliveryman> getDeliveringDeliverymenList();

    StatisticsRecordCard getDeliverymenStatusStats();

    //=========== Restaurant =============================================================

    /**
     * Returns the RestaurantDatabase.
     *
     * @see seedu.deliverymans.model.Model#getRestaurantDatabase()
     */
    ReadOnlyRestaurantDatabase getRestaurantDatabase();

    /**
     * Returns an unmodifiable view of the filtered list of restaurants
     */
    ObservableList<Restaurant> getFilteredRestaurantList();

    /**
     * Returns an unmodifiable view of the editing restaurant
     */
    ObservableList<Restaurant> getEditingRestaurantList();

    /**
     * Returns the user prefs' restaurant database file path.
     */
    Path getRestaurantDatabaseFilePath();

    //=========== Order =============================================================

    /**
     * Returns the AddressBook.
     *
     * @see Model#getOrderDatabase() Book()
     */
    ReadOnlyOrderDatabase getOrderBook();

    /**
     * Returns an unmodifiable view of the filtered list of orders
     */
    ObservableList<Order> getFilteredOrderList();

    /**
     * Returns the user prefs' order book file path.
     */
    Path getOrderBookFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

}
