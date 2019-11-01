package seedu.address.logic.commands.editcommand;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALLOW;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_SCHEDULE;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.UiChange;
import seedu.address.logic.commands.UndoableCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.order.Order;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.schedule.Venue;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing schedule in SML.
 */
public class EditScheduleCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "edit-s";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the schedule identified "
            + "by the schedule's order index number in the displayed order list. "
            + "Existing values will be overwritten by the input values. Add \"" + PREFIX_ALLOW + "\" to confirm any "
            + "clashing schedules.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_DATE + "DATE] "
            + "[" + PREFIX_TIME + "TIME] "
            + "[" + PREFIX_VENUE + "VENUE] "
            + "[" + PREFIX_TAG + "TAG]... "
            + "[" + PREFIX_ALLOW + "]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DATE + "2019.12.12 "
            + PREFIX_TIME + "12.12 "
            + PREFIX_VENUE + "NUS "
            + PREFIX_ALLOW;

    public static final String MESSAGE_EDIT_SCHEDULE_SUCCESS = "Edited Schedule: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_SCHEDULE = "This schedule already exists in the SML.";

    private final Index index;
    private final EditScheduleDescriptor editScheduleDescriptor;
    private final boolean canClash;

    /**
     * Creates an EditScheduleCommand to edit the specified schedule using {@code EditScheduleDescriptor}
     */
    public EditScheduleCommand(Index index, EditScheduleDescriptor editScheduleDescriptor, boolean canClash) {
        requireNonNull(index);
        requireNonNull(editScheduleDescriptor);

        this.index = index;
        this.editScheduleDescriptor = new EditScheduleDescriptor(editScheduleDescriptor);
        this.canClash = canClash;
    }

    @Override
    public CommandResult executeUndoableCommand(Model model, CommandHistory commandHistory,
                                 UndoRedoStack undoRedoStack) throws CommandException {
        requireNonNull(model);

        List<Order> orderList = model.getFilteredOrderList();

        // check if the order index is valid
        if (index.getZeroBased() >= orderList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
        }

        // Check if the order status is valid - only SCHEDULED is valid
        Order orderToReschedule = orderList.get(index.getZeroBased());
        switch (orderToReschedule.getStatus()) {
        case COMPLETED:
            throw new CommandException(Messages.MESSAGE_ORDER_COMPLETED);
        case CANCELLED:
            throw new CommandException(Messages.MESSAGE_ORDER_CANCELLED);
        case UNSCHEDULED:
            throw new CommandException(Messages.MESSAGE_ORDER_UNSCHEDULED);
        default:
            // do nothing
        }

        Schedule scheduleToEdit = orderToReschedule.getSchedule().get();
        Schedule editedSchedule = createEditedSchedule(scheduleToEdit, editScheduleDescriptor);

        if (!scheduleToEdit.isSameAs(editedSchedule) && model.hasSchedule(editedSchedule)) {
            throw new CommandException(MESSAGE_DUPLICATE_SCHEDULE);
        }

        // Get the list of conflicts from model
        List<Schedule> conflicts = model.getConflictingSchedules(editedSchedule);
        StringBuilder sb = new StringBuilder();

        // if there are conflicts present and user did not put -allow flag, throw exception
        if (!conflicts.isEmpty() && !canClash) {
            sb.append("\nHere are the list of conflicting schedules:\n");
            conflicts.stream()
                    .flatMap(x -> orderList.stream()
                            .filter(y -> !x.isSameAs(scheduleToEdit))
                            .filter(y -> y.getSchedule().isPresent())
                            .filter(y -> y.getSchedule().get().isSameAs(x)))
                    .sorted(Comparator.comparing(a -> a.getSchedule().get().getCalendar()))
                    .forEach(y -> sb.append(String.format("%s: Order %d\n", y.getSchedule().get().getCalendarString(),
                            orderList.indexOf(y) + 1)));

            throw new CommandException(Messages.MESSAGE_SCHEDULE_CONFLICT + sb.toString());
        }

        model.setSchedule(scheduleToEdit, editedSchedule);
        model.updateFilteredScheduleList(PREDICATE_SHOW_ALL_SCHEDULE);
        return new CommandResult(String.format(MESSAGE_EDIT_SCHEDULE_SUCCESS + sb.toString(), editedSchedule),
                UiChange.SCHEDULE);
    }

    /**
     * Creates and returns a {@code Schedule} with the details of {@code scheduleToEdit}
     * edited with {@code editScheduleDescriptor}.
     */
    private static Schedule createEditedSchedule(Schedule scheduleToEdit,
                                                 EditScheduleDescriptor editScheduleDescriptor) {
        assert scheduleToEdit != null;

        UUID id = scheduleToEdit.getId();
        Calendar updatedDate = editScheduleDescriptor.getDate().orElse(scheduleToEdit.getCalendar());
        Calendar updatedTime = editScheduleDescriptor.getTime().orElse(scheduleToEdit.getCalendar());
        Calendar updatedCalendar = getUpdatedCalendar(updatedDate, updatedTime);
        Venue updatedVenue = editScheduleDescriptor.getVenue().orElse(scheduleToEdit.getVenue());
        Set<Tag> updatedTags = editScheduleDescriptor.getTags().orElse(scheduleToEdit.getTags());

        return new Schedule(id, updatedCalendar, updatedVenue, updatedTags);
    }

    private static Calendar getUpdatedCalendar(Calendar updatedDate, Calendar updatedTime) {
        int year = updatedDate.get(Calendar.YEAR);
        int month = updatedDate.get(Calendar.MONTH);
        int date = updatedDate.get(Calendar.DAY_OF_MONTH);
        int hour = updatedTime.get(Calendar.HOUR_OF_DAY);
        int minute = updatedTime.get(Calendar.MINUTE);

        return new Calendar.Builder().setDate(year, month, date)
                .setTimeOfDay(hour, minute, 0).build();
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditScheduleCommand)) {
            return false;
        }

        // state check
        EditScheduleCommand e = (EditScheduleCommand) other;
        return index.equals(e.index)
                && editScheduleDescriptor.equals(e.editScheduleDescriptor)
                && canClash == e.canClash;
    }

    /**
     * Stores the details to edit the schedule with. Each non-empty field value will replace the
     * corresponding field value of the schedule.
     */
    public static class EditScheduleDescriptor {
        private Calendar date;
        private Calendar time;
        private Venue venue;
        private Set<Tag> tags;

        public EditScheduleDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditScheduleDescriptor(EditScheduleDescriptor toCopy) {
            setDate(toCopy.date);
            setTime(toCopy.time);
            setVenue(toCopy.venue);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(date, time, venue, tags);
        }

        public void setDate(Calendar calendar) {
            date = calendar;
        }

        public void setTime(Calendar calendar) {
            time = calendar;
        }

        public Optional<Calendar> getDate() {
            return Optional.ofNullable(date);
        }

        public Optional<Calendar> getTime() {
            return Optional.ofNullable(time);
        }

        public void setVenue(Venue venue) {
            this.venue = venue;
        }

        public Optional<Venue> getVenue() {
            return Optional.ofNullable(venue);
        }


        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditScheduleDescriptor)) {
                return false;
            }

            // state check
            EditScheduleDescriptor e = (EditScheduleDescriptor) other;

            return getDate().equals(e.getDate())
                    && getTime().equals(e.getTime())
                    && getVenue().equals(e.getVenue())
                    && getTags().equals(e.getTags());
        }
    }

}
