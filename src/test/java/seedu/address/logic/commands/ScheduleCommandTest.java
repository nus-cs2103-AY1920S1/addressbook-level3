package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.ZACK;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelManager;
import seedu.address.model.display.scheduledisplay.ScheduleState;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.testutil.modelutil.TypicalModel;

class ScheduleCommandTest {

    private ModelManager model;
    private ArrayList<Name> personNames;

    @BeforeEach
    void init() {
        model = TypicalModel.generateModelWithSchedules();
        ArrayList<Person> persons = model.getPersonList().getPersons();
        personNames = new ArrayList<>();
        for (Person person: persons) {
            personNames.add(person.getName());
        }
        model.updateScheduleWithPersons(persons,
                LocalDateTime.now(), ScheduleState.GROUP);
    }

    @Test
    void execute_success() throws CommandException {
        assertEquals(ScheduleState.GROUP, model.getState());

        CommandResult actualCommandResult =
                new ScheduleCommand(personNames).execute(model);

        CommandResult expectedCommandResult =
                new CommandResultBuilder(ScheduleCommand.MESSAGE_SUCCESS).build();

        assertTrue(expectedCommandResult.equals(actualCommandResult));

    }

    @Test
    void execute_failure() throws CommandException {
        assertEquals(ScheduleState.GROUP, model.getState());

        personNames.add(ZACK.getName());

        CommandResult actualCommandResult =
                new ScheduleCommand(personNames).execute(model);

        CommandResult expectedCommandResult =
                new CommandResultBuilder(String.format(
                        ScheduleCommand.MESSAGE_FAILURE, ZACK.getName().toString())).build();

        assertTrue(expectedCommandResult.equals(actualCommandResult));

    }

    @Test
    void testEquals_null() {
        assertFalse(new ScheduleCommand(personNames).equals(null));
    }

    @Test
    void testEquals_otherCommand() {
        assertFalse(new ScheduleCommand(personNames).equals(new AddPersonCommand(ZACK)));
    }

    @Test
    void testEquals_differentNames() {
        assertFalse(new ScheduleCommand(personNames).equals(
                new ScheduleCommand(new ArrayList<>(List.of(ZACK.getName())))));
    }

    @Test
    void testEquals_success() {
        assertTrue(new ScheduleCommand(personNames).equals(
                new ScheduleCommand(personNames)));
    }
}
