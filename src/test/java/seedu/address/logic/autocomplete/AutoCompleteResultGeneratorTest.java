package seedu.address.logic.autocomplete;

import static org.junit.jupiter.api.Assertions.assertTrue;

import static seedu.address.testutil.TypicalCustomers.CUSTOMERONE;
import static seedu.address.testutil.TypicalCustomers.CUSTOMERTHREE;
import static seedu.address.testutil.TypicalCustomers.CUSTOMERTWO;
import static seedu.address.testutil.TypicalCustomers.DEFAULT_CONTACTNUMBER_1;
import static seedu.address.testutil.TypicalCustomers.DEFAULT_CONTACTNUMBER_2;
import static seedu.address.testutil.TypicalCustomers.DEFAULT_CONTACTNUMBER_3;
import static seedu.address.testutil.TypicalCustomers.DEFAULT_EMAIL_1;
import static seedu.address.testutil.TypicalCustomers.DEFAULT_EMAIL_2;
import static seedu.address.testutil.TypicalCustomers.DEFAULT_EMAIL_3;
import static seedu.address.testutil.TypicalCustomers.DEFAULT_NAME_1;
import static seedu.address.testutil.TypicalCustomers.DEFAULT_NAME_2;
import static seedu.address.testutil.TypicalCustomers.DEFAULT_NAME_3;
import static seedu.address.testutil.TypicalCustomers.DEFAULT_TAG_1;
import static seedu.address.testutil.TypicalCustomers.DEFAULT_TAG_2;
import static seedu.address.testutil.TypicalCustomers.DEFAULT_TAG_3;
import static seedu.address.testutil.TypicalOrders.ORDERONE;
import static seedu.address.testutil.TypicalOrders.ORDERTHREE;
import static seedu.address.testutil.TypicalOrders.ORDERTWO;
import static seedu.address.testutil.TypicalPhones.ANDROIDONE;
import static seedu.address.testutil.TypicalPhones.IPHONEONE;
import static seedu.address.testutil.TypicalPhones.IPHONETWO;
import static seedu.address.testutil.TypicalSchedules.CBD_SCHEDULE;
import static seedu.address.testutil.TypicalSchedules.FRIDAY_SCHEDULE;
import static seedu.address.testutil.TypicalSchedules.MONDAY_SCHEDULE;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
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

class AutoCompleteResultGeneratorTest {

    private final Model autoCompleteModelStub = new AutoCompleteModelStub();
    private final AutoCompleteResultGenerator generator = new AutoCompleteResultGenerator(autoCompleteModelStub);

    @Test
    void process_noCommandWord_commandWordsWithInput() {
        AutoCompleteResult result = generator.process("");
        assertTrue(() -> result.getValues().equals(generator.getSupportedCommandWords()));
        assertTrue(() -> result.getStringToCompare().equals(""));
    }

    @Test
    void process_commandWordWithNoSpace_commandWordsWithInput() {
        AutoCompleteResult result = generator.process("add-c");
        assertTrue(() -> result.getValues().equals(generator.getSupportedCommandWords()));
        assertTrue(() -> result.getStringToCompare().equals("add-c"));
    }

    @Test
    void process_addCustomerNoPrefix_firstPrefixWithRemaining() {
        AutoCompleteResult result = generator.process("add-c n");
        SortedSet<String> desired = new TreeSet<>(Collections.singletonList("n/"));
        assertTrue(() -> result.getValues().equals(desired));
        assertTrue(() -> result.getStringToCompare().equals("n"));
    }

    @Test
    void process_addCustomerFirstPrefixComplete_customerNamesWithArgument() {
        AutoCompleteResult result = generator.process("add-c n/abc");
        SortedSet<String> desired = new TreeSet<>(Arrays.asList(
                DEFAULT_NAME_1,
                DEFAULT_NAME_2,
                DEFAULT_NAME_3
        ));
        assertTrue(() -> result.getValues().equals(desired));
        assertTrue(() -> result.getStringToCompare().equals("abc"));
    }

    @Test
    void process_addCustomerFirstArgumentComplete_secondPrefixWithRemaining() {
        AutoCompleteResult result = generator.process("add-c n/abc c");
        SortedSet<String> desired = new TreeSet<>(Collections.singletonList("c/"));
        assertTrue(() -> result.getValues().equals(desired));
        assertTrue(() -> result.getStringToCompare().equals("c"));
    }

