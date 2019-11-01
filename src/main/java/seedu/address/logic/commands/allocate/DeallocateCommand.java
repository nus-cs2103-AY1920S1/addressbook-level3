package seedu.address.logic.commands.allocate;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPLOYEE_ID;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.employee.Employee;
import seedu.address.model.employee.EmployeeId;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventDate;
import seedu.address.model.event.EventDateTimeMap;
import seedu.address.model.event.EventManpowerAllocatedList;
import seedu.address.model.event.EventManpowerNeeded;
import seedu.address.model.event.EventName;
import seedu.address.model.event.EventVenue;
import seedu.address.model.tag.Tag;
import seedu.address.ui.MainWindow;

/**
 * Frees employees associated with an event.
 */
public class DeallocateCommand extends Command {

    public static final String COMMAND_WORD = "free";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": De-allocate the employees associated with the event identified by the index number used in "
            + "the displayed event list.\n"
            + "Parameters: EVENT_INDEX (must be a positive integer)"
            + " [EMPLOYEE_ID]\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_EMPLOYEE_ID + "001";

    public static final String MESSAGE_FREE_EVENT_SUCCESS = "De-allocated %2$s from the Event: %1$s";

    private final Index eventIndex;
    private final String employeeId;

    public DeallocateCommand(Index eventIndex) {
        this.eventIndex = eventIndex;
        this.employeeId = null;
    }

    public DeallocateCommand(Index eventIndex, String employeeId) {
        this.eventIndex = eventIndex;
        this.employeeId = employeeId;
    }

    /**
     * A private method for manual de-allocation used primarily for GUI purposes.
     */
    private CommandResult internalManualFreeById(Model model) throws CommandException {
        List<Employee> lastShownList = model.getFullListEmployees();
        List<Event> lastShownEventList;
        if (MainWindow.getCurrentTabIndex() == 0) {
            lastShownEventList = model.getFilteredEventList();
        } else {
            lastShownEventList = model.getFilteredScheduledEventList();
        }

        Event eventToAllocate = lastShownEventList.get(eventIndex.getZeroBased());
        Optional<Employee> optionalPersonToDelete = lastShownList.stream()
                .filter(x -> x.getEmployeeId().id.equals(employeeId))
                .findAny();

        if (optionalPersonToDelete.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_EVENT_INVALID_EMPLOYEE_ID);
        }

        Employee personToDelete = optionalPersonToDelete.get();
        Event newEventForAllocation = createEditedEvent(eventToAllocate, personToDelete);
        model.setEvent(eventToAllocate, newEventForAllocation);
        return new CommandResult(String.format(MESSAGE_FREE_EVENT_SUCCESS, newEventForAllocation.getName().eventName,
                personToDelete.getEmployeeName().fullName));
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Event> lastShownEventList;

        if (MainWindow.getCurrentTabIndex() == 0) {
            lastShownEventList = model.getFilteredEventList();
        } else {
            lastShownEventList = model.getFilteredScheduledEventList();
        }

        if (eventIndex.getZeroBased() >= lastShownEventList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        if (employeeId != null) {
            return internalManualFreeById(model);
        }

        Event eventToFree = lastShownEventList.get(eventIndex.getZeroBased());
        Event newEvent = createEditedEvent(eventToFree, null);
        model.setEvent(eventToFree, newEvent);
        return new CommandResult(String.format(MESSAGE_FREE_EVENT_SUCCESS, eventToFree.getName(), "ALL Employees"));
    }

    /**
     * Creates and returns a {@code Event} with the details of {@code eventToEdit}
     * and a new {@code EventManpowerAllocatedList}.
     */
    private static Event createEditedEvent(Event eventToEdit, Employee employeeToDelete) {
        assert eventToEdit != null;

        EventName updatedEventName = eventToEdit.getName();
        EventVenue updatedEventVenue = eventToEdit.getVenue();
        EventManpowerNeeded updatedManpowerNeeded = eventToEdit.getManpowerNeeded();
        EventDate updatedStartDate = eventToEdit.getStartDate();
        EventDate updatedEndDate = eventToEdit.getEndDate();
        EventManpowerAllocatedList updatedManpowerAllocatedList;
        if (employeeToDelete == null) {
            updatedManpowerAllocatedList = new EventManpowerAllocatedList();
        } else {
            List<EmployeeId> updatedManpowerList = eventToEdit.getManpowerAllocatedList().getManpowerList();
            updatedManpowerList.remove(employeeToDelete.getEmployeeId());
            updatedManpowerAllocatedList = new EventManpowerAllocatedList(updatedManpowerList);
        }
        EventDateTimeMap eventDateTimeMap = eventToEdit.getEventDateTimeMap();
        Set<Tag> updatedTags = eventToEdit.getTags();

        return new Event(updatedEventName, updatedEventVenue,
                updatedManpowerNeeded, updatedStartDate,
                updatedEndDate, updatedManpowerAllocatedList, eventDateTimeMap, updatedTags);

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeallocateCommand // instanceof handles nulls
                && eventIndex.equals(((DeallocateCommand) other).eventIndex)) // state check
                && employeeId.equals(((DeallocateCommand) other).employeeId); // state check
    }
}
