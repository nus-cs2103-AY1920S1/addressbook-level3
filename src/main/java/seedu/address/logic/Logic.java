package seedu.address.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.CalendarDate;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyDataBook;
import seedu.address.model.customer.Customer;
import seedu.address.model.order.Order;
import seedu.address.model.person.Person;
import seedu.address.model.phone.Phone;
import seedu.address.model.schedule.Schedule;
import seedu.address.statistic.StatsPayload;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the AddressBook.
     *
     * @see seedu.address.model.Model#getAddressBook()
     */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns the Order DataBook.
     *
     * @see seedu.address.model.Model#getOrderBook()
     */
    ReadOnlyDataBook<Order> getOrderBook();

    /**
     * Returns the ArchivedOrder DataBook.
     *
     * @see seedu.address.model.Model#getOrderBook()
     */
    ReadOnlyDataBook<Order> getArchivedOrderBook();

    /**
     * Returns the Phone DataBook.
     *
     * @see seedu.address.model.Model#getPhoneBook()
     */
    ReadOnlyDataBook<Phone> getPhoneBook();

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Person> getFilteredPersonList();

    /** Returns an unmodifiable view of the filtered list of customers */
    ObservableList<Customer> getFilteredCustomerList();

    /** Returns an unmodifiable view of the filtered list of phone */
    ObservableList<Phone> getFilteredPhoneList();

    /** Returns an unmodifiable view of the filtered list of order */
    ObservableList<Order> getFilteredOrderList();

    /** Returns an unmodifiable view of the filtered list of order */
    ObservableList<Order> getFilteredArchivedOrderList();

    /** Returns an unmodifiable view of the filtered list of schedule */
    ObservableList<Schedule> getFilteredScheduleList();



    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Gets logic to calculate total profit based on user input
     */
    String calculateTotalProfit(StatsPayload statsPayload);

    /**
     * Gets logic to calculate Total Revenue based on user input
     */
    String calculateTotalRevenue(StatsPayload statsPayload);


    /**
     * Gets logic to calculate total profit based on user input
     */
    String calculateTotalCost(StatsPayload statsPayload);

    /**
     * Returns the CalendarDate object
     */
    CalendarDate getCalendarDate();

     /**
     * Gets logic to calculate total profit based on user input
     * return a XYChart.series
     */
     XYChart.Series<String, Number> calculateTotalRevenueGraph(StatsPayload statsPayload);

    /**
     * Gets logic to calculate total cost based on user input
     * return a XYChart.series
     */
    XYChart.Series<String, Number> calculateTotalCostGraph(StatsPayload statsPayload);

    /**
     * Gets logic to calculate total cost based on user input
     * return a XYChart.series
     */
    XYChart.Series<String, Number> calculateTotalProfitGraph(StatsPayload statsPayload);

    /**
     * Takes an input string and returns an {@code AutoCompleteResult} according to existing values.
     * @param input The input string.
     * @return A matching {@code AutoCompleteResult}.
     */
    AutoCompleteResult getAutocompleteValues(String input);

}
