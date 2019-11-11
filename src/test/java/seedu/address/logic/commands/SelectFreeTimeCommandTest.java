package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.ALICE;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.BENSON;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.CARL;

import java.time.LocalDateTime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelManager;
import seedu.address.model.display.scheduledisplay.ScheduleState;
import seedu.address.model.group.GroupDescriptor;
import seedu.address.model.group.GroupName;
import seedu.address.model.group.exceptions.GroupNotFoundException;
import seedu.address.model.mapping.Role;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.grouputil.TypicalGroups;
import seedu.address.testutil.modelutil.TypicalModel;

class SelectFreeTimeCommandTest {
    private ModelManager model;
    private GroupName tempGroupName = new GroupName("tempSuccessfulGroup");
    private LocalDateTime dateTimeStub = LocalDateTime.now();

    @BeforeEach
    void init() throws CommandException {
        model = TypicalModel.generateModelWithSchedules();
        GroupDescriptor groupDescriptor = new GroupDescriptor();
        groupDescriptor.setGroupName(tempGroupName);
        new AddGroupCommand(groupDescriptor).execute(model);
        new AddToGroupCommand(ALICE.getName(), tempGroupName, Role.emptyRole());
        new AddToGroupCommand(BENSON.getName(), tempGroupName, Role.emptyRole());
        new AddToGroupCommand(CARL.getName(), tempGroupName, Role.emptyRole());
    }

    @AfterEach
    void deleteTemp() throws CommandException {
        new DeleteGroupCommand(tempGroupName).execute(model);

    }

    @Test
    void executeSuccess() throws CommandException, GroupNotFoundException {
        model.updateScheduleWithGroup(tempGroupName, dateTimeStub, ScheduleState.GROUP);
        SelectFreeTimeCommand selectFreeTimeCommand = new SelectFreeTimeCommand(0, 8);
        assertEquals("I3", selectFreeTimeCommand.execute(model).getLocationData().getFirstClosest());
    }

    @Test
    void executeFailureTooEarly() throws CommandException, GroupNotFoundException {
        model.updateScheduleWithGroup(TypicalGroups.GROUP_NAME1, LocalDateTime.now(), ScheduleState.GROUP);
        SelectFreeTimeCommand selectFreeTimeCommand = new SelectFreeTimeCommand(0, 4);
        assertThrows(CommandException.class, () -> selectFreeTimeCommand.execute(model),
                "We could not find a common location because:\n"
                        + "Everyone has not started their schedule yet. Feel free to meet up any time.");
    }

    @Test
    void executeFailureShowPerson() throws CommandException, PersonNotFoundException {
        model.updateScheduleWithPerson(ALICE.getName(), LocalDateTime.now(), ScheduleState.PERSON);
        SelectFreeTimeCommand selectFreeTimeCommand = new SelectFreeTimeCommand(0, 13);
        assertThrows(CommandException.class, () -> selectFreeTimeCommand.execute(model),
                "Nothing to show, please show a group first");
    }

    @Test
    void executeFailureIdOutOfBound() throws CommandException, GroupNotFoundException {
        model.updateScheduleWithGroup(tempGroupName, dateTimeStub, ScheduleState.GROUP);
        SelectFreeTimeCommand selectFreeTimeCommand0 = new SelectFreeTimeCommand(0, 0);
        assertEquals(selectFreeTimeCommand0.execute(model).getFeedbackToUser(),
                "Invalid time slot ID: 0. Please enter a valid id as shown in the GUI.");

        SelectFreeTimeCommand selectFreeTimeCommand100 = new SelectFreeTimeCommand(0, 100);
        assertEquals(selectFreeTimeCommand100.execute(model).getFeedbackToUser(),
                "Invalid time slot ID: 100. Please enter a valid id as shown in the GUI.");
    }

    @Test
    void executeFailureNoValidPerson() throws GroupNotFoundException {
        model.updateScheduleWithGroup(tempGroupName, dateTimeStub, ScheduleState.GROUP);
        SelectFreeTimeCommand selectFreeTimeCommand = new SelectFreeTimeCommand(0, 1);
        assertThrows(CommandException.class, () -> selectFreeTimeCommand.execute(model),
                "We could not find a common location because:\n"
                        + "All location entered cannot be identified by TimeBook. Refer to  Supported Location "
                        + "table in User Guide to ge the supported locations.\n"
                        + "Source location: Orchard\n"
                        + "Invalid Source location: Orchard\n");
    }

    @Test
    void testEquals() throws GroupNotFoundException {
        model.updateScheduleWithGroup(tempGroupName, dateTimeStub, ScheduleState.GROUP);
        SelectFreeTimeCommand selectFreeTimeCommand = new SelectFreeTimeCommand(0, 8);
        assertTrue(selectFreeTimeCommand.equals(selectFreeTimeCommand));
    }
}
