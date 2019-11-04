package seedu.address.logic.commands.allocate;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPLOYEE_NUMBER;

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
 * Allocates an employee from the  to an event.
 */
public class ManualAllocateCommand extends Command {

    public static final String COMMAND_WORD = "allocatem";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Manually allocates an employee to an event."
            + "\n"
            + "Parameters: EVENT_INDEX "
            + PREFIX_EMPLOYEE_NUMBER + "PERSON_INDEX (must be valid positive integers)\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_EMPLOYEE_NUMBER + "2 ";

    public static final String MESSAGE_ALLOCATE_EVENT_SUCCESS = "Added Employee: %1$s to %2$s.";

    private final Index employeeIndex;
    private final Index eventIndex;
    private final String employeeId;

    /**
     * @param eventIndex of the event in the filtered event list to edit
     * @param employeeIndex      of the employee in the filtered employee list to add to event
     */
    public ManualAllocateCommand(Index eventIndex, Index employeeIndex) {
        requireNonNull(employeeIndex);
        requireNonNull(eventIndex);

        this.employeeIndex = employeeIndex;
        this.eventIndex = eventIndex;
        this.employeeId = null;
    }

    /**
     * A ManualAllocateCommand to allocate employee with specified {@code EmployeeId} to the specified event.
     * This constructor is used for GUI purposes.
     *
     * @param eventIndex of the event in the filtered event list to edit
     * @param employeeId      of the employee to add to the event
     */
    public ManualAllocateCommand(Index eventIndex, String employeeId) {
        requireNonNull(employeeId);
        requireNonNull(eventIndex);

        this.eventIndex = eventIndex;
        this.employeeIndex = null;
        this.employeeId = employeeId;
    }

    /**
     * A private method for manual allocation used primarily for GUI purposes.
     */
    private CommandResult internalManualAllocateById(Model model) throws CommandException {
        List<Employee> lastShownList = model.getFullListEmployees();

        List<Event> lastShownEventList;
        if (MainWindow.getCurrentTabIndex() == 0) {
            lastShownEventList = model.getFilteredEventList();
        } else {
            lastShownEventList = model.getFilteredScheduledEventList();
        }

        Event eventToAllocate = lastShownEventList.get(eventIndex.getZeroBased());

        Optional<Employee> optionalPersonToAdd = lastShownList.stream()
                .filter(x -> x.getEmployeeId().id.equals(employeeId))
                .findAny();

        if (optionalPersonToAdd.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_EVENT_INVALID_EMPLOYEE_ID);
        }

        Employee personToAdd = optionalPersonToAdd.get();

        if (eventToAllocate.getCurrentManpowerCount() == eventToAllocate.getManpowerNeeded().value) {
            throw new CommandException(Messages.MESSAGE_EVENT_FULL_MANPOWER);
        }

        Event newEventForAllocation = createEditedEvent(eventToAllocate, personToAdd);
        model.setEvent(eventToAllocate, newEventForAllocation);
        return new CommandResult(String.format(MESSAGE_ALLOCATE_EVENT_SUCCESS, personToAdd.getEmployeeName(),
                newEventForAllocation.getName()));
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (employeeId != null) {
            return internalManualAllocateById(model);
        }

        List<Employee> lastShownList = model.getFilteredEmployeeList();
        List<Event> lastShownEventList;
        if (MainWindow.getCurrentTabIndex() == 0) {
            lastShownEventList = model.getFilteredEventList();
        } else {
            throw new CommandException(Messages.MESSAGE_WRONG_WINDOW);
        }

        if (employeeIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EMPLOYEE_DISPLAYED_INDEX);
        }

        if (eventIndex.getZeroBased() >= lastShownEventList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        Employee personToAdd = lastShownList.get(employeeIndex.getZeroBased());
        Event eventToAllocate = lastShownEventList.get(eventIndex.getZeroBased());

        if (eventToAllocate.getManpowerAllocatedList().containsEmployee(personToAdd)) {
            throw new CommandException(Messages.MESSAGE_EMPLOYEE_ALREADY_ALLOCATED);
        }

        if (eventToAllocate.getCurrentManpowerCount() == eventToAllocate.getManpowerNeeded().value) {
            throw new CommandException(Messages.MESSAGE_EVENT_FULL_MANPOWER);
        }

        if (!eventToAllocate.isAvailableForEvent(personToAdd, model.getFilteredEventList())) {
            throw new CommandException(Messages.MESSAGE_UNAVAILABLE_MANPOWER);
        }
        Event newEventForAllocation = createEditedEvent(eventToAllocate, personToAdd);

        model.setEvent(eventToAllocate, newEventForAllocation);
        return new CommandResult(String.format(MESSAGE_ALLOCATE_EVENT_SUCCESS, personToAdd.getEmployeeName().fullName,
                newEventForAllocation.getName().eventName));
    }

    /**
     * Creates and returns a {@code Event} with the details of {@code eventToEdit}
     */
    private static Event createEditedEvent(Event eventToEdit, Employee employeeToAdd) {
        assert eventToEdit != null;

        EventName updatedEventName = eventToEdit.getName();
        EventVenue updatedEventVenue = eventToEdit.getVenue();
        EventManpowerNeeded updatedManpowerNeeded = eventToEdit.getManpowerNeeded();
        EventDate updatedStartDate = eventToEdit.getStartDate();
        EventDate updatedEndDate = eventToEdit.getEndDate();
        EventDateTimeMap updatedDateTimeMap = eventToEdit.getEventDateTimeMap();
        List<EmployeeId> updatedManpowerList = eventToEdit.getManpowerAllocatedList().getManpowerList();
        Set<Tag> updatedTags = eventToEdit.getTags();
        updatedManpowerList.add(employeeToAdd.getEmployeeId());
        EventManpowerAllocatedList updatedManpowerAllocatedList = new EventManpowerAllocatedList(updatedManpowerList);

        return new Event(updatedEventName, updatedEventVenue,
                updatedManpowerNeeded, updatedStartDate,
                updatedEndDate, updatedManpowerAllocatedList, updatedDateTimeMap, updatedTags);
    }


    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ManualAllocateCommand)) {
            return false;
        }

        // state check
        ManualAllocateCommand e = (ManualAllocateCommand) other;
        return employeeIndex.equals(e.employeeIndex) && eventIndex.equals(e.eventIndex);
    }

}
