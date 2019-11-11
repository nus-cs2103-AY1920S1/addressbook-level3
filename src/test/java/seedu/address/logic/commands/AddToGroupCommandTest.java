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
import seedu.address.model.group.Group;
import seedu.address.model.group.exceptions.DuplicateGroupException;
import seedu.address.model.group.exceptions.GroupNotFoundException;
import seedu.address.model.mapping.PersonToGroupMapping;
import seedu.address.model.mapping.Role;
import seedu.address.model.mapping.exceptions.DuplicateMappingException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.modelutil.TypicalModel;


class AddToGroupCommandTest {

    private ModelManager model;

    @BeforeEach
    void init() throws DuplicateMappingException, DuplicatePersonException, DuplicateGroupException {
        model = TypicalModel.generateTypicalModel();
    }

    @Test
    void execute_success() throws CommandException, PersonNotFoundException, GroupNotFoundException {

        Person person = model.findPerson(DANIEL.getName());
        Group group = model.findGroup(GROUP_NAME1);

        CommandResult actualCommandResult =
                new AddToGroupCommand(person.getName(), group.getGroupName(), Role.emptyRole()).execute(model);

        CommandResult expectedCommandResult =
                new CommandResult(String.format(AddToGroupCommand.MESSAGE_SUCCESS,
                        person.getName().toString(), group.getGroupName().toString()));

        assertEquals(actualCommandResult, expectedCommandResult);
    }

    @Test
    void execute_personNotFound() throws CommandException {

        CommandResult actualCommandResult =
                new AddToGroupCommand(ZACK.getName(), GROUP_NAME1, Role.emptyRole()).execute(model);

        CommandResult expectedCommandResult =
                new CommandResult(String.format(AddToGroupCommand.MESSAGE_FAILURE,
                        AddToGroupCommand.MESSAGE_PERSON_NOT_FOUND));

        assertEquals(actualCommandResult, expectedCommandResult);
    }

    @Test
    void execute_groupNotFound() throws CommandException {

        CommandResult actualCommandResult =
                new AddToGroupCommand(ALICE.getName(), GROUP_NAME0, Role.emptyRole()).execute(model);

        CommandResult expectedCommandResult =
                new CommandResult(String.format(AddToGroupCommand.MESSAGE_FAILURE,
                        AddToGroupCommand.MESSAGE_GROUP_NOT_FOUND));

        assertEquals(actualCommandResult, expectedCommandResult);
    }

    @Test
    void execute_duplicate() throws CommandException {

        CommandResult actualCommandResult =
                new AddToGroupCommand(ALICE.getName(), GROUP_NAME1, Role.emptyRole()).execute(model);

        CommandResult expectedCommandResult =
                new CommandResult(String.format(AddToGroupCommand.MESSAGE_FAILURE,
                        AddToGroupCommand.MESSAGE_DUPLICATE));

        assertEquals(actualCommandResult, expectedCommandResult);
    }

    @Test
    void execute_alreadyInGroup() throws CommandException, GroupNotFoundException, PersonNotFoundException {

        PersonToGroupMapping mapping = new PersonToGroupMapping(
                model.findPerson(ALICE.getName()).getPersonId(),
                model.findGroup(GROUP_NAME1).getGroupId(),
                new Role("Role")
        );

        CommandResult actualCommandResult =
                new AddToGroupCommand(ALICE.getName(), GROUP_NAME1, new Role("Role")).execute(model);

        CommandResult expectedCommandResult =
                new CommandResult(String.format(AddToGroupCommand.MESSAGE_UPDATED_ROLE,
                        mapping.getRole().toString()));

        assertEquals(actualCommandResult, expectedCommandResult);
    }

    @Test
    void equals_null() {
        assertFalse(new AddToGroupCommand(ALICE.getName(), GROUP_NAME1, Role.emptyRole()).equals(null));
    }

    @Test
    void equals_otherCommand() {
        assertFalse(new AddToGroupCommand(ALICE.getName(), GROUP_NAME1, Role.emptyRole())
                .equals(new AddPersonCommand(ALICE)));
    }

    @Test
    void equals_differentName() {
        assertFalse(
                new AddToGroupCommand(ALICE.getName(), GROUP_NAME1, Role.emptyRole()).equals(
                        new AddToGroupCommand(BENSON.getName(), GROUP_NAME1, Role.emptyRole())
                )
        );
    }

    @Test
    void equals_differentGroup() {
        assertFalse(
                new AddToGroupCommand(ALICE.getName(), GROUP_NAME1, Role.emptyRole()).equals(
                        new AddToGroupCommand(ALICE.getName(), GROUP_NAME2, Role.emptyRole())
                )
        );
    }

    @Test
    void equals_differentRole() {
        assertFalse(
                new AddToGroupCommand(ALICE.getName(), GROUP_NAME1, Role.emptyRole()).equals(
                        new AddToGroupCommand(ALICE.getName(), GROUP_NAME1, new Role("Role"))
                )
        );
    }

    @Test
    void equals() {
        assertTrue(
                new AddToGroupCommand(ALICE.getName(), GROUP_NAME1, Role.emptyRole()).equals(
                        new AddToGroupCommand(ALICE.getName(), GROUP_NAME1, Role.emptyRole()))

        );
    }
}
