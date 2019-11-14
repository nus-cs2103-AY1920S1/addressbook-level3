package seedu.address.logic.commands.allocate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.allocate.AutoAllocateCommand.createEventAfterManpowerAllocation;
import static seedu.address.logic.commands.allocate.ManualAllocateCommand.MESSAGE_ALLOCATE_EVENT_SUCCESS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.employee.ClearEmployeesCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.EventBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyEventBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.employee.Employee;
import seedu.address.model.employee.EmployeeId;
import seedu.address.model.event.Event;
import seedu.address.model.util.SampleDataUtil;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code ManualAllocateCommandTest}.
 */
public class ManualAllocateCommandTest {
    private ReadOnlyAddressBook initialData = SampleDataUtil.getSampleAddressBook();
    private ReadOnlyEventBook initialEventData = SampleDataUtil.getSampleEventBook();
    private Model model = new ModelManager(initialData, initialEventData, new UserPrefs());

    @Test
    public void execute_employeeIndexSpecifiedUnfilteredList_success() throws CommandException {
        ManualAllocateCommand manualAllocateCommand = new ManualAllocateCommand(INDEX_FIRST_EVENT,
                INDEX_FIRST_PERSON, null);

        String expectedMessage = String.format(MESSAGE_ALLOCATE_EVENT_SUCCESS,
                initialData.getEmployeeList().get(0).getEmployeeName(),
                initialEventData.getEventList().get(0).getName());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new EventBook(model.getEventBook()), new UserPrefs());

        assertCommandSuccess(manualAllocateCommand, model, expectedMessage, expectedModel);
    }



    @Test
    public void execute_invalidEventIndexUnfilteredList_failure() throws CommandException {
        Integer outOfBoundInteger = initialEventData.getEventList().size() + 1;
        Index invalidIndex = Index.fromOneBased(outOfBoundInteger);
        ManualAllocateCommand manualAllocateCommand = new ManualAllocateCommand(invalidIndex,
                INDEX_FIRST_PERSON, null);
        assertCommandFailure(manualAllocateCommand, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidEmployeeIndexUnfilteredList_failure() throws CommandException {
        Integer outOfBoundInteger = initialData.getEmployeeList().size() + 1;
        Index invalidIndex = Index.fromOneBased(outOfBoundInteger);
        ManualAllocateCommand manualAllocateCommand = new ManualAllocateCommand(INDEX_FIRST_EVENT,
                invalidIndex, null);
        assertCommandFailure(manualAllocateCommand, model, Messages.MESSAGE_INVALID_EMPLOYEE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidEmployeeIdUnfilteredList_failure() throws CommandException {
        EmployeeId invalidEmployeeId = new EmployeeId("100");

        ManualAllocateCommand manualAllocateCommand = new ManualAllocateCommand(INDEX_FIRST_EVENT,
                null, invalidEmployeeId);
        assertCommandFailure(manualAllocateCommand, model, Messages.MESSAGE_EVENT_INVALID_EMPLOYEE_ID);
    }

    @Test
    public void execute_fullManpowerCountUnfilteredList_failure() throws CommandException {
        Event eventToEdit = initialEventData.getEventList().get(0);
        List<Employee> availableEmployeeList = new ArrayList<>();
        for (int i = 0; i < eventToEdit.getManpowerNeeded().value; i++) {
            availableEmployeeList.add(initialData.getEmployeeList().get(i));
        }
        Event newEvent = createEventAfterManpowerAllocation(eventToEdit, availableEmployeeList, 5);
        model.setEvent(eventToEdit, newEvent);
        Integer validInteger = 6;
        Index validIndex = Index.fromOneBased(validInteger);
        ManualAllocateCommand manualAllocateCommand = new ManualAllocateCommand(INDEX_FIRST_EVENT, validIndex,
                null);

        assertCommandFailure(manualAllocateCommand, model, Messages.MESSAGE_EVENT_FULL_MANPOWER);
    }

    @Test
    public void execute_employeeAlreadyAllocatedUnfilteredList_failure() throws CommandException {
        Event eventToEdit = initialEventData.getEventList().get(0);
        List<Employee> availableEmployeeList = new ArrayList<>();
        for (int i = 0; i < eventToEdit.getManpowerNeeded().value - 1; i++) {
            availableEmployeeList.add(initialData.getEmployeeList().get(i));
        }
        Event newEvent = createEventAfterManpowerAllocation(eventToEdit, availableEmployeeList, 4);
        model.setEvent(eventToEdit, newEvent);
        Integer validInteger = 1;
        Index validIndex = Index.fromOneBased(validInteger);
        ManualAllocateCommand manualAllocateCommand = new ManualAllocateCommand(INDEX_FIRST_EVENT, validIndex,
                null);
        assertCommandFailure(manualAllocateCommand, model, Messages.MESSAGE_EMPLOYEE_ALREADY_ALLOCATED);
    }



    @Test
    public void equals() throws CommandException {
        final ManualAllocateCommand standardCommand = new ManualAllocateCommand(INDEX_FIRST_EVENT, INDEX_FIRST_PERSON,
                null);

        // same values -> returns true
        ManualAllocateCommand commandWithSameValues = new ManualAllocateCommand(INDEX_FIRST_EVENT, INDEX_FIRST_PERSON,
                null);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearEmployeesCommand()));

        // different eventIndex -> returns false
        assertFalse(standardCommand.equals(new ManualAllocateCommand(INDEX_SECOND_EVENT, INDEX_FIRST_PERSON,
                null)));

        // different employeeIndex -> returns false
        assertFalse(standardCommand.equals(new ManualAllocateCommand(INDEX_FIRST_EVENT, INDEX_SECOND_PERSON,
                null)));

    }
}

