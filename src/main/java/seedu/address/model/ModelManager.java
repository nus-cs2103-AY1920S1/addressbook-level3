package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.customer.Customer;
import seedu.address.model.order.Order;
import seedu.address.model.order.Status;
import seedu.address.model.phone.Phone;
import seedu.address.model.schedule.Schedule;

/**
 * Represents the in-memory model of the SML data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final DataBook<Customer> customerBook;
    private final DataBook<Phone> phoneBook;
    private final DataBook<Order> orderBook;
    private final DataBook<Schedule> scheduleBook;
    private final DataBook<Order> archivedOrderBook;

    private final FilteredList<Customer> filteredCustomers;
    private final FilteredList<Phone> filteredPhones;
    private final FilteredList<Order> filteredOrders;
    private final FilteredList<Schedule> filteredSchedules;
    private final FilteredList<Order> filteredArchivedOrders;

    private final UserPrefs userPrefs;
    private final CalendarDate calendarDate;

    /**
     * Initializes a ModelManager with the given userPrefs.
     */
    public ModelManager(ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(userPrefs);

        logger.fine("Initializing with user prefs " + userPrefs);

        this.userPrefs = new UserPrefs(userPrefs);

        this.customerBook = new DataBook<>();
        this.phoneBook = new DataBook<>();
        this.orderBook = new DataBook<>();
        this.scheduleBook = new DataBook<>();
        this.archivedOrderBook = new DataBook<>();

        this.filteredCustomers = new FilteredList<>(this.customerBook.getList());
        this.filteredPhones = new FilteredList<>(this.phoneBook.getList());
        this.filteredOrders = new FilteredList<>(this.orderBook.getList());
        this.filteredSchedules = new FilteredList<>(this.scheduleBook.getList());
        this.filteredArchivedOrders = new FilteredList<>(this.archivedOrderBook.getList());

        this.calendarDate = new CalendarDate(Calendar.getInstance());
    }

    public ModelManager() {
        this(new UserPrefs());
    }

    public ModelManager(ReadOnlyDataBook<Customer> customerBook, ReadOnlyDataBook<Phone> phoneBook,
                        ReadOnlyDataBook<Order> orderBook, ReadOnlyDataBook<Schedule> scheduleBook,
                        ReadOnlyDataBook<Order> archivedOrderBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(customerBook, phoneBook, orderBook, scheduleBook, userPrefs);

        logger.fine("Initializing with customer book: " + customerBook
                + " phone book : " + phoneBook
                + " order book: " + orderBook
                + " schedule book: " + scheduleBook
                + " archived order book: " + archivedOrderBook
                + " and user prefs " + userPrefs);

        this.customerBook = new DataBook<>(customerBook);
        this.phoneBook = new DataBook<>(phoneBook);
        this.orderBook = new DataBook<>(orderBook);
        this.scheduleBook = new DataBook<>(scheduleBook);
        this.archivedOrderBook = new DataBook<>(archivedOrderBook);

        resolveOrderBooksConflict();

        this.userPrefs = new UserPrefs(userPrefs);
        this.calendarDate = new CalendarDate(Calendar.getInstance());

        this.filteredCustomers = new FilteredList<>(this.customerBook.getList());
        this.filteredPhones = new FilteredList<>(this.phoneBook.getList());
        this.filteredOrders = new FilteredList<>(this.orderBook.getList());
        this.filteredSchedules = new FilteredList<>(this.scheduleBook.getList());
        this.filteredArchivedOrders = new FilteredList<>(this.archivedOrderBook.getList());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    //=========== customerBook ================================================================================

    @Override
    public void setCustomerBook(ReadOnlyDataBook<Customer> customerBook) {
        this.customerBook.resetData(customerBook);
    }

    @Override
    public ReadOnlyDataBook<Customer> getCustomerBook() {
        return customerBook;
    }

    @Override
    public boolean hasCustomer(Customer customer) {
        requireNonNull(customer);
        return customerBook.has(customer);
    }

    @Override
    public void deleteCustomer(Customer target) {
        customerBook.remove(target);

        // cascade
        logger.info("Cascade deleting order in OrderBook.");

        List<Order> orders = orderBook.getList();
        for (int i = orders.size() - 1; i >= 0; i--) {
            Order order = orders.get(i);
            if (order.getCustomer().equals(target)) {
                deleteOrder(order);
                break;
            }
        }
    }

    @Override
    public void addCustomer(Customer customer) {
        customerBook.add(customer);
        updateFilteredCustomerList(PREDICATE_SHOW_ALL_CUSTOMERS);
    }

    @Override
    public void setCustomer(Customer target, Customer editedCustomer) {
        requireAllNonNull(target, editedCustomer);
        customerBook.set(target, editedCustomer);

        // cascade
        logger.info("Cascade editing order in OrderBook.");

        List<Order> orders = orderBook.getList();
        for (int i = orders.size() - 1; i >= 0; i--) {
            Order order = orders.get(i);
            if (order.getCustomer().equals(target)) {
                Order editedOrder = new Order(order.getId(), editedCustomer, order.getPhone(),
                        order.getPrice(), order.getStatus(), order.getSchedule(), order.getTags());
                orderBook.set(order, editedOrder);
                break;
            }
        }
    }

    //=========== Filtered Customer List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Customer} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Customer> getFilteredCustomerList() {
        return filteredCustomers;
    }

    @Override
    public void updateFilteredCustomerList(Predicate<Customer> predicate) {
        requireNonNull(predicate);
        filteredCustomers.setPredicate(predicate);
    }

    //=========== phoneBook ================================================================================

    @Override
    public void setPhoneBook(ReadOnlyDataBook<Phone> phoneBook) {
        this.phoneBook.resetData(phoneBook);
    }

    @Override
    public ReadOnlyDataBook<Phone> getPhoneBook() {
        return phoneBook;
    }

    @Override
    public boolean hasPhone(Phone phone) {
        requireNonNull(phone);
        return phoneBook.has(phone);
    }

    @Override
    public void deletePhone(Phone target) {
        phoneBook.remove(target);

        // cascade
        logger.info("Cascade deleting orders in OrderBook.");

        List<Order> orders = orderBook.getList();

        for (int i = orders.size() - 1; i >= 0; i--) {
            Order order = orders.get(i);
            if (order.getPhone().equals(target)) {
                deleteOrder(order);
                break;
            }
        }
    }

    @Override
    public void addPhone(Phone phone) {
        phoneBook.add(phone);
        updateFilteredPhoneList(PREDICATE_SHOW_ALL_PHONES);
    }

    @Override
    public void setPhone(Phone target, Phone editedPhone) {
        requireAllNonNull(target, editedPhone);
        phoneBook.set(target, editedPhone);

        // cascade
        logger.info("Cascade editing Orders in OrderBook.");

        List<Order> orders = orderBook.getList();

        for (int i = orders.size() - 1; i >= 0; i--) {
            Order order = orders.get(i);
            if (order.getPhone().equals(target)) {
                Order editedOrder = new Order(order.getId(), order.getCustomer(), editedPhone,
                        order.getPrice(), order.getStatus(), order.getSchedule(), order.getTags());
                setOrder(order, editedOrder);
                break;
            }
        }
    }

    //=========== Filtered Phone List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Phone} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Phone> getFilteredPhoneList() {
        return filteredPhones;
    }

    @Override
    public void updateFilteredPhoneList(Predicate<Phone> predicate) {
        requireNonNull(predicate);
        filteredPhones.setPredicate(predicate);
    }

    //=========== Order DataBook ================================================================================

    @Override
    public void setOrderBook(ReadOnlyDataBook<Order> orderBook) {
        this.orderBook.resetData(orderBook);
    }

    @Override
    public ReadOnlyDataBook<Order> getOrderBook() {
        return orderBook;
    }

    @Override
    public boolean hasOrder(Order order) {
        requireNonNull(order);
        return orderBook.has(order);
    }

    @Override
    public void deleteOrder(Order target) {
        orderBook.remove(target);

        // cascade
        logger.info("Cascade delete schedule in ScheduleBook.");
        Optional<Schedule> targetSchedule = target.getSchedule();
        if (targetSchedule.isPresent() && hasSchedule(targetSchedule.get())) {
            deleteSchedule(targetSchedule.get());
        }
    }

    @Override
    public void addOrder(Order order) {
        orderBook.add(order);
        updateFilteredOrderList(PREDICATE_SHOW_ALL_ORDER);
    }

    @Override
    public void setOrder(Order target, Order editedOrder) {
        requireAllNonNull(target, editedOrder);

        orderBook.set(target, editedOrder);
    }

    //=========== Filtered Order List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Order} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Order> getFilteredOrderList() {
        return filteredOrders;
    }

    @Override
    public void updateFilteredOrderList(Predicate<Order> predicate) {
        requireNonNull(predicate);
        filteredOrders.setPredicate(predicate);
    }

    //=========== Schedule DataBook ================================================================================

    @Override
    public void setScheduleBook(ReadOnlyDataBook<Schedule> scheduleBook) {
        this.scheduleBook.resetData(scheduleBook);
    }

    @Override
    public ReadOnlyDataBook<Schedule> getScheduleBook() {
        return scheduleBook;
    }

    @Override
    public boolean hasSchedule(Schedule schedule) {
        requireNonNull(schedule);
        return scheduleBook.has(schedule);
    }

    @Override
    public void deleteSchedule(Schedule target) {
        scheduleBook.remove(target);
        setCalendarDate(target.getCalendar());


        // cascade
        logger.info("Cascade deleting schedules in OrderBook.");
        List<Order> orders = orderBook.getList();
        for (Order order : orders) {
            order.getSchedule().ifPresent(schedule -> {
                if (schedule.equals(target)) {
                    Order editedOrder = new Order(order.getId(), order.getCustomer(), order.getPhone(),
                            order.getPrice(), Status.UNSCHEDULED, Optional.empty(), order.getTags());
                    setOrder(order, editedOrder);
                }
            });
        }
    }

    @Override
    public void addSchedule(Schedule schedule) {
        scheduleBook.add(schedule);
        setCalendarDate(schedule.getCalendar());
        updateFilteredScheduleList(PREDICATE_SHOW_ALL_SCHEDULE);
    }

    @Override
    public void setSchedule(Schedule target, Schedule editedSchedule) {
        requireAllNonNull(target, editedSchedule);

        scheduleBook.set(target, editedSchedule);
        setCalendarDate(editedSchedule.getCalendar());

        // cascade
        logger.info("Cascade edited schedules in OrderBook.");
        List<Order> orders = orderBook.getList();
        for (Order order : orders) {
            order.getSchedule().ifPresent(schedule -> {
                if (schedule.equals(target)) {
                    Order editedOrder = new Order(order.getId(), order.getCustomer(), order.getPhone(),
                            order.getPrice(), order.getStatus(), Optional.of(editedSchedule), order.getTags());
                    orderBook.set(order, editedOrder);
                }
            });
        }
    }

    @Override
    public List<Schedule> getConflictingSchedules(Schedule schedule) {
        requireNonNull(schedule);
        List<Schedule> conflicts = new ArrayList<>();

        Calendar startTime = schedule.getCalendar();
        Calendar earliestUnconflictedStartTime = (Calendar) startTime.clone();
        earliestUnconflictedStartTime.add(Calendar.HOUR_OF_DAY, -1);
        Calendar latestUnconflictedStartTime = (Calendar) startTime.clone();
        latestUnconflictedStartTime.add(Calendar.HOUR_OF_DAY, 1);

        List<Schedule> schedules = scheduleBook.getList();

        // defensive filter for orderless schedule - in 0 orders
        // extra filter for same schedule
        schedules.stream()
                .filter(x -> orderBook.getList().stream()
                        .filter(y -> y.getSchedule().isPresent())
                        .anyMatch(y -> y.getSchedule().get().isSameAs(x)))
                .filter(x -> !x.isSameAs(schedule))
                .filter(x -> x.getCalendar().after(earliestUnconflictedStartTime))
                .filter(x -> x.getCalendar().before(latestUnconflictedStartTime))
                .sorted(Comparator.comparing(Schedule::getCalendar))
                .forEach(conflicts::add);

        return conflicts;
    }

    //=========== Filtered Schedule List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Schedule} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Schedule> getFilteredScheduleList() {
        return filteredSchedules;
    }

    @Override
    public void updateFilteredScheduleList(Predicate<Schedule> predicate) {
        requireNonNull(predicate);
        filteredSchedules.setPredicate(predicate);
    }

    //=========== CalendarDate ================================================================================

    @Override
    public void setCalendarDate(Calendar calendar) {
        requireNonNull(calendar);
        calendarDate.setCalendar(calendar);
    }

    @Override
    public CalendarDate getCalendarDate() {
        return calendarDate;
    }

    //=========== Archived Order DataBook ======================================================================

    @Override
    public void setArchivedOrderBook(ReadOnlyDataBook<Order> archivedOrderBook) {
        this.archivedOrderBook.resetData(archivedOrderBook);
    }

    @Override
    public ReadOnlyDataBook<Order> getArchivedOrderBook() {
        return archivedOrderBook;
    }

    @Override
    public boolean hasArchivedOrder(Order archivedOrder) {
        requireNonNull(archivedOrder);
        return archivedOrderBook.has(archivedOrder);
    }

    @Override
    public void deleteArchivedOrder(Order target) {
        archivedOrderBook.remove(target);

    }

    @Override
    public void addArchivedOrder(Order archivedOrder) {
        archivedOrderBook.add(archivedOrder);
        updateFilteredArchivedOrderList(PREDICATE_SHOW_ALL_ORDER);
    }

    @Override
    public void setArchivedOrder(Order target, Order editedArchived) {
        requireAllNonNull(target, editedArchived);

        archivedOrderBook.set(target, editedArchived);
    }

    //=========== Filtered Order List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code ArchivedOrder} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Order> getFilteredArchivedOrderList() {
        return filteredArchivedOrders;
    }

    @Override
    public void updateFilteredArchivedOrderList(Predicate<Order> predicate) {
        requireNonNull(predicate);
        filteredArchivedOrders.setPredicate(predicate);
    }

    @Override
    public void resolveOrderBooksConflict() {
        List<Order> orders = orderBook.getList();

        logger.info("Remove completed/cancelled orders from orderBook and place them in archivedOrderBook");

        for (int i = orders.size() - 1; i >= 0; i--) {
            Order o = orders.get(i);

            boolean isCancelledOrCompleted = o.getStatus().equals(Status.CANCELLED)
                    || o.getStatus().equals(Status.COMPLETED);

            if (isCancelledOrCompleted) {


                orderBook.remove(o);

                if (!archivedOrderBook.has(o)) {
                    archivedOrderBook.add(o);
                }
            }
        }

        List<Order> archivedOrders = archivedOrderBook.getList();

        logger.info("Remove unscheduled/scheduled orders from archivedOrderBook and place them in orderBook");

        for (int i = archivedOrders.size() - 1; i >= 0; i--) {
            Order o = archivedOrders.get(i);

            boolean isNotCancelledOrCompleted = !o.getStatus().equals(Status.CANCELLED)
                    && !o.getStatus().equals(Status.COMPLETED);

            if (isNotCancelledOrCompleted) {
                archivedOrderBook.remove(o);


                //have to add
                if (!orderBook.has(o)) {
                    orderBook.add(o);
                }
            }
        }

        orders = orderBook.getList();

        List<Phone> phones = phoneBook.getList();
        List<Customer> customers = customerBook.getList();

        logger.info("Ensure that all orders in orderBooks have an exact copy of phone and customer in their "
                + "respective books. If not, cancel the order and dump it into archives.");

        for (int i = orders.size() - 1; i >= 0; i--) {
            Order o = orders.get(i);
            assert (!o.getStatus().equals(Status.CANCELLED) && !o.getStatus().equals(Status.COMPLETED));

            boolean hasExactPhoneCopy = false;
            for (Phone p : phones) {
                if (o.getPhone().equals(p)) {
                    hasExactPhoneCopy = true;
                }
            }

            boolean hasExactCustomerCopy = false;
            for (Customer c : customers) {
                if (o.getCustomer().equals(c)) {
                    hasExactCustomerCopy = true;
                }
            }

            if (!hasExactPhoneCopy || !hasExactCustomerCopy) {
                Order editedOrder = new Order(o.getId(), o.getCustomer(), o.getPhone(),
                        o.getPrice(), Status.CANCELLED, o.getSchedule(), o.getTags());
                orderBook.remove(o);

                if (!archivedOrderBook.has(o)) {
                    archivedOrderBook.add(editedOrder);
                }
            }

        }

        ArrayList<Integer> toCancelIndexList = new ArrayList<>();
        archivedOrders = archivedOrderBook.getList();

        logger.info("Ensure that archived orders list has no completed orders with duplicate phones. "
                + "If not, cancel the orders.");

        for (int i = archivedOrders.size() - 1; i >= 0; i--) {
            Order o = archivedOrders.get(i);
            assert (o.getStatus().equals(Status.CANCELLED) || o.getStatus().equals(Status.COMPLETED));

            boolean isCompletedOrder = o.getStatus().equals(Status.COMPLETED);

            if (isCompletedOrder) {

                boolean hasDuplicatePhone = false;

                for (int j = archivedOrders.size() - 1; j >= 0; j--) {
                    Order otherOrder = archivedOrders.get(j);

                    boolean isSameIndex = i != j;
                    boolean isCompletedOtherOrder = otherOrder.getStatus().equals(Status.COMPLETED);
                    boolean isSamePhones = o.getPhone().isSameAs(otherOrder.getPhone());

                    if (isSameIndex
                            && isCompletedOtherOrder
                            && isSamePhones) {
                        hasDuplicatePhone = true;
                        break;
                    }
                }

                if (hasDuplicatePhone) {
                    toCancelIndexList.add(i);
                }
            }
        }

        toCancelIndexList.sort(Collections.reverseOrder());

        for (int index : toCancelIndexList) {
            Order o = archivedOrders.get(index);
            Order editedOrder = new Order(o.getId(), o.getCustomer(), o.getPhone(),
                    o.getPrice(), Status.CANCELLED, o.getSchedule(), o.getTags());
            archivedOrderBook.set(o, editedOrder);
        }

        phones = phoneBook.getList();

        logger.info("Ensure that completed orders do not have phones in the existing phone book. "
                + "If not, delete the phones");
        logger.info("Also ensure that completed orders are scheduled. "
                + "If not, cancel the orders.");
        for (int i = archivedOrders.size() - 1; i >= 0; i--) {
            Order o = archivedOrders.get(i);
            assert (o.getStatus().equals(Status.CANCELLED) || o.getStatus().equals(Status.COMPLETED));

            boolean isCompletedOrder = o.getStatus().equals(Status.COMPLETED);

            if (isCompletedOrder) {
                Phone phone = o.getPhone();
                boolean hasPhoneInPhoneBook = false;


                for (int j = phones.size() - 1; j >= 0; j--) {

                    Phone otherPhone = phones.get(j);

                    hasPhoneInPhoneBook = phone.isSameAs(phones.get(j));

                    if (hasPhoneInPhoneBook) {
                        deletePhone(otherPhone);
                        break;
                    }
                }


                boolean isScheduledOrder = o.getSchedule().isPresent();

                if (!isScheduledOrder) {
                    Order editedOrder = new Order(o.getId(), o.getCustomer(), o.getPhone(),
                            o.getPrice(), Status.CANCELLED, o.getSchedule(), o.getTags());
                    archivedOrderBook.set(o, editedOrder);
                }
            }
        }

        List<Schedule> schedules = scheduleBook.getList();

        logger.info("Ensure that schedules that do not have "
                + "an order attached to it in ScheduleBook are deleted.");

        for (int i = schedules.size() - 1; i >= 0; i--) {

            Schedule s = schedules.get(i);

            boolean hasOrder = false;

            for (int j = orders.size() - 1; j >= 0; j--) {

                Order o = orders.get(j);

                if (o.getSchedule().isPresent() && s.equals(o.getSchedule().get())) {
                    hasOrder = true;
                    break;
                }
            }

            if (!hasOrder) {
                scheduleBook.remove(s);
            }
        }

        logger.info("Ensure that all schedules in OrderBook exist in ScheduleBook.");
        for (int i = orders.size() - 1; i >= 0; i--) {
            Order o = orders.get(i);

            if (o.getSchedule().isPresent() && !scheduleBook.has(o.getSchedule().get())) {
                scheduleBook.add(o.getSchedule().get());
            }
        }

    }


    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return customerBook.equals(other.customerBook)
                && phoneBook.equals(other.phoneBook)
                && orderBook.equals(other.orderBook)
                && scheduleBook.equals(other.scheduleBook)
                && archivedOrderBook.equals(other.archivedOrderBook)
                && userPrefs.equals(other.userPrefs)
                && filteredCustomers.equals(other.filteredCustomers)
                && filteredPhones.equals(other.filteredPhones)
                && filteredOrders.equals(other.filteredOrders)
                && filteredSchedules.equals(other.filteredSchedules)
                && filteredArchivedOrders.equals(other.filteredArchivedOrders);
    }

}
