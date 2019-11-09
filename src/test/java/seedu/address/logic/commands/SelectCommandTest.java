package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUP_NAME1;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.ALICE;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.USER;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.ZACK;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelManager;
import seedu.address.model.display.exceptions.PersonTimeslotNotFoundException;
import seedu.address.model.display.scheduledisplay.ScheduleState;
import seedu.address.model.display.timeslots.PersonTimeslot;
import seedu.address.model.group.exceptions.GroupNotFoundException;
import seedu.address.model.person.Name;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.modelutil.TypicalModel;

class SelectCommandTest {

    private ModelManager model;

    @BeforeEach
    void init() {
        model = TypicalModel.generateModelWithSchedules();
    }

    @Test
    void executeOnGroup_personNotSpecified() throws CommandException, GroupNotFoundException {
        model.updateDisplayWithGroup(
                GROUP_NAME1, LocalDateTime.now(), ScheduleState.GROUP);

        assertEquals(ScheduleState.GROUP, model.getState());

        CommandResult actualCommandResult =
                new SelectCommand(0, Name.emptyName(), 1).execute(model);

        CommandResult expectedCommandResult =
                new CommandResultBuilder(
                        String.format(SelectCommand.MESSAGE_FAILURE,
                                SelectCommand.MESSAGE_PERSON_NOT_SPECIFIED)).build();

        assertTrue(expectedCommandResult.equals(actualCommandResult));

    }

    @Test
    void executeOnGroup_personNotFound() throws CommandException, GroupNotFoundException {
        model.updateDisplayWithGroup(
                GROUP_NAME1, LocalDateTime.now(), ScheduleState.GROUP);

        assertEquals(ScheduleState.GROUP, model.getState());

        CommandResult actualCommandResult =
                new SelectCommand(0, ZACK.getName(), 1).execute(model);

        CommandResult expectedCommandResult =
                new CommandResultBuilder(
                        String.format(SelectCommand.MESSAGE_FAILURE,
                                SelectCommand.MESSAGE_PERSON_NOT_FOUND)).build();

        assertTrue(expectedCommandResult.equals(actualCommandResult));

    }

    @Test
    void executeOnGroup_timeslotNotFound() throws CommandException, GroupNotFoundException {
        model.updateDisplayWithGroup(
                GROUP_NAME1, LocalDateTime.now(), ScheduleState.GROUP);

        assertEquals(ScheduleState.GROUP, model.getState());

        CommandResult actualCommandResult =
                new SelectCommand(0, ALICE.getName(), 123).execute(model);

        CommandResult expectedCommandResult =
                new CommandResultBuilder(
                        String.format(SelectCommand.MESSAGE_FAILURE,
                                SelectCommand.MESSAGE_TIMESLOT_NOT_FOUND)).build();

        assertTrue(expectedCommandResult.equals(actualCommandResult));
    }

    @Test
    void executeOnGroup_success()
            throws CommandException, PersonTimeslotNotFoundException, PersonNotFoundException, GroupNotFoundException {

        model.updateDisplayWithGroup(
                GROUP_NAME1, LocalDateTime.now(), ScheduleState.GROUP);

        assertEquals(ScheduleState.GROUP, model.getState());

        CommandResult actualCommandResult =
                new SelectCommand(0, ALICE.getName(), 1).execute(model);

        PersonTimeslot personTimeslot = model.getScheduleDisplay()
                .getPersonTimeslot(ALICE.getName(), 0, 1);

        CommandResult expectedCommandResult =
                new CommandResultBuilder(SelectCommand.MESSAGE_SUCCESS)
                        .setSelect().setPersonTimeslotData(personTimeslot).build();

        assertTrue(expectedCommandResult.equals(actualCommandResult));
    }

