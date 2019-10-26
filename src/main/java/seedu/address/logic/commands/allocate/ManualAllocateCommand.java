package seedu.address.logic.commands.allocate;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPLOYEE_NUMBER;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.employee.Employee;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventDate;
import seedu.address.model.event.EventManpowerAllocatedList;
import seedu.address.model.event.EventManpowerNeeded;
import seedu.address.model.event.EventName;
import seedu.address.model.event.EventVenue;
import seedu.address.model.tag.Tag;

/**
 * Allocates a person to an event.
 */
public class ManualAllocateCommand extends Command {

    public static final String COMMAND_WORD = "allocatem";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Manually allocates a person to an event."
            + "\n"
            + "Parameters: EVENT_INDEX "
            + "PERSON_INDEX (must be valid positive integers)\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_EMPLOYEE_NUMBER + "2 ";

    public static final String MESSAGE_ALLOCATE_EVENT_SUCCESS = "Added Person: %1$s to %2$s";

    private final Index index;
    private final Index eventIndex;

    /**
     * @param eventIndex of the event in the filtered event list to edit
     * @param index of the person in the filtered person list to add to event
     */
    public ManualAllocateCommand(Index eventIndex, Index index) {
        requireNonNull(index);
        requireNonNull(eventIndex);

        this.index = index;
        this.eventIndex = eventIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Employee> lastShownList = model.getFilteredEmployeeList();
        List<Event> lastShownEventList = model.getFilteredEventList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EMPLOYEE_DISPLAYED_INDEX);
        }

        if (eventIndex.getZeroBased() >= lastShownEventList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        Employee personToAdd = lastShownList.get(index.getZeroBased());
        Event eventToAllocate = lastShownEventList.get(eventIndex.getZeroBased());
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
        List<String> updatedManpowerList = eventToEdit.getManpowerAllocatedList().getManpowerList();
        Set<Tag> updatedTags = eventToEdit.getTags();
        updatedManpowerList.add(employeeToAdd.getEmployeeId().id);
        EventManpowerAllocatedList updatedManpowerAllocatedList = new EventManpowerAllocatedList(updatedManpowerList);

        return new Event(updatedEventName, updatedEventVenue,
                updatedManpowerNeeded, updatedStartDate,
                updatedEndDate, updatedManpowerAllocatedList, updatedTags);
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
        return index.equals(e.index) && eventIndex.equals(e.eventIndex);
    }

}
