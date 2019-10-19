package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPLOYEE_NUMBER;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EVENTS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.employee.Employee;
import seedu.address.model.event.Event;

/**
 * Allocates a person to an event.
 */
public class AutoAllocateCommand extends Command {

    public static final String COMMAND_WORD = "allocate";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Auto allocates a specified number of people to an event."
            + "\n"
            + "Parameters: NUMBER (must be a positive integer) "
            + "[" + PREFIX_EMPLOYEE_NUMBER + "NUMBER] "
            + "Example: " + COMMAND_WORD + " 5";


    public static final String MESSAGE_ALLOCATE_EVENT_SUCCESS = "Allocated to %1$s ";

    private final int manpowerCountToAdd;
    private final Index eventIndex;

    /**
     * @param manpowerCountToAdd of the person in the filtered person list to edit
     */
    public AutoAllocateCommand(Index eventIndex, Integer manpowerCountToAdd) {
        requireNonNull(manpowerCountToAdd);
        requireNonNull(eventIndex);

        this.manpowerCountToAdd = manpowerCountToAdd;
        this.eventIndex = eventIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Employee> lastShownList = model.getFilteredEmployeeList();
        List<Event> lastShownEventList = model.getFilteredEventList();

        if (eventIndex.getZeroBased() >= lastShownEventList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        Event eventToEdit = lastShownEventList.get(eventIndex.getZeroBased());

        int manpowerNeededByEvent = eventToEdit.getManpowerNeeded().value
                - eventToEdit.getManpowerAllocatedList().getCurrentManpowerCount();

        if (manpowerCountToAdd > manpowerNeededByEvent) {
            throw new CommandException(Messages.MESSAGE_MANPOWER_COUNT_EXCEEDED);
        }

        model.updateFilteredEmployeeList(PREDICATE_SHOW_ALL_PERSONS);
        model.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        List<Employee> availableEmployeeList = model.getFilteredEmployeeList().stream()
                .filter(x ->eventToEdit.isAvailableForEvent(x, model.getFilteredEventList()))
                .collect(Collectors.toList());
        if (availableEmployeeList.size() < manpowerCountToAdd) {
            throw new CommandException(Messages.MESSAGE_INSUFFICIENT_MANPOWER_COUNT);
        }
        Collections.shuffle(availableEmployeeList);

        for (int i = 0; i < manpowerCountToAdd; i++) {
            eventToEdit.getManpowerAllocatedList().allocateEmployee(availableEmployeeList.get(i).getEmployeeId().id);
        }

        return new CommandResult(String.format(MESSAGE_ALLOCATE_EVENT_SUCCESS, eventToEdit.getName().eventName));
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
        return eventIndex.equals(e.eventIndex);
    }

}
