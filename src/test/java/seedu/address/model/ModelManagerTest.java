package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CUSTOMERS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalCustomers.BENSON;
import static seedu.address.testutil.TypicalCustomers.CARL;
import static seedu.address.testutil.TypicalCustomers.DANIEL;
import static seedu.address.testutil.TypicalCustomers.FIONA;
import static seedu.address.testutil.TypicalOrders.ORDERONE;
import static seedu.address.testutil.TypicalOrders.ORDERTHREE;
import static seedu.address.testutil.TypicalOrders.VIPORDER;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPhones.ANDROIDONE;
import static seedu.address.testutil.TypicalPhones.IPHONEONE;
import static seedu.address.testutil.TypicalPhones.IPHONEPRO11;
import static seedu.address.testutil.TypicalPhones.IPHONEXR;
import static seedu.address.testutil.TypicalSchedules.CBD_SCHEDULE;
import static seedu.address.testutil.TypicalSchedules.FRIDAY_SCHEDULE;
import static seedu.address.testutil.TypicalSchedules.MONDAY_SCHEDULE;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.customer.Customer;
import seedu.address.model.customer.predicates.CustomerContainsKeywordsPredicate;
import seedu.address.model.order.Order;
import seedu.address.model.phone.Phone;
import seedu.address.model.schedule.Schedule;
import seedu.address.testutil.CustomerBookBuilder;
import seedu.address.testutil.OrderBookBuilder;
import seedu.address.testutil.OrderBuilder;
import seedu.address.testutil.PhoneBookBuilder;
import seedu.address.testutil.ScheduleBookBuilder;
import seedu.address.testutil.ScheduleBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new AddressBook(), new AddressBook(modelManager.getAddressBook()));
    }

    //=========== UserPrefs ==================================================================================

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setAddressBookFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    //=========== AddressBook ================================================================================

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setAddressBookFilePath(null));
    }

    @Test
    public void setAddressBookFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setAddressBookFilePath(path);
        assertEquals(path, modelManager.getAddressBookFilePath());
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        modelManager.addPerson(ALICE);
        assertTrue(modelManager.hasPerson(ALICE));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPersonList().remove(0));
    }

    //=========== customerBook ================================================================================

    @Test
    public void setCustomerBook_nullCustomerBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setCustomerBook(null));
    }

    @Test
    public void setCustomerBook_validCustomerBook_success() {
        ReadOnlyDataBook<Customer> book = new DataBook<Customer>();
        modelManager.setCustomerBook(book);
        assertEquals(book, modelManager.getCustomerBook());
    }

    @Test
    public void addCustomer_nullCustomer_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.addCustomer(null));
    }

    @Test
    public void addCustomer_duplicateCustomerInCustomerBook_throwsDuplicateIdentityException() {
        modelManager.addCustomer(BENSON);
        assertTrue(modelManager.hasCustomer(BENSON));
        assertThrows(DuplicateIdentityException.class, () -> modelManager.addCustomer(BENSON));
    }

    @Test
    public void addCustomer_noSuchCustomerInCustomerBook_success() {
        assertFalse(modelManager.hasCustomer(BENSON));
        modelManager.addCustomer(BENSON);
        assertTrue(modelManager.hasCustomer(BENSON));
    }

    @Test
    public void hasCustomer_nullCustomer_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasCustomer(null));
    }

    @Test
    public void hasCustomer_customerNotInCustomerBook_returnsFalse() {
        assertFalse(modelManager.hasCustomer(DANIEL));
    }

    @Test
    public void hasCustomer_customerInCustomerBook_returnsTrue() {
        modelManager.addCustomer(DANIEL);
        assertTrue(modelManager.hasCustomer(DANIEL));
    }

    @Test
    public void setCustomer_nullFields_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setCustomer(null, null));
    }

    @Test
    public void setCustomer_targetCustomerNotInCustomerBook_throwsIdentityNotFoundException() {
        assertThrows(IdentityNotFoundException.class, () -> modelManager.setCustomer(BENSON, CARL));
    }

    @Test
    public void setCustomer_editedCustomerAlreadyInCustomerBook_throwsDuplicateIdentityException() {
        modelManager.addCustomer(BENSON);
        modelManager.addCustomer(CARL);
        assertThrows(DuplicateIdentityException.class, () -> modelManager.setCustomer(BENSON, CARL));
    }

    @Test
    public void setCustomer_targetCustomerInCustomerBook_success() {
        modelManager.addCustomer(BENSON);
        modelManager.setCustomer(BENSON, CARL);
        assertFalse(modelManager.hasCustomer(BENSON));
        assertTrue(modelManager.hasCustomer(CARL));
    }

    @Test
    public void setCustomer_orderWithCustomerExistsInOrderBook_customerInOrderReplaced() {
        modelManager.addCustomer(BENSON);
        Order order = new OrderBuilder(VIPORDER).withCustomer(BENSON).build();
        modelManager.addOrder(order);
        modelManager.setCustomer(BENSON, CARL);
        Order editedOrder = new OrderBuilder(order).withCustomer(CARL).build();
        assertTrue(modelManager.hasOrder(editedOrder));
    }

    @Test
    public void deleteCustomer_nullCustomer_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.deleteCustomer(null));
    }

    @Test
    public void deleteCustomer_customerNotInCustomerBook_throwsIdentityNotFoundException() {
        assertThrows(IdentityNotFoundException.class, () -> modelManager.deleteCustomer(BENSON));
    }

    @Test
    public void deleteCustomer_customerInCustomerBook_success() {
        modelManager.addCustomer(BENSON);
        modelManager.deleteCustomer(BENSON);
        assertFalse(modelManager.hasCustomer(BENSON));
    }

    @Test
    public void deleteCustomer_orderWithCustomerExistsInOrderBook_orderDeleted() {
        modelManager.addCustomer(BENSON);
        Order order = new OrderBuilder(VIPORDER).withCustomer(BENSON).build();
        modelManager.addOrder(order);
        modelManager.deleteCustomer(BENSON);
        assertFalse(modelManager.hasOrder(order));
    }

    @Test
    public void getFilteredCustomerList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredCustomerList().remove(0));
    }

    //=========== phoneBook ================================================================================

    @Test
    public void setPhoneBook_nullPhoneBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setPhoneBook(null));
    }

    @Test
    public void setPhoneBook_validPhoneBook_success() {
        ReadOnlyDataBook<Phone> book = new DataBook<Phone>();
        modelManager.setPhoneBook(book);
        assertEquals(book, modelManager.getPhoneBook());
    }

    @Test
    public void addPhone_nullPhone_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.addPhone(null));
    }

    @Test
    public void addPhone_duplicatePhoneInPhoneBook_throwsDuplicateIdentityException() {
        modelManager.addPhone(IPHONEPRO11);
        assertTrue(modelManager.hasPhone(IPHONEPRO11));
        assertThrows(DuplicateIdentityException.class, () -> modelManager.addPhone(IPHONEPRO11));
    }

    @Test
    public void addPhone_noSuchPhoneInPhoneBook_success() {
        assertFalse(modelManager.hasPhone(IPHONEPRO11));
        modelManager.addPhone(IPHONEPRO11);
        assertTrue(modelManager.hasPhone(IPHONEPRO11));
    }

    @Test
    public void hasPhone_nullPhone_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasPhone(null));
    }

    @Test
    public void hasPhone_phoneNotInPhoneBook_returnsFalse() {
        assertFalse(modelManager.hasPhone(IPHONEXR));
    }

    @Test
    public void hasPhone_phoneInPhoneBook_returnsTrue() {
        modelManager.addPhone(IPHONEXR);
        assertTrue(modelManager.hasPhone(IPHONEXR));
    }

    @Test
    public void setPhone_nullFields_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setPhone(null, null));
    }

    @Test
    public void setPhone_targetPhoneNotInPhoneBook_throwsIdentityNotFoundException() {
        assertThrows(IdentityNotFoundException.class, () -> modelManager.setPhone(IPHONEPRO11, ANDROIDONE));
    }

    @Test
    public void setPhone_editedPhoneAlreadyInPhoneBook_throwsDuplicateIdentityException() {
        modelManager.addPhone(IPHONEPRO11);
        modelManager.addPhone(ANDROIDONE);
        assertThrows(DuplicateIdentityException.class, () -> modelManager.setPhone(IPHONEPRO11, ANDROIDONE));
    }

    @Test
    public void setPhone_targetPhoneInPhoneBook_success() {
        modelManager.addPhone(IPHONEPRO11);
        modelManager.setPhone(IPHONEPRO11, ANDROIDONE);
        assertFalse(modelManager.hasPhone(IPHONEPRO11));
        assertTrue(modelManager.hasPhone(ANDROIDONE));
    }

    @Test
    public void setPhone_orderWithPhoneExistsInPhoneBook_phoneInOrderReplaced() {
        modelManager.addPhone(IPHONEPRO11);
        Order order = new OrderBuilder(VIPORDER).withPhone(IPHONEPRO11).build();
        modelManager.addOrder(order);
        modelManager.setPhone(IPHONEPRO11, ANDROIDONE);
        Order editedOrder = new OrderBuilder(order).withPhone(ANDROIDONE).build();
        assertTrue(modelManager.hasOrder(editedOrder));
    }

    @Test
    public void deletePhone_nullPhone_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.deletePhone(null));
    }

    @Test
    public void deletePhone_phoneNotInPhoneBook_throwsIdentityNotFoundException() {
        assertThrows(IdentityNotFoundException.class, () -> modelManager.deletePhone(IPHONEPRO11));
    }

    @Test
    public void deletePhone_phoneInPhoneBook_success() {
        modelManager.addPhone(IPHONEPRO11);
        modelManager.deletePhone(IPHONEPRO11);
        assertFalse(modelManager.hasPhone(IPHONEPRO11));
    }

    @Test
    public void deletePhone_orderWithPhoneExistsInOrderBook_orderDeleted() {
        modelManager.addPhone(IPHONEPRO11);
        Order order = new OrderBuilder(VIPORDER).withPhone(IPHONEPRO11).build();
        modelManager.addOrder(order);
        modelManager.deletePhone(IPHONEPRO11);
        assertFalse(modelManager.hasOrder(order));
    }

    @Test
    public void getFilteredPhoneList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPhoneList().remove(0));
    }

    //=========== orderBook ================================================================================

    @Test
    public void setOrderBook_nullOrderBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setOrderBook(null));
    }

    @Test
    public void setOrderBook_validOrderBook_success() {
        ReadOnlyDataBook<Order> book = new DataBook<Order>();
        modelManager.setOrderBook(book);
        assertEquals(book, modelManager.getOrderBook());
    }

    @Test
    public void addOrder_nullOrder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.addOrder(null));
    }

    @Test
    public void addOrder_duplicateOrderInOrderBook_throwsDuplicateIdentityException() {
        modelManager.addOrder(VIPORDER);
        assertTrue(modelManager.hasOrder(VIPORDER));
        assertThrows(DuplicateIdentityException.class, () -> modelManager.addOrder(VIPORDER));
    }

    @Test
    public void addOrder_noSuchOrderInOrderBook_success() {
        assertFalse(modelManager.hasOrder(VIPORDER));
        modelManager.addOrder(VIPORDER);
        assertTrue(modelManager.hasOrder(VIPORDER));
    }

    @Test
    public void hasOrder_nullOrder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasOrder(null));
    }

    @Test
    public void hasOrder_orderNotInOrderBook_returnsFalse() {
        assertFalse(modelManager.hasOrder(VIPORDER));
    }

    @Test
    public void hasOrder_orderInOrderBook_returnsTrue() {
        modelManager.addOrder(VIPORDER);
        assertTrue(modelManager.hasOrder(VIPORDER));
    }

    @Test
    public void setOrder_nullFields_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setOrder(null, null));
    }

    @Test
    public void setOrder_targetOrderNotInOrderBook_throwsIdentityNotFoundException() {
        assertThrows(IdentityNotFoundException.class, () -> modelManager.setOrder(ORDERONE, VIPORDER));
    }

    @Test
    public void setOrder_editedOrderAlreadyInOrderBook_throwsDuplicateIdentityException() {
        modelManager.addOrder(ORDERONE);
        modelManager.addOrder(VIPORDER);
        assertThrows(DuplicateIdentityException.class, () -> modelManager.setOrder(ORDERONE, VIPORDER));
    }

    @Test
    public void setOrder_targetOrderInOrderBook_success() {
        modelManager.addOrder(ORDERONE);
        modelManager.setOrder(ORDERONE, VIPORDER);
        assertFalse(modelManager.hasOrder(ORDERONE));
        assertTrue(modelManager.hasOrder(VIPORDER));
    }

    @Test
    public void setOrder_targetOrderInOrderBookHasSchedule_scheduleDeleted() {
        Order order = new OrderBuilder(ORDERONE).withSchedule(Optional.of(MONDAY_SCHEDULE)).build();
        modelManager.addOrder(order);
        modelManager.setOrder(order, VIPORDER);
        assertFalse(modelManager.hasSchedule(MONDAY_SCHEDULE));
    }

    @Test
    public void deleteOrder_nullOrder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.deleteOrder(null));
    }

    @Test
    public void deleteOrder_orderNotInOrderBook_throwsIdentityNotFoundException() {
        assertThrows(IdentityNotFoundException.class, () -> modelManager.deleteOrder(VIPORDER));
    }

    @Test
    public void deleteOrder_orderInOrderBook_success() {
        modelManager.addOrder(VIPORDER);
        modelManager.deleteOrder(VIPORDER);
        assertFalse(modelManager.hasOrder(VIPORDER));
    }

    @Test
    public void deleteOrder_orderInOrderBookHasSchedule_scheduleDeleted() {
        Order order = new OrderBuilder(ORDERONE).withSchedule(Optional.of(MONDAY_SCHEDULE)).build();
        modelManager.addOrder(order);
        modelManager.deleteOrder(order);
        assertFalse(modelManager.hasSchedule(MONDAY_SCHEDULE));
    }

    @Test
    public void getFilteredOrderList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredOrderList().remove(0));
    }

    //=========== scheduleBook ================================================================================

    @Test
    public void setScheduleBook_nullScheduleBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setScheduleBook(null));
    }

    @Test
    public void setScheduleBook_validScheduleBook_success() {
        ReadOnlyDataBook<Schedule> book = new DataBook<Schedule>();
        modelManager.setScheduleBook(book);
        assertEquals(book, modelManager.getScheduleBook());
    }

    @Test
    public void addSchedule_nullSchedule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.addSchedule(null));
    }

    @Test
    public void addSchedule_duplicateScheduleInScheduleBook_throwsDuplicateIdentityException() {
        modelManager.addSchedule(MONDAY_SCHEDULE);
        assertTrue(modelManager.hasSchedule(MONDAY_SCHEDULE));
        assertThrows(DuplicateIdentityException.class, () -> modelManager.addSchedule(MONDAY_SCHEDULE));
    }

    @Test
    public void addSchedule_noSuchScheduleInScheduleBook_success() {
        assertFalse(modelManager.hasSchedule(MONDAY_SCHEDULE));
        modelManager.addSchedule(MONDAY_SCHEDULE);
        assertTrue(modelManager.hasSchedule(MONDAY_SCHEDULE));
    }

    @Test
    public void hasSchedule_nullSchedule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasSchedule(null));
    }

    @Test
    public void hasSchedule_scheduleNotInScheduleBook_returnsFalse() {
        assertFalse(modelManager.hasSchedule(CBD_SCHEDULE));
    }

    @Test
    public void hasSchedule_scheduleInScheduleBook_returnsTrue() {
        modelManager.addSchedule(CBD_SCHEDULE);
        assertTrue(modelManager.hasSchedule(CBD_SCHEDULE));
    }

    @Test
    public void deleteSchedule_nullSchedule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.deleteSchedule(null));
    }

    @Test
    public void deleteSchedule_scheduleNotInScheduleBook_throwsIdentityNotFoundException() {
        assertThrows(IdentityNotFoundException.class, () -> modelManager.deleteSchedule(MONDAY_SCHEDULE));
    }

    @Test
    public void deleteSchedule_scheduleInScheduleBook_success() {
        modelManager.addSchedule(MONDAY_SCHEDULE);
        modelManager.deleteSchedule(MONDAY_SCHEDULE);
        assertFalse(modelManager.hasSchedule(MONDAY_SCHEDULE));
    }

    @Test
    public void deleteSchedule_scheduleInScheduleBook_orderReplaced() {
        Order order = new OrderBuilder(VIPORDER).withSchedule(Optional.of(MONDAY_SCHEDULE)).build();
        modelManager.addOrder(order);
        modelManager.addSchedule(MONDAY_SCHEDULE);
        modelManager.deleteSchedule(MONDAY_SCHEDULE);
        Order orderWithoutSchedule = new OrderBuilder(VIPORDER).withSchedule(Optional.empty()).build();
        assertTrue(modelManager.hasOrder(orderWithoutSchedule));
    }

    @Test
    public void addSchedule_scheduleNoConflictsInScheduleBook_returnsEmptyList() {
        // set up schedule in model manager
        modelManager.addSchedule(CBD_SCHEDULE);
        assertTrue(modelManager.hasSchedule(CBD_SCHEDULE));

        Calendar newCalendar = (Calendar) CBD_SCHEDULE.getCalendar().clone();
        newCalendar.add(Calendar.HOUR_OF_DAY, 2);
        Schedule newSchedule = new ScheduleBuilder(CBD_SCHEDULE).withCalendar(newCalendar).build();
        List<Schedule> conflicts = modelManager.getConflictingSchedules(newSchedule);
        assertEquals(0, conflicts.size());
    }

    @Test
    public void setSchedule_nullFields_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setSchedule(null, null));
    }

    @Test
    public void setSchedule_targetScheduleNotInScheduleBook_throwsIdentityNotFoundException() {
        assertThrows(IdentityNotFoundException.class, () -> modelManager.setSchedule(MONDAY_SCHEDULE, FRIDAY_SCHEDULE));
    }

    @Test
    public void setSchedule_editedScheduleAlreadyInScheduleBook_throwsDuplicateIdentityException() {
        modelManager.addSchedule(MONDAY_SCHEDULE);
        modelManager.addSchedule(FRIDAY_SCHEDULE);
        assertThrows(DuplicateIdentityException.class, ()
            -> modelManager.setSchedule(MONDAY_SCHEDULE, FRIDAY_SCHEDULE));
    }

    @Test
    public void setSchedule_targetScheduleInScheduleBook_success() {
        modelManager.addSchedule(MONDAY_SCHEDULE);
        modelManager.setSchedule(MONDAY_SCHEDULE, FRIDAY_SCHEDULE);
        assertFalse(modelManager.hasSchedule(MONDAY_SCHEDULE));
        assertTrue(modelManager.hasSchedule(FRIDAY_SCHEDULE));
    }

    @Test
    public void setSchedule_orderWithScheduleInOrderBook_orderReplaced() {
        Order order = new OrderBuilder(VIPORDER).withSchedule(Optional.of(MONDAY_SCHEDULE)).build();
        modelManager.addOrder(order);
        modelManager.addSchedule(MONDAY_SCHEDULE);
        modelManager.setSchedule(MONDAY_SCHEDULE, FRIDAY_SCHEDULE);
        Order newOrder = new OrderBuilder(VIPORDER).withSchedule(Optional.of(FRIDAY_SCHEDULE)).build();
        assertTrue(modelManager.hasOrder(newOrder));
    }

    @Test
    public void getFilteredScheduleList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredScheduleList().remove(0));
    }

    //=========== calendarDate ================================================================================

    @Test
    public void setCalendarDate_nullCalendarDate_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setCalendarDate(null));
    }

    @Test
    public void setCalendarDate_validCalendarDate_replacesData() {
        Calendar newCalendar = Calendar.getInstance();
        modelManager.setCalendarDate(newCalendar);
        assertEquals(newCalendar, modelManager.getCalendarDate().getCalendar());
    }

    //=========== archivedOrderBook ================================================================================

    @Test
    public void setArchivedOrderBook_nullScheduleBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setArchivedOrderBook(null));
    }

    @Test
    public void setArchivedOrderBook_validScheduleBook_success() {
        ReadOnlyDataBook<Order> book = new DataBook<Order>();
        modelManager.setArchivedOrderBook(book);
        assertEquals(book, modelManager.getArchivedOrderBook());
    }

    @Test
    public void addArchivedOrder_nullArchivedOrder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.addArchivedOrder(null));
    }

    @Test
    public void addArchivedOrder_duplicateArchivedOrderInArchivedOrderBook_throwsDuplicateIdentityException() {
        modelManager.addArchivedOrder(VIPORDER);
        assertTrue(modelManager.hasArchivedOrder(VIPORDER));
        assertThrows(DuplicateIdentityException.class, () -> modelManager.addArchivedOrder(VIPORDER));
    }

    @Test
    public void addArchivedOrder_noSuchArchivedOrderInArchivedOrderBook_success() {
        assertFalse(modelManager.hasArchivedOrder(VIPORDER));
        modelManager.addArchivedOrder(VIPORDER);
        assertTrue(modelManager.hasArchivedOrder(VIPORDER));
    }

    @Test
    public void hasArchivedOrder_nullOrder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasArchivedOrder(null));
    }

    @Test
    public void hasArchivedOrder_archivedOrderNotInArchivedOrderBook_returnsFalse() {
        assertFalse(modelManager.hasArchivedOrder(VIPORDER));
    }

    @Test
    public void hasArchivedOrder_archivedOrderInArchivedOrderBook_returnsTrue() {
        modelManager.addArchivedOrder(VIPORDER);
        assertTrue(modelManager.hasArchivedOrder(VIPORDER));
    }

    @Test
    public void setArchivedOrder_nullFields_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setArchivedOrder(null, null));
    }

    @Test
    public void setArchivedOrder_targetArchivedOrderNotInArchivedOrderBook_throwsIdentityNotFoundException() {
        assertThrows(IdentityNotFoundException.class, () -> modelManager.setArchivedOrder(ORDERONE, VIPORDER));
    }

    @Test
    public void setArchivedOrder_editedArchivedOrderAlreadyInArchivedOrderBook_throwsDuplicateIdentityException() {
        modelManager.addArchivedOrder(ORDERONE);
        modelManager.addArchivedOrder(VIPORDER);
        assertThrows(DuplicateIdentityException.class, () -> modelManager.setArchivedOrder(ORDERONE, VIPORDER));
    }

    @Test
    public void setArchivedOrder_targetArchivedOrderInArchivedOrderBook_success() {
        modelManager.addArchivedOrder(ORDERONE);
        modelManager.setArchivedOrder(ORDERONE, VIPORDER);
        assertFalse(modelManager.hasArchivedOrder(ORDERONE));
        assertTrue(modelManager.hasArchivedOrder(VIPORDER));
    }

    @Test
    public void deleteArchivedOrder_nullArchivedOrder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.deleteArchivedOrder(null));
    }

    @Test
    public void deleteArchivedOrder_archivedOrderNotInArchivedOrderBook_throwsIdentityNotFoundException() {
        assertThrows(IdentityNotFoundException.class, () -> modelManager.deleteArchivedOrder(VIPORDER));
    }

    @Test
    public void deleteArchivedOrder_archivedOrderInArchivedOrderBook_success() {
        modelManager.addArchivedOrder(VIPORDER);
        modelManager.deleteArchivedOrder(VIPORDER);
        assertFalse(modelManager.hasArchivedOrder(VIPORDER));
    }

    @Test
    public void getFilteredArchivedOrderList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredArchivedOrderList().remove(0));
    }

    //==============================================================================================================

    @Test
    public void equals() {

        DataBook<Customer> customerBook = new CustomerBookBuilder().withCustomer(DANIEL).withCustomer(FIONA).build();
        DataBook<Phone> phoneBook = new PhoneBookBuilder().withPhone(IPHONEONE).withPhone(IPHONEXR).build();
        DataBook<Order> orderBook = new OrderBookBuilder().withOrder(ORDERONE).build();
        DataBook<Schedule> scheduleBook = new ScheduleBookBuilder().withSchedule(CBD_SCHEDULE)
                .withSchedule(MONDAY_SCHEDULE).build();
        DataBook<Order> archivedOrderBook = new OrderBookBuilder().withOrder(ORDERTHREE).build();

        DataBook<Customer> differentCustomerBook = new DataBook<>();
        DataBook<Phone> differentPhoneBook = new DataBook<>();
        DataBook<Order> differentOrderBook = new DataBook<>();
        DataBook<Schedule> differentScheduleBook = new DataBook<>();
        DataBook<Order> differentArchivedOrderBook = new DataBook<>();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(customerBook, phoneBook, orderBook, scheduleBook, archivedOrderBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(customerBook, phoneBook, orderBook,
                scheduleBook, archivedOrderBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different customerBook -> returns false
        assertFalse(modelManager.equals(new
                ModelManager(differentCustomerBook, phoneBook, orderBook, scheduleBook, archivedOrderBook, userPrefs)));

        // different phoneBook -> returns false
        assertFalse(modelManager.equals(new
                ModelManager(customerBook, differentPhoneBook, orderBook, scheduleBook, archivedOrderBook, userPrefs)));

        // different orderBook -> returns false
        assertFalse(modelManager.equals(new
                ModelManager(customerBook, phoneBook, differentOrderBook, scheduleBook, archivedOrderBook, userPrefs)));

        // different archiveOrderBook -> returns false
        assertFalse(modelManager.equals(new
                ModelManager(customerBook, phoneBook, orderBook, scheduleBook, differentArchivedOrderBook, userPrefs)));

        // different scheduleBook -> returns false
        assertFalse(modelManager.equals(new
                ModelManager(customerBook, phoneBook, orderBook, differentScheduleBook, archivedOrderBook, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = DANIEL.getCustomerName().fullName.split("\\s+");
        modelManager.updateFilteredCustomerList(new CustomerContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(customerBook, phoneBook, orderBook, scheduleBook,
                archivedOrderBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredCustomerList(PREDICATE_SHOW_ALL_CUSTOMERS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new
                ModelManager(customerBook, phoneBook, orderBook, scheduleBook,
                archivedOrderBook, differentUserPrefs)));

        // different calendar in calendarDate -> returns true
        Calendar differentCalendar = Calendar.getInstance();
        modelManagerCopy.setCalendarDate(differentCalendar);
        assertTrue(modelManager.equals(modelManagerCopy));

    }
}
