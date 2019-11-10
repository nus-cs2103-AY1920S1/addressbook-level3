package seedu.address.testutil;

import java.util.Calendar;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.CalendarDate;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyDataBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.customer.Customer;
import seedu.address.model.order.Order;
import seedu.address.model.phone.Phone;
import seedu.address.model.schedule.Schedule;

/**
 * A default model stub that have all of the methods failing.
 */
public class ModelStub implements Model {

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public GuiSettings getGuiSettings() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setCustomerBook(ReadOnlyDataBook<Customer> customerBook) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyDataBook<Customer> getCustomerBook() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasCustomer(Customer customer) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteCustomer(Customer target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addCustomer(Customer customer) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setCustomer(Customer target, Customer editedCustomer) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Customer> getFilteredCustomerList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredCustomerList(Predicate<Customer> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setPhoneBook(ReadOnlyDataBook<Phone> phoneBook) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyDataBook<Phone> getPhoneBook() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasPhone(Phone phone) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deletePhone(Phone target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addPhone(Phone phone) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setPhone(Phone target, Phone editedPhone) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Phone> getFilteredPhoneList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredPhoneList(Predicate<Phone> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setOrderBook(ReadOnlyDataBook<Order> orderBook) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyDataBook<Order> getOrderBook() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasOrder(Order order) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteOrder(Order target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addOrder(Order order) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setOrder(Order target, Order editedOrder) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Order> getFilteredOrderList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredOrderList(Predicate<Order> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    ////schedule operations
    @Override
    public void setScheduleBook(ReadOnlyDataBook<Schedule> scheduleBook) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyDataBook<Schedule> getScheduleBook() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasSchedule(Schedule schedule) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteSchedule(Schedule target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addSchedule(Schedule schedule) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setSchedule(Schedule target, Schedule editedSchedule) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Schedule> getFilteredScheduleList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredScheduleList(Predicate<Schedule> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public List<Schedule> getConflictingSchedules(Schedule schedule) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setCalendarDate(Calendar calendar) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public CalendarDate getCalendarDate() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setArchivedOrderBook(ReadOnlyDataBook<Order> archivedOrderBook) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyDataBook<Order> getArchivedOrderBook() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasArchivedOrder(Order order) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteArchivedOrder(Order target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addArchivedOrder(Order order) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setArchivedOrder(Order target, Order editedOrder) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Order> getFilteredArchivedOrderList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredArchivedOrderList(Predicate<Order> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void resolveOrderBooksConflict() {
        throw new AssertionError("This method should not be called.");
    }

}