    @Test
    void process_addCustomerSecondPrefixComplete_customerContactNumbersWithArgument() {
        AutoCompleteResult result = generator.process("add-c n/abc c/def");
        SortedSet<String> desired = new TreeSet<>(Arrays.asList(
                DEFAULT_CONTACTNUMBER_1,
                DEFAULT_CONTACTNUMBER_2,
                DEFAULT_CONTACTNUMBER_3
        ));
        assertTrue(() -> result.getValues().equals(desired));
        assertTrue(() -> result.getStringToCompare().equals("def"));
    }

    @Test
    void process_addCustomerSecondArgumentComplete_thirdPrefixWithRemaining() {
        AutoCompleteResult result = generator.process("add-c n/abc c/def e");
        SortedSet<String> desired = new TreeSet<>(Collections.singletonList("e/"));
        assertTrue(() -> result.getValues().equals(desired));
        assertTrue(() -> result.getStringToCompare().equals("e"));
    }

    @Test
    void process_addCustomerThirdPrefixComplete_customerEmailsWithArgument() {
        AutoCompleteResult result = generator.process("add-c n/abc c/def e/ghi");
        SortedSet<String> desired = new TreeSet<>(Arrays.asList(
                DEFAULT_EMAIL_1,
                DEFAULT_EMAIL_2,
                DEFAULT_EMAIL_3
        ));
        assertTrue(() -> result.getValues().equals(desired));
        assertTrue(() -> result.getStringToCompare().equals("ghi"));
    }

    @Test
    void process_addCustomerThirdArgumentComplete_fourthPrefixWithRemaining() {
        AutoCompleteResult result = generator.process("add-c n/abc c/def e/ghi t");
        SortedSet<String> desired = new TreeSet<>(Collections.singletonList("t/"));
        assertTrue(() -> result.getValues().equals(desired));
        assertTrue(() -> result.getStringToCompare().equals("t"));
    }

    @Test
    void process_addCustomerFourthPrefixComplete_customerTagsWithArgument() {
        AutoCompleteResult result = generator.process("add-c n/abc c/def e/ghi t/jkl");
        SortedSet<String> desired = new TreeSet<>(Arrays.asList(
                DEFAULT_TAG_1,
                DEFAULT_TAG_2,
                DEFAULT_TAG_3
        ));
        assertTrue(() -> result.getValues().equals(desired));
        assertTrue(() -> result.getStringToCompare().equals("jkl"));
    }

    @Test
    void process_addCustomerFourthArgumentComplete_fifthPrefixWithRemaining() {
        AutoCompleteResult result = generator.process("add-c n/abc c/def e/ghi t/jkl t");
        SortedSet<String> desired = new TreeSet<>(Collections.singletonList("t/"));
        assertTrue(() -> result.getValues().equals(desired));
        assertTrue(() -> result.getStringToCompare().equals("t"));
    }

    @Test
    void process_addCustomerFifthPrefixComplete_customerTagsWithArgument() {
        AutoCompleteResult result = generator.process("add-c n/abc c/def e/ghi t/jkl t/mno");
        SortedSet<String> desired = new TreeSet<>(Arrays.asList(
                DEFAULT_TAG_1,
                DEFAULT_TAG_2,
                DEFAULT_TAG_3
        ));
        assertTrue(() -> result.getValues().equals(desired));
        assertTrue(() -> result.getStringToCompare().equals("mno"));
    }

    @Test
    void process_deleteCustomerNoPreamble_preambleWithRemaining() {
        AutoCompleteResult result = generator.process("delete-c 0");
        SortedSet<String> desired = new TreeSet<>(Arrays.asList(
                String.valueOf(1),
                String.valueOf(2)
        ));
        assertTrue(() -> result.getValues().equals(desired));
        assertTrue(() -> result.getStringToCompare().equals("0"));
    }

    @Test
    void process_findCustomer_customerAllFieldsWithRemaining() {
        AutoCompleteResult result = generator.process("find-c abc");
        SortedSet<String> desired = new TreeSet<>(Arrays.asList(
                DEFAULT_NAME_1,
                DEFAULT_NAME_2,
                DEFAULT_NAME_3,
                DEFAULT_CONTACTNUMBER_1,
                DEFAULT_CONTACTNUMBER_2,
                DEFAULT_CONTACTNUMBER_3,
                DEFAULT_EMAIL_1,
                DEFAULT_EMAIL_2,
                DEFAULT_EMAIL_3,
                DEFAULT_TAG_1,
                DEFAULT_TAG_2,
                DEFAULT_TAG_3
        ));
        assertTrue(() -> result.getValues().equals(desired));
        assertTrue(() -> result.getStringToCompare().equals("abc"));
    }

    private static class AutoCompleteModelStub implements Model {

        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {}

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            return null;
        }

