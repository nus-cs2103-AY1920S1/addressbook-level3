package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPLOYEE_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EVENTS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.employee.Employee;
import seedu.address.model.event.Event;
import seedu.address.model.tag.Tag;

/**
 * Allocates a number of people to an event either randomly or filtered by matching tags.
 */
public class AutoAllocateCommand extends Command {

    public static final String COMMAND_WORD = "allocate";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Auto allocates a specified number of people to an event."
            + "\n"
            + "Parameters: EVENT_INDEX (must be a positive integer) "
            + "[" + PREFIX_EMPLOYEE_NUMBER + "NUMBER] "
            + "[" + PREFIX_TAG + "TAG]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_EMPLOYEE_NUMBER + "3 "
            + PREFIX_TAG + "fun";


    public static final String MESSAGE_ALLOCATE_EVENT_SUCCESS = "Allocated %2$d people to %1$s ";

    private Integer manpowerCountToAdd;
    private final Index eventIndex;
    private final Set<Tag> tagList;

    /**
     * @param eventIndex of the event in the filtered event list to edit
     * @param manpowerCountToAdd for the event
     * @param tagList list of tags to filter the employees to add
     */
    public AutoAllocateCommand(Index eventIndex, Integer manpowerCountToAdd, Set<Tag> tagList) {
        requireNonNull(eventIndex);
        requireNonNull(tagList);

        this.manpowerCountToAdd = manpowerCountToAdd;
        this.eventIndex = eventIndex;
        this.tagList = tagList;
    }

    /**
     * Creates a list of employees who are available for the event.
     * @param employeeList the full list of employees
     * @param eventList the full list of events
     * @param eventToAllocate the specified event to allocate manpower to
     */
    public List<Employee> createAvailableEmployeeListForEvent(List<Employee> employeeList,
                                                              List<Event> eventList, Event eventToAllocate) {
        List<Employee> availableEmployeeList = employeeList.stream()
                .filter(employee ->eventToAllocate.isAvailableForEvent(employee, eventList))
                .filter(employee -> employee.getTags().containsAll(tagList))
                .collect(Collectors.toList());

        return availableEmployeeList;
    }

    /**
     * Calculates the number of employees currently required by the event.
     * @param eventToAllocate the specified event to allocate manpower to
     */
    public Integer getManpowerNeededByEvent(Event eventToAllocate) {
        return eventToAllocate.getManpowerNeeded().value
                - eventToAllocate.getManpowerAllocatedList().getCurrentManpowerCount();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Event> lastShownEventList = model.getFilteredEventList();

        if (eventIndex.getZeroBased() >= lastShownEventList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        Event eventToAllocate = lastShownEventList.get(eventIndex.getZeroBased());
        Integer manpowerNeededByEvent = getManpowerNeededByEvent(eventToAllocate);

        if (manpowerCountToAdd == null) { //if manpower count is not specified
            this.manpowerCountToAdd = getManpowerNeededByEvent(eventToAllocate);

        } else if (manpowerCountToAdd > manpowerNeededByEvent) {
            throw new CommandException(Messages.MESSAGE_MANPOWER_COUNT_EXCEEDED);
        }

        model.updateFilteredEmployeeList(PREDICATE_SHOW_ALL_PERSONS);
        model.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);

        List<Employee> availableEmployeeList = createAvailableEmployeeListForEvent(model.getFilteredEmployeeList(),
                model.getFilteredEventList(), eventToAllocate);

        if (availableEmployeeList.size() < manpowerCountToAdd) {
            throw new CommandException(Messages.MESSAGE_INSUFFICIENT_MANPOWER_COUNT);
        }

        Collections.shuffle(availableEmployeeList);

        for (int i = 0; i < manpowerCountToAdd; i++) {
            Employee employeeToAdd = availableEmployeeList.get(i);
            eventToAllocate.getManpowerAllocatedList().allocateEmployee(employeeToAdd.getEmployeeId().id);
        }
        model.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);

        return new CommandResult(String.format(MESSAGE_ALLOCATE_EVENT_SUCCESS, eventToAllocate.getName().eventName,
                manpowerCountToAdd));
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
                && manpowerCountToAdd.equals(e.manpowerCountToAdd)
                && tagList.equals(e.tagList);
    }

}
