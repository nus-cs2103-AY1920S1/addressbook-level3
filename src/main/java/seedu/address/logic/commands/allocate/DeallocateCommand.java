/*
@@author calvincxz
 */

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
 * Free employees associated with an event.
 */
public class DeallocateCommand extends Command {

    public static final String COMMAND_WORD = "free";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": De-allocate the employees associated with the event in "
            + "the displayed event list.\n"
            + "Parameters: EVENT_INDEX (must be a positive integer)"
            + " [EMPLOYEE_ID]\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_EMPLOYEE_ID + "001";

    public static final String MESSAGE_FREE_EVENT_SUCCESS = "De-allocated %2$s from the Event: %1$s";

    private final Index eventIndex;
    private final EmployeeId employeeId;

    public DeallocateCommand(Index eventIndex) {
        this.eventIndex = eventIndex;
        this.employeeId = null;
    }

    public DeallocateCommand(Index eventIndex, EmployeeId employeeId) {
        this.eventIndex = eventIndex;
        this.employeeId = employeeId;
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (MainWindow.isFinanceTab() || MainWindow.isStatsTab()) {
            throw new CommandException(Messages.MESSAGE_WRONG_TAB_DE_ALLOCATE);
        }

        List<Employee> lastShownList = model.getFullListEmployees();
        List<Event> lastShownEventList;

        // Checks the current tab index and retrieves the relevant event list from model
        if (MainWindow.isMainTab()) {
            lastShownEventList = model.getFilteredEventList();
        } else if (MainWindow.isScheduleTab()) {
            lastShownEventList = model.getFilteredScheduledEventList();
        } else {
            throw new CommandException(Messages.MESSAGE_WRONG_WINDOW);
        }

        if (eventIndex.getZeroBased() >= lastShownEventList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        Event eventToFree = lastShownEventList.get(eventIndex.getZeroBased());
        Event newEventAfterFree;
        String employeeNameToDisplay;

        //Checks if employeeId is found and does the relevant changes for the de-allocation from events.
        if (employeeId != null) {
            Optional<Employee> optionalPersonToDelete = lastShownList.stream()
                    .filter(x -> x.getEmployeeId().equals(employeeId))
                    .findAny();

            if (optionalPersonToDelete.isEmpty()) {
                throw new CommandException(Messages.MESSAGE_EVENT_INVALID_EMPLOYEE_ID);
            }

            Employee personToDelete = optionalPersonToDelete.get();
            newEventAfterFree = createEditedEvent(eventToFree, personToDelete);
            model.setEvent(eventToFree, newEventAfterFree);
            employeeNameToDisplay = personToDelete.getEmployeeName().fullName;
        } else {
            newEventAfterFree = createEditedEvent(eventToFree, null);
            employeeNameToDisplay = "ALL Employees";
        }

        model.setEvent(eventToFree, newEventAfterFree);
        return new CommandResult(String.format(MESSAGE_FREE_EVENT_SUCCESS, eventToFree.getName(),
                employeeNameToDisplay));
    }

    /**
     * Creates and returns a {@code Event} with the details of {@code eventToEdit}
     * and a new {@code EventManpowerAllocatedList} depending on the Employee given as input.
     */
    public static Event createEditedEvent(Event eventToEdit, Employee employeeToDelete) throws CommandException {
        assert eventToEdit != null;

        EventName updatedEventName = eventToEdit.getName();
        EventVenue updatedEventVenue = eventToEdit.getVenue();
        EventManpowerNeeded updatedManpowerNeeded = eventToEdit.getManpowerNeeded();
        EventDate updatedStartDate = eventToEdit.getStartDate();
        EventDate updatedEndDate = eventToEdit.getEndDate();
        EventDateTimeMap eventDateTimeMap = eventToEdit.getEventDateTimeMap();
        Set<Tag> updatedTags = eventToEdit.getTags();
        EventManpowerAllocatedList updatedManpowerAllocatedList;

        if (employeeToDelete == null) {
            updatedManpowerAllocatedList = new EventManpowerAllocatedList();
        } else {
            List<EmployeeId> updatedManpowerList = eventToEdit.getManpowerAllocatedList().getManpowerList();
            if (!updatedManpowerList.remove(employeeToDelete.getEmployeeId())) {
                throw new CommandException(Messages.MESSAGE_EMPLOYEE_DOES_NOT_EXIST);
            }
            updatedManpowerAllocatedList = new EventManpowerAllocatedList(updatedManpowerList);
        }

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
        if (!(other instanceof DeallocateCommand)) {
            return false;
        }

        // state check
        DeallocateCommand e = (DeallocateCommand) other;
        return eventIndex.equals(e.eventIndex)
                && (employeeId == e.employeeId || employeeId.equals(e.employeeId));
    }

}
