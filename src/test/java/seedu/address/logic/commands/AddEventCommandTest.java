package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.ALICE;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.BENSON;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.ZACK;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelManager;
import seedu.address.model.group.exceptions.DuplicateGroupException;
import seedu.address.model.mapping.exceptions.DuplicateMappingException;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.EventClashException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.person.schedule.Event;
import seedu.address.testutil.modelutil.TypicalModel;
import seedu.address.testutil.scheduleutil.TypicalEvents;

class AddEventCommandTest {

    private ModelManager model;

    @BeforeEach
    void init() throws DuplicateMappingException, DuplicatePersonException, DuplicateGroupException {
        model = TypicalModel.generateTypicalModel();
    }

    @Test
    void constructor_allNull_throwsNullPointerException() {
        assertDoesNotThrow(() -> new AddEventCommand(null, null));
    }

    @Test
    void constructor_nullName_throwsNullPointerException() {
        assertDoesNotThrow(() -> new AddEventCommand(null, TypicalEvents.generateTypicalEvent1()));
    }

    @Test
    void execute_successPerson() throws CommandException {
        Event event = TypicalEvents.generateTypicalEvent1();

        CommandResult actualCommandResult =
                new AddEventCommand(ALICE.getName(), event).execute(model);

        CommandResult expectedCommandResult =
                new CommandResult(String.format(AddEventCommand.MESSAGE_SUCCESS, event.getEventName().trim()));

        assertEquals(expectedCommandResult, actualCommandResult);
    }

    @Test
    void execute_successUser() throws CommandException {
        Event event = TypicalEvents.generateTypicalEvent1();

        CommandResult actualCommandResult =
                new AddEventCommand(null, event).execute(model);

        CommandResult expectedCommandResult =
                new CommandResult(String.format(AddEventCommand.MESSAGE_SUCCESS, event.getEventName().trim()));

        assertEquals(expectedCommandResult, actualCommandResult);
    }

    @Test
    void execute_nullEvent() throws CommandException {
        CommandResult actualCommandResult =
                new AddEventCommand(ALICE.getName(), null).execute(model);

        CommandResult expectedCommandResult =
                new CommandResult(String.format(AddEventCommand.MESSAGE_FAILURE,
                        AddEventCommand.MESSAGE_WRONG_TIMINGS));

        assertTrue(expectedCommandResult.equals(actualCommandResult));
    }

    @Test
    void execute_personNotFound() throws CommandException {
        CommandResult actualCommandResult =
                new AddEventCommand(ZACK.getName(), TypicalEvents.generateTypicalEvent1()).execute(model);

        CommandResult expectedCommandResult =
                new CommandResult(String.format(AddEventCommand.MESSAGE_FAILURE,
                        AddEventCommand.MESSAGE_UNABLE_TO_FIND_PERSON));

        assertEquals(expectedCommandResult, actualCommandResult);
    }

    @Test
    void execute_eventClash() throws CommandException, EventClashException, PersonNotFoundException {
        model.addEvent(ALICE.getName(), TypicalEvents.generateTypicalEvent1());

        CommandResult actualCommandResult =
                new AddEventCommand(ALICE.getName(), TypicalEvents.generateTypicalEvent1()).execute(model);

        CommandResult expectedCommandResult =
                new CommandResult(String.format(AddEventCommand.MESSAGE_FAILURE,
                        AddEventCommand.MESSAGE_CLASH_IN_EVENTS));

        assertEquals(expectedCommandResult, actualCommandResult);
    }

    @Test
    void equals_null() {
        assertFalse(new AddEventCommand(ALICE.getName(),
                TypicalEvents.generateTypicalEvent1()).equals(null));
    }

    @Test
    void equals_otherCommand() {
        assertFalse(new AddEventCommand(ALICE.getName(),
                TypicalEvents.generateTypicalEvent1())
                .equals(new AddPersonCommand(ALICE)));
    }

    @Test
    void equals_differentEvent() {
        assertFalse(
                new AddEventCommand(ALICE.getName(), TypicalEvents.generateTypicalEvent1())
                        .equals(new AddEventCommand(ALICE.getName(), TypicalEvents.generateTypicalEvent2()))
        );
    }

    @Test
    void equals_differentName() {
        assertFalse(
                new AddEventCommand(ALICE.getName(), TypicalEvents.generateTypicalEvent1())
                        .equals(new AddEventCommand(BENSON.getName(), TypicalEvents.generateTypicalEvent1()))
        );
    }

    @Test
    void equals_differentNameAndEvent() {
        assertFalse(
                new AddEventCommand(ALICE.getName(), TypicalEvents.generateTypicalEvent1())
                        .equals(new AddEventCommand(BENSON.getName(), TypicalEvents.generateTypicalEvent2()))
        );
    }

    @Test
    void equals_sameNameAndEvent() {
        assertTrue(
                new AddEventCommand(ALICE.getName(), TypicalEvents.generateTypicalEvent1())
                        .equals(new AddEventCommand(ALICE.getName(), TypicalEvents.generateTypicalEvent1()))
        );
    }
}