    @Test
    void executeOnPerson_success()
            throws CommandException, PersonTimeslotNotFoundException, PersonNotFoundException {

        model.updateDisplayWithPerson(
                ALICE.getName(), LocalDateTime.now(), ScheduleState.PERSON);

        assertEquals(ScheduleState.PERSON, model.getState());

        CommandResult actualCommandResult =
                new SelectCommand(0, Name.emptyName(), 1).execute(model);

        PersonTimeslot personTimeslot = model.getScheduleDisplay()
                .getPersonTimeslot(ALICE.getName(), 0, 1);

        CommandResult expectedCommandResult =
                new CommandResultBuilder(SelectCommand.MESSAGE_SUCCESS)
                        .setSelect().setPersonTimeslotData(personTimeslot).build();

        assertTrue(expectedCommandResult.equals(actualCommandResult));
    }

    @Test
    void executeOnPerson_timeslotNotFound()
            throws CommandException, PersonTimeslotNotFoundException, PersonNotFoundException {

        model.updateDisplayWithPerson(
                ALICE.getName(), LocalDateTime.now(), ScheduleState.PERSON);

        assertEquals(ScheduleState.PERSON, model.getState());

        CommandResult actualCommandResult =
                new SelectCommand(0, Name.emptyName(), 123).execute(model);


        CommandResult expectedCommandResult =
                new CommandResultBuilder(
                        String.format(SelectCommand.MESSAGE_FAILURE,
                                SelectCommand.MESSAGE_TIMESLOT_NOT_FOUND)).build();

        assertTrue(expectedCommandResult.equals(actualCommandResult));
    }

    @Test
    void executeOnHome_success()
            throws CommandException, PersonTimeslotNotFoundException, PersonNotFoundException {

        model.updateDisplayWithUser(LocalDateTime.now(), ScheduleState.HOME);

        assertEquals(ScheduleState.HOME, model.getState());

        CommandResult actualCommandResult =
                new SelectCommand(0, Name.emptyName(), 1).execute(model);

        PersonTimeslot personTimeslot = model.getScheduleDisplay()
                .getPersonTimeslotForToday(USER.getName(), 1);

        CommandResult expectedCommandResult =
                new CommandResultBuilder(SelectCommand.MESSAGE_SUCCESS)
                        .setSelect().setPersonTimeslotData(personTimeslot).build();

        assertTrue(expectedCommandResult.equals(actualCommandResult));
    }

    @Test
    void executeOnHome_timeslotNotFound()
            throws CommandException, PersonTimeslotNotFoundException, PersonNotFoundException {

        model.updateDisplayWithUser(LocalDateTime.now(), ScheduleState.HOME);

        assertEquals(ScheduleState.HOME, model.getState());

        CommandResult actualCommandResult =
                new SelectCommand(0, Name.emptyName(), 1123).execute(model);

        CommandResult expectedCommandResult =
                new CommandResultBuilder(
                        String.format(SelectCommand.MESSAGE_FAILURE,
                                SelectCommand.MESSAGE_TIMESLOT_NOT_FOUND)).build();

        assertTrue(expectedCommandResult.equals(actualCommandResult));
    }


    @Test
    void testEquals_null() {
        assertFalse(new SelectCommand(0, ALICE.getName(), 1).equals(null));
    }

    @Test
    void testEquals_differentCommand() {
        assertFalse(new SelectCommand(0, ALICE.getName(), 1)
                .equals(new AddPersonCommand(ZACK)));
    }

    @Test
    void testEquals_differentWeek() {
        assertFalse(
                new SelectCommand(0, ALICE.getName(), 1).equals(
                        new SelectCommand(1, ALICE.getName(), 1)));
    }

    @Test
    void testEquals_differentName() {
        assertFalse(
                new SelectCommand(0, ALICE.getName(), 1).equals(
                        new SelectCommand(0, ZACK.getName(), 1)));
    }

    @Test
    void testEquals_differentId() {
        assertFalse(
                new SelectCommand(0, ALICE.getName(), 1).equals(
                        new SelectCommand(0, ALICE.getName(), 2)));
    }

    @Test
    void testEquals_success() {
        assertTrue(
                new SelectCommand(0, ALICE.getName(), 1).equals(
                        new SelectCommand(0, ALICE.getName(), 1)));
    }
}
