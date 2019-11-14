package seedu.address.logic.commands.employee;

import static java.util.Objects.requireNonNull;

import java.util.List;
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
 * Deletes a employee identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete_em";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the employee identified by the index number used in the displayed employee list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Employee: %1$s";

    private final Index targetIndex;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (MainWindow.isScheduleTab() || MainWindow.isStatsTab()) {
            throw new CommandException(Messages.MESSAGE_WRONG_TAB_MISSING_EMPLOYEE_LIST);
        }

        List<Employee> lastShownList = model.getFilteredEmployeeList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EMPLOYEE_DISPLAYED_INDEX);
        }

        Employee employeeToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteEmployee(employeeToDelete);

        model.getFilteredEventList().stream()
                .forEach(x -> model.setEvent(x, createEditedEvent(x, employeeToDelete)));

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS,
                employeeToDelete.getEmployeeName()), "Employee");
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
                || (other instanceof DeleteCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
    }
}