        @Override
        public GuiSettings getGuiSettings() {
            return null;
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {}

        @Override
        public void setCustomerBook(ReadOnlyDataBook<Customer> customerBook) {}

        @Override
        public ReadOnlyDataBook<Customer> getCustomerBook() {
            return () -> FXCollections.observableList(Arrays.asList(CUSTOMERONE, CUSTOMERTWO, CUSTOMERTHREE));
        }

        @Override
        public boolean hasCustomer(Customer customer) {
            return false;
        }

        @Override
        public void deleteCustomer(Customer target) {}

        @Override
        public void addCustomer(Customer customer) {}

        @Override
        public void setCustomer(Customer target, Customer editedCustomer) {}

        @Override
        public ObservableList<Customer> getFilteredCustomerList() {
            return FXCollections.observableList(Arrays.asList(CUSTOMERONE, CUSTOMERTWO));
        }

        @Override
        public void updateFilteredCustomerList(Predicate<Customer> predicate) {}

        @Override
        public void setPhoneBook(ReadOnlyDataBook<Phone> phoneBook) {}

        @Override
        public ReadOnlyDataBook<Phone> getPhoneBook() {
            return () -> FXCollections.observableList(Arrays.asList(IPHONEONE, IPHONETWO, ANDROIDONE));
        }

        @Override
        public boolean hasPhone(Phone phone) {
            return false;
        }

        @Override
        public void deletePhone(Phone target) {}

        @Override
        public void addPhone(Phone phone) {}

        @Override
        public void setPhone(Phone target, Phone editedPhone) {}

        @Override
        public ObservableList<Phone> getFilteredPhoneList() {
            return FXCollections.observableList(Arrays.asList(IPHONEONE, IPHONETWO));
        }

        @Override
        public void updateFilteredPhoneList(Predicate<Phone> predicate) {}

        @Override
        public void setOrderBook(ReadOnlyDataBook<Order> orderBook) {}

        @Override
        public ReadOnlyDataBook<Order> getOrderBook() {
            return () -> FXCollections.observableList(Arrays.asList(ORDERONE, ORDERTWO, ORDERTHREE));
        }

        @Override
        public boolean hasOrder(Order order) {
            return false;
        }

        @Override
        public void deleteOrder(Order target) {}

        @Override
        public void addOrder(Order order) {}

        @Override
        public void setOrder(Order target, Order editedOrder) {}

        @Override
        public ObservableList<Order> getFilteredOrderList() {
            return FXCollections.observableList(Arrays.asList(ORDERONE, ORDERTWO));
        }

        @Override
        public void updateFilteredOrderList(Predicate<Order> predicate) {}

        @Override
        public void setScheduleBook(ReadOnlyDataBook<Schedule> scheduleBook) {}

        @Override
        public ReadOnlyDataBook<Schedule> getScheduleBook() {
            return () -> FXCollections.observableList(Arrays.asList(MONDAY_SCHEDULE, FRIDAY_SCHEDULE, CBD_SCHEDULE));
        }

        @Override
        public boolean hasSchedule(Schedule schedule) {
            return false;
        }

        @Override
        public void deleteSchedule(Schedule target) {}

        @Override
        public void addSchedule(Schedule schedule) {}

        @Override
        public void setSchedule(Schedule target, Schedule editedSchedule) {}

        @Override
        public ObservableList<Schedule> getFilteredScheduleList() {
            return FXCollections.observableList(Arrays.asList(MONDAY_SCHEDULE, FRIDAY_SCHEDULE));
        }

        @Override
        public void updateFilteredScheduleList(Predicate<Schedule> predicate) {}

        @Override
        public List<Schedule> getConflictingSchedules(Schedule schedule) {
            return null;
        }

        @Override
        public CalendarDate getCalendarDate() {
            return null;
        }

        @Override
        public void setCalendarDate(Calendar calendar) {}

        @Override
        public void setArchivedOrderBook(ReadOnlyDataBook<Order> archivedOrderBook) {}

        @Override
        public ReadOnlyDataBook<Order> getArchivedOrderBook() {
            return null;
        }

        @Override
        public boolean hasArchivedOrder(Order order) {
            return false;
        }

        @Override
        public void deleteArchivedOrder(Order target) {}

        @Override
        public void addArchivedOrder(Order order) {}

        @Override
        public void setArchivedOrder(Order target, Order editedOrder) {}

        @Override
        public ObservableList<Order> getFilteredArchivedOrderList() {
            return null;
        }

        @Override
        public void updateFilteredArchivedOrderList(Predicate<Order> predicate) {}

        @Override
        public void resolveOrderBooksConflict() {}
    }

}
