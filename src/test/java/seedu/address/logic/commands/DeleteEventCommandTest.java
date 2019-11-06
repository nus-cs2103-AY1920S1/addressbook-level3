package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.ALICE;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.BENSON;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.ZACK;
import static seedu.address.testutil.scheduleutil.TypicalEvents.EVENT_NAME1;
import static seedu.address.testutil.scheduleutil.TypicalEvents.EVENT_NAME2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelManager;
import seedu.address.model.group.exceptions.DuplicateGroupException;
import seedu.address.model.mapping.exceptions.DuplicateMappingException;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.EventClashException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.modelutil.TypicalModel;
import seedu.address.testutil.scheduleutil.TypicalEvents;

class DeleteEventCommandTest {

    private ModelManager model;

    @BeforeEach
    void init() throws DuplicateMappingException,
            DuplicatePersonException,
            DuplicateGroupException,
            EventClashException,
            PersonNotFoundException {

        model = TypicalModel.generateTypicalModel();
        model.addEvent(ALICE.getName(), TypicalEvents.generateTypicalEvent1());
        model.addEvent(TypicalEvents.generateTypicalEvent1());
    }

    @Test
    void execute_successPerson() throws CommandException {

        CommandResult actualCommandResult =
                new DeleteEventCommand(ALICE.getName(), EVENT_NAME1).execute(model);

        CommandResult expectedCommandResult =
                new CommandResult(String.format(DeleteEventCommand.MESSAGE_SUCCESS, EVENT_NAME1));

        assertEquals(actualCommandResult, expectedCommandResult);
    }

    @Test
    void execute_successUser() throws CommandException {

        CommandResult actualCommandResult =
                new DeleteEventCommand(null, EVENT_NAME1).execute(model);

        CommandResult expectedCommandResult =
                new CommandResult(String.format(DeleteEventCommand.MESSAGE_SUCCESS, EVENT_NAME1));

        assertEquals(actualCommandResult, expectedCommandResult);
    }

    @Test
    void execute_eventNotFound() throws CommandException {

        CommandResult actualCommandResult =
                new DeleteEventCommand(null, EVENT_NAME2).execute(model);

        CommandResult expectedCommandResult =
                new CommandResult(String.format(DeleteEventCommand.MESSAGE_FAILURE,
                        DeleteEventCommand.MESSAGE_EVENT_NOT_FOUND));

        assertEquals(actualCommandResult, expectedCommandResult);
    }

    @Test
    void execute_personNotFound() throws CommandException {

        CommandResult actualCommandResult =
                new DeleteEventCommand(ZACK.getName(), EVENT_NAME1).execute(model);

        CommandResult expectedCommandResult =
                new CommandResult(String.format(DeleteEventCommand.MESSAGE_FAILURE,
                        DeleteEventCommand.MESSAGE_PERSON_NOT_FOUND));

        assertEquals(actualCommandResult, expectedCommandResult);
    }

    @Test
    void equals_null() {
        assertFalse(new DeleteEventCommand(ALICE.getName(), EVENT_NAME1).equals(null));
    }

    @Test
    void equals_otherCommand() {
        assertFalse(new DeleteEventCommand(ALICE.getName(), EVENT_NAME1)
                .equals(new AddPersonCommand(ALICE)));
    }

    @Test
    void equals_differentName() {
        assertFalse(
                new DeleteEventCommand(ALICE.getName(), EVENT_NAME1).equals(
                        new DeleteEventCommand(BENSON.getName(), EVENT_NAME1)
                )
        );
    }

    @Test
    void equals_differentEventName() {
        assertFalse(
                new DeleteEventCommand(ALICE.getName(), EVENT_NAME1).equals(
                        new DeleteEventCommand(ALICE.getName(), EVENT_NAME2)
                )
        );
    }


    @Test
    void equals() {
        assertTrue(
                new DeleteEventCommand(ALICE.getName(), EVENT_NAME1).equals(
                        new DeleteEventCommand(ALICE.getName(), EVENT_NAME1)
                )
        );
    }
}
