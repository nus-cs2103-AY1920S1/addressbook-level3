package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUP_NAME0;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUP_NAME1;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUP_NAME2;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.ALICE;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.BENSON;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.DANIEL;
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
import seedu.address.testutil.modelutil.TypicalModel;
import seedu.address.testutil.scheduleutil.TypicalEvents;

class DeleteFromGroupCommandTest {

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
    void execute_success() throws CommandException {

        CommandResult actualCommandResult =
                new DeleteFromGroupCommand(ALICE.getName(), GROUP_NAME1).execute(model);

        CommandResult expectedCommandResult =
                new CommandResult(String.format(DeleteFromGroupCommand.MESSAGE_SUCCESS,
                        ALICE.getName().toString(), GROUP_NAME1));

        assertEquals(actualCommandResult, expectedCommandResult);
    }

    @Test
    void execute_personNotFound() throws CommandException {

        CommandResult actualCommandResult =
                new DeleteFromGroupCommand(ZACK.getName(), GROUP_NAME1).execute(model);

        CommandResult expectedCommandResult =
                new CommandResult(String.format(DeleteFromGroupCommand.MESSAGE_FAILURE,
                        DeleteFromGroupCommand.MESSAGE_PERSON_NOT_FOUND));

        assertEquals(actualCommandResult, expectedCommandResult);
    }

    @Test
    void execute_groupNotFound() throws CommandException {

        CommandResult actualCommandResult =
                new DeleteFromGroupCommand(ALICE.getName(), GROUP_NAME0).execute(model);

        CommandResult expectedCommandResult =
                new CommandResult(String.format(DeleteFromGroupCommand.MESSAGE_FAILURE,
                        DeleteFromGroupCommand.MESSAGE_GROUP_NOT_FOUND));

        assertEquals(actualCommandResult, expectedCommandResult);
    }

    @Test
    void execute_mappingNotFound() throws CommandException {

        CommandResult actualCommandResult =
                new DeleteFromGroupCommand(DANIEL.getName(), GROUP_NAME1).execute(model);

        CommandResult expectedCommandResult =
                new CommandResult(String.format(DeleteFromGroupCommand.MESSAGE_FAILURE,
                        DeleteFromGroupCommand.MESSAGE_MAPPING_NOT_FOUND));

        assertEquals(actualCommandResult, expectedCommandResult);
    }

    @Test
    void equals_null() {
        assertFalse(new DeleteFromGroupCommand(ALICE.getName(), GROUP_NAME1).equals(null));
    }

    @Test
    void equals_otherCommand() {
        assertFalse(new DeleteFromGroupCommand(ALICE.getName(), GROUP_NAME1)
                .equals(new AddPersonCommand(ALICE)));
    }

    @Test
    void equals_differentName() {
        assertFalse(
                new DeleteFromGroupCommand(ALICE.getName(), GROUP_NAME1).equals(
                        new DeleteFromGroupCommand(BENSON.getName(), GROUP_NAME1)
                )
        );
    }

    @Test
    void equals_differentGroupName() {
        assertFalse(
                new DeleteFromGroupCommand(ALICE.getName(), GROUP_NAME1).equals(
                        new DeleteFromGroupCommand(ALICE.getName(), GROUP_NAME2)
                )
        );
    }


    @Test
    void equals() {
        assertTrue(
                new DeleteFromGroupCommand(ALICE.getName(), GROUP_NAME1).equals(
                        new DeleteFromGroupCommand(ALICE.getName(), GROUP_NAME1)
                )
        );
    }
}
