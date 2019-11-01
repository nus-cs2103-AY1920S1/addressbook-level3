package seedu.address.logic.commands.addcommand;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
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
    private static final boolean VALID_ALLOW = true;
    private static final Schedule VALID_SCHEDULE = new ScheduleBuilder().build();
    private static final Order VALID_ORDER = new OrderBuilder().build();

    @Test
    public void constructor_nullSchedule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddScheduleCommand(null, VALID_INDEX, VALID_ALLOW));
    }

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddScheduleCommand(VALID_SCHEDULE, null, VALID_ALLOW));
    }

    @Test
    public void constructor_nullScheduleAndIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddScheduleCommand(null, null, VALID_ALLOW));
    }

    @Test
    public void execute_scheduleAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingScheduleAdded modelStub = new ModelStubAcceptingScheduleAdded();

        CommandResult commandResult = new AddScheduleCommand(VALID_SCHEDULE, VALID_INDEX, VALID_ALLOW)
                .executeUndoableCommand(modelStub, new CommandHistory(), new UndoRedoStack());

        assertEquals(String.format(AddScheduleCommand.MESSAGE_SUCCESS, VALID_SCHEDULE),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(VALID_SCHEDULE), modelStub.schedulesAdded);
    }

    @Test
    public void execute_duplicateSchedule_throwsCommandException() {
        AddScheduleCommand addScheduleCommand = new AddScheduleCommand(VALID_SCHEDULE, VALID_INDEX, VALID_ALLOW);
        ModelStub modelStub = new ModelStubWithSchedule(VALID_SCHEDULE, VALID_ORDER);

        assertThrows(CommandException.class, AddScheduleCommand.MESSAGE_DUPLICATE_SCHEDULE, ()
            -> addScheduleCommand.executeUndoableCommand(modelStub, new CommandHistory(), new UndoRedoStack()));
    }

    @Test
    public void execute_clashingScheduleWithNoAllow_throwsCommandException() throws CommandException {
        ModelStubAcceptingScheduleAdded modelStub = new ModelStubAcceptingScheduleAdded();
        AddScheduleCommand addScheduleCommand = new AddScheduleCommand(VALID_SCHEDULE, VALID_INDEX, VALID_ALLOW);
        addScheduleCommand.executeUndoableCommand(modelStub, new CommandHistory(), new UndoRedoStack());

        Calendar newCalendar = (Calendar) VALID_SCHEDULE.getCalendar().clone();
        newCalendar.add(Calendar.MINUTE, 30);
        Schedule clash = new ScheduleBuilder().withCalendar(newCalendar).build();
        Index newIndex = Index.fromOneBased(2);
        AddScheduleCommand newAddScheduleCommand = new AddScheduleCommand(clash, newIndex, false);
        String conflict = "\nHere are the list of conflicting schedules:\n" + VALID_SCHEDULE.getCalendarString()
                + ": Order 1\n";

        assertThrows(CommandException.class, Messages.MESSAGE_SCHEDULE_CONFLICT + conflict, ()
            -> newAddScheduleCommand.executeUndoableCommand(modelStub, new CommandHistory(), new UndoRedoStack()));
    }

    @Test
    public void execute_clashingScheduleWithAllow_addSuccessful() throws Exception {
        ModelStubAcceptingScheduleAdded modelStub = new ModelStubAcceptingScheduleAdded();
        AddScheduleCommand addScheduleCommand = new AddScheduleCommand(VALID_SCHEDULE, VALID_INDEX, VALID_ALLOW);
        addScheduleCommand.executeUndoableCommand(modelStub, new CommandHistory(), new UndoRedoStack());

        Calendar newCalendar = (Calendar) VALID_SCHEDULE.getCalendar().clone();
        newCalendar.add(Calendar.MINUTE, 10);
        Schedule clash = new ScheduleBuilder().withCalendar(newCalendar).build();
        Index newIndex = Index.fromOneBased(2);
        AddScheduleCommand newAddScheduleCommand = new AddScheduleCommand(clash, newIndex, VALID_ALLOW);

        assertEquals(String.format(AddScheduleCommand.MESSAGE_SUCCESS, clash),
                newAddScheduleCommand.executeUndoableCommand(modelStub, new CommandHistory(),
                        new UndoRedoStack()).getFeedbackToUser());

    }

    @Test
    public void equals() {

        Calendar calendarOne = new Calendar.Builder().setDate(2019, 12, 12)
                .setTimeOfDay(12, 12, 0).build();
        Calendar calendarTwo = new Calendar.Builder().setDate(2019, 10, 10)
                .setTimeOfDay(12, 12, 0).build();

        Schedule scheduleOne = new ScheduleBuilder().withCalendar(calendarOne).build();
        Schedule scheduleTwo = new ScheduleBuilder().withCalendar(calendarTwo).build();

        AddScheduleCommand addScheduleOneCommand = new AddScheduleCommand(scheduleOne, VALID_INDEX, VALID_ALLOW);
        AddScheduleCommand addScheduleTwoCommand = new AddScheduleCommand(scheduleTwo, VALID_INDEX, VALID_ALLOW);

        // same object -> returns true
        assertTrue(addScheduleOneCommand.equals(addScheduleOneCommand));

        // same values -> returns true
        AddScheduleCommand addScheduleOneCommandCopy = new AddScheduleCommand(scheduleOne, VALID_INDEX, VALID_ALLOW);
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
     * A Model stub that always accept the schedule being added unless there are clashes.
     */
    private class ModelStubAcceptingScheduleAdded extends ModelStub {
        final ArrayList<Schedule> schedulesAdded = new ArrayList<>();
        final ObservableList<Order> filteredOrderList = FXCollections.observableArrayList();

        public ModelStubAcceptingScheduleAdded() {
            filteredOrderList.add(new OrderBuilder().build());
            filteredOrderList.add(new OrderBuilder().build());
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
            requireNonNull(target);
            requireNonNull(order);
            filteredOrderList.set(filteredOrderList.indexOf(target), order);
        }

        @Override
        public List<Schedule> getConflictingSchedules(Schedule schedule) {
            requireNonNull(schedule);

            Calendar startTime = schedule.getCalendar();
            Calendar earliestUnconflictedStartTime = (Calendar) startTime.clone();
            earliestUnconflictedStartTime.add(Calendar.HOUR_OF_DAY, -1);
            Calendar latestUnconflictedStartTime = (Calendar) startTime.clone();
            latestUnconflictedStartTime.add(Calendar.HOUR_OF_DAY, 1);

            List<Schedule> conflicts = schedulesAdded.stream()
                    .filter(x -> x.getCalendar().after(earliestUnconflictedStartTime))
                    .filter(x -> x.getCalendar().before(latestUnconflictedStartTime))
                    .sorted(Comparator.comparing(Schedule::getCalendar))
                    .collect(Collectors.toList());
            return conflicts;
        }
    }

}

