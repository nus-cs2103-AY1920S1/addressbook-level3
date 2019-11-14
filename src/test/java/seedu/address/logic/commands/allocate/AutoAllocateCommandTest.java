package seedu.address.logic.commands.allocate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.allocate.AutoAllocateCommand.MESSAGE_ALLOCATE_SUCCESS;
import static seedu.address.logic.commands.allocate.AutoAllocateCommand.createEventAfterManpowerAllocation;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EVENT;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import seedu.address.model.event.Event;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code AutoAllocateCommandTest}.
 */
public class AutoAllocateCommandTest {
    private ReadOnlyAddressBook initialData = SampleDataUtil.getSampleAddressBook();
    private ReadOnlyEventBook initialEventData = SampleDataUtil.getSampleEventBook();
    private Model model = new ModelManager(initialData, initialEventData, new UserPrefs());
    private final Integer sampleManpowerCountToAdd = 2;
    private final Integer sampleManpowerNeeded = 5;
    private Set<Tag> tagList = new HashSet<Tag>();

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() throws CommandException {
        tagList.add(new Tag("male"));
        AutoAllocateCommand autoAllocateCommand = new AutoAllocateCommand(INDEX_FIRST_EVENT,
                sampleManpowerCountToAdd, tagList);

        String expectedMessage = String.format(MESSAGE_ALLOCATE_SUCCESS,
                initialEventData.getEventList().get(0).getName().eventName, sampleManpowerCountToAdd);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new EventBook(model.getEventBook()), new UserPrefs());

        assertCommandSuccess(autoAllocateCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_onlyTagsSpecifiedUnfilteredList_success() throws CommandException {
        tagList.add(new Tag("male"));
        AutoAllocateCommand autoAllocateCommand = new AutoAllocateCommand(INDEX_FIRST_EVENT, null,
                tagList);
        String expectedMessage = String.format(MESSAGE_ALLOCATE_SUCCESS,
                initialEventData.getEventList().get(0).getName().eventName, sampleManpowerNeeded);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new EventBook(model.getEventBook()), new UserPrefs());

        assertCommandSuccess(autoAllocateCommand, model, expectedMessage, expectedModel);
    }
    @Test
    public void execute_onlyManpowerCountToAddSpecifiedUnfilteredList_success() throws CommandException {
        AutoAllocateCommand autoAllocateCommand = new AutoAllocateCommand(INDEX_FIRST_EVENT, sampleManpowerCountToAdd,
                tagList);
        String expectedMessage = String.format(MESSAGE_ALLOCATE_SUCCESS,
                initialEventData.getEventList().get(0).getName().eventName, sampleManpowerCountToAdd);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new EventBook(model.getEventBook()), new UserPrefs());

        assertCommandSuccess(autoAllocateCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() throws CommandException {
        AutoAllocateCommand autoAllocateCommand = new AutoAllocateCommand(INDEX_FIRST_EVENT, null,
                tagList);
        String expectedMessage = String.format(MESSAGE_ALLOCATE_SUCCESS,
                initialEventData.getEventList().get(0).getName().eventName, sampleManpowerNeeded);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new EventBook(model.getEventBook()), new UserPrefs());
        assertCommandSuccess(autoAllocateCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidEventIndexUnfilteredList_failure() throws CommandException {
        int outOfBoundInteger = initialEventData.getEventList().size() + 1;
        Index invalidIndex = Index.fromOneBased(outOfBoundInteger);
        AutoAllocateCommand autoAllocateCommand = new AutoAllocateCommand(invalidIndex,
                sampleManpowerCountToAdd, tagList);

        assertCommandFailure(autoAllocateCommand, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidManpowerCountUnfilteredList_failure() throws CommandException {
        Integer invalidManpowerCount = 20;
        AutoAllocateCommand autoAllocateCommand = new AutoAllocateCommand(INDEX_FIRST_EVENT,
                invalidManpowerCount, tagList);

        assertCommandFailure(autoAllocateCommand, model, Messages.MESSAGE_MANPOWER_COUNT_EXCEEDED);
    }

    @Test
    public void execute_fullManpowerCountUnfilteredListNoInputSpecified_failure() throws CommandException {
        Event eventToEdit = initialEventData.getEventList().get(0);
        List<Employee> availableEmployeeList = new ArrayList<>();
        for (int i = 0; i < eventToEdit.getManpowerNeeded().value; i++) {
            availableEmployeeList.add(initialData.getEmployeeList().get(i));
        }
        Event newEvent = createEventAfterManpowerAllocation(eventToEdit, availableEmployeeList, 5);
        model.setEvent(eventToEdit, newEvent);
        AutoAllocateCommand autoAllocateCommand = new AutoAllocateCommand(INDEX_FIRST_EVENT,
                null, tagList);

        assertCommandFailure(autoAllocateCommand, model, Messages.MESSAGE_EVENT_FULL_MANPOWER);
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
        AutoAllocateCommand autoAllocateCommand = new AutoAllocateCommand(INDEX_FIRST_EVENT,
                5, tagList);

        assertCommandFailure(autoAllocateCommand, model, Messages.MESSAGE_EVENT_FULL_MANPOWER);
    }


    @Test
    public void execute_insufficientManpowerCountUnfilteredList_failure() throws CommandException {
        AutoAllocateCommand autoAllocateCommand = new AutoAllocateCommand(INDEX_SECOND_EVENT,
                null, tagList);
        assertCommandFailure(autoAllocateCommand, model, Messages.MESSAGE_INSUFFICIENT_MANPOWER_COUNT);
    }

    @Test
    public void equals() throws CommandException {
        final AutoAllocateCommand standardCommand = new AutoAllocateCommand(INDEX_FIRST_EVENT,
                sampleManpowerCountToAdd, tagList);

        // same values -> returns true
        AutoAllocateCommand commandWithSameValues = new AutoAllocateCommand(INDEX_FIRST_EVENT,
                sampleManpowerCountToAdd, tagList);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearEmployeesCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new AutoAllocateCommand(INDEX_SECOND_EVENT,
                sampleManpowerCountToAdd, tagList)));

        // different index -> returns false
        Integer differentManpowerCountToAdd = 3;
        assertFalse(standardCommand.equals(new AutoAllocateCommand(INDEX_SECOND_EVENT,
                differentManpowerCountToAdd, tagList)));

        Set<Tag> differentTagList = new HashSet<Tag>();
        differentTagList.add(new Tag("something"));
        // different tags -> returns false
        assertFalse(standardCommand.equals(new AutoAllocateCommand(INDEX_FIRST_EVENT,
                sampleManpowerCountToAdd, differentTagList)));
    }
}

