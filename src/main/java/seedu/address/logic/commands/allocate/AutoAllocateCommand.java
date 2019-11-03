package seedu.address.logic.commands.allocate;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPLOYEE_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MANPOWER_TO_ADD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
 * Allocates a number of people to an event either randomly or filtered by matching tags.
 */
public class AutoAllocateCommand extends Command {

    public static final String COMMAND_WORD = "allocate";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Allocates a specified number of available employees to an event (with possible filters)."
            + "\n"
            + "Parameters: EVENT_INDEX "
            + "[" + PREFIX_MANPOWER_TO_ADD + "NUMBER_OF_EMPLOYEES_TO_ALLOCATE] "
            + "[" + PREFIX_TAG + "TAG_FOR_FILTER]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_EMPLOYEE_NUMBER + "3 "
            + PREFIX_TAG + "male";


    public static final String MESSAGE_ALLOCATE_SUCCESS = "Allocated %2$d people to %1$s ";

    private Integer manpowerCountToAdd;
    private final Index eventIndex;
    private final Set<Tag> tagList;

    /**
     * @param eventIndex         of the event in the filtered event list to edit (must contain a positive Integer)
     * @param manpowerCountToAdd for the event (must be a positive Integer)
     * @param tagList            list of tags to filter the list of available employees
     */
    public AutoAllocateCommand(Index eventIndex, Integer manpowerCountToAdd, Set<Tag> tagList) {
        requireNonNull(eventIndex);
        requireNonNull(tagList);

        assert (eventIndex.getOneBased() > 0);

        assert manpowerCountToAdd == null || manpowerCountToAdd > 0;

        this.manpowerCountToAdd = manpowerCountToAdd;
        this.eventIndex = eventIndex;
        this.tagList = tagList;
    }

    /**
     * Creates a list of employees who are available for the specified event.
     *
     * @param model           the full list of employees
     * @param eventToAllocate the specified event to allocate employees
     */
    private List<Employee> createAvailableEmployeeListForEvent(Model model, Event eventToAllocate) {
        List<Employee> employeeList = model.getFullListEmployees();
        List<Event> eventList = model.getFullListEvents();

        return employeeList.stream()
                .filter(employee -> eventToAllocate.isAvailableForEvent(employee, eventList))
                .filter(employee -> employee.getTags().containsAll(tagList))
                .collect(Collectors.toList());
    }

    /**
     * Calculates the number of employees currently required by the specified event.
     *
     * @param eventToAllocate the specified event to allocate manpower to
     */
    private Integer getManpowerNeededByEvent(Event eventToAllocate) {
        return eventToAllocate.getManpowerNeeded().value
                - eventToAllocate.getCurrentManpowerCount();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Event> lastShownEventList;

        //Checks the current tab index and retrieves the relevant list from model
        if (MainWindow.getCurrentTabIndex() == 0) {
            lastShownEventList = model.getFilteredEventList();
        } else {
            lastShownEventList = model.getFilteredScheduledEventList();
        }

        if (eventIndex.getOneBased() > lastShownEventList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        Event eventToAllocate = lastShownEventList.get(eventIndex.getZeroBased());
        Integer manpowerNeededByEvent = getManpowerNeededByEvent(eventToAllocate);

        if (manpowerCountToAdd == null) { //if manpower count is not specified
            this.manpowerCountToAdd = manpowerNeededByEvent;
        }

        if (manpowerNeededByEvent == 0) {
            throw new CommandException(Messages.MESSAGE_EVENT_FULL_MANPOWER);
        }

        if (manpowerCountToAdd > manpowerNeededByEvent) {
            throw new CommandException(Messages.MESSAGE_MANPOWER_COUNT_EXCEEDED);
        }

        List<Employee> availableEmployeeList = createAvailableEmployeeListForEvent(model, eventToAllocate);

        if (availableEmployeeList.size() < manpowerCountToAdd) {
            throw new CommandException(Messages.MESSAGE_INSUFFICIENT_MANPOWER_COUNT);
        }

        Collections.shuffle(availableEmployeeList);
        Event newEventForAllocation = createEventAfterManpowerAllocation(eventToAllocate,
                availableEmployeeList, manpowerCountToAdd);

        model.setEvent(eventToAllocate, newEventForAllocation);

        return new CommandResult(String.format(MESSAGE_ALLOCATE_SUCCESS, eventToAllocate.getName().eventName,
                manpowerCountToAdd));
    }

    /**
     * Creates and returns a {@code Event} with the details of {@code eventToEdit}
     */
    public static Event createEventAfterManpowerAllocation(Event eventToEdit, List<Employee> availableEmployeeList,
                                                           Integer manpowerCountToAdd) {
        assert eventToEdit != null;
        assert availableEmployeeList != null;
        assert manpowerCountToAdd != null;

        EventName updatedEventName = eventToEdit.getName();
        EventVenue updatedEventVenue = eventToEdit.getVenue();
        EventManpowerNeeded updatedManpowerNeeded = eventToEdit.getManpowerNeeded();
        EventDate updatedStartDate = eventToEdit.getStartDate();
        EventDate updatedEndDate = eventToEdit.getEndDate();
        EventDateTimeMap eventDateTimeMap = eventToEdit.getEventDateTimeMap();
        List<EmployeeId> updatedManpowerList = eventToEdit.getManpowerAllocatedList().getManpowerList();
        Set<Tag> updatedTags = eventToEdit.getTags();

        for (int i = 0; i < manpowerCountToAdd; i++) {
            Employee employeeToAdd = availableEmployeeList.get(i);
            updatedManpowerList.add(employeeToAdd.getEmployeeId());
        }
        EventManpowerAllocatedList updatedManpowerAllocatedList = new EventManpowerAllocatedList(updatedManpowerList);

        return new Event(updatedEventName, updatedEventVenue,
                updatedManpowerNeeded, updatedStartDate,
                updatedEndDate, updatedManpowerAllocatedList, eventDateTimeMap, updatedTags);
    }


    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AutoAllocateCommand)) {
            return false;
        }

        // state check
        AutoAllocateCommand e = (AutoAllocateCommand) other;
        return eventIndex.equals(e.eventIndex)
                && (manpowerCountToAdd == e.manpowerCountToAdd || manpowerCountToAdd.equals(e.manpowerCountToAdd))
                && tagList.equals(e.tagList);
    }

}
