package seedu.address.logic.commands.addcommand;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.DataBook;
import seedu.address.model.ReadOnlyDataBook;
import seedu.address.model.order.Order;
import seedu.address.model.schedule.Schedule;
import seedu.address.testutil.ModelStub;
import seedu.address.testutil.OrderBuilder;
import seedu.address.testutil.ScheduleBuilder;

public class AddScheduleCommandTest {

    private static final Index VALID_INDEX = Index.fromOneBased(1);
    private static final Schedule VALID_SCHEDULE = new ScheduleBuilder().build();
    private static final Order VALID_ORDER = new OrderBuilder().build();

    @Test
    public void constructor_nullSchedule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddScheduleCommand(null, VALID_INDEX));
    }

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddScheduleCommand(VALID_SCHEDULE, null));
    }

    @Test
    public void constructor_nullScheduleAndIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddScheduleCommand(null, null));
    }

    @Test
    public void execute_scheduleAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingScheduleAdded modelStub = new ModelStubAcceptingScheduleAdded();

        CommandResult commandResult = new AddScheduleCommand(VALID_SCHEDULE, VALID_INDEX).execute(modelStub);

        assertEquals(String.format(AddScheduleCommand.MESSAGE_SUCCESS, VALID_SCHEDULE),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(VALID_SCHEDULE), modelStub.schedulesAdded);
    }

    @Test
    public void execute_duplicateSchedule_throwsCommandException() {
        AddScheduleCommand addScheduleCommand = new AddScheduleCommand(VALID_SCHEDULE, VALID_INDEX);
        ModelStub modelStub = new ModelStubWithSchedule(VALID_SCHEDULE, VALID_ORDER);

        assertThrows(CommandException.class, AddScheduleCommand.MESSAGE_DUPLICATE_SCHEDULE, ()
            -> addScheduleCommand.execute(modelStub));
    }

    @Test
    public void equals() {

        Calendar calendarOne = new Calendar.Builder().setDate(2019, 12, 12)
                .setTimeOfDay(12, 12, 0).build();
        Calendar calendarTwo = new Calendar.Builder().setDate(2019, 10, 10)
                .setTimeOfDay(12, 12, 0).build();

        Schedule scheduleOne = new ScheduleBuilder().withCalendar(calendarOne).build();
        Schedule scheduleTwo = new ScheduleBuilder().withCalendar(calendarTwo).build();

        AddScheduleCommand addScheduleOneCommand = new AddScheduleCommand(scheduleOne, VALID_INDEX);
        AddScheduleCommand addScheduleTwoCommand = new AddScheduleCommand(scheduleTwo, VALID_INDEX);

        // same object -> returns true
        assertTrue(addScheduleOneCommand.equals(addScheduleOneCommand));

        // same values -> returns true
        AddScheduleCommand addScheduleOneCommandCopy = new AddScheduleCommand(scheduleOne, VALID_INDEX);
        assertTrue(addScheduleOneCommand.equals(addScheduleOneCommandCopy));

        // different types -> returns false
        assertFalse(addScheduleOneCommand.equals(1));

        // null -> returns false
        assertFalse(addScheduleOneCommand.equals(null));

        // different calendar -> returns false
        assertFalse(addScheduleOneCommand.equals(addScheduleTwoCommand));

        // different venue -> returns false
        scheduleOne = new ScheduleBuilder().withVenue("MRT").build();
        scheduleTwo = new ScheduleBuilder().withVenue("NUS").build();
        assertFalse(addScheduleOneCommand.equals(addScheduleTwoCommand));


    }

    /**
     * A Model stub that contains a single schedule and order.
     */
    private class ModelStubWithSchedule extends ModelStub {
        private final Schedule schedule;
        private final Order order;
        private final ObservableList<Order> filteredOrderList = FXCollections.observableArrayList();

        ModelStubWithSchedule(Schedule schedule, Order order) {
            requireNonNull(schedule);
            requireNonNull(order);
            this.schedule = schedule;
            this.order = order;
            filteredOrderList.add(VALID_ORDER);
        }

        @Override
        public boolean hasSchedule(Schedule schedule) {
            requireNonNull(schedule);
            return this.schedule.isSameAs(schedule);
        }

        @Override
        public ObservableList<Order> getFilteredOrderList() {
            return filteredOrderList;
        }
    }

    /**
     * A Model stub that always accept the schedule being added.
     */
    private class ModelStubAcceptingScheduleAdded extends ModelStub {
        final ArrayList<Schedule> schedulesAdded = new ArrayList<>();
        final ObservableList<Order> filteredOrderList = FXCollections.observableArrayList();

        public ModelStubAcceptingScheduleAdded() {
            filteredOrderList.add(VALID_ORDER);
        }

        @Override
        public boolean hasSchedule(Schedule schedule) {
            requireNonNull(schedule);
            return schedulesAdded.stream().anyMatch(schedule::isSameAs);
        }

        @Override
        public void addSchedule(Schedule schedule) {
            requireNonNull(schedule);
            schedulesAdded.add(schedule);
        }

        @Override
        public ObservableList<Order> getFilteredOrderList() {
            return filteredOrderList;
        }

        @Override
        public ReadOnlyDataBook<Schedule> getScheduleBook() {
            return new DataBook<Schedule>();
        }

        @Override
        public void setOrder(Order target, Order order) {
            filteredOrderList.remove(target);
            filteredOrderList.add(order);
        }

    }

}

