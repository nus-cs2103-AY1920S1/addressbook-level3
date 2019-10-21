package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPLOYEE_NUMBER;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EVENTS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.employee.Employee;
import seedu.address.model.event.Event;

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
        Event eventToEdit = lastShownEventList.get(eventIndex.getZeroBased());
        if (!eventToEdit.isAvailableForEvent(personToAdd, model.getFilteredEventList())) {
            throw new CommandException(Messages.MESSAGE_UNAVAILABLE_MANPOWER);
        }
        eventToEdit.getManpowerAllocatedList().allocateEmployee(personToAdd.getEmployeeId().id);

        model.updateFilteredEmployeeList(PREDICATE_SHOW_ALL_PERSONS);
        model.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        return new CommandResult(String.format(MESSAGE_ALLOCATE_EVENT_SUCCESS, personToAdd.getEmployeeName().fullName,
                eventToEdit.getName().eventName));
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
